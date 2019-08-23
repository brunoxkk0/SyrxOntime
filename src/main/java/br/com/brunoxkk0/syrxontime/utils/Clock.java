package br.com.brunoxkk0.syrxontime.utils;

import br.com.brunoxkk0.syrxontime.SyrxOntime;
import br.com.brunoxkk0.syrxontime.events.DayChangeEvent;
import br.com.brunoxkk0.syrxontime.manager.ConfigManager;
import org.bukkit.Bukkit;

import java.util.Calendar;
import java.util.TimeZone;

public class Clock {

    private static Integer today; //Salva o dia atual.

    public static void setup(){
        today = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).get(Calendar.DAY_OF_MONTH); // Pega o dia seguindo o time zone do Servidor.
    }

    /*
    esse método é usado para atualizar o dia do servidor e saber se mudou de dia ou não.
     */
    public static void update(){

        int day = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).get(Calendar.DAY_OF_MONTH);

        if(today != day){
            today = day;
            dayChanged();
        }

    }

    /*
    Aqui é realizado todos os updates quando muda o dia.
     */
    private synchronized static void dayChanged(){
        SyrxOntime.logger().info("Um outro dia começou...");
        Bukkit.getPluginManager().callEvent(new DayChangeEvent()); //Chama o evento DayChangeEvent pra dizer que o dia mudou.
        ConfigManager.wipeCache(); //Limpa o "Cache".

    }

    /*
    Retorna o dia atual.
     */
    public static int getCurrentDay(){
        return today;
    }


}
