package com.todo_app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

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
    }
}
