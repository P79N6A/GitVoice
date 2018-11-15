package com.ellison.library.utils.system;

import android.app.Activity;
import android.content.Context;
import android.os.Process;

import com.ellison.library.logger.L;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Stack;


/**
 * @author : Ellison.Sun
 * @ClassName : ActivityStackManager
 * @Description : (应用程序Activity管理类)
 * @date : 2017年3月15日20:52:22
 */
public class ActivityStackManager {
    private static Stack<WeakReference<Activity>> activityStack;
    private static ActivityStackManager INSTANCE = new ActivityStackManager();

    private ActivityStackManager() {
    }

    public static ActivityStackManager create() {
        return INSTANCE;
    }

    /**
     * 获取当前Activity栈中元素个数
     */
    public int getCount() {
        return activityStack.size();
    }

    /**
     * 添加Activity到栈
     */
    public void addActivity(WeakReference<Activity> activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }


    /**
     * 结束指定的Activity(重载)
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
//            activity.finish();//此处不用finish
        }
    }

    /**
     * 获取当前Activity（栈顶Activity）
     */
    public Activity getTopActivity() {
        if (activityStack == null || activityStack.isEmpty()) {
            return null;
        }
        Activity activity = activityStack.lastElement().get();
        if (null == activity) {
            return null;
        } else {
            return activity;
        }
    }

    /**
     * 获取当前Activity（栈顶Activity） 没有找到则返回null
     */
    public Activity getActivityByClass(Class<?> cls) {
        Activity activity = null;
        if (activityStack == null || activityStack.isEmpty()) {
            return null;
        }
        for (WeakReference<Activity> aty : activityStack) {
            if (aty.getClass().equals(cls)) {
                activity = aty.get();
                break;
            }
        }
        return activity;
    }

    /**
     * 结束当前Activity（栈顶Activity）
     */
    public void finishTopActivity() {
        if (activityStack == null || activityStack.isEmpty()) {
            return;
        }
        try {
            WeakReference<Activity> activity = activityStack.lastElement();
            finishActivity(activity);
        } catch (Exception e) {
            L.e(e.getMessage());
        }
    }

    /**
     * 结束指定的Activity(重载)
     */
    public void finishActivity(WeakReference<Activity> cls) {
        if (activityStack == null || activityStack.isEmpty()) {
            return;
        }
        try {
            Iterator<WeakReference<Activity>> iterator = activityStack.iterator();
            while ((iterator.hasNext())) {
                WeakReference<Activity> stackActivity = iterator.next();
                if (stackActivity.get() == null) {
                    iterator.remove();
                    continue;
                }
                // 获取到相同的Activity进行移除
                if (stackActivity.get().getClass().getName().equals(cls.get().getClass().getName())) {
                    iterator.remove();
                    stackActivity.get().finish();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭除了指定activity以外的全部activity 如果cls不存在于栈中，则栈全部清空
     *
     * @param cls
     */
    public void finishOthersActivity(Class<?> cls) {
        if (activityStack == null || activityStack.isEmpty()) {
            return;
        }
        try {
            for (int i = 0; i < activityStack.size(); i++) {
                WeakReference<Activity> activity = activityStack.get(i);
                if (activity.getClass().equals(cls)) {
                    break;
                }
                if (activityStack.get(i) != null) {
                    finishActivity(activity);
                }
            }
        } catch (Exception e) {
            L.e(e.getMessage());
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (activityStack == null || activityStack.isEmpty()) {
            return;
        }
        try {
            ListIterator<WeakReference<Activity>> weakReferenceListIterator = activityStack.listIterator();
            while (weakReferenceListIterator.hasNext()) {
                Activity baseActivity = weakReferenceListIterator.next().get();
                if (baseActivity != null) {
                    baseActivity.finish();
                }
                weakReferenceListIterator.remove();
            }
        } catch (Exception e) {
            L.e(e.getMessage());
        }
    }

    /**
     * 获取到Activity栈
     *
     * @return 返回栈
     */
    public static Stack<WeakReference<Activity>> getActivityStack() {
        return activityStack;
    }

    /**
     * 应用程序退出
     */
    public void appExit(Context context) {
        try {
            finishAllActivity();
            Process.killProcess(Process.myPid());
//            Runtime.getRuntime().exit(0);
        } catch (Exception e) {
            Runtime.getRuntime().exit(-1);
        }
    }

}
