package projeto.desafio.forumHub.domain.topico;

// Importações necessárias para a classe.
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import projeto.desafio.forumHub.domain.usuario.Usuario;

import java.time.LocalDateTime;

// Anotação que indica que esta classe é uma entidade JPA.
@Entity
// Define o nome da tabela no banco de dados para esta entidade.
@Table(name = "topicos")
// Anotações do Lombok para gerar um construtor sem argumentos, um construtor com todos os argumentos,
// além de métodos getter, setter, equals e hashCode.
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
// Anotação que indica que esta entidade deve usar um auditor para preencher automaticamente campos de data.
@EntityListeners(AuditingEntityListener.class)
public class Topico {

    // Define a chave primária da entidade.
    @Id
    // Define a estratégia de geração de valores para a chave primária.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String mensagem;

    // Anotação que indica que este campo deve ser preenchido com a data de criação da entidade.
    @CreatedDate
    private LocalDateTime dataCriacao;

    private String curso;

    // Define uma relação muitos-para-um com a entidade Usuario.
    @ManyToOne
    // Especifica a coluna de junção para a relação.
    @JoinColumn(columnDefinition = "autor_id", referencedColumnName = "id")
    private Usuario autor;

    // Construtor que inicializa um Topico a partir de um objeto DadosRegistroTopico e um Usuario.
    public Topico(DadosRegistroTopico dados, Usuario autor) {
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.curso = dados.curso();
        this.autor = autor;
    }

    // Método que atualiza os campos do Topico a partir de um objeto DadosAtualizacaoTopico.
    public void atualizar(DadosAtualizacaoTopico dados) {
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.curso = dados.curso();
    }
}
