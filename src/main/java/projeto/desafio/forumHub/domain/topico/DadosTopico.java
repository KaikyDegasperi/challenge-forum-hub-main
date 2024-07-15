package projeto.desafio.forumHub.domain.topico;

import java.time.LocalDateTime;

// Definição de um record chamado DadosTopico.
public record DadosTopico(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        String curso
) {

    // Construtor adicional que aceita um objeto Topico e inicializa o record com os valores do Topico.
    public DadosTopico(Topico topico) {
        // Chama o construtor principal do record com os valores extraídos do objeto Topico.
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao(), topico.getCurso());
    }
}
