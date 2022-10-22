package com.afroze.photoapp.api.users.service

import com.afroze.photoapp.api.users.data.UserEntity
import com.afroze.photoapp.api.users.data.UsersRepository
import com.afroze.photoapp.api.users.service.UsersService
import com.afroze.photoapp.api.users.shared.UserDto
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.util.*

@Component
class UsersServiceImpl(
    val usersRepository: UsersRepository,
    val bCryptPasswordEncoder: BCryptPasswordEncoder) : UsersService {
    override fun createUser(userDetails: UserDto): UserDto {
        userDetails.userId = UUID.randomUUID().toString()
        val userEntity:UserEntity = userDetails.toUserEntity()
        userEntity.encryptedPassword = bCryptPasswordEncoder.encode(userDetails.password)

        usersRepository.save(userEntity)
        return userEntity.toUserDto()
    }

    fun UserDto.toUserEntity() = UserEntity(
        firstName = firstName,
        lastName = lastName,
        email = email,
        userId = userId,
        encryptedPassword = encryptedPassword,
        id = null
    )

    fun UserEntity.toUserDto() = UserDto(
        firstName = firstName,
        lastName = lastName,
        email = email,
        userId = userId,
        encryptedPassword = encryptedPassword,
        password = ""
    )
}