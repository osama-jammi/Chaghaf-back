package ma.chaghaf.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * ✅ FIX CRITIQUE :
 *
 * Le gateway valide le JWT et forward les headers X-User-Id, X-User-Email, X-User-Role.
 * L'auth-service n'a PAS de filtre JWT → Spring Security bloque les requêtes
 * qui ne sont pas "permitAll()" même si elles viennent du gateway avec le bon token.
 *
 * SOLUTION : l'auth-service fait confiance au gateway et laisse passer toutes les
 * requêtes. La sécurité applicative est gérée dans le controller via @RequestHeader("X-User-Id").
 */
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // ✅ Tout est permis ici — le gateway s'occupe de valider le JWT
                // et d'injecter X-User-Id dans les headers
                .anyRequest().permitAll()
            );
        return http.build();
    }
}