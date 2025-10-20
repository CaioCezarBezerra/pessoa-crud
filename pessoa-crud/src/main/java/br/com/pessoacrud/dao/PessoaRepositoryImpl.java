package br.com.pessoacrud.dao;

import java.util.List;

import br.com.pessoacrud.model.Pessoa;
import jakarta.ejb.Stateless;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
@Named("pessoaRepository")
public class PessoaRepositoryImpl implements PessoaRepository<Pessoa> {

    @PersistenceContext(unitName = "PessoaCRUD-PU")
    private EntityManager entityManager;

    @Override
    public Pessoa save(Pessoa pessoa) {
        entityManager.persist(pessoa);
        return pessoa;
    }

    @Override
    public Pessoa update(Pessoa pessoa) {
        return entityManager.merge(pessoa);
    }

    @Override
    public Pessoa findById(Object entityId) {
        return entityManager.find(Pessoa.class, entityId);
    }

    @Override
    public void delete(Pessoa pessoa) {
        Pessoa managedPessoa = entityManager.contains(pessoa) ? pessoa : entityManager.merge(pessoa);
        entityManager.remove(managedPessoa);
    }

    @Override
    public List<Pessoa> findAll() {
        return entityManager.createQuery("SELECT p FROM Pessoa p", Pessoa.class)
                          .getResultList();
    }
}