package br.com.brunoxkk0.syrxontime.utils;

import org.bukkit.ChatColor;


public class TimeAPI {

    /* Formata o tempo em segundos, em uma String no formato
        XX Dias, XX Horas, XX Minutos, XX Segundos.
     */
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

    /* Formata o tempo em segundos, em uma String no formato
    XX D, XX H, XX M, XX S.
    */
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

}
