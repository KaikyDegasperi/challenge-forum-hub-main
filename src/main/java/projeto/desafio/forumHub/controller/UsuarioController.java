package projeto.desafio.forumHub.controller;

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

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping
    public ResponseEntity<DadosUsuario> registrar(@Valid @RequestBody DadosRegistroUsuario dados, UriComponentsBuilder builder) {
        DadosUsuario dadosUsuario = service.registrar(dados);
        URI uri = builder.path("/usuarios/{id}").buildAndExpand(dadosUsuario.id()).toUri();
        return ResponseEntity.created(uri).body(dadosUsuario);
    }
}
