package conn.cursos.infra;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenJWT = recuperaToken(request);
        String subject = tokenService.getSubject(tokenJWT);
        filterChain.doFilter(request, response);
    }

    private String recuperaToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader == null ) throw new RuntimeException("token nao enviado");
        return authorizationHeader;
    }
}