package conn.cursos.infra;

import conn.cursos.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component  // Define como um componente gerenciado pelo Spring
public class SecurityFilter extends OncePerRequestFilter {  // Filtro executado uma vez por requisição

    @Autowired
    private TokenService tokenService;  // Serviço para validar tokens JWT

    @Autowired
    private UsuarioRepository repository;  // Repositório para buscar usuários

    // Método principal que intercepta cada requisição
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Extrai o token JWT do cabeçalho "Authorization"
        String tokenJWT = recuperaToken(request);

        // 2. Se existir token:
        if (tokenJWT != null) {
            // 2.1 Valida o token e extrai o login do usuário (subject)
            String subject = tokenService.getSubject(tokenJWT);

            // 2.2 Busca o usuário no banco pelo login
            var usuario = repository.findByLogin(subject);

            // 2.3 Configura a autenticação no Spring Security
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            usuario,  // Dados do usuário
                            null,     // Credenciais (não necessárias após autenticação)
                            usuario.getAuthorities()  // Permissões do usuário
                    )
            );
        }

        // 3. Continua o processamento da requisição
        filterChain.doFilter(request, response);
    }

    // Método auxiliar para extrair o token do cabeçalho
    private String recuperaToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader;  // Retorna o token (melhoraria: remover "Bearer ")
        }
        return null;  // Se não houver token
    }
}