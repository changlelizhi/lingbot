package space.changle.lingbot.auth.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationManagers;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import space.changle.lingbot.auth.LoginType;
import space.changle.lingbot.auth.UserRole;
import space.changle.lingbot.auth.jwt.JwtAuthenticationFilter;
import space.changle.lingbot.common.ApiResponse;
import space.changle.lingbot.common.Result;
import space.changle.lingbot.common.util.JsonUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author 长乐
 * @version 1.0.0
 * @date 2026/5/13
 * @time 20:31
 * @description security配置类
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/checkUser", "/api/auth/tmaLogin", "/api/auth/webLogin").permitAll()
                        .requestMatchers("/api/tma/**").hasAuthority(LoginType.TMA.getAuthority())
                        .requestMatchers("/api/web/**").hasAuthority(LoginType.WEB.getAuthority())
                        .requestMatchers("/api/ban/**").access(AuthorizationManagers.allOf(
                                AuthorityAuthorizationManager.hasAuthority(LoginType.TMA.getAuthority()),
                                AuthorityAuthorizationManager.hasAnyRole(UserRole.BAN.name(), UserRole.ADMIN.name())))
                        .requestMatchers("/api/review/**").access(AuthorizationManagers.allOf(
                                AuthorityAuthorizationManager.hasAuthority(LoginType.TMA.getAuthority()),
                                AuthorityAuthorizationManager.hasAnyRole(UserRole.REVIEWER.name(), UserRole.ADMIN.name())))
                        .requestMatchers("/api/admin/**").access(AuthorizationManagers.allOf(
                                AuthorityAuthorizationManager.hasAuthority(LoginType.WEB.getAuthority()),
                                AuthorityAuthorizationManager.hasRole(UserRole.ADMIN.name())))
                        .anyRequest().authenticated())
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint())
                        .accessDeniedHandler(accessDeniedHandler()))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * 认证失败
     *
     * @return
     */
    private AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.getWriter().write(JsonUtil.toJson(ApiResponse.fail(Result.TOKEN_MISSING)));
        };


    }

    /**
     * 访问权限不足
     *
     * @return
     */
    private AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.getWriter().write(JsonUtil.toJson(ApiResponse.fail(Result.FORBIDDEN)));
        };

    }

}
