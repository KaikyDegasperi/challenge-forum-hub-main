package projeto.desafio.forumHub.domain.topico;

// Importações necessárias para a classe.
import jakarta.validation.constraints.NotBlank;

// Definição de um record chamado DadosRegistroTopico.
public record DadosRegistroTopico(

        // O título do tópico, que não pode ser nulo ou vazio.
        @NotBlank
        String titulo,

        // A mensagem do tópico, que não pode ser nulo ou vazio.
        @NotBlank
        String mensagem,

        // O curso relacionado ao tópico, que não pode ser nulo ou vazio.
        @NotBlank
        String curso
) {
}
