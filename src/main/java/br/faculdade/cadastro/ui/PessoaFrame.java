// Pacote destinado à interface gráfica Swing.
package br.faculdade.cadastro.ui;

// Importa a entidade que será exibida.
import br.faculdade.cadastro.model.Pessoa;
// Importa o repositório JPA.
import br.faculdade.cadastro.repository.PessoaRepository;
// Permite criar ações com comportamento personalizado.
import javax.swing.AbstractAction;
// Permite criar o painel de janela.
import javax.swing.BorderFactory;
// Permite criar botões.
import javax.swing.JButton;
// Permite criar campos com rótulo.
import javax.swing.JLabel;
// Permite criar caixas de texto.
import javax.swing.JTextField;
// Permite exibir mensagens ao usuário.
import javax.swing.JOptionPane;
// Permite criar painel com organização em grade.
import javax.swing.JPanel;
// Permite organizar componentes por regiões.
import javax.swing.JScrollPane;
// Permite criar uma tabela.
import javax.swing.JTable;
// Permite mostrar linhas em uma tabela.
import javax.swing.table.DefaultTableModel;
// Permite criar uma janela Swing.
import javax.swing.JFrame;
// Permite organizar componentes em grade por linhas e colunas.
import java.awt.GridLayout;
// Permite organizar componentes verticalmente.
import java.awt.BorderLayout;
// Permite fechar os recursos ao encerrar.
import java.awt.event.WindowAdapter;
// Permite receber o evento de fechamento.
import java.awt.event.WindowEvent;
// Permite representar listas.
import java.util.List;

// Define a janela principal do sistema.
public class PessoaFrame extends JFrame {
    // Compartilha o repositório com os eventos da janela.
    private final PessoaRepository repository;
    // Guarda o campo usado para editar o identificador.
    private final JTextField campoId;
    // Guarda o campo do nome.
    private final JTextField campoNome;
    // Guarda o campo do CPF.
    private final JTextField campoCpf;
    // Guarda o campo do telefone.
    private final JTextField campoTelefone;
    // Guarda o campo do e-mail.
    private final JTextField campoEmail;
    // Guarda o modelo dos dados da tabela.
    private final DefaultTableModel modeloTabela;
    // Guarda a tabela exibida na janela.
    private final JTable tabela;

