package com.afroze.photoapp.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PhotoAppApiGatewayApplication

fun main(args: Array<String>) {
	runApplication<PhotoAppApiGatewayApplication>(*args)
}
