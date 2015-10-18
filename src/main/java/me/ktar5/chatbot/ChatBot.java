package me.ktar5.chatbot;
/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of ChatBot.
 * 
 * ChatBot can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ChatBot extends JavaPlugin implements Listener {

    private final String NAME = "C-Bot";
    private final Set<String> ACTIVATORS = new HashSet<>(Arrays.asList("bot", "cbot", "c-bot"));
    private final String PREFIX = ChatColor.translateAlternateColorCodes('&', "&c&l" + NAME + "&r&f&l" + " > ");
    private ChatterBotSession session;

    @Override
    public void onEnable(){
        getServer().getPluginManager().registerEvents(this, this);
        try {
            ChatterBotFactory factory = new ChatterBotFactory();
            ChatterBot bot = factory.create(ChatterBotType.CLEVERBOT);
            session = bot.createSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void ChatEvent(AsyncPlayerChatEvent event) throws Exception {
        if(event.getMessage().startsWith("@")){
            String name = event.getMessage().substring(0, event.getMessage().indexOf(" "));
            name = name.replace("@", "");
            name = name.toLowerCase();
            if(ACTIVATORS.contains(name)){
                String message = event.getMessage().substring(event.getMessage().indexOf(" "));
                Bukkit.getServer().broadcastMessage(event.getPlayer().getName() + " > " + message);
                Bukkit.getServer().broadcastMessage(PREFIX + session.think(message));
                event.setCancelled(true);
            }
        }
    }

}
