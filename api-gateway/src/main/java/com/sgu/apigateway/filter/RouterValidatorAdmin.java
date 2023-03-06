package com.sgu.apigateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidatorAdmin {

    public static final List<String> adminEndpoints= List.of(
            "/api/v1/user/get-all-account",
            "/api/v1/account/get-all-account",
            "/api/v1/account/get-all-account",
            "/api/v1/account/get-all-account-with-pagination",
            "/api/v1/account/get-account-by-person-id/",
	        "/api/v1/account/get-account-by-username",
	        "/api/v1/person/get-all-person",
	        "/api/v1/person/get-all-person-with-pagination",
            "/api/v1/person/delete"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> adminEndpoints
                    .stream()
                    .anyMatch(uri -> request.getURI().getPath().contains(uri));

}
