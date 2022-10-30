package com.afroze.photoapp.api.gateway

import io.jsonwebtoken.Jwts
import org.springframework.http.HttpStatus
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.core.env.Environment
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class AuthorizationHeaderFilter : AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    constructor(environment: Environment): super(Config::class.java) {
        this.environment = environment
    }

    lateinit var environment: Environment

    override fun apply(config: Config?): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            val request = exchange.request
            if(!request.headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                return@GatewayFilter onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED)
            }

            val authorizationHeader = request.headers.get(HttpHeaders.AUTHORIZATION)?.get(0)
            val jwt = authorizationHeader?.replace("Bearer", "")?.trim()

            if(!isJwtValid(jwt)) {
                return@GatewayFilter onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED)
            }

            return@GatewayFilter chain.filter(exchange)
        }
    }

    private fun onError(exchange: ServerWebExchange, err: String, httpStatus: HttpStatus):Mono<Void> {
        val response = exchange.response
        response.statusCode = httpStatus
        return response.setComplete()
    }

    private fun isJwtValid(jwt: String?):Boolean {
        val secretKey = environment.getProperty("token.secret")
        var subject:String? = null;

        try {
            subject = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt).body.subject
        } catch (ex: Exception) {
            return false
        }

        if(subject.isNullOrEmpty()){
            return false
        }

        return true
    }

    companion object Config {
    }
}