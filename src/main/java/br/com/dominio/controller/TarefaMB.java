package br.com.dominio.controller;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.dominio.model.Tarefa;
import br.com.dominio.service.TarefaService;
import br.com.dominio.utility.Message;
import br.com.dominio.utility.NegocioException;

@Named
@SessionScoped
public class TarefaMB implements Serializable {


	private static final long serialVersionUID = 1L;
	
	@Inject
	private Tarefa tarefa;
	@Inject
	private TarefaService service;
	
	private List<Tarefa> tarefas;
	
	@PostConstruct
	public void carregar() {
		tarefas = service.findAll();
		
		for (Iterator<Tarefa> iterator = tarefas.iterator(); iterator.hasNext();) {
		    Tarefa item = iterator.next();
		    
		    if(item.getSituacao().equals("Concluída"))
		    iterator.remove();
		}
	}
	
	public String adicionar() {
		try {
			tarefa.setSituacao("Em andamento");
			service.salvar(tarefa);
			tarefa = new Tarefa();
			carregar();
			
			Message.info("Salvo com sucesso!");
		}catch(NegocioException e){
			Message.erro(e.getMessage());
		}
		
		return "/CadastroTarefas";
	}
	
	public void remover(){
		try {
			service.remover(tarefa);
			tarefa = new Tarefa();
			carregar();
			
			Message.info(tarefa.getTitulo()+"Foi removido!");
		}catch(NegocioException e){
			Message.erro(e.getMessage());
		}
	}
	
	public void concluirTarefa() {
		try {
			tarefa.setSituacao("Concluída");
			service.salvar(tarefa);
			tarefa = new Tarefa();
			carregar();
			
		}catch(NegocioException e){
			Message.erro(e.getMessage());
		}
	}
	public String editar() {
		return "/EditarTarefa";
	}

	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public List<Tarefa> getTarefas() {
		return tarefas;
	}
	
	
}