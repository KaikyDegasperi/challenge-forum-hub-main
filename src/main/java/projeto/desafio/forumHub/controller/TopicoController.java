package projeto.desafio.forumHub.controller;

// Importações necessárias para a classe.
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import projeto.desafio.forumHub.domain.topico.*;
import projeto.desafio.forumHub.domain.usuario.Usuario;

import java.net.URI;

// Anotação que indica que esta classe é um controlador REST.
@RestController
// Anotação que mapeia requisições HTTP para /topicos para esta classe.
@RequestMapping("/topicos")
// Anotação do Lombok para gerar um construtor com todos os atributos finais como parâmetros.
@RequiredArgsConstructor
public class TopicoController {
    // Serviço responsável pelas operações relacionadas a tópicos.
    private final TopicoService service;

    // Método que trata requisições POST para registrar um novo tópico.
    @PostMapping
    @Transactional
    public ResponseEntity<DadosTopico> registrar(@Valid @RequestBody DadosRegistroTopico dados, UriComponentsBuilder builder,
                                                 @AuthenticationPrincipal Usuario usuario) {
        // Registra um novo tópico utilizando o serviço.
        DadosTopico dadosTopico = service.registrar(dados, usuario);

        // Constrói a URI do novo recurso criado.
        URI uri = builder.path("/topicos/{id}").buildAndExpand(dadosTopico.id()).toUri();

        // Retorna uma resposta HTTP 201 Created com a URI do novo recurso e o corpo contendo os dados do tópico.
        return ResponseEntity.created(uri).body(dadosTopico);
    }

    // Método que trata requisições GET para buscar todos os tópicos com paginação e ordenação.
    @GetMapping
    public ResponseEntity<Page<DadosTopico>> buscarTodos(@PageableDefault(size = 10, sort = {"dataCriacao"}, direction = Sort.Direction.ASC)
                                                         Pageable pageable) {
        // Retorna uma resposta HTTP 200 OK com a página de tópicos.
        return ResponseEntity.ok(service.buscarTodos(pageable));
    }

    // Método que trata requisições GET para buscar um tópico pelo ID.
    @GetMapping("/{id}")
    public ResponseEntity<DadosTopico> buscarPeloId(@PathVariable Long id) {
        // Retorna uma resposta HTTP 200 OK com os dados do tópico.
        return ResponseEntity.ok(service.buscar(id));
    }

    // Método que trata requisições PUT para atualizar um tópico.
    @PutMapping("/{id}")
    public ResponseEntity<DadosTopico> atualizar(@PathVariable Long id, @Valid @RequestBody DadosAtualizacaoTopico dados,
                                                 @AuthenticationPrincipal Usuario usuario) {
        // Atualiza o tópico utilizando o serviço e retorna uma resposta HTTP 200 OK com os dados atualizados do tópico.
        return ResponseEntity.ok(service.atualizar(id, dados, usuario));
    }

    // Método que trata requisições DELETE para deletar um tópico.
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id, @AuthenticationPrincipal Usuario usuario) {
        // Deleta o tópico utilizando o serviço.
        service.deletar(id, usuario);

        // Retorna uma resposta HTTP 200 OK sem corpo.
        return ResponseEntity.ok().build();
    }
}
