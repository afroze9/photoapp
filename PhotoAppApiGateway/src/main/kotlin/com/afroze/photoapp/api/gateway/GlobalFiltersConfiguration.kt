package com.afroze.photoapp.api.gateway

import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import reactor.core.publisher.Mono

@Configuration
class GlobalFiltersConfiguration {

    companion object {
        val logger = LoggerFactory.getLogger(GlobalPreFilter::class.java)
    }

    @Order(1)
    @Bean
    fun customFilterOne(): GlobalFilter {
        return GlobalFilter { exchange, chain ->
            logger.info("Custom Filter One pre-executed")
            return@GlobalFilter chain.filter(exchange).then(Mono.fromRunnable {
                logger.info("Custom Filter One post-executed")
            })
        }
    }

    @Order(2)
    @Bean
    fun customFilterTwo(): GlobalFilter {
        return GlobalFilter { exchange, chain ->
            logger.info("Custom Filter Two pre-executed")
            return@GlobalFilter chain.filter(exchange).then(Mono.fromRunnable {
                logger.info("Custom Filter Two post-executed")
            })
        }
    }
}