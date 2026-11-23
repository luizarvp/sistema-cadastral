// Organiza a classe principal no mesmo projeto.
package br.faculdade.cadastro;

// Permite configurar o visual do sistema.
import br.faculdade.cadastro.ui.PessoaFrame;
// Permite aplicar o visual Nimbus.
import javax.swing.UIManager;
// Permite iniciar componentes Swing pela thread correta.
import javax.swing.SwingUtilities;

// Contém o ponto de entrada da aplicação.
public class Main {
    // Inicia o programa Java.
    public static void main(String[] args) {
        // Executa a criação da interface na thread de eventos.
        SwingUtilities.invokeLater(() -> {
            // Prepara a aparência Nimbus quando disponível.
            try {
                // Percorre os visuais disponíveis.
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    // Verifica se Nimbus está instalado.
                    if ("Nimbus".equals(info.getName())) {
                        // Aplica o visual Nimbus.
                        UIManager.setLookAndFeel(info.getClassName());
                        // Interrompe a procura após encontrar Nimbus.
                        break;
                    }
                }
            // Captura qualquer falha ao configurar o visual.
            } catch (Exception excecao) {
                // Mantém o visual padrão quando Nimbus não puder ser aplicado.
                excecao.printStackTrace();
            }
            // Cria a janela principal.
            PessoaFrame frame = new PessoaFrame();
            // Exibe a janela ao usuário.
            frame.setVisible(true);
        });
    }
}
