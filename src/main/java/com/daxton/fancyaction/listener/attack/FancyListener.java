package com.daxton.fancyaction.listener.attack;

import com.daxton.fancyaction.other.TriggerAction;
import com.daxton.fancycore.api.aims.entity.Convert;
import com.daxton.fancycore.api.event.PhysicalDamageEvent;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import static org.bukkit.entity.EntityType.ARMOR_STAND;

public class FancyListener implements Listener {

	//攻擊監聽
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPhysicalDamageListener(PhysicalDamageEvent event){
		if(event.getTarget().getType() == ARMOR_STAND){
			return;
		}
		if(event.getTarget().getCustomName() != null && event.getTarget().getCustomName().equals("ModleEngine")){
			return;
		}
		if(Bukkit.getServer().getPluginManager().getPlugin("Citizens") !=null){
			if(CitizensAPI.getNPCRegistry().isNPC(event.getTarget())){
				return;
			}
		}

		if(event.getDamager() instanceof Player){

			LivingEntity target = event.getTarget();

			Player player = (Player) event.getDamager();

			String damageType = event.getDamageType();

			if(damageType.contains("PHYSICAL_MISS")){
				TriggerAction.onPlayer(player, target, "~onatkmiss");
			}

			if(damageType.contains("PHYSICAL_BLOCK")){
				TriggerAction.onPlayer(player, target, "~onatkmiss");
			}

			if(damageType.contains("PHYSICAL_CRITICAL")){
				TriggerAction.onPlayer(player, target, "~oncrit");
			}

			if(damageType.contains("Melee_ATTACK")){
				TriggerAction.onPlayer(player, target, "~onattack");
			}

			if(damageType.contains("RANGE_ATTACK")){
				TriggerAction.onPlayer(player, target, "~onattack");
			}

			if(damageType.contains("MAGIC_ATTACK")){
				TriggerAction.onPlayer(player, target, "~onmagic");
			}

		}

	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onAttack(EntityDamageByEntityEvent event){
		if(!(event.getEntity() instanceof LivingEntity) || event.getEntity().getType() == ARMOR_STAND){
			return;
		}
		if(Bukkit.getServer().getPluginManager().getPlugin("Citizens") !=null){
			if(CitizensAPI.getNPCRegistry().isNPC(event.getEntity())){
				return;
			}
		}

		Entity entity = Convert.convertEntity(event.getDamager());

		if(entity instanceof Player){
			Player player = (Player) entity;
			LivingEntity target = (LivingEntity) event.getEntity();
			if (event.isCancelled()) {
				TriggerAction.onPlayer(player, target, "~onatkmiss");
			}else {
				TriggerAction.onPlayer(player, target, "~onattack");
			}

		}

	}

}
