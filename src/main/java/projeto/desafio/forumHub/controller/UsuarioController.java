package projeto.desafio.forumHub.controller;

// Importações necessárias para a classe.
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import projeto.desafio.forumHub.domain.usuario.DadosRegistroUsuario;
import projeto.desafio.forumHub.domain.usuario.DadosUsuario;
import projeto.desafio.forumHub.domain.usuario.UsuarioService;

import java.net.URI;

// Anotação que indica que esta classe é um controlador REST.
@RestController
// Anotação que mapeia requisições HTTP para /usuarios para esta classe.
@RequestMapping("/usuarios")
// Anotação do Lombok para gerar um construtor com todos os atributos finais como parâmetros.
@RequiredArgsConstructor
public class UsuarioController {

    // Serviço responsável pelas operações relacionadas a usuários.
    private final UsuarioService service;

    // Método que trata requisições POST para registrar um novo usuário.
    @PostMapping
    public ResponseEntity<DadosUsuario> registrar(@Valid @RequestBody DadosRegistroUsuario dados, UriComponentsBuilder builder) {
        // Registra um novo usuário utilizando o serviço.
        DadosUsuario dadosUsuario = service.registrar(dados);

        // Constrói a URI do novo recurso criado.
        URI uri = builder.path("/usuarios/{id}").buildAndExpand(dadosUsuario.id()).toUri();

        // Retorna uma resposta HTTP 201 Created com a URI do novo recurso e o corpo contendo os dados do usuário.
        return ResponseEntity.created(uri).body(dadosUsuario);
    }
}
