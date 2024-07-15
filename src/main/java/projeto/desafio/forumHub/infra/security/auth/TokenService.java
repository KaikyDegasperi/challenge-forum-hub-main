package projeto.desafio.forumHub.infra.security.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import projeto.desafio.forumHub.domain.usuario.Usuario;
import projeto.desafio.forumHub.infra.exception.TokenException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    public String gerar(Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withSubject(usuario.getId().toString())
                .withExpiresAt(LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")))
                .withIssuer("API ForumHub")
                .sign(algorithm);
    }

    public Long getSubject(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        try {
            String subject = JWT.require(algorithm)
                    .withIssuer("API ForumHub")
                    .build()
                    .verify(token)
                    .getSubject();
            return Long.valueOf(subject);
        }catch (Exception e) {
            throw new TokenException("Token invalido ou expirado");
        }
    }
}
