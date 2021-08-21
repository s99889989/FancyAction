package com.daxton.fancyaction.listener.attack;

import com.daxton.fancyaction.other.TriggerAction;
import com.daxton.fancycore.api.aims.entity.Convert;
import io.lumine.mythic.lib.api.DamageType;
import io.lumine.mythic.lib.api.event.PlayerAttackEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Set;

public class MythicLibListener implements Listener {

	private boolean crit = false;

	private Set<DamageType> damageTypeSet;

	@EventHandler(priority = EventPriority.HIGHEST)//攻擊
	public void onPhysicalDamage(PlayerAttackEvent event){
		crit = event.isCrit();
		damageTypeSet = event.getAttack().getTypes();

	}

	@EventHandler(priority = EventPriority.MONITOR ) //玩家攻擊
	public void onAttack(EntityDamageByEntityEvent event){
		Entity entity = Convert.convertEntity(event.getDamager());
		Entity damaged = event.getEntity();
		if(!(entity instanceof Player) || !(damaged instanceof LivingEntity)){
			return;
		}
		LivingEntity livingDamaged = (LivingEntity) event.getEntity();
		Player killer = (Player) entity;

		if (event.isCancelled()) {
			TriggerAction.onPlayer(killer, livingDamaged, "~onatkmiss");
			return;
		}

		if(damageTypeSet.contains(DamageType.PHYSICAL)){
			if(crit){
				//當玩家普通攻擊
				TriggerAction.onPlayer(killer, livingDamaged, "~oncrit");
			}else {
				//當玩家普通攻擊暴擊
				TriggerAction.onPlayer(killer, livingDamaged, "~onattack");
			}
		}
		if(damageTypeSet.contains(DamageType.MAGIC)){
			if(crit){
				//當玩家魔法攻擊暴擊
				TriggerAction.onPlayer(killer, livingDamaged, "~onmcrit");
			}else {
				//當玩家魔法攻擊
				TriggerAction.onPlayer(killer, livingDamaged, "~onmagic");
			}
		}





	}



}
