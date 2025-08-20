# ☕ Projeto Funcionários
# 📝 Visão Geral do Projeto
Este é um sistema de gerenciamento de funcionários desenvolvido em Java e utilizando Apache Maven para o gerenciamento de dependências. O projeto tem como objetivo demonstrar uma aplicação de console simples que realiza as principais operações de um sistema de gestão de dados: criação, leitura, atualização e exclusão (CRUD) de registros de funcionários.

✨ Tecnologias Utilizadas
Java 24: A linguagem de programação principal, utilizada para construir a lógica da aplicação.

Apache Maven: O gerenciador de dependências e a ferramenta de build, que simplifica a compilação e o empacotamento do projeto.

🚀 Como Rodar o Projeto
Siga os passos abaixo para clonar e executar o projeto na sua máquina local.

🔧 Pré-requisitos
Ter o Java Development Kit (JDK) 24 ou superior instalado.

Ter o Apache Maven instalado e configurado nas variáveis de ambiente.

# ▶️ Passos para Execução


# 1. Clone o repositório
git clone https://github.com/Glestman/projeto-funcionarios.git

# 2. Navegue até a pasta do projeto
cd projeto-funcionarios

# 3. Compile o projeto e gere o arquivo .jar
mvn clean package

# 4. Execute a classe principal
java -cp target/projeto-funcionarios-1.0-SNAPSHOT.jar br.com.empresa.ui.Main
📚 Estrutura do Projeto
src/main/java/: Contém todo o código-fonte da aplicação.

br/com/empresa/: O pacote raiz do projeto.

ui/main.java: A classe principal que inicia a aplicação.

pom.xml: O arquivo de configuração do Maven, onde as dependências e o build do projeto são definidos.

🤝 Contribuições
Contribuições, sugestões e relatórios de bugs são muito bem-vindos! Se você encontrar um problema ou tiver uma ideia de melhoria, sinta-se à vontade para abrir uma issue ou enviar um pull request.
