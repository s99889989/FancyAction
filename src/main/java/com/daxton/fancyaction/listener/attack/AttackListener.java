package com.daxton.fancyaction.listener.attack;

import com.daxton.fancyaction.other.TriggerAction;
import com.daxton.fancycore.api.aims.entity.Convert;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class AttackListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR ) //玩家攻擊
    public void onAttack(EntityDamageByEntityEvent event){
        Entity entity = Convert.convertEntity(event.getDamager());
        Entity damaged = event.getEntity();
        if(Bukkit.getServer().getPluginManager().getPlugin("Citizens") !=null){
            if(CitizensAPI.getNPCRegistry().isNPC(event.getEntity())){
                return;
            }
        }
        if(damaged.getCustomName() != null && damaged.getCustomName().equals("ModleEngine")){
            return;
        }
        if(!(entity instanceof Player) || !(damaged instanceof LivingEntity)){
            return;
        }
        LivingEntity livingDamaged = (LivingEntity) event.getEntity();
        Player killer = (Player) entity;
        //當玩家攻擊
        TriggerAction.onPlayer(killer, livingDamaged, "~onattack");

    }






}
