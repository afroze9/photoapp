package com.afroze.photoapp.api.users.data

import org.springframework.data.repository.CrudRepository

interface UsersRepository : CrudRepository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity
}