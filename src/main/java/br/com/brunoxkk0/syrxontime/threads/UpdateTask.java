package br.com.brunoxkk0.syrxontime.threads;


import br.com.brunoxkk0.syrxontime.SyrxOntime;
import br.com.brunoxkk0.syrxontime.data.store.Cache;
import br.com.brunoxkk0.syrxontime.manager.ConfigManager;
import br.com.brunoxkk0.syrxontime.utils.Clock;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class UpdateTask extends Thread {

    private static UpdateTask updateTask;

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

    private boolean sleepFor(){
        try {
            Thread.sleep(1000);
            return true;
        } catch (InterruptedException e) {
            if(SyrxOntime.debug) e.printStackTrace();
        }
        return false;
    }

    private static final int CACHE_SAVE_TIME = 5;
    private static int cacheSaveTime = CACHE_SAVE_TIME;

    private boolean tickCacheForSave(){
        if (cacheSaveTime <= 0){
            cacheSaveTime = CACHE_SAVE_TIME;
            return true;
        }
        cacheSaveTime--;
        return false;
    }

    @Override
    public void run() {
        while(true) {

            if (!sleepFor()) return;

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
