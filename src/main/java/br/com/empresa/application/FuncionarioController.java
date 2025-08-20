package br.com.empresa.application;

import br.com.empresa.domain.entity.Funcionario;
import br.com.empresa.infrastructure.repository.FuncionarioRepositoryImpl;
import br.com.empresa.service.FuncionarioService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class FuncionarioController {
    private final FuncionarioRepositoryImpl repository;
    private final FuncionarioService service;

    public FuncionarioController() {
        this.repository = new FuncionarioRepositoryImpl();
        this.service = new FuncionarioService();
    }

    public void adicionarFuncionario(Funcionario funcionario) {
        repository.salvar(funcionario);
    }

    public void removerFuncionario(String nome) {
        repository.listarTodos().removeIf(f -> f.getNome().equalsIgnoreCase(nome));
    }

    public List<Funcionario> listarFuncionarios() {
        return repository.listarTodos();
    }

    public void aumentarSalario(BigDecimal percentual) {
        service.aumentarSalario(repository.listarTodos(), percentual);
    }

    public Map<String, List<Funcionario>> agruparPorFuncao() {
        return service.agruparPorFuncao(repository.listarTodos());
    }

    public Funcionario funcionarioMaisVelho() {
        return service.maisVelho(repository.listarTodos());
    }

    public List<Funcionario> aniversariantes(int... meses) {
        return service.aniversariantes(repository.listarTodos(), meses);
    }

    public BigDecimal somarSalarios() {
        return service.somarSalarios(repository.listarTodos());
    }

    public List<Funcionario> ordenarPorNome() {
        return service.ordenarPorNome(repository.listarTodos());
    }

    public FuncionarioService getService() {
        return service;
    }
}
