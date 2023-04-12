package com.jul.jumpropetornamentchecker.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static String version = "v1.0.0";
    private static String title = "줄넘기 대회 프로그램 API";
    private static String description = "줄넘기 대회 프로그램 API Docs 입니다.";

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .version(version)
                .title(title)
                .description(description);

        return new OpenAPI()
                .info(info);
    }
}
