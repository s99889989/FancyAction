package com.daxton.fancyaction.listener;

import com.daxton.fancyaction.FancyAction;
import com.daxton.fancyaction.other.TriggerAction;
import com.daxton.fancycore.api.event.PlayerPackReceivedEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ModListener implements Listener {

	@EventHandler
	public void onPlayerPackReceived(PlayerPackReceivedEvent event){
		Player player = event.getPlayer();
		String type = event.getType();
		String receivedString = event.getReceived();

		//判定有無裝模組 和 模組版本正確與否
		if(type.equalsIgnoreCase("keyboard")){
			String[] keyBoardArray = receivedString.split("\\.");
			String keyID = keyBoardArray[0];
			String keyAction = keyBoardArray[1];
			String keyString = keyBoardArray[2];
			if(keyAction.equals("1")){
				TriggerAction.onPlayer(player, null, "~onKeyOn"+keyString);
			}else {
				TriggerAction.onPlayer(player, null, "~onKeyOff"+keyString);
			}
			//player.sendMessage(receivedString);
			//FancyAction.sendLogger(receivedString);

		}
	}

}
