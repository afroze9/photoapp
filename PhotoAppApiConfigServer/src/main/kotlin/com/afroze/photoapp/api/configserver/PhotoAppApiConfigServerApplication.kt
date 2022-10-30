package com.afroze.photoapp.api.configserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.config.server.EnableConfigServer

@SpringBootApplication
@EnableConfigServer
class PhotoAppApiConfigServerApplication

fun main(args: Array<String>) {
    runApplication<PhotoAppApiConfigServerApplication>(*args)
}
