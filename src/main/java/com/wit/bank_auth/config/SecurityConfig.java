package com.wit.bank_auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean  // Password'u Criptoluyor
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    // Authentication Yönetimi, Spring'in Default yönetiminden çıkarmak amacıyla yazıyorum. Veritabanı üzerinden gidip password eşleşmesini kontrol eder.
    // SpringSecurity'ye bırakıyor userName = password eşleşmesini. decode'u ben elle yapamam, Spring de yapmaz. gelen password'ü encode eder iki encode uyuşmasını kontrol eder. decode Bcript kendisi yapar decode fln kendi içerisinde artık.
    public AuthenticationManager authManager(UserDetailsService userDetailsService){
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(daoProvider);
    }

    @Bean // her endpoint'e gidişin nasıl yönetileceği belirtiyorum
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http.csrf(csrf ->csrf.disable())
                .authorizeHttpRequests(auth -> {
                    auth.anyRequest().authenticated();
                })
                // default Blue page login
                // .formLogin(Customizer.withDefaults())

                //  Bizim tanımladığımız şekilde girebilecek, blue page pom.xml'dekine göre giriş yapmaz. user kendi username ve password 'ü ile giriş yapabilir.
                .httpBasic(Customizer.withDefaults())

                // .oauth2Login(Customizer.withDefaults())
                //  GitHub, Google gibi hesaplarla giriş yapmak için
                .build();
    }
}
