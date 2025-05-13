package com.example.NovoTesteCrud.domain.useracadadmin;

import com.example.NovoTesteCrud.domain.acad.Academia;
import com.example.NovoTesteCrud.domain.userbase.Role;
import com.example.NovoTesteCrud.domain.userbase.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Delegate;

@Entity
@Table(name = "user_acad_admin")
@NoArgsConstructor
@Getter
@Setter
public class UserAcadAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @Delegate
    private Usuario usuario;

    @Column(name = "cnpj", unique = true, length = 30)
    private String cnpj;

    @OneToOne
    @JoinColumn(name = "academia_id", unique = true)
    private Academia academia;

    public UserAcadAdmin(Long id, String name, String email, String senha, String telefone, String cnpj, Academia academia, Role role) {
        this.usuario = new Usuario(name, email, senha, telefone, role);
        this.id = id;
        this.cnpj = cnpj;
        this.academia = academia;
    }

    public void atualizarDados(RequestUserAcadAdmin data) {
        usuario.setNome(data.name());
        usuario.setEmail(data.email());
        usuario.setSenha(data.senha());
        usuario.setTelefone(data.telefone());
        this.cnpj = data.cnpj();
        this.academia = new Academia(data.academiaId());
    }
}
