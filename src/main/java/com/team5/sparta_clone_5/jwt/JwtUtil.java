package com.team5.sparta_clone_5.jwt;

import com.team5.sparta_clone_5.config.UserDetailsServiceImpl;
import com.team5.sparta_clone_5.model.RefreshToken;
import com.team5.sparta_clone_5.repository.RefreshTokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserDetailsServiceImpl userDetailsService;

    public static final String ACCESS = "Authorization";
    public static final String REFRESH = "Refresh";

    //private static final String AUTHORITIES_KEY = "auth";
    //private static final String BEARER_PREFIX = "bearer";
    private static final long AT_EXPIRE_TIME = 2 * 60 * 60 * 1000L;
    private static final long RT_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L;




    /*토큰 생성*/
    //토큰 만들 때 필요한 것들
    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;        //security 객체
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    //빈 생성 시 키겍체 초기화 (Security 의 Key 객체에 시크릿키 디코딩 해서 넣었다
    @PostConstruct
    public void initKey() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }


    /* header 에서 토큰을 가져오는 기능*/
    public String getTokenFromHeader(HttpServletRequest request, String type) {
        return type.equals("Access") ? request.getHeader(ACCESS) : request.getHeader(REFRESH);
    }

    /* 토큰 담기 */
    public TokenDto issueToken(String email) {
        return new TokenDto(createAccessToken(email), createRefreshToken(email));
    }

    /*토큰 생성*/
    public String createAccessToken(String email) {
        Date date = new Date();
        //토큰 안에 들어가는 정보
        String accessToken = Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(date.getTime() + AT_EXPIRE_TIME))
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm)
                .compact();

        return accessToken;

    }
    //필터 살펴보고 email 안넣는 방법 가능할듯 !?
    public String createRefreshToken(String email) {
        Date date = new Date();
        String refreshToken = Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(date.getTime() + RT_EXPIRE_TIME))
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm)
                .compact();

        return refreshToken;
    }



    /*access 토큰 검증*/
    public Boolean validateAccessToken(String accessToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken);  //parseClaimsJws 들어가면 예외 볼수있음
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
            // 이 때만 재발급 해줘야 겠네 !
            // boolean말고 숫자로 -> 앞단에서
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    /*refresh 토큰 검증*/
    public Boolean validateRefreshToken(String refreshToken) {
        // 1차 토큰 검증
        if(!validateAccessToken(refreshToken)) return false;

        // DB에 저장한 토큰 비교
        Optional<RefreshToken> savedRefreshToken = refreshTokenRepository.findByEmail(getEmailFromToken(refreshToken));

        boolean valid = savedRefreshToken.isPresent() && refreshToken.equals(savedRefreshToken.get().getRefreshToken());
        return valid;

    }

    /*인증 객체 생성*/
    public Authentication createAuthentication(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        //원래 security 에서 생성하는 인증 객체
    }


    /*토큰에서 email 가져오기*/
    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
        //앞에서 검증이 끝났기 때문에 따로 예외처리 X
    }


}
