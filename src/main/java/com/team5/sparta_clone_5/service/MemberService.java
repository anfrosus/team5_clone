package com.team5.sparta_clone_5.service;

import com.team5.sparta_clone_5.config.UserDetailsImpl;
import com.team5.sparta_clone_5.dto.request.LoginRequestDto;
import com.team5.sparta_clone_5.dto.request.SignupRequestDto;
import com.team5.sparta_clone_5.dto.response.MemberResponseDto;
import com.team5.sparta_clone_5.exception.CustomException;
import com.team5.sparta_clone_5.exception.ErrorCode;
import com.team5.sparta_clone_5.jwt.JwtUtil;
import com.team5.sparta_clone_5.jwt.TokenDto;
import com.team5.sparta_clone_5.model.Member;
import com.team5.sparta_clone_5.model.RefreshToken;
import com.team5.sparta_clone_5.repository.MemberRepository;
import com.team5.sparta_clone_5.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    //회원가입
    @Transactional
    public ResponseEntity<MemberResponseDto> createMember(SignupRequestDto signupRequestDto) {
        //중복확인
        if(memberRepository.existsByEmail(signupRequestDto.getMemberEmail())) {
            throw new CustomException("email", ErrorCode.AlreadyExists);
        }else if (memberRepository.existsByName(signupRequestDto.getMemberName())) {
            throw new CustomException("Name", ErrorCode.AlreadyExists);
        }else if (!signupRequestDto.getMemberPw().equals(signupRequestDto.getPwCheck())) {
            throw new CustomException("비밀번호 확인", ErrorCode.NotMatch);
        }
        //저장
        Member member = Member.builder()
                .email(signupRequestDto.getMemberEmail())
                .password(passwordEncoder.encode(signupRequestDto.getMemberPw()))
                .name(signupRequestDto.getMemberName())
                .build();

        memberRepository.save(member);

        return ResponseEntity.ok(
                MemberResponseDto.builder()
                        .memberId(member.getId())
                        .msg("회원가입이 완료되었습니다.")
                        .memberName(member.getName())
                        .build()
        );
    }

    //마이페이지가 같이 생성되야하면 readOnly 는 빼야할 듯
    @Transactional(readOnly = true)
    public ResponseEntity<MemberResponseDto> loginMember(LoginRequestDto loginRequestDto, HttpServletResponse response) {

        Member member = memberRepository.findByEmail(loginRequestDto.getMemberEmail()).orElseThrow(
                () -> new CustomException("회원", ErrorCode.NotFound)
        );
        //패스워드 인코더의 매치스함수 이용해서 비밀번호 대조 (boolean 리턴)
        if (!passwordEncoder.matches(loginRequestDto.getMemberPw(), member.getPassword())) {
            throw new CustomException("비밀번호", ErrorCode.NotMatch);
        }

        //인증 된 사람에게 토큰 발급하기
        TokenDto tokenDto = jwtUtil.issueToken(loginRequestDto.getMemberEmail());

        //기존에 리프레쉬 토큰을 가지고 있었다면 갈아끼워주고
        Optional<RefreshToken> savedRefreshToken = refreshTokenRepository.findByEmail(loginRequestDto.getMemberEmail());
        if (savedRefreshToken.isPresent()) {
            refreshTokenRepository.save(savedRefreshToken.get().updateRefreshToken(tokenDto.getRefreshToken()));
        } else {
            //새로 발급된 토큰과 이메일을 담아서 저장하자.
            RefreshToken newRefreshToken = new RefreshToken(tokenDto.getRefreshToken(), loginRequestDto.getMemberEmail());
            refreshTokenRepository.save(newRefreshToken);
        }

        //response 내장함수이용해서 헤더에 각각 토큰 키값과 함께 넣어주기
        setHeader(response, tokenDto);

        return ResponseEntity.ok(
                MemberResponseDto.builder()
                        .memberId(member.getId())
                        .msg("로그인 완료")
                        .memberName(member.getName())
                        .build()
        );
    }

    @Transactional
    public ResponseEntity<String> logout(Member currentMember) {
        refreshTokenRepository.deleteByEmail(currentMember.getEmail());
        return ResponseEntity.ok("로그아웃 완료");
    }

    private static void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtUtil.ACCESS, tokenDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH, tokenDto.getRefreshToken());
    }
}
