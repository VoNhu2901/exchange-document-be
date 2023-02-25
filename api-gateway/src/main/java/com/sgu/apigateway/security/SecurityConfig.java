package com.sgu.apigateway.security;

import com.sgu.apigateway.config.AuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    private AuthFilter authFilter;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity
                .csrf().disable()
                .authorizeExchange(exchange ->
                        exchange.pathMatchers("/api/v1/person/**")
                                .permitAll()
                                .anyExchange()
                                .authenticated())
           ;

        return serverHttpSecurity.build();
    }
}
