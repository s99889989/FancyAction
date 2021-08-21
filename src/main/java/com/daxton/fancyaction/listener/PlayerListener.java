package com.daxton.fancyaction.listener;


import com.daxton.fancyaction.api.PlayerDataAction;
import com.daxton.fancyaction.manager.PlayerManagerAction;
import com.daxton.fancyaction.other.TriggerAction;
import com.daxton.fancycore.manager.PlayerManagerCore;
import com.daxton.fancycore.other.playerdata.PlayerDataFancy;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;

import java.util.UUID;


public class PlayerListener implements Listener {
    //當玩家登入
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        TriggerAction.onPlayer(player, null, "~onjoin");
        //玩家動作Data
        PlayerManagerAction.player_PlayerData.putIfAbsent(uuid, new PlayerDataAction());
        //玩家的F狀態
        PlayerDataFancy playerDataFancy = PlayerManagerCore.player_Data_Map.get(uuid);
        playerDataFancy.player_F = false;
    }

    //當玩家登出
    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        TriggerAction.onPlayer(player, null, "~onquit");

        //玩家動作Data
        PlayerManagerAction.player_PlayerData.remove(uuid);

    }

    //當玩家移動
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        TriggerAction.onPlayer(player, null, "~onmove");
    }
    //當玩家蹲下
    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event){
        Player player = event.getPlayer();
        if(event.isSneaking()){
            TriggerAction.onPlayer(player, null, "~onsneak");
        }else {
            TriggerAction.onPlayer(player, null, "~onstandup");
        }
    }
    //當玩家死亡
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        UUID uuid = player.getUniqueId();

        TriggerAction.onPlayer(player, null, "~ondeath");

        //玩家的F狀態
        PlayerDataFancy playerDataFancy = PlayerManagerCore.player_Data_Map.get(uuid);
        playerDataFancy.player_F = false;
        TriggerAction.onPlayer(player, null, "~onkeyfoff");
    }
    //當玩家回血
    @EventHandler
    public void onRegainHealth(EntityRegainHealthEvent event){
        Entity entity = event.getEntity();
        if(entity instanceof Player){
            Player player = (Player) entity;
            TriggerAction.onPlayer(player, null, "~onregainhealth");
        }
    }

    //當玩家聊天
    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        TriggerAction.onPlayer(player, null, "~onchat");
    }

    //當經驗值改變時
    @EventHandler
    public void onExpChange(PlayerExpChangeEvent event){
        Player player = event.getPlayer();
        TriggerAction.onPlayer(player, null, "~onexpup");

    }

    //當等級改變時
    @EventHandler
    public void onLevelChange(PlayerLevelChangeEvent event){
        Player player = event.getPlayer();
        int oldLevel = event.getOldLevel();
        int newLevel = event.getNewLevel();
        if(newLevel > oldLevel){
            TriggerAction.onPlayer(player, null, "~onlevelup");
        }else {
            TriggerAction.onPlayer(player, null, "~onexpdown");
            TriggerAction.onPlayer(player, null, "~onleveldown");
        }

    }
    //怪物死亡
    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if(event.getEntity().getKiller() != null){
            Player player = event.getEntity().getKiller();
            TriggerAction.onPlayer(player, null, "~onmobdeath");
        }
    }

    //當玩家點擊時
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Action action = event.getAction();
        EquipmentSlot equipmentSlot = event.getHand();
        Block block = event.getClickedBlock();
        //左鍵點擊空氣
        if(action == Action.LEFT_CLICK_AIR){
            TriggerAction.onPlayer(player, null, "~leftclickair");
            return;
        }
        //左鍵點擊方塊
        if(action == Action.LEFT_CLICK_BLOCK){
            TriggerAction.onPlayer(player, null, "~leftclickblock");
            return;
        }
        //確定是主手
        if(equipmentSlot == EquipmentSlot.HAND){
            //右鍵點擊空氣
            if(action == Action.RIGHT_CLICK_AIR){
                TriggerAction.onPlayer(player, null, "~rightclickair");
                return;
            }
            //右鍵點擊方塊
            if(action == Action.RIGHT_CLICK_BLOCK){
                if (block != null) {
                    if(block.getType().toString().endsWith("_BUTTON")){
                        TriggerAction.onPlayer(player, null, "~onbutton");
                        return;
                    }
                    if(block.getType() == Material.LEVER){
                        String test = block.getBlockData().toString();
                        int start = test.indexOf("powered=")+8;
                        int end = test.indexOf("]", start);
                        String name = test.substring(start, end);
                        boolean bb = Boolean.parseBoolean(name);
                        if(bb){
                            TriggerAction.onPlayer(player, null, "~onleveron");
                        }else {
                            TriggerAction.onPlayer(player, null, "~onleveroff");
                        }
                        return;
                    }
                }
                TriggerAction.onPlayer(player, null, "~rightclickblock");
                return;
            }
        }
        //踩到壓力板時
        if(action == Action.PHYSICAL){
            TriggerAction.onPlayer(player, null, "~pressureplate");
        }

    }

    //當按下F鍵
    @EventHandler
    public void onSwapHand(PlayerSwapHandItemsEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        //玩家的F狀態
        PlayerDataFancy playerDataFancy = PlayerManagerCore.player_Data_Map.get(uuid);
        playerDataFancy.player_F = !playerDataFancy.player_F;
        if(playerDataFancy.player_F){
            TriggerAction.onPlayer(player, null, "~onkeyfon");
        }else {
            TriggerAction.onPlayer(player, null, "~onkeyfoff");
        }

    }

    //當按下切換1~9時
    @EventHandler
    public void onItemHeld(PlayerItemHeldEvent event){
        Player player = event.getPlayer();
        int key = event.getNewSlot();
        switch(key){
            case 0:
                TriggerAction.onPlayer(player, null, "~onkey1");
                break;
            case 1:
                TriggerAction.onPlayer(player, null, "~onkey2");
                break;
            case 2:
                TriggerAction.onPlayer(player, null, "~onkey3");
                break;
            case 3:
                TriggerAction.onPlayer(player, null, "~onkey4");
                break;
            case 4:
                TriggerAction.onPlayer(player, null, "~onkey5");
                break;
            case 5:
                TriggerAction.onPlayer(player, null, "~onkey6");
                break;
            case 6:
                TriggerAction.onPlayer(player, null, "~onkey7");
                break;
            case 7:
                TriggerAction.onPlayer(player, null, "~onkey8");
                break;
            case 8:
                TriggerAction.onPlayer(player, null, "~onkey9");
                break;
        }


    }

}
