// Organiza o teste no mesmo pacote da aplicação.
package br.faculdade.cadastro;

// Importa a classe Pessoa.
import br.faculdade.cadastro.model.Pessoa;
// Importa o repositório JPA.
import br.faculdade.cadastro.repository.PessoaRepository;
// Importa a fábrica JPA.
import jakarta.persistence.EntityManagerFactory;
// Importa o criador da unidade de persistência.
import jakarta.persistence.Persistence;
// Importa a anotação de limpeza depois do teste.
import org.junit.jupiter.api.AfterEach;
// Importa a anotação de preparação antes do teste.
import org.junit.jupiter.api.BeforeEach;
// Importa a anotação que identifica um teste.
import org.junit.jupiter.api.Test;
// Importa verificações automáticas.
import static org.junit.jupiter.api.Assertions.assertEquals;
// Importa a verificação de objeto não nulo.
import static org.junit.jupiter.api.Assertions.assertNotNull;
// Importa a verificação de objeto nulo.
import static org.junit.jupiter.api.Assertions.assertNull;

// Agrupa os testes do repositório.
public class PessoaRepositoryTest {
    // Guarda a fábrica usada pelos testes.
    private EntityManagerFactory factory;
    // Guarda o repositório usado pelos testes.
    private PessoaRepository repository;

    // Prepara um banco separado antes de cada teste.
    @BeforeEach
    void preparar() {
        // Cria uma unidade temporária em memória.
        factory = Persistence.createEntityManagerFactory("cadastroPU");
        // Inicializa o repositório com essa fábrica.
        repository = new PessoaRepository(factory);
        // Remove registros existentes antes do teste.
        repository.excluirTodas();
    }

    // Fecha os recursos após cada teste.
    @AfterEach
    void finalizar() {
        // Remove os registros criados pelo teste.
        repository.excluirTodas();
        // Fecha o repositório compartilhado.
        repository.fechar();
    }

    // Verifica inclusão e consulta por identificador.
    @Test
    void deveSalvarEBuscarPessoa() {
        // Cria os dados da pessoa.
        Pessoa pessoa = new Pessoa("Maria Silva", "11111111111", "11999999999", "maria@teste.com");
        // Persiste a pessoa.
        Pessoa salva = repository.salvar(pessoa);
        // Confirma que o banco gerou um identificador.
        assertNotNull(salva.getId());
        // Busca novamente a pessoa pelo identificador.
        Pessoa encontrada = repository.buscarPorId(salva.getId());
        // Confirma que o nome foi recuperado.
        assertEquals("Maria Silva", encontrada.getNome());
    }

    // Verifica alteração e exclusão de pessoa.
    @Test
    void deveAtualizarEExcluirPessoa() {
        // Cria uma pessoa inicial.
        Pessoa pessoa = new Pessoa("Joao Souza", "22222222222", "", "joao@teste.com");
        // Salva a pessoa inicial.
        Pessoa salva = repository.salvar(pessoa);
        // Altera o nome da entidade.
        salva.setNome("Joao Pedro Souza");
        // Persiste a alteração.
        repository.salvar(salva);
        // Confirma o novo nome no banco.
        assertEquals("Joao Pedro Souza",
                repository.buscarPorId(salva.getId()).getNome());
        // Solicita a exclusão.
        repository.excluir(salva.getId());
        // Confirma que a busca não encontra mais a pessoa.
        assertNull(repository.buscarPorId(salva.getId()));
    }
}
