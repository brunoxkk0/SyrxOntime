package br.com.brunoxkk0.syrxontime.utils;

import br.com.brunoxkk0.syrxontime.SyrxOntime;
import br.com.brunoxkk0.syrxontime.manager.ConfigManager;

import java.util.Calendar;
import java.util.TimeZone;

public class Clock {

    public static Integer today;

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

    public static void dayChanged(){
        SyrxOntime.logger().info("Um outro dia começou...");
        ConfigManager.wipeCache();

    }

    public static int getCurrentDay(){
        return today;
    }


}
