package br.com.brunoxkk0.syrxontime.utils;

import br.com.brunoxkk0.syrxontime.data.store.Cache;
import br.com.brunoxkk0.syrxontime.events.DayChangeEvent;
import org.bukkit.Bukkit;

import java.util.Calendar;
import java.util.TimeZone;

public class Clock {

    private static Calendar calendar;
    public static Integer currentDay;

    public Clock(){
        calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static void update(){

        if(calendar == null) {
            new Clock();
        }

        //Atualiza o date para ver se mudou o dia;
       calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));

        if(currentDay < calendar.get(Calendar.DAY_OF_MONTH)){
            Bukkit.getPluginManager().callEvent(new DayChangeEvent());
            currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        }

        Cache.todayDAY = calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int getCurrentDay(){
        if(calendar != null) {
            return calendar.get(Calendar.DAY_OF_MONTH);
        }

        update();
        return getCurrentDay();
    }


}
