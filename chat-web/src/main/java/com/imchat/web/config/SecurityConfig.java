package com.imchat.web.config;

import com.alibaba.fastjson.JSON;
import com.imchat.web.auth.UserDetailsServiceImpl;
import com.imchat.web.auth.WhaleAuthenticationProvider;
import com.imchat.web.auth.jwt.JwtAuthenticationConfig;
import com.imchat.web.auth.jwt.JwtTokenAuthenticationFilter;
import com.imchat.web.auth.jwt.JwtUsernamePasswordAuthenticationFilter;
import com.imchat.web.controllers.bean.models.response.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * 网关安全配置，用于过滤访问请求
 * @author Administrator
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtAuthenticationConfig config;

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public WhaleAuthenticationProvider authenticationProvider() {
        WhaleAuthenticationProvider result = new WhaleAuthenticationProvider();
        result.setUserDetailsService(userDetailsService);
        result.setPasswordEncoder(new BCryptPasswordEncoder(4));

        return result;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and().csrf().disable().logout().disable().formLogin().disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling()
                .authenticationEntryPoint((req, rsp, e) -> {
                    rsp.getWriter().println(JSON.toJSONString(ResultModel.unauthorized("token invalidation")));
                    rsp.getWriter().flush();
                })
                .and()
                .addFilterAfter(new JwtTokenAuthenticationFilter(config),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new JwtUsernamePasswordAuthenticationFilter(config, authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests().antMatchers(config.getUrl()).permitAll()
                // 放行swagger
                .antMatchers("/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/v3/**", "/api/**", "/doc.html").permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest().authenticated();
    }

    @Autowired
    public void setConfig(JwtAuthenticationConfig config) {
        this.config = config;
    }
}
