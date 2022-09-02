package com.Ironhack.BankApi.Security;

import com.Ironhack.BankApi.Services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration  {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder(); }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConf) throws Exception {
        return authConf.getAuthenticationManager();
    }




    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic();

        httpSecurity.authorizeRequests()
                .mvcMatchers(HttpMethod.POST,"Account/create-checking-account").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST,"Account/create-saving-account").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST,"Account/create-credit-card").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.DELETE,"Account/delete-account").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.GET,"Account/check-my-balance/{accountId}").hasRole("HOLDER")
                .mvcMatchers(HttpMethod.GET,"Account/check-balance/{accountId}").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.PATCH,"Account/add-money").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.PATCH,"Account/rest-money").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST,"User/add-third-party").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST,"User/create-account-holder").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.PATCH,"Account/third-party-transfer").hasRole("TP")
                .anyRequest().permitAll();

        httpSecurity.csrf().disable();

        return httpSecurity.build();

    }

}
