package com.ellison.library.utils.common;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;

/**
 * @author Ellison
 * @date 2018/7/25
 * @desc 用一句话描述这个类的作用
 */

public class InputMethodManagerLeakUtils {

    public static void fixInputMethodManagerLeak(Context context) {
        if (context == null) {
            return;
        }
        try {
            // 对 mCurRootView mServedView mNextServedView 进行置空...
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm == null) {
                return;
            }

            Object obj = null;
            Field mCurRootView = imm.getClass().getDeclaredField("mCurRootView");
            Field mServedView = imm.getClass().getDeclaredField("mServedView");
            Field mNextServedView = imm.getClass().getDeclaredField("mNextServedView");

            if (!mCurRootView.isAccessible()) {
                mCurRootView.setAccessible(true);
            }
            obj = mCurRootView.get(imm);
            if (obj != null) {
                // 不为null则置为空
                mCurRootView.set(imm, null);
            }

            if (!mServedView.isAccessible()) {
                mServedView.setAccessible(true);
            }
            obj = mServedView.get(imm);
            if (obj != null) {
                // 不为null则置为空
                mServedView.set(imm, null);
            }

            if (!mNextServedView.isAccessible()) {
                mNextServedView.setAccessible(true);
            }
            obj = mNextServedView.get(imm);
            if (obj != null) {
                // 不为null则置为空
                mNextServedView.set(imm, null);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
