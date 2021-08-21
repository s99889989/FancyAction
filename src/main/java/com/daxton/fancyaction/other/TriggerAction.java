package com.daxton.fancyaction.other;

import com.daxton.fancyaction.FancyAction;
import com.daxton.fancycore.other.taskaction.MapGetKey;
import com.daxton.fancycore.task.TaskAction;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TriggerAction {

	//玩家觸發動作
	public static void onPlayer(Player player, LivingEntity target, String triggerName){

		UUID uuid = player.getUniqueId();

		ActionControl.action_MapList_Map.forEach((actionFile, actionList) -> {

			if(!actionFile.equals("Default") && player.hasPermission("fancyaction."+actionFile.toLowerCase())){
				//FancyAction.fancyAction.getLogger().info(actionFile);
				actionList.forEach(stringStringMap -> {
					MapGetKey mapGetKey =  new MapGetKey(stringStringMap, player, target);
					String targetString = mapGetKey.getString(new String[]{"triggerkey"}, "");
					if(targetString.equalsIgnoreCase(triggerName)){
						TaskAction.execute(player, target, stringStringMap, null, (int)(Math.random()*Integer.MAX_VALUE)+"");

					}
				});
			}else {
				//FancyAction.fancyAction.getLogger().info(actionFile);
				actionList.forEach(stringStringMap -> {
					MapGetKey mapGetKey =  new MapGetKey(stringStringMap, player, target);
					String targetString = mapGetKey.getString(new String[]{"triggerkey"}, "");
					//FancyAction.fancyAction.getLogger().info(targetString);
					if(targetString.equalsIgnoreCase(triggerName)){
						TaskAction.execute(player, target, stringStringMap, null, (int)(Math.random()*Integer.MAX_VALUE)+"");

					}
				});
			}

		});

	}



}
