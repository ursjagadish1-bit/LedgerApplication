package com.ledgerapp.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI ledgerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Ledger Application API")
                        .description("Event Ledger API for transaction event ingestion and account balance calculation.")
                        .version("v1")
                        .contact(new Contact().name("Ledger API Support").email("support@example.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
