package com.afroze.photoapp.api.users.shared

import java.io.Serializable

data class UserDto (
    var firstName: String,
    var lastName: String,
    var email: String,
    var password: String,
    var userId: String,
    var encryptedPassword: String
) : Serializable {
    companion object {
        private const  val serialVersionUID = -625885L
    }
}