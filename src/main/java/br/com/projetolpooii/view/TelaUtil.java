package br.com.projetolpooii.view;

import javax.swing.JLabel;

public final class TelaUtil {

    private TelaUtil() {
    }

    public static JLabel rotulo(String texto) {
        return new JLabel(texto + ":");
    }
}
