package br.com.brunoxkk0.syrxontime.commands;

import org.bukkit.entity.Player;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Lang {

    private HashMap<String,String> msg = new HashMap<>();

    public Lang(){

        //COMMANDS
        msg.put("missing_perms","&cVocê não pode executar esse comando!");
        msg.put("player_null","&cJogador não encontrado.");

        //REWARDS
        msg.put("reward_win","&8[&6&lOntime&r&8]&r &fVocê acaba atingir&e%time&f de jogo hoje.");
        msg.put("reward_vault_money","&8[&6&lOntime&r&8]&r &fVocê acaba de receber &e%money&f.");
        msg.put("reward_xp","&8[&6&lOntime&r&8]&r &fVocê acaba de receber &e%xp&f de experiência.");
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

        for(int i = 0; i < replace.length;i++){

            List<String> list = Arrays.asList(replace[i]);

            if(list.size() == 2){
                s = s.replace(list.get(0),list.get(1));
            }

        }

        return s;
    }

}
