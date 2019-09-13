package br.com.brunoxkk0.syrxontime.manager;


import br.com.brunoxkk0.syrxontime.SyrxOntime;
import br.com.brunoxkk0.syrxontime.data.PlayerTime;
import br.com.brunoxkk0.syrxontime.data.Provider;
import br.com.brunoxkk0.syrxontime.data.store.Cache;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventListener implements Listener {

    public static void setup(){
        SyrxOntime.logger().info("Registrando eventos...");
        Bukkit.getPluginManager().registerEvents(new EventListener(), SyrxOntime.getProvidingPlugin(SyrxOntime.class));

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        if(SyrxOntime.isIsCauldron()){
            if(!Cache.exist(Provider.getOfflinePlayer(e.getPlayer()))){
                Cache.create(e.getPlayer());
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        if(SyrxOntime.isIsCauldron()){
            if(Cache.exist(Provider.getOfflinePlayer(e.getPlayer()))){
                Cache.resetLastUpdate(e.getPlayer());
            }
        }
    }
}
