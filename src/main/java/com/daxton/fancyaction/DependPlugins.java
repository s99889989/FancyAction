package com.daxton.fancyaction;



import com.daxton.fancyaction.listener.EqmListener;
import com.daxton.fancyaction.listener.PlayerListener;
import org.bukkit.Bukkit;

import static com.daxton.fancyaction.config.FileConfig.languageConfig;


public class DependPlugins {

    public static boolean depend(){

        FancyAction fancyAction = FancyAction.fancyAction;

        if (Bukkit.getServer().getPluginManager().getPlugin("FancyCore") != null && Bukkit.getPluginManager().isPluginEnabled("FancyCore")){
            fancyAction.getLogger().info(languageConfig.getString("LogMessage.LoadFancyCore"));
        }else {
            if(languageConfig != null){
                languageConfig.getStringList("LogMessage.UnLoadFancyCore").forEach(message-> fancyAction.getLogger().info(message));
            }else {
                fancyAction.getLogger().info("§4*** FancyCore is not installed or not enabled. ***");
                fancyAction.getLogger().info("§4*** FancyAction will be disabled. ***");
            }

            return false;
        }

        if (Bukkit.getServer().getPluginManager().getPlugin("FancyEquipment") != null && Bukkit.getPluginManager().isPluginEnabled("FancyEquipment")){

            if(languageConfig.getString("LogMessage.LoadFancyEquipment") != null){
                fancyAction.getLogger().info(languageConfig.getString("LogMessage.LoadFancyEquipment"));
            }else {
                fancyAction.getLogger().info("§aLoaded FancyEquipment.");
            }
            Bukkit.getPluginManager().registerEvents(new EqmListener(), fancyAction);
        }

        return true;
    }

}
