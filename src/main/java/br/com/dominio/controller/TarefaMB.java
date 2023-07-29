package br.com.dominio.controller;

import java.io.Serializable;
import java.util.ArrayList;
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
	private Long numeroBusca;
    private String tituloBusca;
    private String prioridadeBusca;
    private String responsavelBusca;
    private List<Tarefa> tarefasFiltradas;
    private List<Tarefa> tarefasConcluidas;
	
	@PostConstruct
	public void carregar() {
		tarefas = service.findAll();
		tarefasFiltradas = tarefas;
		tarefasConcluidas = new ArrayList<>();
		
		for (Iterator<Tarefa> iterator = tarefas.iterator(); iterator.hasNext();) {
		    Tarefa item = iterator.next();
		    
		    if(item.getSituacao().equals("Concluída")) {
		    tarefasConcluidas.add(item);
		    iterator.remove();
		    }
		    
		}
	}
	

	public List<Tarefa> getTarefasFiltradas() {
		return tarefasFiltradas;
	}

	public void setTarefasFiltradas(List<Tarefa> tarefasFiltradas) {
		this.tarefasFiltradas = tarefasFiltradas;
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
	public String paginaEditar() {
		return "/EditarTarefa";
	}
	public String editar() {
		try {
			tarefa.setSituacao("Em andamento");
			service.salvar(tarefa);
			tarefa = new Tarefa();
			carregar();
			
			Message.info("Salvo com sucesso!");
		}catch(NegocioException e){
			Message.erro(e.getMessage());
		}
		
		return "/GerenciarTarefas";
	}
	
	public List<Tarefa> filtrarTarefas() {
		
		System.out.print("Chegou aqui");
		System.out.print(numeroBusca);
		System.out.print(tituloBusca);
		System.out.print(prioridadeBusca);
		System.out.print(responsavelBusca);
		
		tarefasFiltradas = new ArrayList<>();
		
        for (Tarefa tarefa : tarefas) {
            boolean correspondeNumero = (numeroBusca == null) || (tarefa.getId() == numeroBusca);
            boolean correspondeTitulo = (tituloBusca == null) || tarefa.getTitulo().toLowerCase().contains(tituloBusca.toLowerCase());
            boolean correspondePrioridade = ((prioridadeBusca == null) ||  prioridadeBusca.isEmpty()) || tarefa.getPrioridade().equalsIgnoreCase(prioridadeBusca);
            boolean correspondeResponsavel = ((responsavelBusca == null) || responsavelBusca.isEmpty()) || tarefa.getResponsavel().equalsIgnoreCase(responsavelBusca);

            if (correspondeNumero && correspondeTitulo && correspondePrioridade && correspondeResponsavel) {
                tarefasFiltradas.add(tarefa);
            }
        }
        System.out.print(tarefasFiltradas);
        return tarefasFiltradas;
    }
	
	public String cancelar() {
		carregar();
		return "/GerenciarTarefas";
	}
	
	public Long getNumeroBusca() {
		return numeroBusca;
	}

	public void setNumeroBusca(Long numeroBusca) {
		this.numeroBusca = numeroBusca;
	}

	public String getTituloBusca() {
		return tituloBusca;
	}

	public void setTituloBusca(String tituloBusca) {
		this.tituloBusca = tituloBusca;
	}

	public String getPrioridadeBusca() {
		return prioridadeBusca;
	}

	public void setPrioridadeBusca(String prioridadeBusca) {
		this.prioridadeBusca = prioridadeBusca;
	}

	public String getResponsavelBusca() {
		return responsavelBusca;
	}

	public void setResponsavelBusca(String responsavelBusca) {
		this.responsavelBusca = responsavelBusca;
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

	public List<Tarefa> getTarefasConcluidas() {
		return tarefasConcluidas;
	}

	public void setTarefasConcluidas(List<Tarefa> tarefasConcluidas) {
		this.tarefasConcluidas = tarefasConcluidas;
	}

	public void setTarefas(List<Tarefa> tarefas) {
		this.tarefas = tarefas;
	}
	
	
	
}