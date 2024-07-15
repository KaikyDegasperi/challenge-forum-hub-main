package projeto.desafio.forumHub.controller;

// Importações necessárias para a classe.
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projeto.desafio.forumHub.domain.usuario.DadosLogin;
import projeto.desafio.forumHub.domain.usuario.TokenResponse;
import projeto.desafio.forumHub.infra.security.auth.TokenService;

// Anotação que indica que esta classe é um controlador REST.
@RestController
// Anotação que mapeia requisições HTTP para /login para esta classe.
@RequestMapping("/login")
// Anotação do Lombok para gerar um construtor com todos os atributos finais como parâmetros.
@RequiredArgsConstructor
public class AuthenticationController {
    // Gerenciador de autenticação do Spring Security.
    private final AuthenticationManager manager;

    // Serviço para geração de tokens.
    private final TokenService tokenService;

    // Método que trata requisições POST para /login.
    @PostMapping
    public ResponseEntity<?> login(@Valid @RequestBody DadosLogin dados) {
        // Cria um token de autenticação com as credenciais fornecidas.
        var authToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.password());

        // Autentica o usuário utilizando o gerenciador de autenticação.
        Authentication auth = manager.authenticate(authToken);

        // Gera um token JWT para o usuário autenticado.
        String token = tokenService.gerar(auth);

        // Retorna uma resposta HTTP 200 OK com o token JWT.
        return ResponseEntity.ok(new TokenResponse("Bearer", token));
    }
}
