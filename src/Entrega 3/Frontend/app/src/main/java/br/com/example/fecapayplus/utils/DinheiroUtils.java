package br.com.example.fecapayplus.utils;

import java.util.Locale;

public class DinheiroUtils {
    public static String format(double value) {
        return String.format(Locale.getDefault(), "R$ %.2f", value);
    }

    public static String format(String value) {
        try {

            String cleaned = value.replace("R$", "").replace(",", ".").trim();
            double valor = Double.parseDouble(cleaned);
            return format(valor);
        } catch (NumberFormatException e) {
            return value;
        }
    }
}