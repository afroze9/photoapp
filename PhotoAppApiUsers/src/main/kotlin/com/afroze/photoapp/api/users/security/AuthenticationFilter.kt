package com.afroze.photoapp.api.users.security

import com.afroze.photoapp.api.users.service.UsersService
import com.afroze.photoapp.api.users.ui.model.LoginRequestModel
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.jsonwebtoken.Jwts
import org.springframework.core.env.Environment
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.Date
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter(
    private val usersService: UsersService,
    private val env: Environment,
    authenticationManager: AuthenticationManager) : UsernamePasswordAuthenticationFilter(authenticationManager) {

    override fun attemptAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse): Authentication {

        val credentials = jacksonObjectMapper().readValue(request.inputStream, LoginRequestModel::class.java)
        val token = UsernamePasswordAuthenticationToken(
            credentials.email,
            credentials.password
        )

        return authenticationManager.authenticate(token)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        val userName = (authResult.principal as User).username
        val userDto = usersService.getUserDetailsByEmail(userName)

        var expirationTime = env.getProperty("token.expiration_time")?.toLong()
        if(expirationTime == null) {
            expirationTime = 86400000
        }

        val secretKey = env.getProperty("token.secret")

        val token = Jwts.builder()
            .setSubject(userDto.userId)
            .setExpiration(Date(System.currentTimeMillis() + expirationTime))
            .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, secretKey)
            .compact()

        response.addHeader("token", token)
        response.addHeader("userId", userDto.userId)
    }
}