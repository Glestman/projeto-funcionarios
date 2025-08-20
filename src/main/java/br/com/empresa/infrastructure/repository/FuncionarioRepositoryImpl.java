package br.com.empresa.infrastructure.repository;

import br.com.empresa.domain.entity.Funcionario;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioRepositoryImpl {
    private final List<Funcionario> funcionarios = new ArrayList<>();

    public void salvar(Funcionario funcionario) {
        funcionarios.add(funcionario);
    }

    public void remover(Funcionario funcionario) {
        funcionarios.remove(funcionario);
    }

    public List<Funcionario> listarTodos() {
        return new ArrayList<>(funcionarios);
    }
}
