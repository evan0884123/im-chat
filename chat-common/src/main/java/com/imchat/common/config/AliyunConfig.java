package com.imchat.common.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Administrator
 */
@Configuration
@ConfigurationProperties(prefix = "aliyun")
@Data
public class AliyunConfig {

    /**
     *
     */
    private String endpoint;

    /**
     *
     */
    private String accessKeyId;
    /**
     *
     */
    private String accessKeySecret;

    /**
     *
     */
    private String bucketName;

    /**
     *
     */
    private String urlPrefix;

    @Bean
    public OSS oSSClient() {
        return new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

}
