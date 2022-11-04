package com.imchat.web.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * @author Administrator
 */
@Slf4j
@Component
public class PrintFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        printing();
        chain.doFilter(request, response);
    }

    protected void printing() {
        HttpServletRequest requ = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        Map<String, String[]> parameterMap = requ.getParameterMap();
        StringBuilder sb = new StringBuilder();
        sb.append("--本次请求");
        sb.append(", [URL]: ").append(requ.getRequestURI());
        sb.append(", [参数]: ").append(JSON.toJSONString(parameterMap));
        log.info(sb.toString());
    }
}
