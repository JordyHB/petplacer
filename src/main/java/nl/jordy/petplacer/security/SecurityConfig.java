package nl.jordy.petplacer.security;

import nl.jordy.petplacer.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public final CustomUserDetailsService customerUserDetails;

    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(CustomUserDetailsService customerUserDetails, JwtRequestFilter jwtRequestFilter) {
        this.customerUserDetails = customerUserDetails;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        var auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder);
        auth.setUserDetailsService(customerUserDetails);
        return new ProviderManager(auth);
    }

    @Bean
    protected SecurityFilterChain filter (HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .httpBasic(basic -> basic.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/auth").permitAll()
                                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                                .requestMatchers("/users/*/admin").hasAuthority("ROLE_ADMIN")
                                .requestMatchers("/users/*").hasAuthority("ROLE_USER")
                                .requestMatchers("/users/*/shelters").hasAuthority("ROLE_USER")
                                .requestMatchers("/users/**").hasAuthority("ROLE_ADMIN")

                                .requestMatchers(HttpMethod.GET, "/shelters/**").permitAll()
                                .requestMatchers("/shelters/*").hasAnyAuthority("ROLE_SHELTER_MANAGER", "ROLE_ADMIN")
                                .requestMatchers("/shelters/*/shelterpets").hasAuthority("ROLE_USER")
                                .requestMatchers("/shelters/*/managers/*").hasAnyAuthority("ROLE_SHELTER_MANAGER", "ROLE_ADMIN")

                                .requestMatchers(HttpMethod.GET, "/shelterpets/**").permitAll()
                                .requestMatchers("/shelterpets/*").permitAll()

                                .anyRequest().denyAll()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
