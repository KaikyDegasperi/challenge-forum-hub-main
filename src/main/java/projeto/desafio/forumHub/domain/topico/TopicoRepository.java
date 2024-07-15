package projeto.desafio.forumHub.domain.topico;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.desafio.forumHub.domain.usuario.Usuario;

import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico,Long> {

    Optional<Topico> findByTituloAndMensagem(String titulo, String mensagem);

    Optional<Topico> findByIdAndAutor(Long id, Usuario autor);
}
