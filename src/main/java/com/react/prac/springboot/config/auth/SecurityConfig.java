package com.react.prac.springboot.config.auth;

import com.react.prac.springboot.domain.user.Role;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // private final CustomOAuth2UserService customOAuth2UserService;

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CorsConfig corsConfig;
//    private final TokenService tokenService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // rest api 사용시 disable
                .headers().frameOptions().disable()
                .and()
                .cors()
                .configurationSource(corsConfig.corsConfigurationSource())
                .and()
                // .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .authorizeHttpRequests()
                .requestMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                // .anyRequest().authenticated()
                .anyRequest().permitAll()
                //.requestMatchers("/api/v1/**").hasRole(Role.USER.name())
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .oauth2Login()
                .successHandler(new MyAuthenticationSuccessHandler())
                //.defaultSuccessUrl("/oauth/loginInfo", true)
                .userInfoEndpoint().userService(customOAuth2UserService);

        return http.build();
    }

    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .requestMatchers("/token/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthFilter(tokenService),
                        UsernamePasswordAuthenticationFilter.class)
                .oauth2Login().loginPage("/token/expired")
                .successHandler(successHandler)
                .userInfoEndpoint().userService(oAuth2UserService);

        http.addFilterBefore(new JwtAuthFilter(tokenService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }*/
}