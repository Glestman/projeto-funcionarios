package br.com.empresa.service;

import br.com.empresa.domain.entity.Funcionario;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class FuncionarioService {
    private static final BigDecimal SALARIO_MINIMO = new BigDecimal("1212.00");

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DecimalFormat df = new DecimalFormat("#,##0.00");

    public void aumentarSalario(List<Funcionario> funcionarios, BigDecimal percentual) {
        for (Funcionario f : funcionarios) {
            BigDecimal novoSalario = f.getSalario().add(f.getSalario().multiply(percentual));
            f.setSalario(novoSalario);
        }
    }

    public Map<String, List<Funcionario>> agruparPorFuncao(List<Funcionario> funcionarios) {
        return funcionarios.stream().collect(Collectors.groupingBy(Funcionario::getFuncao));
    }

    public List<Funcionario> aniversariantes(List<Funcionario> funcionarios, int... meses) {
        Set<Integer> mesesSet = Arrays.stream(meses).boxed().collect(Collectors.toSet());
        return funcionarios.stream()
                .filter(f -> mesesSet.contains(f.getDataNascimento().getMonthValue()))
                .collect(Collectors.toList());
    }

    public Funcionario maisVelho(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .orElse(null);
    }

    public List<Funcionario> ordenarPorNome(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .collect(Collectors.toList());
    }

    public BigDecimal somarSalarios(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String formatarFuncionario(Funcionario f) {
        return String.format("Nome: %s | Nascimento: %s | Função: %s | Salário: R$ %s",
                f.getNome(),
                f.getDataNascimento().format(formatter),
                f.getFuncao(),
                df.format(f.getSalario()));
    }

    public String calcularQtdSalariosMinimos(Funcionario f) {
        BigDecimal qtd = f.getSalario().divide(SALARIO_MINIMO, 2, BigDecimal.ROUND_HALF_UP);
        return f.getNome() + " ganha " + qtd + " salários mínimos.";
    }

    public int calcularIdade(Funcionario f) {
        return Period.between(f.getDataNascimento(), LocalDate.now()).getYears();
    }
}
