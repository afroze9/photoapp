package com.afroze.photoapp.api.users

import com.afroze.photoapp.api.users.service.UsersService
import com.afroze.photoapp.api.users.shared.UserDto
import java.util.*

class UsersServiceImpl : UsersService {
    override fun createUser(userDetails: UserDto): UserDto? {
        userDetails.userId = UUID.randomUUID().toString()
        return null
    }
}