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

import com.hepta.funcionarios.dto.FuncionarioDto;
import com.hepta.funcionarios.entity.Funcionario;
import com.hepta.funcionarios.entity.Setor;
import com.hepta.funcionarios.persistence.FuncionarioDAO;
import com.hepta.funcionarios.persistence.SetorDAO;

@Path("/funcionarios")
public class FuncionarioService {

    private FuncionarioDAO dao;

    private SetorDAO setorDao;
    
    public FuncionarioService() {
        dao = new FuncionarioDAO();
        setorDao = new SetorDAO();
    }

    /**
     * Adiciona novo Funcionario
     * 
     * @param Funcionario: Novo Funcionario
     * @return response 200 (OK) - Conseguiu adicionar
     */
    @Path("/salvar")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response funcionarioCreate(FuncionarioDto dto) {
        try {
        	Funcionario funcionario = new Funcionario();
        	funcionario.setNome(dto.getNome());
        	Setor setor = buscarSetor(dto.getSetor());
        	funcionario.setSetor(setor);
        	funcionario.setSalario(Double.parseDouble(dto.getSalario()));
        	funcionario.setEmail(dto.getEmail());
        	funcionario.setIdade(dto.getIdade());
        	
            dao.save(funcionario);
            return Response.status(Status.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao salvar funcionário: " + e.getMessage()).build();
        }
    }
    
    @Path("/find/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response funcionarioFind(@PathParam("id") String userId) {
		FuncionarioDto dto = new FuncionarioDto();
    	try {
    		Funcionario func =  dao.find(Integer.parseInt(userId));
    		dto.setId(func.getId().longValue());
    		dto.setNome(func.getNome());
    		dto.setIdade(func.getId());
    		dto.setEmail(func.getEmail());
    		dto.setSalario(func.getSalario().toString());
    		dto.setSetor(func.getSetor().getId());
            
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao procurar funcionário: " + e.getMessage()).build();
        }
        return Response.status(Status.OK).entity(dto).build();
    }

    private Setor buscarSetor(Integer setorId) {
    	try {
            return setorDao.find(setorId);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
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
    public Response funcionarioUpdate(FuncionarioDto funcionario) {
    	FuncionarioDto dto = new FuncionarioDto();
    	try {
    		Funcionario func =  dao.find(Integer.parseInt(funcionario.getId().toString()));
    		dto.setId(func.getId().longValue());
    		dto.setNome(func.getNome());
    		dto.setIdade(func.getId());
    		dto.setEmail(func.getEmail());
    		dto.setSalario(func.getSalario().toString());
    		dto.setSetor(func.getSetor().getId());
    		dao.update(func);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao procurar funcionário: " + e.getMessage()).build();
        }
        return Response.status(Status.OK).entity(dto).build();
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
        try {
            FuncionarioDAO dao = new FuncionarioDAO();
            dao.delete(id);
            return Response.status(Status.OK).build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
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
