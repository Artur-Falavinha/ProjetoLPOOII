# Projeto LPOOII – Locadora de Veículos

Aplicação desktop em Java (Swing) que controla clientes, veículos, locações, devoluções e vendas para a disciplina LPOO II.

## Requisitos

- Java 17+
- Maven 3.9+
- MySQL rodando em `127.0.0.1:3306`

## Banco de dados

Execute o script `database/projetolpooii.sql` no MySQL Workbench (ou cliente equivalente) para criar o schema `projetolpooii` e as tabelas necessárias.

Credenciais esperadas pela aplicação (`FabricaDeConexao`):

```
Usuário: root
Senha: 3492Sibila#
```

Ajuste a classe `FabricaDeConexao` caso use outra configuração.

## Build e execução

1. Compile e gere o `.jar` com dependências:
   ```bash
   mvn clean package
   ```
   O arquivo executável ficará em `target/projeto-lpooii.jar`.

2. Rode a aplicação:
   ```bash
   java -jar target/projeto-lpooii.jar
   ```

## Estrutura

- `model` – Entidades, enums e lógica de negócio dos veículos.
- `dao` – Acesso a dados usando JDBC.
- `controller` – Regras de aplicação utilizadas pelas telas.
- `view` – Telas Swing (MVC).
- `util` – Utilitários de formatação.
- `docs/diagrama-classes.puml` – diagrama de classes (PlantUML).

## Diagrama de classes

Para gerar a imagem a partir do PlantUML:

```bash
# Necessário ter o plantuml.jar ou usar o plugin da IDE
plantuml docs/diagrama-classes.puml
```

O resultado será um arquivo PNG na mesma pasta.

## Observações

- As telas seguem o padrão exigido: manutenção de clientes, cadastro de veículos, locação, devolução e venda.
- Comentários curtos foram adicionados nos pontos-chave para facilitar a defesa oral.
- Mensagens de erro e sucesso foram pensadas em português para apoiar a apresentação.
