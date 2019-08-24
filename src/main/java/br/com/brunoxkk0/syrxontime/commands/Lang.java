package br.com.brunoxkk0.syrxontime.commands;

import br.com.brunoxkk0.syrxontime.SyrxOntime;
import br.com.brunoxkk0.syrxontime.utils.ConfigAPI;
import org.bukkit.entity.Player;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Lang {

    private HashMap<String,String> msg = new HashMap<>();
    private ConfigAPI lang;

    public Lang(){

        lang = new ConfigAPI(SyrxOntime.getInstance(), "lang.yml");

        //COMMANDS
        msg.put("missing_perms", lang.getString("missing_perms","&cVocê não pode executar esse comando!"));
        msg.put("player_null", lang.getString("player_null","&cJogador não encontrado."));

        //REWARDS
        msg.put("reward_win", lang.getString("reward_win","&8[&6&lOntime&r&8]&r &fVocê acaba atingir&e%time&f de jogo hoje."));
        msg.put("reward_vault_money", lang.getString("reward_vault_money","&8[&6&lOntime&r&8]&r &fVocê acaba de receber &e%money&f."));
        msg.put("reward_xp", lang.getString("reward_xp","&8[&6&lOntime&r&8]&r &fVocê acaba de receber &e%xp&f de experiência."));
    }


    public String getMessage(String key){
        return msg.getOrDefault(key,"");
    }

    public String getMessage(String key, String defaultValue){
        return msg.getOrDefault(key,defaultValue);
    }

    public void process(String key, Player player){

        if(getMessage(key).split("\n").length > 1){
            String[] msg = getMessage(key).split("\n");

            for(String m : msg){
                player.sendMessage(m.replace("&","\u00a7"));
            }

        }else{
            player.sendMessage(getMessage(key).replace("&","\u00a7"));
        }

    }

    public void process(String key, String defaultValue, Player player){

        if(getMessage(key,defaultValue).split("\n").length > 1){
            String[] msg = getMessage(key,defaultValue).split("\n");

            for(String m : msg){
                player.sendMessage(m.replace("&","\u00a7"));
            }

        }else{
            player.sendMessage(getMessage(key,defaultValue).replace("&","\u00a7"));
        }

    }

    public void process(String key, Player player, String[][] replace){

        if(getMessage(key).split("\n").length > 1){
            String[] msg = getMessage(key).split("\n");

            for(String m : msg){
                String rm = replace(m,replace);

                player.sendMessage(rm.replace("&","\u00a7"));
            }

        }else{
            String rm = replace(getMessage(key),replace);
            player.sendMessage(rm.replace("&","\u00a7"));
        }

    }

    public void process(String key, String defaultValue, Player player, String[][] replace){

        if(getMessage(key,defaultValue).split("\n").length > 1){
            String[] msg = getMessage(key,defaultValue).split("\n");

            for(String m : msg){
                String rm = replace(m,replace);

                player.sendMessage(rm.replace("&","\u00a7"));
            }

        }else{
            String rm = replace(getMessage(key,defaultValue),replace);
            player.sendMessage(rm.replace("&","\u00a7"));
        }

    }

    private String replace(String string, String[][] replace){
        String s = string;

        for (String[] strings : replace) {

            List<String> list = Arrays.asList(strings);

            if (list.size() == 2) {
                s = s.replace(list.get(0), list.get(1));
            }

        }

        return s;
    }

}
