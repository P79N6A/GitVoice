package com.ellison.library.utils.system;

import android.os.Handler;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;


/**
 * @author Ellison
 * @date 2018/6/14
 * @desc 弱引用
 */

public class WeakFragmentHandler extends Handler {

    public WeakReference mWeakReference;

    public WeakFragmentHandler(Fragment weakReference) {
        mWeakReference = new WeakReference<>(weakReference);
    }
}
