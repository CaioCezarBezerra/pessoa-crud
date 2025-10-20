package br.com.pessoacrud.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.print.Doc;

import org.primefaces.PrimeFaces;

import br.com.pessoacrud.model.Documento;
import br.com.pessoacrud.model.Endereco;
import br.com.pessoacrud.model.Pessoa;
import br.com.pessoacrud.model.Telefone;
import br.com.pessoacrud.service.IPessoaService;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named(value = "pessoaController")
@ViewScoped
public class PessoaController implements Serializable {

    @EJB
    private IPessoaService pessoaService;

    private Pessoa pessoa;
    private List<Pessoa> pessoas;
    private List<Pessoa> pessoasSelecionadas;
    private Pessoa pessoaSelecionada;
    private Endereco endereco = new Endereco();
    private Documento documento = new Documento();
    private Telefone telefone = new Telefone();

    @PostConstruct
    public void init() {
        carregarPessoas();
        pessoasSelecionadas = new ArrayList<>();
        pessoaSelecionada = new Pessoa();
        telefone = new Telefone();
        endereco = new Endereco();
        documento = new Documento();
    }

    public void carregarPessoas() {
        pessoas = pessoaService.buscarTodasPessoas();
    }

    public void novaPessoa() {
        pessoaSelecionada = new Pessoa();
        endereco = new Endereco();
        documento = new Documento();
        telefone = new Telefone();
    }

    public void prepararNovaPessoa() {
        pessoaSelecionada = new Pessoa();
    endereco = new Endereco();
    documento = new Documento();
    telefone = new Telefone();
    }

    public void salvarPessoa() {
        try {
            if (pessoaSelecionada.getId() == null) {
                pessoaService.guardarPessoa(pessoaSelecionada);
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Pessoa criada com sucesso!"));
            } else {
                pessoaService.atualizarPessoa(pessoaSelecionada);
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Pessoa atualizada com sucesso!"));
            }

            carregarPessoas();
            PrimeFaces.current().executeScript("PF('managePessoaDialog').hide()");

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao salvar pessoa: " + e.getMessage()));
        }
    
    }
    public void editarPessoa() {
        if (pessoaSelecionada != null) {
            pessoaSelecionada = pessoaService.buscarTodasPessoas()
                .stream()
                .filter(p -> p.getId().equals(pessoaSelecionada.getId()))
                .findFirst()
                .orElse(new Pessoa());
        }

}

    public void excluirPessoa() {
        try {
            pessoaService.excluirPessoa(pessoaSelecionada);
            carregarPessoas();
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Pessoa excluída com sucesso!"));
            PrimeFaces.current().executeScript("PF('deletePessoaDialog').hide()");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao excluir pessoa: " + e.getMessage()));
        }
    }

    public void excluirPessoasSelecionadas() {
        try {
            for (Pessoa pessoa : pessoasSelecionadas) {
                pessoaService.excluirPessoa(pessoa);
            }
            carregarPessoas();
            pessoasSelecionadas.clear();
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Pessoas excluídas com sucesso!"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao excluir pessoas: " + e.getMessage()));
        }
    }

    public boolean isTemPessoasSelecionadas() {
        return pessoasSelecionadas != null && !pessoasSelecionadas.isEmpty();
    }

    public String getDeleteButtonMessage() {
        if (isTemPessoasSelecionadas()) {
            int size = pessoasSelecionadas.size();
            return size > 1 ? size + " pessoas selecionadas" : "1 pessoa selecionada";
        }
        return "Excluir";
    }

    public void adicionarEndereco() {
        try {
            if (pessoaSelecionada != null && endereco != null) {
                if (endereco.getCep() == null || endereco.getCep().isEmpty() ||
                    endereco.getLogradouro() == null || endereco.getLogradouro().isEmpty() ||
                    endereco.getNumero() == null || endereco.getNumero().isEmpty() ||
                    endereco.getCidade() == null || endereco.getCidade().isEmpty() ||
                    endereco.getEstado() == null || endereco.getEstado().isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Preencha todos os campos obrigatórios do endereço!"));
                    return;
                }
                
                if (pessoaSelecionada.getEnderecos() == null) {
                    pessoaSelecionada.setEnderecos(new ArrayList<>());
                }
                
                pessoaSelecionada.getEnderecos().add(endereco);
                
                endereco = new Endereco();
                
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Endereço adicionado com sucesso!"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao adicionar endereço: " + e.getMessage()));
        }
    } 



    public void removerEndereco(Endereco endereco) {
        if (pessoaSelecionada != null && pessoaSelecionada.getEnderecos() != null) {
            pessoaSelecionada.getEnderecos().remove(endereco);
        }
    }
    public void adicionarDocumento() {
    try {
        if (pessoaSelecionada != null && documento != null) {
            if (documento.getTipo() == null || documento.getTipo().isEmpty() ||
                documento.getNumero() == null || documento.getNumero().isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Preencha todos os campos obrigatórios do documento!"));
                return;
            }
            
            if (pessoaSelecionada.getDocumentos() == null) {
                pessoaSelecionada.setDocumentos(new ArrayList<>());
            }
            
            pessoaSelecionada.getDocumentos().add(documento);
            documento = new Documento();
            
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Documento adicionado com sucesso!"));
        }
    } catch (Exception e) {
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao adicionar documento: " + e.getMessage()));
    }
}
    public void removerDocumento(Documento documento) {
        if (pessoaSelecionada != null && pessoaSelecionada.getDocumentos() != null) {
            pessoaSelecionada.getDocumentos().remove(documento);
        }
    }
public void adicionarTelefone() {
    try {
        if (pessoaSelecionada != null && telefone != null) {
            if (telefone.getDdd() == null || telefone.getDdd().isEmpty() ||
                telefone.getNumero() == null || telefone.getNumero().isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Preencha todos os campos obrigatórios do telefone!"));
                return;
            }
        
            telefone.setPessoa(pessoaSelecionada);
            
            if (pessoaSelecionada.getTelefones() == null) {
                pessoaSelecionada.setTelefones(new ArrayList<>());
            }
            
            pessoaSelecionada.getTelefones().add(telefone);
            
            telefone = new Telefone();
            
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Telefone adicionado com sucesso!"));
        }
    } catch (Exception e) {
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao adicionar telefone: " + e.getMessage()));
    }
}
    public void removerTelefone(Telefone telefone) {
        if (pessoaSelecionada != null && pessoaSelecionada.getTelefones() != null) {
            pessoaSelecionada.getTelefones().remove(telefone);
        }
    }



    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    public void setPessoas(List<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }

    public List<Pessoa> getPessoasSelecionadas() {
        return pessoasSelecionadas;
    }

    public void setPessoasSelecionadas(List<Pessoa> pessoasSelecionadas) {
        this.pessoasSelecionadas = pessoasSelecionadas;
    }

    public Pessoa getPessoaSelecionada() {
        return pessoaSelecionada;
    }

    public void setPessoaSelecionada(Pessoa pessoaSelecionada) {
        this.pessoaSelecionada = pessoaSelecionada;
    }

    public IPessoaService getPessoaService() {
        return pessoaService;
    }

    public void setPessoaService(IPessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }
    public Endereco getEndereco() {
        return endereco;
    }
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    public Documento getDocumento() {
        return documento;
    }
    public void setDocumento(Documento documento) {
        this.documento = documento;
    }
    public Telefone getTelefone() {
        return telefone;
    }
    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }
}