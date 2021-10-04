package com.daxton.fancyaction.listener;

import com.daxton.fancyaction.FancyAction;
import com.daxton.fancyaction.config.FileConfig;
import com.daxton.fancyaction.other.TriggerAction;
import com.daxton.fancycore.api.aims.entity.Convert;
import com.daxton.fancycore.api.other.DigitConversion;
import com.daxton.fancycore.other.hologram.FloatMessage;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class AttackedListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR) //玩家被攻擊
    public void onAttacked(EntityDamageByEntityEvent event){
        Entity entity = event.getEntity();
        Entity killer = event.getDamager();
        if(Bukkit.getServer().getPluginManager().getPlugin("Citizens") !=null){
            if(CitizensAPI.getNPCRegistry().isNPC(entity)){
                return;
            }
        }
        if(entity.getCustomName() != null && entity.getCustomName().equals("ModleEngine")){
            return;
        }
        if(!(entity instanceof Player)){
            return;
        }

        Player damaged = (Player) entity;

        LivingEntity killerLiving = (LivingEntity) Convert.convertEntity2(killer);

        //當玩家被攻擊
        TriggerAction.onPlayer(damaged, killerLiving, "~ondamaged");

    }


    @EventHandler(priority = EventPriority.MONITOR)//無攻擊者的被攻擊傷害
    public void onDamaged(EntityDamageEvent event){
        FileConfiguration configuration = FileConfig.config_Map.get("NoDamagerDamage.yml");
        boolean bb = configuration.getBoolean("NoDamagerDamage.enable");

        if(bb && event.getCause().toString().contains("CUSTOM")){
            LivingEntity entity = (LivingEntity) event.getEntity();
            Location location = entity.getLocation();
            FloatMessage floatMessage = FloatMessage.createFloatMessage(location.add(0,entity.getHeight(),0));


            String number = DigitConversion.NumberUtil(event.getFinalDamage(), configuration.getString("NoDamagerDamage.decimal")+"");
            String numberString = configuration.getString("NoDamagerDamage.content")+"";
            numberString = numberString.replace("{number}",number);
            numberString = "\uF80B"+numberString+"\uF80B";
            numberString = DigitConversion.NumberHead2(numberString, configuration.getString("NoDamagerDamage.head_conversion"));
            numberString = DigitConversion.NumberUnits2(numberString, configuration.getString("NoDamagerDamage.units_conversion"));
            String doubleString = configuration.getString("NoDamagerDamage.double_conversion")+"";
            String[] containKeyList = doubleString.split(",");

            for(String containKey : containKeyList){
                String[] containKeyList2 = containKey.split(">");
                if(containKeyList2.length == 2){
                    numberString = numberString.replace(containKeyList2[0],containKeyList2[1]);
                }
            }

            floatMessage.addLine(numberString);

            BukkitRunnable bukkitRunnable = new BukkitRunnable() {
                int tickRun = 0;
                @Override
                public void run() {
                    if(tickRun > configuration.getInt("NoDamagerDamage.duration")){
                        floatMessage.delete();
                        cancel();
                        return;
                    }
                    floatMessage.teleport(location.add(0,configuration.getDouble("NoDamagerDamage.tpHeight"),0));
                    tickRun++;
                }
            };
            bukkitRunnable.runTaskTimer(FancyAction.fancyAction,0,configuration.getInt("NoDamagerDamage.period"));
        }


    }

}
