package com.aegis.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig
{
    @Bean
    public OpenAPI aegisOpenApi() {
        return new OpenAPI()
                .info(new Info()
                .title("Aegis API")
                .version("v1")
                .description("Medicine batch verification and custody tracking platform using " +
                        "Spring Boot, BPMN, and private blockchain")
                .contact(new Contact()
                        .name("Aegis Development Team")));
    }
}