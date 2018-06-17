package com.gcyh.jiedian.util;

import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;

/**
 * 发送event_bus事件工具
 * Created by caizhiguang on 18/4/13.
 */
public class EventBusUtil {

    public static void postEvent(int eventCode, Bundle bundle){
        if (bundle == null){
            bundle = new Bundle();
        }
        bundle.putInt(EventBusCode.EVENT_BUS_CODE, eventCode);
        EventBus.getDefault().post(bundle);
    }

    public static void postEvent(int eventCode){
        Bundle bundle = new Bundle();
        bundle.putInt(EventBusCode.EVENT_BUS_CODE, eventCode);
        EventBus.getDefault().post(bundle);
    }
}
