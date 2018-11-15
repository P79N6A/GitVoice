package com.ellison.library.utils.system;

import android.app.Activity;
import android.os.Handler;

import java.lang.ref.WeakReference;

/**
 * @author Ellison
 * @date 2018/6/14
 * @desc 弱引用
 */

public class WeakActivityHandler extends Handler {

    public WeakReference mWeakReference;

    public WeakActivityHandler(Activity weakReference) {
        mWeakReference = new WeakReference<>(weakReference);
    }
}
