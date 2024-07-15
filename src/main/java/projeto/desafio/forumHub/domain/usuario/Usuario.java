package projeto.desafio.forumHub.domain.usuario;

// Importações necessárias para a classe.
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

// Anotação que indica que esta classe é uma entidade JPA.
@Entity
// Define o nome da tabela no banco de dados para esta entidade.
@Table(name = "usuarios")
// Anotações do Lombok para gerar um construtor sem argumentos, um construtor com todos os argumentos,
// além de métodos getter, setter, equals e hashCode.
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
// Implementa a interface UserDetails do Spring Security para fornecer detalhes do usuário para autenticação e autorização.
public class Usuario implements UserDetails {

    // Define a chave primária da entidade.
    @Id
    // Define a estratégia de geração de valores para a chave primária.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String password;

    // Construtor que inicializa um Usuario a partir de um objeto DadosRegistroUsuario e uma senha.
    public Usuario(DadosRegistroUsuario dados, String password) {
        this.nome = dados.nome();
        this.email = dados.email();
        this.password = password;
    }

    // Retorna as autoridades concedidas ao usuário.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    // Retorna o nome de usuário usado para autenticar o usuário.
    @Override
    public String getUsername() {
        return email;
    }

    // Retorna a senha usada para autenticar o usuário.
    @Override
    public String getPassword() {
        return password;
    }

    // Indica se a conta do usuário não expirou.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Indica se o usuário não está bloqueado.
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Indica se as credenciais do usuário (senha) não expiraram.
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Indica se o usuário está habilitado ou desabilitado.
    @Override
    public boolean isEnabled() {
        return true;
    }
}
