package br.com.brunoxkk0.syrxontime.utils;

import org.bukkit.ChatColor;


public class TimeAPI {

    public static String formatSec(int secondsLeft){

        int dias = 0;
        int horas = 0;
        int minutos = 0;
        int segundos = 0;


        if (secondsLeft >= 86400){
            dias = secondsLeft / 86400;
            secondsLeft = secondsLeft % 86400;
        }
        if (secondsLeft >= 3600){
            horas = secondsLeft / 3600;
            secondsLeft = secondsLeft % 3600;
        }
        if (secondsLeft >= 60){
            minutos = secondsLeft / 60;
            secondsLeft = secondsLeft % 60;
        }
        segundos = secondsLeft;
        String message = "";

        if (dias > 0){
            message = dias + "Dias";
        }
        if (horas > 0){
            message = message + " " + horas +"Horas";
        }
        if (minutos > 0){
            message = message + " " + minutos +"Min";
        }
        if (segundos > 0){
            message = message + " " + segundos +"Seg";
        }

        return message;
    }

    public static String formatSec2(int secondsLeft){

        int dias = 0;
        int horas = 0;
        int minutos = 0;
        int segundos = 0;


        if (secondsLeft >= 86400){
            dias = secondsLeft / 86400;
            secondsLeft = secondsLeft % 86400;
        }
        if (secondsLeft >= 3600){
            horas = secondsLeft / 3600;
            secondsLeft = secondsLeft % 3600;
        }
        if (secondsLeft >= 60){
            minutos = secondsLeft / 60;
            secondsLeft = secondsLeft % 60;
        }
        segundos = secondsLeft;
        String message = "";

        if (dias > 0){
            message = dias + "D";
        }
        if (horas > 0){
            message = message + " " + horas +"H";
        }
        if (minutos > 0){
            message = message + " " + minutos +"M";
        }
        if (segundos > 0){
            message = message + " " + segundos +"S";
        }

        return message;
    }

    public static String getProgressBar(int current, int max, int totalBars, String symbol, String completedColor, String notCompletedColor){

        float percent = (float) current / max;

        int progressBars = (int) (totalBars * percent);

        int leftOver = (totalBars - progressBars);

        StringBuilder sb = new StringBuilder();
        sb.append(ChatColor.translateAlternateColorCodes('&', completedColor));
        for (int i = 0; i < progressBars; i++) {
            sb.append(symbol);
        }
        sb.append(ChatColor.translateAlternateColorCodes('&', notCompletedColor));
        for (int i = 0; i < leftOver; i++) {
            sb.append(symbol);
        }
        return sb.toString();
    }

}
