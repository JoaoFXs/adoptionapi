package io.gituhub.jfelixy.petadoptionapi.config.filter;

import io.gituhub.jfelixy.petadoptionapi.application.jwt.InvalidTokenException;
import io.gituhub.jfelixy.petadoptionapi.application.jwt.JwtService;
import io.gituhub.jfelixy.petadoptionapi.domain.entity.User;
import io.gituhub.jfelixy.petadoptionapi.domain.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor // Generates a constructor with all final fields
@Slf4j // Enables logging using SLF4J
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    // This method is called once per request to apply the filter logic
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Extracts the JWT token from the Authorization header
        String token = getToken(request);

        // If a token is present, try to validate and authenticate the user
        if (token != null) {
            try {
                // Extracts the user's email from the token
                String email = jwtService.getEmailFromToken(token);

                // Loads the user from the database using the extracted email
                User user = userService.getByEmail(email);

                // Sets the authenticated user in the security context
                setUserAsAuthenticated(user);

            } catch (InvalidTokenException e) {
                // Logs if the token is invalid
                log.error("Invalid Token: {}", e.getMessage());
            } catch (Exception e) {
                // Logs any unexpected errors during token validation
                log.error("Error validating the Token: {}", e.getMessage());
            }
        }

        // Proceeds with the next filter in the chain
        filterChain.doFilter(request, response);
    }

    // Sets the current user as authenticated in the Spring Security context
    private void setUserAsAuthenticated(User user) {
        // Creates a Spring Security UserDetails object with user's email and password
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles("USER") // Assigns a default role to the user
                .build();

        // Creates an authentication token based on the user details
        UsernamePasswordAuthenticationToken authentication
                = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());

        // Sets the authentication in the security context so that it is available throughout the request
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    // Extracts the token from the Authorization header in the format "Bearer <token>"
    private String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null) {
            String[] authHeaderParts = authHeader.split(" ");
            if (authHeaderParts.length == 2) {
                return authHeaderParts[1]; // Returns the actual JWT
            }
        }

        return null; // No token found
    }

    @Override
// This method determines whether the filter should be applied to the incoming request.
// If the request URI contains "/v1/users", the filter will be skipped (not applied).
// Returns 'true' to skip filtering, 'false' to apply the filter.
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().contains("/v1/users");
    }
}
