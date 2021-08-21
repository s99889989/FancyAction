package com.daxton.fancyaction.command;

import com.daxton.fancyaction.FancyAction;
import com.daxton.fancyaction.manager.PlayerManagerAction;
import com.daxton.fancyaction.other.TriggerAction;
import com.daxton.fancyaction.task.Reload;
import com.daxton.fancycore.manager.PlayerManagerCore;
import com.daxton.fancycore.other.playerdata.PlayerDataFancy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static com.daxton.fancyaction.config.FileConfig.languageConfig;

public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args){

        if(sender instanceof Player){
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();
            if(args.length == 2 && args[0].equalsIgnoreCase("cast")) {
                //玩家輸入的指令
                PlayerDataFancy playerDataFancy = PlayerManagerCore.player_Data_Map.get(uuid);
                playerDataFancy.player_command = args[1];
                //當玩家輸入指令
                TriggerAction.onPlayer(player, null, "~oncommand");
            }
        }
        //以下為OP指令
        if(sender instanceof Player && !sender.isOp()){
            return true;
        }

        //重新讀取設定
        if(args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            //重新讀取的一些程序
            Reload.execute();
            if(sender instanceof Player){
                Player player = (Player) sender;
                player.sendMessage(languageConfig.getString("OpMessage.Reload")+"");
            }
            FancyAction.fancyAction.getLogger().info(languageConfig.getString("LogMessage.Reload")+"");
        }

        return true;
    }

}
