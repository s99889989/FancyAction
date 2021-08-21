package com.daxton.fancyaction.task;


import com.daxton.fancyaction.config.FileConfig;
import com.daxton.fancyaction.other.ActionControl;

public class Reload {
    //重新讀取的一些任務
    public static void execute(){
        //設定檔
        FileConfig.reload();
        //設置動作
        ActionControl.setActionMap();


    }

}
