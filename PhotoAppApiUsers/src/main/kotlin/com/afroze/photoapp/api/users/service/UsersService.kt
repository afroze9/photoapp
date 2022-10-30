package com.afroze.photoapp.api.users.service

import com.afroze.photoapp.api.users.shared.UserDto
import org.springframework.security.core.userdetails.UserDetailsService

interface UsersService : UserDetailsService {
    fun createUser(userDetails: UserDto): UserDto
    fun getUserDetailsByEmail(email: String): UserDto
}