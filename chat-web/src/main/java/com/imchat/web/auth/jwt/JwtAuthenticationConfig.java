package com.imchat.web.auth.jwt;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Description: jwt配置
 *
 * @author Administrator
 */
@Data
@ToString
@ConfigurationProperties(prefix = "jwt")
@Component
public class JwtAuthenticationConfig {

    private String url = "/login";

    private String header = "Authorization";

    private String prefix;

    /**
     * 默认1小时超时
     */
    private int expiration = 60 * 60 * 12;

    private String secret;
}
