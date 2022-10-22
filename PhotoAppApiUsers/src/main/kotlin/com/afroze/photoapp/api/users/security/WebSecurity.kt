package com.afroze.photoapp.api.users.security

import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableWebSecurity
class WebSecurity(val environment: Environment) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity?) {
        if(http == null) {
            return;
        }

        http.csrf().disable()
        http.authorizeRequests().antMatchers("/**").hasIpAddress(environment.getProperty("gateway.ip"))
            .and()
            .addFilter(getAuthenticationFilter())
        http.headers().frameOptions().disable()
    }

    fun getAuthenticationFilter():AuthenticationFilter {
        var filter:AuthenticationFilter = AuthenticationFilter()
        filter.setAuthenticationManager(authenticationManager())

        return filter
    }
}