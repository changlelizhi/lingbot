package space.changle.lingbot.auth.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import space.changle.lingbot.auth.LoginType;
import space.changle.lingbot.auth.UserRole;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author 长乐
 * @version 1.0.0
 * @date 2026/5/19
 * @time 10:58
 * @description
 */
@Slf4j
@Component
public class JwtUtil {

    private final SecretKey secretKey;

    private final long tmaExpireMs;

    private final long webExpireMs;

    public JwtUtil(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.tma-expire-ms}") long tmaExpireMs,
                   @Value("${jwt.web-expire-ms}") long webExpireMs) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.tmaExpireMs = tmaExpireMs;
        this.webExpireMs = webExpireMs;
    }

    public String createToken(String userId, LoginType loginType, UserRole userRole) {
        long expireMs = loginType == LoginType.WEB ? webExpireMs : tmaExpireMs;
        Date now = new Date();
        return Jwts.builder()
                .subject(userId)
                .claim("loginType", loginType.name())
                .claim("role", userRole.name())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expireMs))
                .signWith(secretKey)
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        }catch (JwtException e){
            log.error("token验证失败：{}",e.getMessage());
            return false;
        }
    }
}