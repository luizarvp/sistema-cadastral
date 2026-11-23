// Pacote que organiza as classes do domínio.
package br.faculdade.cadastro.model;

// Importa a anotação que identifica uma chave primária.
import jakarta.persistence.Column;
// Importa a entidade gerenciada pelo JPA.
import jakarta.persistence.Entity;
// Importa a estratégia de geração automática do identificador.
import jakarta.persistence.GeneratedValue;
// Importa a estratégia usada para criar o identificador.
import jakarta.persistence.GenerationType;
// Importa a anotação da chave primária.
import jakarta.persistence.Id;
// Importa a anotação que permite definir o nome da tabela.
import jakarta.persistence.Table;

// Define que objetos desta classe são persistidos no banco.
@Entity
// Define o nome da tabela correspondente.
@Table(name = "PESSOAS")
// Declara a entidade Pessoa.
public class Pessoa {
    // Identifica o campo que representa a chave primária.
    @Id
    // Solicita que o banco gere o identificador.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Declara a propriedade identificadora.
    private Long id;

    // Define que o nome não pode ser nulo e possui limite de tamanho.
    @Column(nullable = false, length = 100)
    // Declara o nome da pessoa.
    private String nome;

    // Impede nomes nulos e define o limite da coluna CPF.
    @Column(nullable = false, unique = true, length = 14)
    // Declara o CPF da pessoa.
    private String cpf;

    // Permite armazenamento opcional do telefone.
    @Column(length = 20)
    // Declara o telefone da pessoa.
    private String telefone;

    // Permite armazenamento opcional do e-mail.
    @Column(length = 120)
    // Declara o e-mail da pessoa.
    private String email;

    // Cria o construtor vazio exigido pelo JPA.
    public Pessoa() {
        // A inicialização dos campos é feita pelo JPA.
    }

    // Cria um construtor para cadastrar dados completos.
    public Pessoa(String nome, String cpf, String telefone, String email) {
        // Copia o nome recebido para o atributo da entidade.
        this.nome = nome;
        // Copia o CPF recebido para o atributo da entidade.
        this.cpf = cpf;
        // Copia o telefone recebido para o atributo da entidade.
        this.telefone = telefone;
        // Copia o e-mail recebido para o atributo da entidade.
        this.email = email;
    }

    // Retorna o identificador da pessoa.
    public Long getId() {
        // Entrega o valor armazenado no campo id.
        return id;
    }

    // Define o identificador da pessoa.
    public void setId(Long id) {
        // Atribui o valor recebido ao campo id.
        this.id = id;
    }

    // Retorna o nome da pessoa.
    public String getNome() {
        // Entrega o valor armazenado no campo nome.
        return nome;
    }

    // Define o nome da pessoa.
    public void setNome(String nome) {
        // Atribui o valor recebido ao campo nome.
        this.nome = nome;
    }

    // Retorna o CPF da pessoa.
    public String getCpf() {
        // Entrega o valor armazenado no campo cpf.
        return cpf;
    }

    // Define o CPF da pessoa.
    public void setCpf(String cpf) {
        // Atribui o valor recebido ao campo cpf.
        this.cpf = cpf;
    }

    // Retorna o telefone da pessoa.
    public String getTelefone() {
        // Entrega o valor armazenado no campo telefone.
        return telefone;
    }

    // Define o telefone da pessoa.
    public void setTelefone(String telefone) {
        // Atribui o valor recebido ao campo telefone.
        this.telefone = telefone;
    }

    // Retorna o e-mail da pessoa.
    public String getEmail() {
        // Entrega o valor armazenado no campo email.
        return email;
    }

    // Define o e-mail da pessoa.
    public void setEmail(String email) {
        // Atribui o valor recebido ao campo email.
        this.email = email;
    }
}
