package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.UsuarioEntity;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepositoryBase<UsuarioEntity, Integer> {
    

}
