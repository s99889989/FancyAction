package com.daxton.fancyaction;

import com.daxton.fancyaction.command.MainCommand;
import com.daxton.fancyaction.command.TabCommand;
import com.daxton.fancyaction.config.FileConfig;
import com.daxton.fancyaction.listener.attack.AttackListener;
import com.daxton.fancyaction.listener.AttackedListener;
import com.daxton.fancyaction.listener.PlayerListener;
import com.daxton.fancyaction.listener.attack.MythicLibListener;
import com.daxton.fancyaction.task.Start;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

import static com.daxton.fancyaction.config.FileConfig.languageConfig;

public final class FancyAction extends JavaPlugin {

    public static FancyAction fancyAction;

    @Override
    public void onEnable() {
        fancyAction = this;
        //設定檔
        FileConfig.execute();
        if(!DependPlugins.depend()){
            fancyAction.setEnabled(false);
        }
        //指令
        Objects.requireNonNull(Bukkit.getPluginCommand("fancyaction")).setExecutor(new MainCommand());
        Objects.requireNonNull(Bukkit.getPluginCommand("fancyaction")).setTabCompleter(new TabCommand());
        //開服執行任務
        Start.execute();
        //監聽
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), fancyAction);
        Bukkit.getPluginManager().registerEvents(new AttackedListener(), fancyAction);
        //傷害核心
        AttackCore.setCore();

    }

    @Override
    public void onDisable() {
        if(languageConfig != null){
            fancyAction.getLogger().info(languageConfig.getString("LogMessage.Disable"));
        }
    }
}
