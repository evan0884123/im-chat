package com.imchat.web.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.imchat.web.auth.jwt.JwtAuthenticationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.*;

@Configuration
@EnableKnife4j
@EnableConfigurationProperties
public class SwaggerConfig {

    @Value("${knife4j.setting.swaggerModelName}")
    private String title;

    @Value("${knife4j.setting.version}")
    private String version;

    @Value("${knife4j.setting.description}")
    private String description;

    @Value("${knife4j.setting.enable:true}")
    private boolean enable;

    private JwtAuthenticationConfig config;

    /**
     * 创建RestApi 并包扫描controller
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .enable(enable)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("^(?!/error.*).*$"))
                .build()
                .globalRequestParameters(requestParameters())
                .protocols(setOf("https", "http"));
    }

    /**
     * 全局请求头
     */
    private List<RequestParameter> requestParameters() {
        List<RequestParameter> headerPars = new ArrayList<>();
        headerPars.add(new RequestParameterBuilder()
                .in(ParameterType.HEADER)
                .name(config.getHeader())
                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
                .description("令牌")
                .build());
        return headerPars;
    }

    /**
     * 创建 Swagger 主页信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .contact(new Contact("《JAVA 开发团队》", null, null))
                .version(version)
                .build();
    }


    private static <T> Set<T> setOf(T... ts) {
        return new HashSet<>(Arrays.asList(ts));
    }

    @Autowired
    public void setConfig(JwtAuthenticationConfig config) {
        this.config = config;
    }
}
