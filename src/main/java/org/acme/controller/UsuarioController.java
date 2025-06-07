package org.acme.controller;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.entity.UsuarioEntity;
import org.acme.service.UsuarioService;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GET
    public Response findAll(@QueryParam("page") @DefaultValue("0") Integer page,
                            @QueryParam("pageSize") @DefaultValue("15") Integer pageSize) {
        return Response.ok(usuarioService.findAll(page, pageSize)).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Integer id) {
        return Response.ok(usuarioService.findById(id)).build();
    }

    @POST
    @Transactional
    public Response create(UsuarioEntity usuario) {
        return Response.ok(usuarioService.create(usuario)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Integer id, UsuarioEntity usuario) {
        return Response.ok(usuarioService.update(id, usuario)).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Integer id) {
        usuarioService.delete(id);
        return Response.noContent().build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(UsuarioEntity credentials) {
        UsuarioEntity usuario = usuarioService.findByEmailAndSenha(credentials.getEmail(), credentials.getSenha());

        if (usuario != null) {
            return Response.ok(usuario).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{"message": "Email ou senha inválidos."}")
                    .build();
        }
    }
}
