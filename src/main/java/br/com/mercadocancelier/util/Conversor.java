package br.com.mercadocancelier.util;

import java.text.DecimalFormat;

public abstract class Conversor {

	public static double stringParaNumero(String texto) {
        try {
            return Double.parseDouble(texto.replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Formato inválido para conversão de string para número.");
        }
    }

    public static String numeroParaString(double numero) {
        DecimalFormat formato = new DecimalFormat("#,##0.00");
        return formato.format(numero);
    }

}
