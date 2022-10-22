package com.afroze.photoapp.api.users.security

import com.afroze.photoapp.api.users.ui.model.LoginRequestModel
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.lang.RuntimeException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter : UsernamePasswordAuthenticationFilter() {
    override fun attemptAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse): Authentication {
        try {
            val credentials = jacksonObjectMapper().readValue(request.inputStream, LoginRequestModel::class.java)
            val token = UsernamePasswordAuthenticationToken(
                credentials.email,
                credentials.password
            )

            return authenticationManager.authenticate(token)
        } catch (ex: IOException) {
            throw RuntimeException(ex)
        }
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        super.successfulAuthentication(request, response, chain, authResult)
    }
}