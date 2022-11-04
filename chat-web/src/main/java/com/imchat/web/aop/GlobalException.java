package com.imchat.web.aop;

import com.imchat.common.utils.IPUtil;
import com.imchat.web.auth.jwt.JwtAuthenticationConfig;
import com.imchat.web.common.RequestContext;
import com.imchat.web.controllers.bean.models.response.ResultModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * @author Administrator
 */
@Slf4j
@RestControllerAdvice
public class GlobalException {

    private RequestContext context;

    private JwtAuthenticationConfig config;

    @ExceptionHandler(value = Throwable.class)
    public ResultModel<String> exceptionHandler(Throwable ex) {

        // 打印错误
        printError(ex);
        ResultModel<String> failed = null;

        // 自定义的错误
        if (ex instanceof IllegalArgumentException) {
            IllegalArgumentException se = (IllegalArgumentException) ex;
            failed = ResultModel.failed(se.getMessage());
        }
        // 通用错误提示
        else {
            failed = ResultModel.failed("服务异常，请联系管理员");
        }

        return failed;
    }

    /**
     * 打印请求属性
     */
    private void printError(Throwable ex) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        StringBuilder sb = new StringBuilder();
        sb.append("\n捕获到全局异常: ");
        if (ex != null && StringUtils.isNotBlank(ex.getMessage())) {
            sb.append(ex.getMessage());
        }
        // 拼接请求IP
        sb.append("\n\t请求 IP: ").append(IPUtil.getIpAddr(request));
        // 拼接请求路径
        sb.append("\n\t请求路径: ").append(request.getRequestURL().toString());
        // 拼接请求头
        String token = request.getHeader(config.getHeader());
        String account = context.getRequestUser().getAccount();
        sb.append("\n\t请求头: ");
        if (StringUtils.isNotBlank(account)) {
            sb.append("\n\t\t").append("ACCOUNT").append(": ").append(account);
        }
        if (StringUtils.isNotBlank(token)) {
            sb.append("\n\t\t").append("TOKEN").append(": ").append(token);
        }
        // 拼接参数
        Map<String, String[]> params = request.getParameterMap();
        if (!params.isEmpty()) {
            sb.append("\n\t请求参数: ");
            for (String key : params.keySet()) {
                sb.append("\n\t\t").append(key).append(" : ");
                String[] val = params.get(key);
                if (val == null) {
                    sb.append("null");
                } else {
                    sb.append(String.join(",", val));
                }
            }
        }
        // 打印错误级别日志
        log.error(sb.toString(), ex);
    }

    @Autowired
    public void setContext(RequestContext context) {
        this.context = context;
    }

    @Autowired
    public void setConfig(JwtAuthenticationConfig config) {
        this.config = config;
    }
}