package com.daxton.fancyaction.other;

import com.daxton.fancyaction.FancyAction;
import com.daxton.fancyaction.config.FileConfig;
import com.daxton.fancycore.api.config.SearchConfigMap;
import com.daxton.fancycore.other.taskaction.StringToMap;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ActionControl {
	//動作文件 動作表
	public static Map<String, List<Map<String, String>>> action_MapList_Map = new ConcurrentHashMap<>();
	//把動作文件轉成動作Map 並分文件路徑儲存
	public static void setActionMap(){
		FancyAction fancyAction = FancyAction.fancyAction;

		SearchConfigMap.filePathList(FileConfig.config_Map, "Action/", true).forEach(configName -> {
			//fancyAction.getLogger().info(configName);

			FileConfiguration ationConfig = FileConfig.config_Map.get(configName+".yml");

			List<Map<String, String>> actionList = new ArrayList<>();

			ationConfig.getStringList("Action").forEach(actionString -> {
				Map<String, String> string_Map = StringToMap.toActionMap(actionString);
				actionList.add(string_Map);
				//fancyAction.getLogger().info(actionString);
			});
			//儲存的檔案名稱
			String putName = configName.replace("Action/", "").replace("/", ".");

			action_MapList_Map.put(putName, actionList);
		});
		//action_MapList_Map.forEach((s, maps) -> fancyAction.getLogger().info(s));
	}


}
