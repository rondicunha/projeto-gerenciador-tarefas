package br.com.dominio.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.dominio.dao.DAO;
import br.com.dominio.model.Tarefa;
import br.com.dominio.utility.NegocioException;

public class TarefaService implements Serializable {


	private static final long serialVersionUID = 1L;
	
	@Inject
	private DAO<Tarefa> dao;
	
	public void salvar(Tarefa t)throws NegocioException {
		
		dao.salvar(t);
	}
	public void remover(Tarefa t)throws NegocioException {
		dao.remover(Tarefa.class, t.getId());
	}
	public List<Tarefa> findAll(){
		return dao.findAll("select t from Tarefa t order by t.id");
	}
}

