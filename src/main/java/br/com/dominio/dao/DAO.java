package br.com.dominio.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.dominio.model.Base;

public class DAO<T extends Base> implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	private static EntityManager manager = ConnectionFactory.getEntityManager();
	
	public T findById(Class<T> clazz, Long id) {
		return manager.find(clazz, id);
	}
	
	public void salvar(T t) {
		try {
			manager.getTransaction().begin();
			if(t.getId() == null) {
				manager.persist(t);
			}
			else {
				manager.merge(t);
			}
			manager.getTransaction().commit();
		}catch(Exception e) {
			manager.getTransaction().rollback();
		}
	}
	
	public void remover(Class<T> clazz, Long id) {
		T t = findById(clazz, id);
		
		try {
			manager.getTransaction().begin();
			manager.remove(t);
			manager.getTransaction().commit();
		}catch(Exception e) {
			manager.getTransaction().rollback();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findAll(String jpql){
		Query query = manager.createQuery(jpql);
		
		return query.getResultList();
	}
}