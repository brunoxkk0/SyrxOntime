package br.com.brunoxkk0.syrxontime.utils;

import br.com.brunoxkk0.syrxontime.SyrxOntime;
import br.com.brunoxkk0.syrxontime.events.DayChangeEvent;
import br.com.brunoxkk0.syrxontime.manager.ConfigManager;
import org.bukkit.Bukkit;

import java.util.Calendar;
import java.util.TimeZone;

public class Clock {

    private static Integer today;

    public static void setup(){
        today = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).get(Calendar.DAY_OF_MONTH);
    }

    public static void update(){

        int day = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).get(Calendar.DAY_OF_MONTH);

        if(today < day){
            today = day;
            dayChanged();
        }

    }

    private static void dayChanged(){
        SyrxOntime.logger().info("Um outro dia começou...");
        Bukkit.getPluginManager().callEvent(new DayChangeEvent());
        ConfigManager.wipeCache();

    }

    public static int getCurrentDay(){
        return today;
    }


}
