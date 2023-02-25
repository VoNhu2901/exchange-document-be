package com.sgu.apigateway.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Predicate;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config>{
    public static class Config {
        // empty class as I don't need any particular configuration
    }

    private AuthFilter() {
        super(Config.class);
    }
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            List<String> openApiEndpoints= List.of(
                    "/api/v1/auth",
                    "/api/v1/posts/get-all"
            );

            Predicate<ServerHttpRequest> isSecured =
                    request -> openApiEndpoints
                            .stream()
                            .noneMatch(uri -> request.getURI().getPath().contains(uri));

            if(!isSecured.test(exchange.getRequest())){
                return chain.filter(exchange.mutate().build());
            }

            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new RuntimeException("Missing authorization information");
            }

            String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

            String[] parts = authHeader.split(" ");

            if (parts.length != 2 || !"Bearer".equals(parts[0])) {
                throw new RuntimeException("Incorrect authorization structure");
            }

            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(parts[1]);
            String username = decodedJWT.getSubject();
            if(username == "" || username == null) {
                throw new RuntimeException("Athorization error");
            }
            ServerHttpRequest request = exchange.getRequest().mutate().
                    header("X-auth-username", username).
                    build();
            return chain.filter(exchange.mutate().request(request).build());



        };
    }

}
