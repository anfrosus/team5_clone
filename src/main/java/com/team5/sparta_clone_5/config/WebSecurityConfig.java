package com.team5.sparta_clone_5.config;

import com.team5.sparta_clone_5.exception.AccessDeniedHandlerException;
import com.team5.sparta_clone_5.exception.AuthenticationEntryPointException;
import com.team5.sparta_clone_5.jwt.JwtAuthFilter;
import com.team5.sparta_clone_5.jwt.JwtUtil;
import com.team5.sparta_clone_5.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;

    private final RefreshTokenRepository refreshTokenRepository;


    private final AuthenticationEntryPointException authenticationEntryPointException;
    private final AccessDeniedHandlerException accessDeniedHandlerException;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer ignoringCustomizer() {
        return (web) -> web.ignoring().antMatchers("/h2-console/**");
    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource(){
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//
////        corsConfiguration.setAllowedOrigins(Arrays.asList());
//        corsConfiguration.addAllowedOrigin("http//프론트origin");
//        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.setAllowedMethods(Arrays.asList("HEAD","GET", "PUT", "POST", "DELETE"));
//        corsConfiguration.addAllowedMethod("*");
//        corsConfiguration.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfiguration);
//        return source;
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                httpBasic().disable()       //있어야하나? auth기반 로그인창 띄우는 것이라던데. .
//                .cors().configurationSource(corsConfigurationSource());

        http.csrf().disable();

        http
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPointException)
                .accessDeniedHandler(accessDeniedHandlerException);

        //세션 사용 X
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers("/auth/**").permitAll()

                .anyRequest().authenticated()

                //security 인증 필터 앞에 jwt 필터 추가
                .and().addFilterBefore(new JwtAuthFilter(jwtUtil, refreshTokenRepository), UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }
}
