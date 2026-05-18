package space.changle.lingbot.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

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
public class SecurityConfig {

/*    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/tmaLogin", "/api/auth/webLogin","/api/auth/register","/api/auth/getme").permitAll()
                        .requestMatchers("/api/tma/**").hasAuthority(LoginType.TMA.authority())
                        .requestMatchers("/api/web/**").hasAuthority(LoginType.WEB.authority())
                        .requestMatchers("api/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                ) .formLogin(from->{})
               *//* .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint())
                        .accessDeniedHandler(accessDeniedHandler())
                )*//*
            *//*    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)*//*;

        return http.build();
        
    }*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/user/**", "/api/auth/webLogin","/api/auth/register","/api/auth/getme").permitAll()
                        .anyRequest().authenticated()
                ) .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);
               /* .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint())
                        .accessDeniedHandler(accessDeniedHandler())
                )*/
        /*    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)*/;

        return http.build();

    }

    

}
