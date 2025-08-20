package br.com.empresa;

import br.com.empresa.domain.entity.Funcionario;
import br.com.empresa.domain.entity.Pessoa;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class ProjetoFuncionarios {

    public static void main(String[] args) {
        // ===== FORMATADORES =====
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);

        // ===== 3.1 Inserir todos os funcionários =====
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));

        // ===== 3.2 Remover João =====
        funcionarios.removeIf(f -> f.getNome().equals("João"));

        // ===== 3.3 Imprimir todos com formatação =====
        System.out.println("\n--- Lista de Funcionários ---");
        funcionarios.forEach(f -> {
            System.out.println(
                    "Nome: " + f.getNome() +
                    " | Nascimento: " + f.getDataNascimento().format(dtf) +
                    " | Salário: R$ " + df.format(f.getSalario()) +
                    " | Função: " + f.getFuncao()
            );
        });

        // ===== 3.4 Aumento de 10% =====
        funcionarios.forEach(f -> f.setSalario(f.getSalario().multiply(BigDecimal.valueOf(1.10))));

        // ===== 3.5 Agrupar por função =====
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        // ===== 3.6 Imprimir agrupados por função =====
        System.out.println("\n--- Funcionários agrupados por função ---");
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println(funcao + ":");
            lista.forEach(f -> System.out.println("   - " + f.getNome()));
        });

        // ===== 3.8 Aniversariantes (mês 10 e 12) =====
        System.out.println("\n--- Funcionários com aniversário em Outubro e Dezembro ---");
        funcionarios.stream()
                .filter(f -> f.getDataNascimento().getMonthValue() == 10 || f.getDataNascimento().getMonthValue() == 12)
                .forEach(f -> System.out.println(f.getNome() + " - " + f.getDataNascimento().format(dtf)));

        // ===== 3.9 Funcionário com maior idade =====
        Funcionario maisVelho = funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .orElse(null);

        if (maisVelho != null) {
            int idade = Period.between(maisVelho.getDataNascimento(), LocalDate.now()).getYears();
            System.out.println("\n--- Funcionário mais velho ---");
            System.out.println("Nome: " + maisVelho.getNome() + " | Idade: " + idade + " anos");
        }

        // ===== 3.10 Ordenar por nome =====
        System.out.println("\n--- Funcionários em ordem alfabética ---");
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(f -> System.out.println(f.getNome()));

        // ===== 3.11 Total dos salários =====
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("\nTotal dos salários: R$ " + df.format(totalSalarios));

        // ===== 3.12 Salários mínimos =====
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        System.out.println("\n--- Quantos salários mínimos cada funcionário recebe ---");
        funcionarios.forEach(f -> {
            BigDecimal qtd = f.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_UP);
            System.out.println(f.getNome() + " recebe " + qtd + " salários mínimos");
        });
    }
}
