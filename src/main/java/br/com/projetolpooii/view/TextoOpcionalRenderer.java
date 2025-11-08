package br.com.projetolpooii.view;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import java.awt.Component;

public class TextoOpcionalRenderer extends DefaultListCellRenderer {

    private final String textoPadrao;

    public TextoOpcionalRenderer(String textoPadrao) {
        this.textoPadrao = textoPadrao;
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (value == null) {
            value = "Todos" + (textoPadrao != null ? " (" + textoPadrao + ")" : "");
        }
        return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
    }
}
