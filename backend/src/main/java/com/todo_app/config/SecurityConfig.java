package com.todo_app.config;

<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
=======
import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
>>>>>>> 70ee6e2 (Auth error correction)
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

<<<<<<< HEAD
    @Value("${security.user.name}")
    private String username;

    @Value("${security.user.password}")
    private String password;

    @Value("${react.server.url}")
    private String reactUrl;

    @Value("${app.url}")
    private String appUrl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  
            .cors(withDefaults())  
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/projects/**").permitAll()  
                .anyRequest().authenticated() 
            )
            .formLogin(form -> form
                .defaultSuccessUrl(reactUrl, true) 
                .permitAll() 
            )
            .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl(appUrl)  
            .invalidateHttpSession(true) 
            .clearAuthentication(true)) 
            .httpBasic(withDefaults());
    return http.build();
    }
    

 @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username(username)
                .password(password)
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
=======
    @Value("${react.server.url}")
    private String reactServer;
  
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .cors(cors -> {})
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**", "/projects/**").permitAll()
                .anyRequest().authenticated() 
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl(reactServer+"/login")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
            )
            .httpBasic(withDefaults()); 
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
>>>>>>> 70ee6e2 (Auth error correction)
    }
}
