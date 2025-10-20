package br.com.pessoacrud.service;

import java.util.List;

import br.com.pessoacrud.model.Pessoa;
import jakarta.ejb.Local;


@Local
public interface IPessoaService {
    
    Pessoa guardarPessoa(Pessoa pessoa);

    Pessoa atualizarPessoa(Pessoa pessoa);

    Pessoa excluirPessoa(Pessoa pessoa);

    List<Pessoa> buscarTodasPessoas();

    
}
