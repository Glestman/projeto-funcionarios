package br.com.empresa.ui;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import br.com.empresa.domain.entity.Funcionario;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.NumberFormat;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

public class FuncionarioUI extends JFrame {

    private JTextArea textArea;
    private List<Funcionario> funcionarios;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DecimalFormat df = new DecimalFormat("#,##0.00");

    public FuncionarioUI() {
        setTitle("Gerenciamento de Funcionários");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Ações");

        String[] opcoes = {
                "3.1 Inserir Funcionário",
                "3.2 Remover João",
                "3.3 Listar Funcionários",
                "3.4 Aumentar 10% salário",
                "3.5 Agrupar por função",
                "3.8 Aniversariantes (10 e 12)",
                "3.9 Funcionário mais velho",
                "3.10 Ordenar por nome",
                "3.11 Total salários",
                "3.12 Salários mínimos"
        };

        for (String opcao : opcoes) {
            JMenuItem item = new JMenuItem(opcao);
            item.addActionListener(e -> executarAcao(opcao));
            menu.add(item);
        }

        menuBar.add(menu);
        setJMenuBar(menuBar);

        // Lista inicial fixa
        funcionarios = new ArrayList<>();
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
    }

    private void executarAcao(String opcao) {
        switch (opcao) {
            case "3.1 Inserir Funcionário":
                inserirFuncionario();
                break;
            case "3.2 Remover João":
                funcionarios.removeIf(f -> f.getNome().equalsIgnoreCase("João"));
                textArea.setText("Funcionário João removido!");
                break;
            case "3.3 Listar Funcionários":
                listarFuncionarios();
                break;
            case "3.4 Aumentar 10% salário":
                funcionarios.forEach(f -> f.setSalario(f.getSalario().multiply(new BigDecimal("1.1"))));
                textArea.setText("Salários atualizados com 10% de aumento!");
                break;
            case "3.5 Agrupar por função":
                Map<String, List<Funcionario>> agrupados = funcionarios.stream()
                        .collect(Collectors.groupingBy(Funcionario::getFuncao));
                StringBuilder sb = new StringBuilder("Funcionários agrupados por função:\n");
                agrupados.forEach((funcao, lista) -> {
                    sb.append("\n").append(funcao).append(":\n");
                    lista.forEach(f -> sb.append(" - ").append(f.getNome()).append("\n"));
                });
                textArea.setText(sb.toString());
                break;
            case "3.8 Aniversariantes (10 e 12)":
                String aniversarios = funcionarios.stream()
                        .filter(f -> f.getDataNascimento().getMonthValue() == 10 || f.getDataNascimento().getMonthValue() == 12)
                        .map(Funcionario::getNome)
                        .collect(Collectors.joining("\n"));
                textArea.setText("Aniversariantes (Outubro e Dezembro):\n" + aniversarios);
                break;
            case "3.9 Funcionário mais velho":
                Funcionario maisVelho = funcionarios.stream()
                        .min(Comparator.comparing(Funcionario::getDataNascimento))
                        .orElse(null);
                if (maisVelho != null) {
                    int idade = Period.between(maisVelho.getDataNascimento(), LocalDate.now()).getYears();
                    textArea.setText("Mais velho:\n" + maisVelho.getNome() + " - " + idade + " anos");
                }
                break;
            case "3.10 Ordenar por nome":
                funcionarios.sort(Comparator.comparing(Funcionario::getNome));
                listarFuncionarios();
                break;
            case "3.11 Total salários":
                BigDecimal total = funcionarios.stream()
                        .map(Funcionario::getSalario)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                textArea.setText("Total de salários: R$ " + df.format(total));
                break;
            case "3.12 Salários mínimos":
                BigDecimal salarioMinimo = new BigDecimal("1212.00");
                StringBuilder sb2 = new StringBuilder("Salários em múltiplos de salário mínimo:\n");
                for (Funcionario f : funcionarios) {
                    BigDecimal qtd = f.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_UP);
                    sb2.append(f.getNome()).append(": ").append(qtd).append(" salários mínimos\n");
                }
                textArea.setText(sb2.toString());
                break;
        }
    }

;

// ... (seus outros imports)

private void inserirFuncionario() {
        JTextField nomeField = new JTextField();
        
        MaskFormatter dateFormatter = null;
        try {
            dateFormatter = new MaskFormatter("##/##/####");
            dateFormatter.setPlaceholderCharacter('_');
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        JFormattedTextField dataField = new JFormattedTextField(dateFormatter);
        
        JTextField funcaoField = new JTextField();

        // 1. Crie o DecimalFormat para a entrada de salário
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        NumberFormatter formatter = new NumberFormatter(decimalFormat);
        formatter.setValueClass(Double.class);
        formatter.setAllowsInvalid(false);
        formatter.setOverwriteMode(true);

        // 2. Crie o JFormattedTextField usando o NumberFormatter
        JFormattedTextField salarioField = new JFormattedTextField(formatter);
        salarioField.setValue(0.00); // Valor inicial

        salarioField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                salarioField.selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (salarioField.getValue() == null) {
                    salarioField.setValue(0.00);
                }
            }
        });
        
        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Data de Nascimento:"));
        panel.add(dataField);
        panel.add(new JLabel("Função:"));
        panel.add(funcaoField);
        panel.add(new JLabel("Salário:"));
        panel.add(salarioField);

        int result = JOptionPane.showConfirmDialog(this, panel,
                "Adicionar Funcionário", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String nome = nomeField.getText().trim();
                
                String dataTexto = dataField.getText().replace('_', '0').trim();
                LocalDate dataNasc = LocalDate.parse(dataTexto, dtf);
                
                String funcao = funcaoField.getText().trim();
                
                // 3. Obtenha o valor numérico (agora como Double)
                Number valorSalario = (Number) salarioField.getValue();
                BigDecimal salario = BigDecimal.valueOf(valorSalario.doubleValue());

                Funcionario novo = new Funcionario(nome, dataNasc, salario, funcao);
                funcionarios.add(novo);

                textArea.setText("✅ Funcionário adicionado:\n" + nome + " - " + funcao);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Erro ao adicionar funcionário!\nVerifique os campos.\nFormato de data: dd/MM/yyyy",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void listarFuncionarios() {
        StringBuilder sb = new StringBuilder("Lista de Funcionários:\n");
        for (Funcionario f : funcionarios) {
            int idade = Period.between(f.getDataNascimento(), LocalDate.now()).getYears();
            sb.append(f.getNome()).append(" | ")
                    .append(dtf.format(f.getDataNascimento())).append(" | ")
                    .append(f.getFuncao()).append(" | R$ ")
                    .append(df.format(f.getSalario())).append(" | ")
                    .append(idade).append(" anos\n");
        }
        textArea.setText(sb.toString());
    }

}
