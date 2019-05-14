package br.com.brunoxkk0.syrxontime.data;

import br.com.brunoxkk0.syrxontime.data.store.Cache;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Provider {

    public static TimeObj getPlayer(Player p){
        if(Cache.cache.containsKey(p)){
            return Cache.cache.get(p);
        }

        return null;
    }

    public static Long getPlayerToday(Player p){
        TimeObj timeObj = getPlayer(p);
        return (timeObj != null) ? timeObj.getTotalTimeToday() / 1000 : 0;
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
}
