package projeto.desafio.forumHub.domain.topico;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import projeto.desafio.forumHub.domain.usuario.Usuario;
import projeto.desafio.forumHub.infra.exception.NotFoundException;
import projeto.desafio.forumHub.infra.exception.RegisterException;

import java.util.Optional;

// Anotação que define esta classe como um serviço Spring.
@Service
// Anotação do Lombok para gerar um construtor com todos os atributos finais como parâmetros.
@RequiredArgsConstructor
public class TopicoService {
    // Repositório para acesso aos dados do Topico.
    private final TopicoRepository repository;

    // Método para registrar um novo tópico.
    public DadosTopico registrar(DadosRegistroTopico dados, Usuario usuario) {
        // Verifica se já existe um tópico com o mesmo título e mensagem.
        Optional<Topico> optionalTopico = repository.findByTituloAndMensagem(dados.titulo(), dados.mensagem());

        if(optionalTopico.isPresent()) {
            throw new RegisterException("Não é possivel registrar um topico com o mesmo titulo e mensagem");
        }
        // Salva o novo tópico no repositório.
        Topico topico = repository.save(new Topico(dados, usuario));
        return new DadosTopico(topico);
    }

    // Método para buscar todos os tópicos com paginação.
    public Page<DadosTopico> buscarTodos(Pageable pageable) {
        return repository.findAll(pageable).map(DadosTopico::new);
    }

    // Método para buscar um tópico pelo seu ID.
    public DadosTopico buscar(Long id) {
        return new DadosTopico(this.buscarTopicoPeloId(id));
    }

    // Método auxiliar para buscar um tópico pelo seu ID.
    private Topico buscarTopicoPeloId(Long id) {
        Optional<Topico> optionalTopico = repository.findById(id);

        if(optionalTopico.isEmpty()) {
            throw new NotFoundException("Topico com esse id não encontrado");
        }
        return optionalTopico.get();
    }

    // Método para atualizar um tópico.
    public DadosTopico atualizar(Long id, DadosAtualizacaoTopico dados, Usuario usuario) {
        // Verifica se já existe um tópico com o mesmo título e mensagem.
        Optional<Topico> optionalTopico = repository.findByTituloAndMensagem(dados.titulo(), dados.mensagem());

        if(optionalTopico.isPresent()) {
            throw new RegisterException("Não é possivel registrar um topico com o mesmo titulo e mensagem");
        }
        // Busca o tópico pelo ID e autor.
        Topico topico = this.buscarTopicoPeloIdEUsuario(id, usuario);
        // Atualiza os dados do tópico.
        topico.atualizar(dados);

        return new DadosTopico(repository.save(topico));
    }

    // Método para deletar um tópico.
    public void deletar(Long id, Usuario usuario) {
        Topico topico = this.buscarTopicoPeloIdEUsuario(id, usuario);
        repository.deleteById(topico.getId());
    }

    // Método auxiliar para buscar um tópico pelo seu ID e autor.
    private Topico buscarTopicoPeloIdEUsuario(Long id, Usuario usuario) {
        Optional<Topico> optionalTopico = repository.findByIdAndAutor(id, usuario);

        if(optionalTopico.isEmpty()) {
            throw new NotFoundException("Topico com esse id não encontrado");
        }
        return optionalTopico.get();
    }
}
