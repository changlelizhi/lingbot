package space.changle.lingbot.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import space.changle.lingbot.auth.UserRole;
import space.changle.lingbot.common.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 长乐
 * @version 1.0.0
 * @date 2026/5/19
 * @time 22:20
 * @description
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {

        String token = extractToken(request);
        if (!StringUtils.hasText(token)){
            filterChain.doFilter(request, response);
            return;
        }
        try {
            Claims claims = jwtUtil.parseToken(token);
            String userId = claims.getSubject();
            String loginType = claims.get("loginType", String.class);
            String role = claims.get("role", String.class);
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            if (StringUtils.hasText(loginType)){
                authorities.add(new SimpleGrantedAuthority(loginType));
            }
            if (StringUtils.hasText(role)) {
                UserRole.valueOf(role).authorities()
                        .forEach(a -> authorities.add(new SimpleGrantedAuthority(a)));
            }

            Authentication authentication=new UsernamePasswordAuthenticationToken(userId, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }catch (ExpiredJwtException e){
            SecurityContextHolder.clearContext();
            request.setAttribute("TOKEN_ERROR", Result.TOKEN_EXPIRED);
        }catch (JwtException e){
            SecurityContextHolder.clearContext();
            request.setAttribute("TOKEN_ERROR", Result.TOKEN_INVALID);
        }
        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        // 1. TMA: Authorization header
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(header) && header.startsWith(BEARER_PREFIX)) {
            return header.substring(BEARER_PREFIX.length());
        }
        // 2. WEB: Cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName()) && StringUtils.hasText(cookie.getValue())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
