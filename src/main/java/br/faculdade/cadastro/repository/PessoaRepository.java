// Organiza o acesso aos dados em um pacote próprio.
package br.faculdade.cadastro.repository;

// Permite buscar e excluir entidades.
import br.faculdade.cadastro.model.Pessoa;
// Cria e gerencia o EntityManagerFactory.
import jakarta.persistence.EntityManager;
// Representa uma unidade de persistência ativa.
import jakarta.persistence.EntityManagerFactory;
// Cria o EntityManagerFactory a partir do persistence.xml.
import jakarta.persistence.Persistence;
// Mantém uma coleção de pessoas.
import java.util.List;

// Centraliza todas as operações JPA de Pessoa.
public class PessoaRepository {
    // Armazena o único objeto de fábrica da aplicação.
    private final EntityManagerFactory factory;

    // Inicializa o repositório com a unidade cadastrada.
    public PessoaRepository() {
        // Cria o fábrica usando cadastroPU.
        this.factory = Persistence.createEntityManagerFactory("cadastroPU");
    }

    // Construtor alternativo usado em testes isolados.
    public PessoaRepository(EntityManagerFactory factory) {
        // Recebe uma fábrica já configurada.
        this.factory = factory;
    }

    // Insere uma pessoa nova no banco.
    public Pessoa salvar(Pessoa pessoa) {
        // Abre uma sessão JPA.
        EntityManager manager = factory.createEntityManager();
        // Inicia uma transação.
        manager.getTransaction().begin();
        // Persiste ou atualiza conforme o identificador.
        Pessoa resultado = manager.merge(pessoa);
        // Confirma a alteração no banco.
        manager.getTransaction().commit();
        // Fecha a sessão.
        manager.close();
        // Entrega a pessoa salva.
        return resultado;
    }

    // Pesquisa uma pessoa pelo identificador.
    public Pessoa buscarPorId(Long id) {
        // Abre uma nova sessão JPA.
        EntityManager manager = factory.createEntityManager();
        // Localiza a entidade pelo identificador.
        Pessoa pessoa = manager.find(Pessoa.class, id);
        // Fecha a sessão.
        manager.close();
        // Entrega o resultado da pesquisa.
        return pessoa;
    }

    // Lista todas as pessoas cadastradas.
    public List<Pessoa> listarTodas() {
        // Abre uma nova sessão JPA.
        EntityManager manager = factory.createEntityManager();
        // Executa uma consulta JPQL pelo nome da entidade.
        List<Pessoa> pessoas = manager.createQuery(
                "SELECT p FROM Pessoa p ORDER BY p.nome", Pessoa.class)
                .getResultList();
        // Fecha a sessão.
        manager.close();
        // Entrega a lista encontrada.
        return pessoas;
    }

    // Remove uma pessoa pelo identificador.
    public void excluir(Long id) {
        // Abre uma nova sessão JPA.
        EntityManager manager = factory.createEntityManager();
        // Inicia uma transação.
        manager.getTransaction().begin();
        // Busca a entidade a ser removida.
        Pessoa pessoa = manager.find(Pessoa.class, id);
        // Só remove quando existe uma entidade correspondente.
        if (pessoa != null) {
            // Solicita a remoção da entidade.
            manager.remove(pessoa);
        }
        // Confirma a transação.
        manager.getTransaction().commit();
        // Fecha a sessão.
        manager.close();
    }

    // Remove todos os registros para permitir testes e limpeza controlada.
    public void excluirTodas() {
        // Abre uma sessão JPA.
        EntityManager manager = factory.createEntityManager();
        // Inicia uma transação.
        manager.getTransaction().begin();
        // Executa uma exclusão em massa das entidades Pessoa.
        manager.createQuery("DELETE FROM Pessoa").executeUpdate();
        // Confirma a exclusão em massa.
        manager.getTransaction().commit();
        // Fecha a sessão.
        manager.close();
    }

    // Fecha os recursos JPA do repositório.
    public void fechar() {
        // Encerra o EntityManagerFactory.
        factory.close();
    }
}
