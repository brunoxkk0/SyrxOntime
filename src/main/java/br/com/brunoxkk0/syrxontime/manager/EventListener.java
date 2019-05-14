package br.com.brunoxkk0.syrxontime.manager;


import br.com.brunoxkk0.syrxontime.SyrxOntime;
import br.com.brunoxkk0.syrxontime.data.TimeObj;
import br.com.brunoxkk0.syrxontime.data.store.Cache;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventListener implements Listener {

    public EventListener(){
        if(SyrxOntime.debug){SyrxOntime.debug("Registrando eventos!");}
        Bukkit.getPluginManager().registerEvents(this, SyrxOntime.getProvidingPlugin(SyrxOntime.class));

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        if(!Cache.cache.containsKey(e.getPlayer())){
            Cache.cache.put(e.getPlayer(), new TimeObj(e.getPlayer()));
        }
    }
}
