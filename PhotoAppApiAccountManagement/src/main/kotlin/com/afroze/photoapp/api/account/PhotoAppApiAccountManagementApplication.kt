package com.afroze.photoapp.api.account

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class PhotoAppApiAccountManagementApplication

fun main(args: Array<String>) {
	runApplication<PhotoAppApiAccountManagementApplication>(*args)
}
