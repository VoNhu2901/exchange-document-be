package com.sgu.exchage.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

@Configuration
public class OpenApiConfiguration {
    private final String serverUrl;

    public OpenApiConfiguration(
            @Value("${app.server}") String serverUrl) {
        this.serverUrl = serverUrl;
    }

    @Bean
    public OpenAPI openAPiConfig() {
        final String securitySchemeName = "bearerAuth";

        ArrayList<Server> servers = new ArrayList<>();
        servers.add(new Server().url(serverUrl).description("Local Server"));

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )
                .info(new Info()
                        .title("Exchange Website API")
                        .description("This document is specified by Vo Nhu and Pham Thuan")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Vo Nhu ")
                                .url("https://github.com/vonhu2901")
                                .email("vohoangquynhnhu40.41@gmail.com"))
                        .termsOfService("TOC")
                        .license(new License().name("Github").url("https://github.com/VoNhu2901/traodoitailieusgu"))
                )
                .servers(servers);
    }
}