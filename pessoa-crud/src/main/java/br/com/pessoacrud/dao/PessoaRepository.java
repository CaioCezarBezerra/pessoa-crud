package br.com.pessoacrud.dao;

public interface PessoaRepository<T> {
    T save(T entity);

    T update(T entity);

    T findById(Object entityId);
    void delete(T entity);

    java.util.List<T> findAll();

    
}