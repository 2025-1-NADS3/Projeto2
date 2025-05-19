package br.com.example.fecapayplus.utils;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Formatters {
    public static String formatedBalance(double balance){
        NumberFormat formated = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));
        return formated.format(balance);

    }
    public static String formatedDateMonth(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy", Locale.getDefault());
        return formatter.format(date);
    }
    public static String formatedDate(Date date){
        SimpleDateFormat fomatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return fomatter.format(date);
    }
}
