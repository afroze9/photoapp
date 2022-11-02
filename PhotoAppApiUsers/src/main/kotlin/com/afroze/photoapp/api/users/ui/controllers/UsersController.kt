package com.afroze.photoapp.api.users.ui.controllers

import com.afroze.photoapp.api.users.service.UsersService
import com.afroze.photoapp.api.users.shared.UserDto
import com.afroze.photoapp.api.users.ui.model.CreateUserRequestModel
import com.afroze.photoapp.api.users.ui.model.CreateUserResponseModel
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
class UsersController(val usersService: UsersService, val environment: Environment) {
    @GetMapping("/status/check")
    fun status():String = "Working: " + environment.getProperty("token.secret")

    @PostMapping
    fun createUser(@Valid @RequestBody user: CreateUserRequestModel): ResponseEntity<CreateUserResponseModel> {
        val userDto:UserDto = user.toUserDto()
        val createdUser = usersService.createUser(userDto)
        val response = createdUser.toCreateUserResponseModel()

        return ResponseEntity(response, HttpStatus.CREATED)
    }

    fun CreateUserRequestModel.toUserDto() = UserDto(
        firstName = firstName,
        lastName = lastName,
        email = email,
        password = password,
        encryptedPassword = "",
        userId = ""
    )

    fun UserDto.toCreateUserResponseModel() = CreateUserResponseModel(
        firstName = firstName,
        lastName = lastName,
        email = email,
        userId = userId
    )
}