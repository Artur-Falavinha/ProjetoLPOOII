package br.com.projetolpooii.util;

import java.text.NumberFormat;
import java.util.Locale;

public final class FormatadorMonetario {

    private static final Locale LOCALE_BR = new Locale("pt", "BR");
    private static final NumberFormat FORMATO = NumberFormat.getCurrencyInstance(LOCALE_BR);

    private FormatadorMonetario() {
    }

    public static String formatar(double valor) {
        return FORMATO.format(valor);
    }
}
