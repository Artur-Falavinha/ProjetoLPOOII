package br.com.projetolpooii.view;

import javax.swing.SwingUtilities;

// Classe principal que inicia o sistema
public class AplicacaoPrincipal {

    public static void main(String[] args) {
        // invokeLater garante que a interface gráfica rode na thread correta do Swing
        SwingUtilities.invokeLater(() -> {
            ContextoAplicacao contexto = new ContextoAplicacao();
            TelaPrincipal telaPrincipal = new TelaPrincipal(contexto);
            telaPrincipal.setVisible(true);
        });
    }
}
