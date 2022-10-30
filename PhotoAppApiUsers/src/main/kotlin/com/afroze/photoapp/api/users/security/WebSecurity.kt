package com.afroze.photoapp.api.users.security

import com.afroze.photoapp.api.users.service.UsersService
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
class WebSecurity(
    val environment: Environment,
    val usersService: UsersService,
    val bCryptPasswordEncoder: BCryptPasswordEncoder) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity?) {
        if(http == null) {
            return
        }

        val gatewayIp = environment.getProperty("gateway.ip")

        http.csrf().disable()
        http.authorizeRequests()
            .antMatchers("/**").hasIpAddress(gatewayIp)
            .antMatchers("/login").permitAll()
            .and()
            .addFilter(getAuthenticationFilter())

        http.headers().frameOptions().disable()
    }

    fun getAuthenticationFilter():AuthenticationFilter {
        val filter = AuthenticationFilter(usersService, environment, authenticationManager())
        filter.setFilterProcessesUrl(environment.getProperty("login.url.path"))

        return filter
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        if(auth == null) {
            return
        }
        auth.userDetailsService(usersService)
            .passwordEncoder(bCryptPasswordEncoder)
    }
}