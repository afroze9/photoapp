package com.afroze.photoapp.api.users.ui.controllers

import com.afroze.photoapp.api.users.ui.model.CreateUserRequestModel
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UsersController(val environment: Environment) {
    @GetMapping("/status/check")
    fun status():String{
        return "Working on port " + environment.getProperty("local.server.port")
    }

    @PostMapping
    fun createUser(@Valid @RequestBody user: CreateUserRequestModel): ResponseEntity<UserRest> {
        val createdUser = userService.createUser(user)
        return ResponseEntity(createdUser, HttpStatus.CREATED)
    }
}