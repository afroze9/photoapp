package com.afroze.photoapp.api.users.service

import com.afroze.photoapp.api.users.data.UserEntity
import com.afroze.photoapp.api.users.data.UsersRepository
import com.afroze.photoapp.api.users.shared.UserDto
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.util.*
import kotlin.collections.ArrayList

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

    override fun getUserDetailsByEmail(email: String): UserDto {
        val userEntity = usersRepository.findByEmail(email)

        return UserDto(userEntity.firstName, userEntity.lastName, userEntity.email, "", userEntity.userId, userEntity.encryptedPassword)
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        if(username == null) {
            throw Exception("No username provided")
        }

        val userEntity = usersRepository.findByEmail(username)

        return User(userEntity.email, userEntity.encryptedPassword, true, true, true, true, ArrayList<GrantedAuthority>())
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