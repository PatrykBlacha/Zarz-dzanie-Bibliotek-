package pl.agh.edu.library.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()

                        .requestMatchers("/", "/index.html", "/*.css", "/*.js", "/css/**", "/js/**", "/assets/**", "/favicon.ico").permitAll()
                        .requestMatchers("/account/**", "/login/**", "/api/login/**").permitAll()

                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/docs").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/books/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/categories").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/books").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/books/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/books/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/loans").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/categories").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/categories/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}