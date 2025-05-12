package com.example.NovoTesteCrud.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeHttpRequests()
//                .requestMatchers(
//                        "/auth/**",                       // Acesso sem autenticação para autenticação
//                        "/",                               // Página inicial
//                        "/index",                          // Página inicial
//                        "/login",                           // Página de login
//                        "/cadastro",                       // Página de cadastro
//                        "/reset-senha",                    // Página de reset de senha
//                        "/troca-senha",                    // Página de troca de senha
//                        "/senha",                          // Página de alteração de senha
//                        "/perfil-usuario",                 // Página do perfil do usuário
//                        "/perfil-academias",               // Página do perfil das academias
//                        "/perfil-personais",               // Página do perfil dos personal trainers
//                        "/academias",                      // Página das academias
//                        "/cadastrar-academia",             // Página de cadastro de academia
//                        "/detalhes-academias",             // Página de detalhes de academia
//                        "/personais",                      // Página de personal trainers
//                        "/detalhes-personais",             // Página de detalhes dos personal trainers
//                        "/treinos",                        // Página de treinos
//                        "/detalhes-treinos",               // Página de detalhes dos treinos
//                        "/favicon.ico",
//                        "/dietas"// Ícone do site
//                ).permitAll()                         // Permitir acesso a todas as páginas estáticas e de login
//
//                .anyRequest().authenticated()           // Exige autenticação para todas as outras requisições
//
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Sessões sem estado para API
//
//        // Adiciona o filtro JWT antes do filtro de autenticação padrão
//        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/static/**", "/css/**", "/js/**", "/assets/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/academia").permitAll()
                        .requestMatchers(HttpMethod.GET, "/academias").permitAll()
                        .requestMatchers(HttpMethod.GET, "/atividades").permitAll()
                        .requestMatchers(HttpMethod.GET, "/avaliacoes/academia/{academiaId}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/avaliacoes/personal/{personalId}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/dietas").permitAll()
                        .requestMatchers(HttpMethod.GET, "/exercicios").permitAll()
                        .requestMatchers(HttpMethod.GET, "/planos/academia/{academiaId}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/treino").permitAll()
                        .requestMatchers(HttpMethod.GET, "/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/cadastro").permitAll()
                        .requestMatchers(HttpMethod.GET, "/reset-senha").permitAll()
                        .requestMatchers(HttpMethod.GET, "/troca-senha").permitAll()
                        .requestMatchers(HttpMethod.GET, "/senha").permitAll()
                        .requestMatchers(HttpMethod.GET, "/perfil-usuario").permitAll()
                        .requestMatchers(HttpMethod.GET, "/perfil-academias").permitAll()
                        .requestMatchers(HttpMethod.GET, "/perfil-personais").permitAll()
                        .requestMatchers(HttpMethod.GET, "/academias").permitAll()
                        .requestMatchers(HttpMethod.GET, "/cadastrar-academia").permitAll()
                        .requestMatchers(HttpMethod.GET, "/detalhes-academias").permitAll()
                        .requestMatchers(HttpMethod.GET, "/personais").permitAll()
                        .requestMatchers(HttpMethod.GET, "/detalhes-personais").permitAll()
                        .requestMatchers(HttpMethod.GET, "/treinos").permitAll()
                        .requestMatchers(HttpMethod.GET, "/detalhes-treinos").permitAll()
                        .requestMatchers(HttpMethod.GET, "/cadastrar-treino").permitAll()
                        .requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/swagger-resources/**").permitAll()
                        .anyRequest().authenticated()

                ).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                        .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
