package alexjuro.de.kleiderschrank.auth;

import alexjuro.de.kleiderschrank.dto.UserDTO;
import alexjuro.de.kleiderschrank.service.UserService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FirebaseTokenFilter extends OncePerRequestFilter {
    @Autowired
    private UserService userService;
    private final FirebaseTokenCache tokenCache = new FirebaseTokenCache(); // Cache-Instanz

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String idToken = authHeader.substring(7);

            try {
                // Überprüfen, ob das Token bereits im Cache ist
                FirebaseToken decodedToken = tokenCache.get(idToken);
                if (decodedToken == null) {
                    decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
                    tokenCache.put(idToken, decodedToken);
                }

                UserDTO user = userService.getUser(decodedToken.getUid());

                // Userdetails für Spring Security
                FirebaseAuthenticationToken authentication =
                        new FirebaseAuthenticationToken(user, null, null);

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
