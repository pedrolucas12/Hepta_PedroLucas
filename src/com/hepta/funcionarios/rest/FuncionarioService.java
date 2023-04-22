package com.hepta.funcionarios.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.hepta.funcionarios.entity.Funcionario;
import com.hepta.funcionarios.persistence.FuncionarioDAO;

@Path("/funcionarios")
public class FuncionarioService {

    private FuncionarioDAO dao;

    public FuncionarioService() {
        dao = new FuncionarioDAO();
    }

    /**
     * Adiciona novo Funcionario
     * 
     * @param Funcionario: Novo Funcionario
     * @return response 200 (OK) - Conseguiu adicionar
     */
    @Path("/salvar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response funcionarioCreate(Funcionario funcionario) {
        try {
            dao.save(funcionario);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return Response.status(Status.OK).build();
    }

    /**
     * Lista todos os Funcionarios
     * 
     * @return response 200 (OK) - Conseguiu listar
     */
    @Path("/listar")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response funcionarioRead() {
        List<Funcionario> funcionarios = new ArrayList<>();
        try {
            funcionarios = dao.getAll();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar Funcionarios").build();
        }

        GenericEntity<List<Funcionario>> entity = new GenericEntity<List<Funcionario>>(funcionarios) {
        };
        return Response.status(Status.OK).entity(entity).build();
    }

    /**
     * Atualiza um Funcionario
     * 
     * @param id:          id do Funcionario
     * @param Funcionario: Funcionario atualizado
     * @return response 200 (OK) - Conseguiu atualizar
     */
    @Path("/atualizar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PUT
    public Response funcionarioUpdate(Funcionario funcionario) {
        return Response.status(Status.NOT_IMPLEMENTED).build();
    }

    /**
     * Remove um Funcionario
     * 
     * @param id: id do funcionario
     * @return response 200 (OK) - Conseguiu remover
     */
    @Path("/deletar/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @DELETE
    public Response FuncionarioDelete(@PathParam("id") Integer id) {
        return Response.status(Status.NOT_IMPLEMENTED).build();
    }

    /**
     * Métodos simples apenas para testar o REST
     * 
     * @return
     */
    @Path("/teste")
    @Produces(MediaType.TEXT_PLAIN)
    @GET
    public String testeJersey() {
        return "Funcionando.";
    }

}
