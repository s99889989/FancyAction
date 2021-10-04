package com.daxton.fancyaction.listener;

import com.daxton.fancyaction.other.TriggerAction;
import com.daxton.fancyequipment.api.event.EquipmentChangeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EqmListener implements Listener {

	//當裝備切換
	@EventHandler
	public void onEqmChange(EquipmentChangeEvent event){
		Player player = event.getPlayer();
		TriggerAction.onPlayer(player, null, "~eqmchange");
		//player.sendMessage(event.eqmSlot+" : "+event.getItemStack().getType());
	}

}
