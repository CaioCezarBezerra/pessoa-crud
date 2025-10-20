package br.com.pessoacrud.dao;



import br.com.pessoacrud.model.Pessoa;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;


@Stateless
public class UsuarioRepository extends CrudRespository<Pessoa> {

    public UsuarioRepository() {
        super( Pessoa.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    //public void List<Pessoa> findByName(String name) {
   //     return em.createQuery("SELECT p FROM Pessoa p WHERE p.nome LIKE :name", Pessoa.class)
    //             .setParameter("name", "%" + name + "%")
   //              .getResultList();
    //}


    


    
}
