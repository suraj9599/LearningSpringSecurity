package com.codewithsuraj.LearningSpringSecurity.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user1 = User
                .withUsername("suraj")
                .password(passwordEncoder().encode("suraj123"))
                .roles("ADMIN")
                .build();

        UserDetails user2 = User
                .withUsername("aman")
                .password(passwordEncoder().encode("aman123"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user1,user2);
    }
}






//Learning Notes:
//formLogin will only work with statefull , when we make it stateless then it will stop working
//because session id will be changed on every request so it will not be able to use the authenticate
//form login option because it will not find any session id there.
//so now for stateless we use httbBasic which will give the login option through popup
//in general we use stateless in case of jwt token


//- Stateful authentication: The server typically stores authentication state in an HTTP session.
//- Stateless authentication: The server does not rely on an HTTP session to remember authentication between requests.
//- HTTP Basic: The client sends the username and password in the Authorization header on each request.
//- JWT authentication: The client typically sends a token in the Authorization: Bearer ... header on each request.
//HTTP Basic does not require a session ID.