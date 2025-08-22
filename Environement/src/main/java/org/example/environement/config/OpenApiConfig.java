package org.example.environement.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ECF - Environnement API")
                        .description("API ECF - Environnement")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Axel Demeyere")));
    }
}