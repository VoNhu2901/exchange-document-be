package com.sgu.apigateway.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

@RefreshScope
@Component
public class AuthenticationFilter implements GatewayFilter {

    @Value("${security.app.jwtSecret}")
    private String secret;
    @Autowired
    private RouterValidator routerValidator;//custom route validator

    @Autowired
    private RouterValidatorAdmin routerValidatorAdmin;//custom route validator

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (routerValidator.isSecured.test(request)) {
            if (this.isAuthMissing(request)) {
                System.out.println("Authorization header is missing in request");
                return this.onError(exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);
            }
            String token = this.getAuthHeader(request);

            //Incorrect authorization structure
            if(!token.startsWith("Bearer ")){
                System.out.println("Incorrect authorization structure");
                return this.onError(exchange, "Incorrect authorization structure", HttpStatus.UNAUTHORIZED);
            }

            token = token.substring("Bearer ".length());

            DecodedJWT jwt = JWT.decode(token);

            if( jwt.getExpiresAt().before(new Date())) {
                System.out.println("Token is expired");
                return this.onError(exchange, "Token is expired", HttpStatus.UNAUTHORIZED);
            }

            Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            Claim role = decodedJWT.getClaims().get("role");
            Claim sub = decodedJWT.getClaims().get("sub");


            if(role == null || role.isNull()){
                System.out.println("'Role' not found");
                return this.onError(exchange, "'Role' not found", HttpStatus.UNAUTHORIZED);
            }

            System.out.println(role.asString());

            //Check user call admin api
            if(role.asString().compareToIgnoreCase("USER") == 0
            && routerValidatorAdmin.isSecured.test(request)){
                System.out.println("Not authorization");
                return this.onError(exchange, "Not authorization", HttpStatus.UNAUTHORIZED);
            }

            exchange.getRequest().mutate()
                .header("role", String.valueOf(role))
                .header("user", String.valueOf(sub))

                .build();


        }
        return chain.filter(exchange);
    }


    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {

//        exchange.getRequest().mutate()
//                .header("id", String.valueOf(claims.get("id")))
//                .header("role", String.valueOf(claims.get("role")))
//                .build();
    }
}
