package com.afroze.photoapp.api.users.data

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class UserEntity(
    @Id
    @GeneratedValue
    var id: Long?,

    @Column(nullable = false, length = 50)
    var firstName: String,

    @Column(nullable = false, length = 50)
    var lastName: String,

    @Column(nullable = false, length = 120, unique = true)
    var email: String,

    @Column(nullable = false, unique = true)
    var userId: String,

    @Column(nullable = false)
    var encryptedPassword: String
) : Serializable {
    companion object {
        private const val serialVersionUID = -12021L
    }
}