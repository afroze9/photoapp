package com.afroze.photoapp.api.gateway

import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class GlobalPostFilter : GlobalFilter {

    companion object {
        val logger = LoggerFactory.getLogger(GlobalPostFilter::class.java)
    }

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        return chain
            .filter(exchange)
            .then(Mono.fromRunnable {
                logger.info("Global post-filter executed")
            })
    }

}