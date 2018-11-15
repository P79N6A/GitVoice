package com.ellison.library.utils.system;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ellison.library.application.DefaultApplication;
import com.ellison.library.utils.common.NetUtil;

import java.util.ArrayList;

/**
 * @desc (监听网络的广播)
 * @createtime 2016/12/16 0016--14:55
 * @Created by wukefan.
 */
public class NetBroadcastReceiver extends BroadcastReceiver {
    public static ArrayList<NetEventHandler> mListeners = new ArrayList<NetEventHandler>();
    private static String NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(NET_CHANGE_ACTION)) {
            DefaultApplication.mNetWorkState = NetUtil.getNetworkState(context);
            if (mListeners.size() > 0) {// 通知接口完成加载
                for (NetEventHandler handler : mListeners) {
                    handler.onNetChange();
                }
            }
        }
    }

    public static abstract interface NetEventHandler {
        /**
         * 网络更改
         */
        public abstract void onNetChange();
    }
}
