package br.com.projetolpooii.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DataUtil {

    private static final String PADRAO_BR = "dd/MM/yyyy";

    private DataUtil() {
    }

    public static Calendar paraCalendar(Date data) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(data);
        return calendario;
    }

    public static String formatar(Calendar calendario) {
        if (calendario == null) {
            return "";
        }
        return new SimpleDateFormat(PADRAO_BR).format(calendario.getTime());
    }
}
