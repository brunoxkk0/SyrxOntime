package br.com.brunoxkk0.syrxontime.data;

import br.com.brunoxkk0.syrxontime.SyrxOntime;
import br.com.brunoxkk0.syrxontime.manager.AfkManager;
import br.com.brunoxkk0.syrxontime.utils.Clock;
import org.bukkit.OfflinePlayer;

public class TimeObj {

    public OfflinePlayer player;
    private long lastSession = 0;
    private long totalTimeToday;
    private long lastUpdate;
    private int today;
    public boolean isSaved = false;

    public TimeObj(OfflinePlayer p){
        player = p;
        today = Clock.currentDay;
        lastUpdate = System.currentTimeMillis();
        totalTimeToday = 0;
    }

    public TimeObj(OfflinePlayer p, Long totalTimeToday){
        player = p;
        today = Clock.currentDay;
        lastUpdate = System.currentTimeMillis();
        this.totalTimeToday = totalTimeToday;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public long getLastSession() {
        return lastSession;
    }

    public long getTotalTimeToday() {
        return (totalTimeToday > 0 ) ? totalTimeToday : 0;
    }

    public void setTotalTimeToday(Long timeToday){
        totalTimeToday = timeToday;
    }

    public void update(){
        if(lastSession == 0){
            if((player) != null){
                lastSession = (player).getLastPlayed();
            }
        }

        if(SyrxOntime.debug){
            SyrxOntime.debug("Atualizando TimeObj " + player.getName()+ ".");
        }

        long currentTime = System.currentTimeMillis();

        totalTimeToday += (currentTime - lastUpdate);

        if(today < Clock.currentDay){
            today = Clock.currentDay;
            totalTimeToday = 0;
        }

        lastUpdate = currentTime;
        isSaved = false;
    }
}
