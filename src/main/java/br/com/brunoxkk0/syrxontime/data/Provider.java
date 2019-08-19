package br.com.brunoxkk0.syrxontime.data;

import br.com.brunoxkk0.syrxontime.data.store.Cache;
import br.com.brunoxkk0.syrxontime.rewards.RewardManager;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Provider {

    public static PlayerTime getPlayer(Player player){
        return Cache.get(player);
    }

    public static Long getPlayerNextRewardTime(Player p){

        if(p != null){
            PlayerTime time = Provider.getPlayer(p);

            return (time != null) ? RewardManager.timeToNextReward(p, time.getTotalTimeToday()) : -1;
        }

        return -1L;
    }

    public static Long getPlayerToday(Player p){
        PlayerTime playerTime = getPlayer(p);
        return (playerTime != null) ? playerTime.getTotalTimeToday() / 1000 : 0;
    }

    public static Double getPlayerTimeH(Player p) {
        Double Ontime = (double) Math.round((p.getStatistic(Statistic.PLAY_ONE_TICK) / (20.0 * 60.0 * 60.0)) * 100d) / 100;
        NumberFormat formatter = new DecimalFormat("#0.00");
        String f = formatter.format(Ontime);
        return Double.valueOf(f.replace(",", "."));
    }


    public static Double getPlayerTimeM(Player p) {
        Double Ontime = (double) Math.round((p.getStatistic(Statistic.PLAY_ONE_TICK) / (20.0 * 60.0)) * 100d) / 100;
        return Ontime;
    }

    public static Double getPlayerTimeS(Player p) {

        Double Ontime = (double) Math.round((p.getStatistic(Statistic.PLAY_ONE_TICK) / (20.0)) * 100d) / 100;
        NumberFormat formatter = new DecimalFormat("#0.00");
        String f = formatter.format(Ontime);
        return Double.valueOf(f.replace(",", "."));
    }

    public static Double getPlayerTimeD(Player p) {
        Double Ontime = (double) Math.round((p.getStatistic(Statistic.PLAY_ONE_TICK) / (20.0 * 60.0 * 60.0)) * 100d) / 100;
        NumberFormat formatter = new DecimalFormat("#0.00");
        String f = formatter.format(Ontime/24);
        return Double.valueOf(f.replace(",", "."));
    }

    public static String getPlayerJoinedTime(Player p) {

        Date date = new Date(p.getFirstPlayed());
        DateFormat formatter = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
        String dateFormatted = formatter.format(date);

        return dateFormatted;
    }

    public static String getServerTime() {

        Date date = new Date(System.currentTimeMillis());
        DateFormat formatter = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
        String dateFormatted = formatter.format(date);

        return dateFormatted;
    }

    public static int convert(TimeUnit unit, long time){
        switch (unit){
            case MINUTES:{
                return (int) Math.abs((time/1000)/60);
            }

            case SECONDS:{
                return (int) Math.abs((time/1000));
            }

            case HOURS:{
                return (int) Math.abs((time/1000)/60/60);
            }

            default: {
                return 0;
            }
        }
    }

    public static TimeUnit parse(long time){
        if(time < 60000){
            return TimeUnit.SECONDS;
        }else if(time < 3600000){
            return TimeUnit.MINUTES;
        }else{
            return TimeUnit.HOURS;
        }
    }

    public static Long convert(int time, TimeUnit unit){
        long t = 0;

        switch (unit){
            case SECONDS:{
                t = time*1000;
                break;
            }

            case MINUTES:{
                t = time*1000*60;
                break;
            }

            case HOURS:{
                t = time*1000*(60*60);
                break;
            }
        }

        return t;
    }

    public static String getBar(int min, int max, int cubes){

        System.out.println(min);
        System.out.println(max);
        System.out.println(cubes);

        int a = 0;
        int b = 0;

        if(max != -1 || min != -1){
            a = (min * 100) / max;
            b = (cubes * a) / 100;
        }else{
            b = cubes;
        }

        StringBuilder bar = new StringBuilder();

        if(max == min || min > max) {
            b = cubes;
        }

        System.out.println(a);
        System.out.println(b);
        System.out.println(cubes);

        for(int i = 0; i <= b; i++){
            bar.append("\u2b1b");
        }

        if(bar.length() <= cubes){
            for(int i = bar.length(); i <= cubes; i++){
                bar.append("\u2b1c");
            }
        }

        System.out.println(bar.toString());
        return bar.toString();
    }

}
