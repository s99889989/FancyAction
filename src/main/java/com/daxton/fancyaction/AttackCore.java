package com.daxton.fancyaction;

import com.daxton.fancyaction.config.FileConfig;
import com.daxton.fancyaction.listener.attack.AttackListener;
import com.daxton.fancyaction.listener.attack.MythicLibListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class AttackCore {

	public static void setCore(){

		FancyAction fancyAction = FancyAction.fancyAction;

		FileConfiguration config = FileConfig.config_Map.get("config.yml");

		String attackCore = config.getString("AttackCore")+"";

		if(attackCore.equalsIgnoreCase("MythicLib") && Bukkit.getServer().getPluginManager().getPlugin("MythicLib") != null && Bukkit.getPluginManager().isPluginEnabled("MythicLib")){
			fancyAction.getLogger().info(ChatColor.GREEN+"Loaded AttackCore: MythicLib");
			Bukkit.getPluginManager().registerEvents(new MythicLibListener(), fancyAction);
		}else {
			fancyAction.getLogger().info(ChatColor.GREEN+"Loaded AttackCore: Default");
			Bukkit.getPluginManager().registerEvents(new AttackListener(), fancyAction);
		}

	}

}
