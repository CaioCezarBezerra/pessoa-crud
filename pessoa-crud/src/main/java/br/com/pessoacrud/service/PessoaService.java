package br.com.pessoacrud.service;

import java.util.List;

import br.com.pessoacrud.dao.PessoaRepository;
import br.com.pessoacrud.model.Pessoa;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
public class PessoaService implements IPessoaService {

    @EJB
    private PessoaRepository<Pessoa> pessoaRepository;

    public PessoaService() {

    }

    @Override
    public Pessoa guardarPessoa(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
        
    }

    @Override
    public Pessoa atualizarPessoa(Pessoa pessoa) {
        return pessoaRepository.update(pessoa);
    }

    @Override
    public Pessoa excluirPessoa(Pessoa pessoa) {
        pessoaRepository.delete(pessoa);
        return pessoa;
    }

    @Override
    public List<Pessoa> buscarTodasPessoas() {
        return pessoaRepository.findAll();
    }
}