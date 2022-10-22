package com.afroze.photoapp.api.users.ui.controllers

import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UsersController(val environment: Environment) {
    @GetMapping("/status/check")
    fun status():String{
        return "Working on port " + environment.getProperty("local.server.port")
    }
}