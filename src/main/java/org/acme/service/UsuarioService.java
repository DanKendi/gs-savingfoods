package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.acme.entity.UsuarioEntity;
import org.acme.repository.UsuarioRepository;

import java.util.List;

@ApplicationScoped
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioEntity> findAll(int page, int pageSize) {
        try {
            return usuarioRepository.findAll().page(page, pageSize).list();
        } catch (Exception e) {
            throw new WebApplicationException("Erro ao buscar usuários", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    public UsuarioEntity findById(Integer id) {
        try {
            return usuarioRepository.findByIdOptional(id)
                    .orElseThrow(() -> new WebApplicationException("Usuário não encontrado com id: " + id, Response.Status.NOT_FOUND));
        } catch (WebApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new WebApplicationException("Erro inesperado ao buscar usuário", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    public UsuarioEntity create(UsuarioEntity usuario) {
        try {
            usuarioRepository.persist(usuario);
            return usuario;
        } catch (Exception e) {
            throw new WebApplicationException("Erro ao criar usuário", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    public UsuarioEntity update(Integer id, UsuarioEntity updated) {
        try {
            UsuarioEntity usuario = findById(id);
            usuario.setNome(updated.getNome());
            usuario.setEmail(updated.getEmail());
            usuario.setCnpj(updated.getCnpj());
            usuario.setSenha(updated.getSenha());
            usuario.setTipo_usuario(updated.getTipo_usuario());

            usuarioRepository.persist(usuario);
            return usuario;
        } catch (WebApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new WebApplicationException("Erro ao atualizar usuário", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    public void delete(Integer id) {
        try {
            UsuarioEntity usuario = findById(id);
            usuarioRepository.delete(usuario);
        } catch (WebApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new WebApplicationException("Erro ao excluir usuário", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    public UsuarioEntity login(String email, String senha) {
        return usuarioRepository.findByEmailAndSenha(email, senha);
    }
}
