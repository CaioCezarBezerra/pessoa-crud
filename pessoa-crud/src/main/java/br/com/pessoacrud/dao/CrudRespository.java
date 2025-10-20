package br.com.pessoacrud.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;



public abstract class CrudRespository<T> implements PessoaRepository<T>{


    private final Class<T> entityClass;
    
    @PersistenceContext(unitName = "PessoaCRUD-PU")
    protected EntityManager em;
    protected  EntityManager getEntityManager() {
        return em;
    }


    public CrudRespository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }



    @Override
    public T save(T entity){
        getEntityManager().persist(entity);
        return entity;
    }

    @Override
    public T update(T entity){
        getEntityManager().merge(entity);
        return entity;

    }

    @Override
    public T findById(Object entityId){
        getEntityManager().find(entityClass, entityId);
        return null;
    }
    @Override
    public void delete(T entity){
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    @Override
    public List<T> findAll(){
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
}
