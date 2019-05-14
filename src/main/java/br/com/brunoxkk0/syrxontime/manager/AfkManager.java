package br.com.brunoxkk0.syrxontime.manager;

import br.com.brunoxkk0.syrxontime.SyrxOntime;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

import java.util.HashMap;

public class AfkManager implements Listener {

    public static HashMap<Player, Long> data = new HashMap<>();
    public HashMap<Player, Float> lastyaw = new HashMap<>();

    public AfkManager(){
        Bukkit.getPluginManager().registerEvents(this, SyrxOntime.getProvidingPlugin(SyrxOntime.class));
    }

    public static boolean isAfk(Player player) {
        if(data.getOrDefault(player,0L) + 900000 < (System.currentTimeMillis())){
            return true;
        }else{
            return false;
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        if(data.containsKey(e.getPlayer())){
            data.remove(e.getPlayer());
        }

        if(lastyaw.containsKey(e.getPlayer())){
            lastyaw.remove(e.getPlayer());
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        data.put(e.getPlayer(), System.currentTimeMillis());
        lastyaw.put(e.getPlayer(), e.getPlayer().getLocation().getYaw());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDeath(PlayerDeathEvent e){

        if(!(e.getEntity() instanceof Player) || !e.getEntity().isOnline()){
            return;
        }

        float ly = lastyaw.getOrDefault(e.getEntity(), -1F);

        if(!(ly != -1 && ly == e.getEntity().getLocation().getYaw())){
            data.put(e.getEntity(), System.currentTimeMillis());
            lastyaw.put(e.getEntity(), e.getEntity().getLocation().getYaw());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onMove(PlayerMoveEvent e){
        float ly = lastyaw.getOrDefault(e.getPlayer(), -1F);

        if(!(ly != -1 && ly == e.getPlayer().getLocation().getYaw())){
            data.put(e.getPlayer(), System.currentTimeMillis());
            lastyaw.put(e.getPlayer(), e.getPlayer().getLocation().getYaw());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInteract(PlayerInteractEvent e){
        float ly = lastyaw.getOrDefault(e.getPlayer(), -1F);

        if(!(ly != -1 && ly == e.getPlayer().getLocation().getYaw())){
            data.put(e.getPlayer(), System.currentTimeMillis());
            lastyaw.put(e.getPlayer(), e.getPlayer().getLocation().getYaw());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onFish(PlayerFishEvent e){
        float ly = lastyaw.getOrDefault(e.getPlayer(), -1F);

        if(!(ly != -1 && ly == e.getPlayer().getLocation().getYaw())){
            data.put(e.getPlayer(), System.currentTimeMillis());
            lastyaw.put(e.getPlayer(), e.getPlayer().getLocation().getYaw());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(PlayerChatEvent e){
        float ly = lastyaw.getOrDefault(e.getPlayer(), -1F);

        if(!(ly != -1 && ly == e.getPlayer().getLocation().getYaw())){
            data.put(e.getPlayer(), System.currentTimeMillis());
            lastyaw.put(e.getPlayer(), e.getPlayer().getLocation().getYaw());
        }
    }
}
