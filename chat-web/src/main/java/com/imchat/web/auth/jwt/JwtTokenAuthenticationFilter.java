package com.imchat.web.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 认证,WEB过滤器配置
 *
 * @author Administrator
 */
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private JwtAuthenticationConfig config;

    public JwtTokenAuthenticationFilter(JwtAuthenticationConfig config) {
        this.config = config;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = req.getHeader(config.getHeader());
        if (token != null && token.startsWith(config.getPrefix())) {
            token = token.substring(config.getPrefix().length());
            try {
                Claims claims = Jwts.parser().setSigningKey(config.getSecret().getBytes())
                        .parseClaimsJws(token).getBody();
                String userId = claims.getSubject();
                @SuppressWarnings("unchecked")
                List<String> authorities = claims.get("authorities", List.class);
                if (userId != null) {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userId,
                            null,
                            authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (Exception ignore) {
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(req, res);
    }
}
