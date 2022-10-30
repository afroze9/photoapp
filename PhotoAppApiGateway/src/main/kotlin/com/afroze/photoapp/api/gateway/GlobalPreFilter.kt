package com.afroze.photoapp.api.gateway

import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class GlobalPreFilter : GlobalFilter {

    companion object {
        val logger = LoggerFactory.getLogger(GlobalPreFilter::class.java)
    }

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        logger.info("Global pre-filter executed")
        val requestPath = exchange.request.path.toString()

        logger.info("Request path: " + requestPath)
        exchange.request.headers.forEach { t, u ->  logger.info(t + " : " + u)}

        return chain.filter(exchange)
    }
}

