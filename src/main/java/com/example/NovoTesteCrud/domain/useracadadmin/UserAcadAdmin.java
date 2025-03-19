package com.example.NovoTesteCrud.domain.useracadadmin;

import com.example.NovoTesteCrud.domain.acad.Academia;
import com.example.NovoTesteCrud.domain.userbase.IRequestUsuario;
import com.example.NovoTesteCrud.domain.userbase.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "user_acad_admin")
@Table(name = "user_acad_admin")
@NoArgsConstructor
public class UserAcadAdmin extends Usuario {

    @Column(name = "cnpj", unique = true, length = 14)
    private String cnpj;

    @OneToOne
    @JoinColumn(name = "academia_id", unique = true)
    private Academia academia;

    public UserAcadAdmin(String name, String email, String senha, String telefone, String cnpj, Academia academia) {
        super(name, email, senha, telefone);
        this.cnpj = cnpj;
        this.academia = academia;
    }

    @Override
    public void atualizarDados(IRequestUsuario data) {
        if (data instanceof RequestUserAcadAdmin adminData) {
            super.setName(adminData.name());
            super.setEmail(adminData.email());
            super.setSenha(adminData.senha());
            super.setTelefone(adminData.telefone());

            this.cnpj = adminData.cnpj();
            this.academia = new Academia(adminData.academiaId());
        }
    }
}
