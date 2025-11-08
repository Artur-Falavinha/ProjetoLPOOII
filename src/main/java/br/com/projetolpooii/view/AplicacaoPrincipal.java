package br.com.projetolpooii.view;

import javax.swing.SwingUtilities;

public class AplicacaoPrincipal {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ContextoAplicacao contexto = new ContextoAplicacao();
            TelaPrincipal telaPrincipal = new TelaPrincipal(contexto);
            telaPrincipal.setVisible(true);
        });
    }
}