    // Constrói todos os componentes da interface.
    public PessoaFrame() {
        // Cria o repositório JPA.
        repository = new PessoaRepository();
        // Define o título da janela.
        setTitle("Sistema Cadastral de Pessoas");
        // Define o tamanho inicial da janela.
        setSize(850, 550);
        // Centraliza a janela na tela.
        setLocationRelativeTo(null);
        // Interrompe o processo ao fechar a janela.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Cria o campo do identificador e o desabilita para edição direta.
        campoId = new JTextField();
        // Impede alteração manual do identificador.
        campoId.setEditable(false);
        // Cria o campo do nome.
        campoNome = new JTextField();
        // Cria o campo do CPF.
        campoCpf = new JTextField();
        // Cria o campo do telefone.
        campoTelefone = new JTextField();
        // Cria o campo do e-mail.
        campoEmail = new JTextField();

        // Cria o painel com campos organizados em grade.
        JPanel painelFormulario = new JPanel(new GridLayout(5, 2, 8, 8));
        // Adiciona margem ao painel.
        painelFormulario.setBorder(BorderFactory.createTitledBorder("Dados da pessoa"));
        // Exibe o rótulo do identificador.
        painelFormulario.add(new JLabel("ID:"));
        // Adiciona o campo do identificador.
        painelFormulario.add(campoId);
        // Exibe o rótulo do nome.
        painelFormulario.add(new JLabel("Nome:"));
        // Adiciona o campo do nome.
        painelFormulario.add(campoNome);
        // Exibe o rótulo do CPF.
        painelFormulario.add(new JLabel("CPF:"));
        // Adiciona o campo do CPF.
        painelFormulario.add(campoCpf);
        // Exibe o rótulo do telefone.
        painelFormulario.add(new JLabel("Telefone:"));
        // Adiciona o campo do telefone.
        painelFormulario.add(campoTelefone);
        // Exibe o rótulo do e-mail.
        painelFormulario.add(new JLabel("E-mail:"));
        // Adiciona o campo do e-mail.
        painelFormulario.add(campoEmail);

        // Cria os nomes das colunas da tabela.
        modeloTabela = new DefaultTableModel(
                new Object[]{"ID", "Nome", "CPF", "Telefone", "E-mail"}, 0) {
            // Impede edição direta das células.
            @Override
            public boolean isCellEditable(int row, int column) {
                // Mantém a tabela somente para leitura.
                return false;
            }
        };
        // Cria a tabela com o modelo configurado.
        tabela = new JTable(modeloTabela);
        // Permite selecionar somente uma linha por vez.
        tabela.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        // Cria o painel dos botões.
        JPanel painelBotoes = new JPanel();
        // Cria o botão de inclusão.
        JButton botaoNovo = new JButton("Novo");
        // Associa o botão à ação de limpar os campos.
        botaoNovo.addActionListener(evento -> limparCampos());
        // Adiciona o botão ao painel.
        painelBotoes.add(botaoNovo);
        // Cria o botão de salvar.
        JButton botaoSalvar = new JButton("Salvar");
        // Associa o botão à ação de salvar.
        botaoSalvar.addActionListener(evento -> salvarPessoa());
        // Adiciona o botão ao painel.
        painelBotoes.add(botaoSalvar);
        // Cria o botão de carregar.
        JButton botaoEditar = new JButton("Editar");
        // Associa o botão à preparação da edição.
        botaoEditar.addActionListener(evento -> carregarPessoaSelecionada());
        // Adiciona o botão ao painel.
        painelBotoes.add(botaoEditar);
        // Cria o botão de excluir.
        JButton botaoExcluir = new JButton("Excluir");
        // Associa o botão à confirmação e exclusão.
        botaoExcluir.addActionListener(evento -> excluirPessoa());
        // Adiciona o botão ao painel.
        painelBotoes.add(botaoExcluir);

        // Organiza o formulário no topo da janela.
        add(painelFormulario, BorderLayout.NORTH);
        // Coloca a tabela em uma área com barra de rolagem.
        add(new JScrollPane(tabela), BorderLayout.CENTER);
        // Coloca os botões na parte inferior.
        add(painelBotoes, BorderLayout.SOUTH);

        // Fecha o repositório quando a janela encerra.
        addWindowListener(new WindowAdapter() {
            // Executa ações após o fechamento.
            @Override
            public void windowClosing(WindowEvent evento) {
                // Libera os recursos JPA.
                repository.fechar();
            }
        });

        // Atualiza a tabela ao abrir a janela.
        atualizarTabela();
    }

    // Limpa todos os campos do formulário.
    private void limparCampos() {
        // Remove o identificador.
        campoId.setText("");
        // Remove o nome.
        campoNome.setText("");
        // Remove o CPF.
        campoCpf.setText("");
        // Remove o telefone.
        campoTelefone.setText("");
        // Remove o e-mail.
        campoEmail.setText("");
        // Coloca o foco no campo nome.
        campoNome.requestFocus();
    }

