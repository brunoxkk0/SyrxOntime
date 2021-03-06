package br.com.brunoxkk0.syrxontime.data;

import br.com.brunoxkk0.syrxontime.SyrxOntime;
import br.com.brunoxkk0.syrxontime.rewards.RewardManager;
import br.com.brunoxkk0.syrxontime.utils.Clock;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlayerTime {

    public OfflinePlayer player;
    private long lastSession = 0;
    private long totalTimeToday = 0;
    private long lastUpdate;
    private int today;
    public boolean isSaved = false;

    public PlayerTime(OfflinePlayer player){
        this.player = player;
        this.today = Clock.getCurrentDay();
        this.lastUpdate = System.currentTimeMillis();
        this.totalTimeToday = 0;
    }

    public PlayerTime(Player player){
        this(Provider.getOfflinePlayer(player));
    }

    public PlayerTime(OfflinePlayer player, Long totalTimeToday){
        this(player);
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

    public PlayerTime update(){
        if(lastSession == 0 || lastSession != player.getLastPlayed()){
            if(player != null){
                lastSession = player.getLastPlayed();
                resetLastUpdate();
            }
            resetLastUpdate();
        }

        if(SyrxOntime.debug){
            SyrxOntime.logger().warning("Atualizando PlayerTime " + player.getName()+ ".");
        }

        if(RewardManager.process(player,totalTimeToday)){
            isSaved = false;
        }

        long currentTime = System.currentTimeMillis();

        totalTimeToday += (currentTime - lastUpdate);

        if(today != Clock.getCurrentDay()){

            today = Clock.getCurrentDay();
            totalTimeToday = 0;
            lastUpdate = System.currentTimeMillis();

        }

        lastUpdate = System.currentTimeMillis();
        isSaved = false;

        return this;
    }

    public void resetLastUpdate(){
        lastUpdate = System.currentTimeMillis();
    }
}
