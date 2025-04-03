package conn.cursos.infra;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import conn.cursos.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
@Service
public class TokenService {
    @Value("${api:security:token:secret}")
    private String secret;

    public String gerarToken(Usuario usuario) {
        try {
            // Cria algoritmo de assinatura usando HMAC256 com chave "123456"
            var algorithm = Algorithm.HMAC256(secret);

            // Constrói o token JWT
            return JWT.create()
                    .withIssuer("remedios_api")  // Quem emitiu o token
                    .withSubject(usuario.getLogin())  // Identifica o usuário
                    .withExpiresAt(DataExpiracao())  // Define expiração
                    .sign(algorithm);  // Assina o token
        } catch (JWTCreationException e){
            throw new RuntimeException("erro ao gerar token, erro: ", e);
        }
    }
    public String getSubject(String tokenJWT) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("remedios_api")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();

        } catch (JWTVerificationException exception){
            throw new RuntimeException("token invalido ou expirado");
        }
    }
    private Instant DataExpiracao() {
        // Calcula data de expiração: 2 horas a partir de agora
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.UTC);
    }
}