    // Valida e salva uma pessoa.
    private void salvarPessoa() {
        // Remove espaços desnecessários dos campos.
        String nome = campoNome.getText().trim();
        // Remove espaços desnecessários do CPF.
        String cpf = campoCpf.getText().trim();
        // Remove espaços desnecessários do telefone.
        String telefone = campoTelefone.getText().trim();
        // Remove espaços desnecessários do e-mail.
        String email = campoEmail.getText().trim();

        // Exige o preenchimento do nome.
        if (nome.isEmpty()) {
            // Mostra uma orientação ao usuário.
            JOptionPane.showMessageDialog(this, "Informe o nome.", "Validação",
                    JOptionPane.WARNING_MESSAGE);
            // Interrompe o salvamento.
            return;
        }
        // Exige o preenchimento do CPF.
        if (cpf.isEmpty()) {
            // Mostra uma orientação ao usuário.
            JOptionPane.showMessageDialog(this, "Informe o CPF.", "Validação",
                    JOptionPane.WARNING_MESSAGE);
            // Interrompe o salvamento.
            return;
        }

        // Cria a entidade que será persistida.
        Pessoa pessoa = new Pessoa();
        // Reutiliza o identificador em uma atualização.
        if (!campoId.getText().isEmpty()) {
            // Converte o identificador textual.
            pessoa.setId(Long.valueOf(campoId.getText()));
        }
        // Copia o nome validado para a entidade.
        pessoa.setNome(nome);
        // Copia o CPF validado para a entidade.
        pessoa.setCpf(cpf);
        // Copia o telefone para a entidade.
        pessoa.setTelefone(telefone);
        // Copia o e-mail para a entidade.
        pessoa.setEmail(email);

        // Tenta salvar e informa falhas JPA.
        try {
            // Solicita ao repositório que persista a entidade.
            repository.salvar(pessoa);
            // Confirma a operação ao usuário.
            JOptionPane.showMessageDialog(this, "Pessoa salva com sucesso.");
            // Limpa o formulário após salvar.
            limparCampos();
            // Atualiza a tabela exibida.
            atualizarTabela();
        // Captura erros de banco ou validação.
        } catch (Exception excecao) {
            // Exibe uma mensagem segura ao usuário.
            JOptionPane.showMessageDialog(this,
                    "Não foi possível salvar. Verifique se o CPF já está cadastrado.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Carrega os dados da linha selecionada.
    private void carregarPessoaSelecionada() {
        // Recupera a linha selecionada na tabela.
        int linha = tabela.getSelectedRow();
        // Impede edição sem seleção.
        if (linha < 0) {
            // Pede ao usuário que escolha uma pessoa.
            JOptionPane.showMessageDialog(this, "Selecione uma pessoa na tabela.");
            // Interrompe o carregamento.
            return;
        }
        // Converte o identificador exibido.
        Long id = Long.valueOf(modeloTabela.getValueAt(linha, 0).toString());
        // Busca os dados completos no banco.
        Pessoa pessoa = repository.buscarPorId(id);
        // Verifica se a pessoa ainda existe.
        if (pessoa == null) {
            // Atualiza a tabela caso tenha sido removida.
            atualizarTabela();
            // Interrompe o carregamento.
            return;
        }
        // Preenche o identificador.
        campoId.setText(String.valueOf(pessoa.getId()));
        // Preenche o nome.
        campoNome.setText(pessoa.getNome());
        // Preenche o CPF.
        campoCpf.setText(pessoa.getCpf());
        // Preenche o telefone ou uma cadeia vazia.
        campoTelefone.setText(pessoa.getTelefone() == null ? "" : pessoa.getTelefone());
        // Preenche o e-mail ou uma cadeia vazia.
        campoEmail.setText(pessoa.getEmail() == null ? "" : pessoa.getEmail());
    }

    // Exclui a pessoa selecionada após confirmação.
    private void excluirPessoa() {
        // Recupera a linha selecionada.
        int linha = tabela.getSelectedRow();
        // Verifica se existe uma linha selecionada.
        if (linha < 0) {
            // Solicita seleção ao usuário.
            JOptionPane.showMessageDialog(this, "Selecione uma pessoa na tabela.");
            // Interrompe a exclusão.
            return;
        }
        // Pede confirmação antes da exclusão.
        int confirmacao = JOptionPane.showConfirmDialog(this,
                "Deseja realmente excluir esta pessoa?", "Confirmação",
                JOptionPane.YES_NO_OPTION);
        // Cancela quando o usuário não confirma.
        if (confirmacao != JOptionPane.YES_OPTION) {
            // Interrompe a exclusão.
            return;
        }
        // Converte o identificador da pessoa selecionada.
        Long id = Long.valueOf(modeloTabela.getValueAt(linha, 0).toString());
        // Solicita a exclusão ao repositório.
        repository.excluir(id);
        // Limpa o formulário após excluir.
        limparCampos();
        // Atualiza a tabela.
        atualizarTabela();
    }

    // Atualiza os dados mostrados na tabela.
    private void atualizarTabela() {
        // Remove todas as linhas anteriores.
        modeloTabela.setRowCount(0);
        // Obtém todas as pessoas do banco.
        List<Pessoa> pessoas = repository.listarTodas();
        // Percorre cada pessoa retornada.
        for (Pessoa pessoa : pessoas) {
            // Adiciona uma linha com os dados da pessoa.
            modeloTabela.addRow(new Object[]{
                    pessoa.getId(),
                    pessoa.getNome(),
                    pessoa.getCpf(),
                    pessoa.getTelefone(),
                    pessoa.getEmail()
            });
        }
    }
}
