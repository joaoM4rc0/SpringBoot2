package conn.cursos.controller;

import conn.cursos.infra.TokenService;
import conn.cursos.usuarios.Usuario;
import conn.cursos.usuarios.ValidacaoDados;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/login")
public class AutenticacaoController {
    @Autowired
    private AuthenticationManager manager;  // Gerenciador de autenticação do Spring

    @Autowired
    private TokenService tokenService;  // Seu serviço de token

    @PostMapping
    public ResponseEntity<?> efetuaLogin(@RequestBody @Valid ValidacaoDados dados) {
        // Cria objeto de autenticação com login/senha recebidos
        var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());

        // Autentica o usuário (verifica se existe e se a senha está correta)
        var autenticacao = manager.authenticate(token);

        // Gera token JWT para o usuário autenticado e retorna
        return ResponseEntity.ok(tokenService.gerarToken((Usuario) autenticacao.getPrincipal()));
    }
}