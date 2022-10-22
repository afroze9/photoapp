package com.afroze.photoapp.api.users.service

import com.afroze.photoapp.api.users.shared.UserDto

interface UsersService {
    fun createUser(userDetails: UserDto): UserDto?
}