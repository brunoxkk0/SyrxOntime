package br.com.brunoxkk0.syrxontime.manager;


import br.com.brunoxkk0.syrxontime.SyrxOntime;
import br.com.brunoxkk0.syrxontime.data.store.Cache;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventListener implements Listener {

    public static void setup(){
        SyrxOntime.logger().info("Registrando eventos...");
        Bukkit.getPluginManager().registerEvents(new EventListener(), SyrxOntime.getProvidingPlugin(SyrxOntime.class));

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        if(!Cache.exist(e.getPlayer())){
            Cache.create(e.getPlayer());
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        e.setMessage(e.getMessage().replace("&","\u00a7"));
    }
}
