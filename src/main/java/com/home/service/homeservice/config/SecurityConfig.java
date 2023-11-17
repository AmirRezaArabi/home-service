package com.home.service.homeservice.config;

import com.home.service.homeservice.repository.AdminRepository;
import com.home.service.homeservice.repository.CustomerRepository;
import com.home.service.homeservice.repository.ExpertRepository;
import com.home.service.homeservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests( a -> a.requestMatchers("/admin/**").hasRole("ADMIN"))
                .authorizeHttpRequests(a-> a.requestMatchers("/service/**").hasRole("ADMIN"))
                .authorizeHttpRequests(a-> a.requestMatchers("/sub-service/**").hasRole("ADMIN"))
//                .authorizeHttpRequests( a -> a.requestMatchers("/customer/**").hasRole("CUSTOMER"))
//                .authorizeHttpRequests( a -> a.requestMatchers("expert").hasRole("EXPERT"))
                .authorizeHttpRequests(a -> a.anyRequest().permitAll())
                .httpBasic(withDefaults());
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userRepository::findByUsername).passwordEncoder(bCryptPasswordEncoder);
    }

}
