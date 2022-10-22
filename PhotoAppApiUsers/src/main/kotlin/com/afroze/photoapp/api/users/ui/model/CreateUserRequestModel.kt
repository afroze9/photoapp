package com.afroze.photoapp.api.users.ui.model

import javax.validation.constraints.Email
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class CreateUserRequestModel(
    @field:NotNull(message = "FirstName is required")
    @field:Size(min=2, message = "First name must be at least 2 characters")
    var firstName: String,

    @field:NotNull(message = "LastName is required")
    @field:Size(min=2, message = "Last name must be at least 2 characters")
    var lastName: String,

    @field:NotNull()
    @field:Email()
    var email: String,

    @field:NotNull(message = "Password is required")
    @field:Size(min = 8)
    var password: String
)