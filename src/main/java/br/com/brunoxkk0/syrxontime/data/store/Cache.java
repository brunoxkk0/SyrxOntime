package br.com.brunoxkk0.syrxontime.data.store;

import br.com.brunoxkk0.syrxontime.data.TimeObj;
import br.com.brunoxkk0.syrxontime.utils.Clock;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Cache {
    public static int todayDAY = Clock.currentDay;
    public static HashMap<OfflinePlayer, TimeObj> cache = new HashMap<org.bukkit.OfflinePlayer, TimeObj>();
}
