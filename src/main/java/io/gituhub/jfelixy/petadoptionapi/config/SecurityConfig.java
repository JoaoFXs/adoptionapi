package io.gituhub.jfelixy.imagevaultapi.config;

import io.gituhub.jfelixy.imagevaultapi.application.jwt.JwtService;
import io.gituhub.jfelixy.imagevaultapi.config.filter.JwtFilter;
import io.gituhub.jfelixy.imagevaultapi.domain.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration // Marks this class as a configuration class for Spring
@EnableWebSecurity // Enables Spring Security for the application
//@CrossOrigin("*") // Allows cross-origin requests from any origin (currently commented out)
public class SecurityConfig {

    @Bean
    public JwtFilter jwtFilter(JwtService jwtService, UserService userService){
        return new JwtFilter(jwtService, userService);
    }
    // Defines a bean for password encoding using BCrypt hashing algorithm
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Defines the security filter chain to configure HTTP security
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception{
        return http
                // Disables CSRF protection as it may interfere with frontend frameworks like React
                .csrf(AbstractHttpConfigurer::disable)
                // Enables CORS support using the defined configuration
                .cors(cors -> cors.configure(http))
                // Allows all incoming requests without authentication (for development/testing)
                .authorizeHttpRequests(auth -> {
                    //Configuration auth requests
                    auth.requestMatchers("/v1/users/**").permitAll();//All of users urls permit requests
                    auth.requestMatchers(HttpMethod.GET, "/v1/images/**").permitAll();
                    auth.anyRequest().authenticated();//Any other transition requires authentication.
                })
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                // Builds and returns the configured SecurityFilterChain
                .build();
    }

    // Defines the CORS configuration source to allow cross-origin requests
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        // Applies default CORS settings (allows all origins, methods, headers, etc.)
        CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
        // Maps the configuration to all paths
        UrlBasedCorsConfigurationSource cors = new UrlBasedCorsConfigurationSource();
        cors.registerCorsConfiguration("/**", config);

        return cors;
    }
}
