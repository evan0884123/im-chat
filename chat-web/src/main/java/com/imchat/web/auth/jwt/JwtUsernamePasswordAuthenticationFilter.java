package com.imchat.web.auth.jwt;

import com.alibaba.fastjson.JSON;
import com.imchat.web.controllers.bean.models.response.ResultModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Description: 默认往 /login post json '{ username, password }'
 *
 * @author Administrator
 */
public class JwtUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final JwtAuthenticationConfig config;

    public JwtUsernamePasswordAuthenticationFilter(JwtAuthenticationConfig config,
                                                   AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(config.getUrl(), "POST"));
        setAuthenticationManager(authManager);
        this.config = config;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException,
            IOException {
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(req.getParameter("username"), req
                        .getParameter("password"), Collections.emptyList()));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        response.getWriter().println(JSON.toJSONString(ResultModel.failed(failed.getMessage())));
        response.getWriter().flush();
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res,
                                            FilterChain chain, Authentication auth) throws IOException {
        Instant now = Instant.now();

        String token = Jwts
                .builder()
                .setSubject(auth.getName())
                .claim(
                        "authorities",
                        auth.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList())).setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(config.getExpiration())))
                .signWith(SignatureAlgorithm.HS256, config.getSecret().getBytes()).compact();
        res.addHeader(config.getHeader(), config.getPrefix() + token);

        res.getWriter().write(JSON.toJSONString(ResultModel.succeed(config.getPrefix() + token)));
        res.getWriter().flush();
    }

}
