package com.team5.sparta_clone_5.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team5.sparta_clone_5.model.RefreshToken;
import com.team5.sparta_clone_5.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;


/**
 * 필터에서 토큰 인증 로직
 */

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final RefreshTokenRepository refreshTokenRepository;

    private boolean fin = false;

    @Override                       //서블릿에서 만들어놓은 객체들(요청을 받아올 때 미리 response 객체 생성함)
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        //request 의 헤더를 가져와서 키값으로 accessToken 꺼내오기
        //재발급 할 때 accessToken 이 없다면 else if 만 타게 되면서??? 아니 access 토큰 유효하지 않을 때 재발급 해줘야하는거아님?
        String accessToken = jwtUtil.getTokenFromHeader(request, "Access");
        String refreshToken = jwtUtil.getTokenFromHeader(request, "Refresh");

        //엑세스 토큰 가지고 있다면
        if (accessToken != null) {

            if (jwtUtil.validateAccessToken(accessToken) == 1 || jwtUtil.validateAccessToken(accessToken) == 2) {
                //규범님 방식 -> 숫자로 리턴받아서 나누어 처리하는 방법도 있음

                if (refreshToken != null) {

                    if (!jwtUtil.validateRefreshToken(refreshToken)) {
//                        jwtExceptionHandler(response, "refreshToken 이 유효하지 않습니다. (refreshToken Not Valid)", HttpStatus.BAD_REQUEST);
                        //로그 찍고 Authentication 날려주면 되겠네.
                        throw new AuthenticationException("토큰유효X");
//                        return;  ->  doFilter 나간다.
                    }// else 로 해도되지않나? 일단 생각해보자
                    if(!fin) {
                        //리프레쉬 토큰이 유효하다면

                        //리프레쉬 토큰 폐기 및 재발급 (db에 있는 refreshtoken 교체하기)
                        String email = jwtUtil.getEmailFromToken(refreshToken);
                        String newRefreshToken = replaceRefreshToken(email);

                        response.addHeader(JwtUtil.ACCESS, jwtUtil.createAccessToken(jwtUtil.getEmailFromToken(refreshToken)));
                        response.addHeader(JwtUtil.REFRESH, newRefreshToken);
                        setAuthentication(jwtUtil.getEmailFromToken(refreshToken));

                        fin = true;
                    }
                } else {
//                    jwtExceptionHandler(response, "토큰이 존재하지 않습니다. (No Token)", HttpStatus.BAD_REQUEST);
                    throw new AuthenticationException("access토큰 잘못됨");
                }
            }
            if(!fin) setAuthentication(jwtUtil.getEmailFromToken(accessToken));

            fin = false;
        }

        //다음 필터로 넘겨주기
        filterChain.doFilter(request, response);
    }

    //토큰이 검증 되었을 때 인증 객체 만들어주기
    public void setAuthentication(String email) {
        Authentication authentication = jwtUtil.createAuthentication(email);
        //security 가 컨텍스트에 접근해서 인증 객체가 있는지 없는지 확인하기 떄문에 다음 필터가 돌 때 시큐리티가 인증객체로 인지
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Transactional
    public String replaceRefreshToken(String email) {
        String newRefreshToken = jwtUtil.createRefreshToken(email);
        RefreshToken oldRefreshToken = refreshTokenRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("토큰 폐기 중 해당회원 못찾음"));
        refreshTokenRepository.save(oldRefreshToken.updateRefreshToken(newRefreshToken));
        return newRefreshToken;
    }

    public void jwtExceptionHandler(HttpServletResponse response, String msg, HttpStatus status) {
        response.setStatus(status.value());
        response.setContentType("application/json");
        try {
            //responseDto 에 넣어서 보내기
//            String json = new ObjectMapper().writeValueAsString(ResponseDto.fail(response.getStatus() + "", msg));
            String json = new ObjectMapper().writeValueAsString("");
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}

