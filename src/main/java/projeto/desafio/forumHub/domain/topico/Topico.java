package projeto.desafio.forumHub.domain.topico;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import projeto.desafio.forumHub.domain.usuario.Usuario;

import java.time.LocalDateTime;

@Entity
@Table(name = "topicos")
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
@EqualsAndHashCode(of = "id")
@EntityListeners(AuditingEntityListener.class)
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String mensagem;

    @CreatedDate
    private LocalDateTime dataCriacao;

    private String curso;

    @ManyToOne
    @JoinColumn(columnDefinition = "autor_id",referencedColumnName = "id")
    private Usuario autor;

    public Topico(DadosRegistroTopico dados, Usuario autor) {
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.curso = dados.curso();
        this.autor = autor;
    }

    public void atualizar(DadosAtualizacaoTopico dados) {
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.curso = dados.curso();
    }
}
