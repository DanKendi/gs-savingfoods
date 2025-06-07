package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.UsuarioEntity;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepositoryBase<UsuarioEntity, Integer> {
    public UsuarioEntity findByEmailAndSenha(String email, String senha) {
        return find("email = ?1 and senha = ?2", email, senha).firstResult();
    }
    
}
