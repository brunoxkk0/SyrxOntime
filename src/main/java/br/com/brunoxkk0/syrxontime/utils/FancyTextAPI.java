package br.com.brunoxkk0.syrxontime.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class FancyTextAPI {

    private String text = "";
    private String hoverText = null;
    private String runCommandActionText = null;
    private String suggestCommandAction = null;

    public FancyTextAPI() {
    }

    public FancyTextAPI(String text) {
        this.text = text;
    }

    public FancyTextAPI(List<String> textList) {
        this.text = String.join("\n", textList);
    }

    public FancyTextAPI(String text, String hoverText) {
        this.text = text;
        this.hoverText = hoverText;
    }

    public FancyTextAPI(String text, String hoverText, String runCommandActionText) {
        this.text = text;
        this.hoverText = hoverText;
        this.runCommandActionText = runCommandActionText;
    }

    public FancyTextAPI(String text, String hoverText, String commandActionText, boolean suggestionCommand) {
        this.text = text;
        this.hoverText = hoverText;
        if (suggestionCommand){
            this.suggestCommandAction = commandActionText;
        }else {
            this.runCommandActionText = commandActionText;
        }
    }

    /**
     *   Seta o texto dessa FancyMessage, por padrão, o texto é vazio "";
     */
    public FancyTextAPI setText(String text) {
        this.text = text;
        return this;
    }
    public FancyTextAPI setText(List<String> textList){
        this.text = String.join("\n", textList);
        return this;
    }

    /**
     *   Seta o texto dessa FancyMessage, por padrão, o texto é vazio "";
     */
    public FancyTextAPI addText(String text) {
        this.text += text;
        return this;
    }
    public FancyTextAPI addText(List<String> textList){
        this.text += String.join("\n", textList);
        return this;
    }

    /**
     *   Seta o a HoverMessage dessa FancyMessage;
     */
    public FancyTextAPI setHoverText(String hoverText) {
        this.hoverText = hoverText;
        return this;
    }
    public FancyTextAPI setHoverText(List<String> hoverTextList){
        this.hoverText = String.join("\n", hoverTextList);
        return this;
    }

    /**
     *   Seta o a RunCommand dessa FancyMessage;
     *   (Comando executado quando o jogador clica na mensagem)
     */
    public FancyTextAPI setRunCommandActionText(String runCommandActionText) {
        this.runCommandActionText = runCommandActionText;
        return this;
    }

    /**
     *   Seta o a SuggestCommand dessa FancyMessage;
     *   (Comando sugerido quando o jogador clica na mensagem)
     */
    public FancyTextAPI setSuggestCommandAction(String suggestCommandAction) {
        this.suggestCommandAction = suggestCommandAction;
        return this;
    }


    /**
     *   Retorna a parte do comando fancytext dessa FancyMessage!
     *
     *   Vale salientar que esse método nao retorna o comando correto do fancytext, apenas uma parte dele!
     *
     *   Use as funções do FancyTextAPI.class ao invés dessa!
     */
    public String getTellRawString(){


        int currentColorStart = 0;
        int currentFormatStart = 0;
        int currentChangerStart = 0;







        String tellRaw = "{\"text\":\"" + this.text + "\"";
        if (hoverText != null){
            tellRaw = tellRaw + ",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + this.hoverText + "\"}";
        }
        if (runCommandActionText != null){
            tellRaw = tellRaw + ",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + this.runCommandActionText + "\"}";
        }else if (suggestCommandAction != null){
            tellRaw = tellRaw + ",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"" + this.suggestCommandAction + "\"}";
        }
        tellRaw = tellRaw + "}";
        return tellRaw;
    }


    // ------------------------------------------------------------------------------------------------------
    // Métodos Estáticos para se entregar as mensagens
    // ------------------------------------------------------------------------------------------------------


    /**
     *   Recebe um player como argumento e uma List de FancyTextElement
     *
     *   Monta o comando para esses FancyTextElement
     *   e envia a mensagem para o jogador!
     */
    public static void sendTo(CommandSender player, List<FancyTextAPI> texts){
        if(player instanceof Player){
            String command = tellRawCommandBuilder(texts);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + player.getName() + " " + command);
        }else {
            player.sendMessage(textOnlyTextBuilder(texts));
        }
    }

    /**
     *   Monta o comando para esses FancyTextElement
     *   e envia a mensagem para todos os jogadores!
     */
    public static void tellRawBroadcast(List<FancyTextAPI> texts){
        String command = tellRawCommandBuilder(texts);
        for (Player player : Bukkit.getServer().getOnlinePlayers()){
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + player.getName() + " " + command);
        }
        Bukkit.getConsoleSender().sendMessage(textOnlyTextBuilder(texts));
    }

    /**
     *   Recebe uma List de FancyTextElement e retorna
     *   o comando (String) corresponde a lista!
     */
    public static String tellRawCommandBuilder(List<FancyTextAPI> texts){
        String command = "[\"\"";
        for (FancyTextAPI aFancyTextElement : texts){
            command = command + "," + aFancyTextElement.getTellRawString();
        }
        command = command + "]";
        return command;
    }

    /**
     *   Recebe uma List de FancyTextElement e retorna
     *   o comando (String) corresponde a lista!
     */
    public static String textOnlyTextBuilder(List<FancyTextAPI> texts){
        StringBuilder text = new StringBuilder();
        for (FancyTextAPI aFancyTextElement : texts){
            text.append(aFancyTextElement.text);
        }
        return text.toString();
    }


    // ------------------------------------------------------------------------------------------------------
    // Faz a mesma coisa que os de cima, contudo, recebe itens diretamente, nao necessariamente em uma lista!
    // Um vetor simples por exemplo!
    // ------------------------------------------------------------------------------------------------------


    public static void sendTo(CommandSender player, FancyTextAPI... texts){
        if(player instanceof Player){
            String command = tellRawCommandBuilder(texts);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + player.getName() + " " + command);
        }else {
            player.sendMessage(textOnlyTextBuilder(texts));
        }
    }

    public static void tellRawBroadcast(FancyTextAPI... texts){
        String command = tellRawCommandBuilder(texts);
        for (Player player : Bukkit.getServer().getOnlinePlayers()){
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + player.getName() + " " + command);
        }
        Bukkit.getConsoleSender().sendMessage(textOnlyTextBuilder(texts));
    }

    public static String tellRawCommandBuilder(FancyTextAPI... texts){
        String command = "[\"\"";
        for (FancyTextAPI aFancyTextElement : texts){
            command = command + "," + aFancyTextElement.getTellRawString();
        }
        command = command + "]";
        return command;
    }

    /**
     *   Recebe uma List de FancyTextElement e retorna
     *   o comando (String) corresponde a lista!
     */
    public static String textOnlyTextBuilder(FancyTextAPI... texts){
        StringBuilder text = new StringBuilder();
        for (FancyTextAPI aFancyTextElement : texts){
            text.append(aFancyTextElement.text);
        }
        return text.toString();
    }

}
