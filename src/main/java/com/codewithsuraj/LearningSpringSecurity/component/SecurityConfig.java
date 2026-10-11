package com.codewithsuraj.LearningSpringSecurity.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        return http.csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth->auth.requestMatchers("/home","/about").permitAll().anyRequest().authenticated())
//                .formLogin(form->form.permitAll())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}






//Learning Notes:
//formLogin will only work with statefull , when we make it stateless then it will stop working
//because session id will be changed on every request so it will not be able to use the authenticate
//form login option because it will not find any session id there.
//so now for stateless we use httbBasic which will give the login option through popup
//in general we use stateless in case of jwt token