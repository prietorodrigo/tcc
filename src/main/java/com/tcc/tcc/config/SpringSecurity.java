package com.tcc.tcc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurity {
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("/register/**").permitAll()
                                .requestMatchers("/index/**").permitAll()
                                .requestMatchers("/users/**").permitAll()
                                .requestMatchers("/novoCurso").hasRole("ADMIN")
                                .requestMatchers("/listarCursos").hasRole("ADMIN")
                                .requestMatchers("/editarCurso/**").hasRole("ADMIN")
                                .requestMatchers("/novaArea").hasRole("ADMIN")
                                .requestMatchers("/listarAreas").hasRole("ADMIN")
                                .requestMatchers("/editarArea/**").hasRole("ADMIN")
                                .requestMatchers("/pesquisarUsuario/**").hasRole("ADMIN")
                                .requestMatchers("/novoUsuarioCurso/**").hasRole("ADMIN")
                                .requestMatchers("/listarUsuariosCursos").hasRole("ADMIN")
                                .requestMatchers("/editarUsuarioCurso/**").hasRole("ADMIN")
                                .requestMatchers("/foto/**").permitAll()
                                .requestMatchers("/editarUser/**").hasRole("ADMIN")
                                .requestMatchers("/novaProposta").hasRole("ADMIN")
                                .requestMatchers("/listarPropostas").hasRole("ADMIN")
                                .requestMatchers("/editarProposta/**").hasRole("ADMIN")
                                .requestMatchers("/novaProducao/**").hasRole("ADMIN")
                                .requestMatchers("/listarProducoes").hasRole("ADMIN")
                                .requestMatchers("/editarProducao/**").hasRole("ADMIN")
                ).formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/users")
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}