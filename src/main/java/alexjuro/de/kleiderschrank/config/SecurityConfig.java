package alexjuro.de.kleiderschrank.config;

import alexjuro.de.kleiderschrank.auth.FirebaseTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final FirebaseTokenFilter firebaseTokenFilter;

    public SecurityConfig(FirebaseTokenFilter firebaseTokenFilter) {
        this.firebaseTokenFilter = firebaseTokenFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .addFilterBefore(firebaseTokenFilter, UsernamePasswordAuthenticationFilter.class);
//
//        http
//                .csrf(AbstractHttpConfigurer::disable)  // Deaktiviert CSRF-Schutz global)  // Deaktiviert CSRF für die H2-Konsole
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/account/**").permitAll()  // Erlaubt den Zugriff auf die H2-Konsole
//                        .requestMatchers("/h2-console/**").permitAll()  // Erlaubt den Zugriff auf die H2-Konsole
//                        .anyRequest().authenticated()  // Alle anderen Anfragen erfordern Authentifizierung
//                )
//                .addFilterBefore(firebaseTokenFilter, UsernamePasswordAuthenticationFilter.class)
//                .headers((headers) -> headers.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()));

        return http.build();
    }
}
