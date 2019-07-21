package br.com.brunoxkk0.syrxontime.threads;


import br.com.brunoxkk0.syrxontime.SyrxOntime;
import br.com.brunoxkk0.syrxontime.data.PlayerTime;
import br.com.brunoxkk0.syrxontime.data.store.Cache;
import br.com.brunoxkk0.syrxontime.manager.ConfigManager;
import br.com.brunoxkk0.syrxontime.utils.Clock;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class UpdateTask extends Thread {

    public static UpdateTask updateTask;
    public static Boolean repeat = true;

    public static void shutdown(){
        if (updateTask != null) updateTask.interrupt();
        updateTask = null;
    }

    public static void initialize(){
        if (updateTask == null){
            updateTask = new UpdateTask();
            updateTask.start();
        }
    }

    public boolean sleepFor(int millis){
        try {
            Thread.sleep(millis);
            return true;
        } catch (InterruptedException e) {
            if(SyrxOntime.debug) e.printStackTrace();
        }
        return false;
    }

    public static final int CACHE_SAVE_TIME = 5;
    public static int cacheSaveTime = CACHE_SAVE_TIME;

    private boolean tickCacheForSave(){     //Faz um contador decer até zero, quando dá 0 ele retorna TRUE e reseta o contador para 5!
        if (cacheSaveTime <= 0){
            cacheSaveTime = CACHE_SAVE_TIME;
            return true;
        }
        cacheSaveTime--;
        return false;
    }

    @Override
    public void run() {
        while(repeat) {

            if (!sleepFor(1000)) return;

            if (tickCacheForSave()) {
                ConfigManager.saveCache();
            }

            for(Player player : Bukkit.getServer().getOnlinePlayers()){
                Cache.update(player);
            }

            Clock.update();
        }
    }

}
