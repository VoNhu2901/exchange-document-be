package com.sgu.apigateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidatorAdmin {

    public static final List<String> adminEndpoints= List.of(
            "/api/v1/person/get-all"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> adminEndpoints
                    .stream()
                    .anyMatch(uri -> request.getURI().getPath().contains(uri));

}
