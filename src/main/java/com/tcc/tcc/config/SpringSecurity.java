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
                        authorize.requestMatchers("/register/**").hasRole("ADMIN")
                                .requestMatchers("/index/**").permitAll()
                                .requestMatchers("/users/**").hasRole("ADMIN")
                                .requestMatchers("/generateWordCloud").permitAll()
                                .requestMatchers("/foto/**").permitAll()
                                .requestMatchers("/editUser/**").hasRole("ADMIN")
                                .requestMatchers("/listarUserRole/**").hasRole("ADMIN")
                                .requestMatchers("/excluirUserRole/**").hasRole("ADMIN")
                                .requestMatchers("/novoCurso").hasRole("ADMIN")
                                .requestMatchers("/listarCursos").hasRole("ADMIN")
                                .requestMatchers("/editarCurso/**").hasRole("ADMIN")
                                .requestMatchers("/excluirCurso/**").hasRole("ADMIN")
                                .requestMatchers("/novaArea").hasRole("ADMIN")
                                .requestMatchers("/listarAreas").hasRole("ADMIN")
                                .requestMatchers("/editarArea/**").hasRole("ADMIN")
                                .requestMatchers("/excluirArea/**").hasRole("ADMIN")
                                .requestMatchers("/novoUsuarioCurso/**").hasRole("ADMIN")
                                .requestMatchers("/listarUsuariosCursos/**").hasRole("ADMIN")
                                .requestMatchers("/editarUsuarioCurso/**").hasRole("ADMIN")
                                .requestMatchers("/excluirUsuarioCurso/**").hasRole("ADMIN")
                                .requestMatchers("/novaProposta").hasAnyRole("ADMIN", "ESTUDIANTE")
                                .requestMatchers("/listarPropostas/**").hasAnyRole("ADMIN", "ORIENTADOR", "ESTUDIANTE")
                                .requestMatchers("/editarProposta/**").hasAnyRole("ADMIN", "ESTUDIANTE")
                                .requestMatchers("/excluirProposta/**").hasRole("ADMIN")
                                .requestMatchers("/aceitarProposta/**").hasAnyRole("ADMIN", "ORIENTADOR")
                                .requestMatchers("/pesquisar/**").hasAnyRole("ADMIN", "ORIENTADOR", "ESTUDIANTE")
                                .requestMatchers("/novaProducao/**").hasRole("ADMIN")
                                .requestMatchers("/listarProducoes").permitAll()
                                .requestMatchers("/editarProducao/**").hasRole("ADMIN")
                                .requestMatchers("/excluirProducao/**").hasRole("ADMIN")
                                .requestMatchers("/vermaisProducao/**").permitAll()
                                .requestMatchers("/pdf/**").permitAll()
                                .requestMatchers("/novaIdeia").hasRole("ADMIN")
                                .requestMatchers("/listarIdeias").hasAnyRole("ADMIN", "ORIENTADOR", "ESTUDIANTE")
                                .requestMatchers("/editarIdeia/**").hasRole("ADMIN")
                                .requestMatchers("/excluirIdeia/**").hasRole("ADMIN")
                                .requestMatchers("/novaPropostaIdeia/**").hasAnyRole("ADMIN", "ESTUDIANTE")
                                .requestMatchers("/header").permitAll()
                ).formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/index")
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