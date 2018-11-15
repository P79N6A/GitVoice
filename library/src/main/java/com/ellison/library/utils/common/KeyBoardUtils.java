package com.ellison.library.utils.common;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.ellison.library.logger.L;

import java.lang.reflect.Method;

/**
 * Created by SY on 2017/5/15.
 */

public class KeyBoardUtils {
    private KeyBoardUtils() {
    }

    /**
     * 关闭activity中打开的键盘
     *
     * @param activity
     */
    public static void  closeKeyboard(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
    }

    /**
     * 关闭dialog中打开的键盘
     *
     * @param dialog
     */
    public static void closeKeyboard(Dialog dialog) {
        View view = dialog.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) dialog.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
    }

    /**
     * 关闭
     *
     * @param v
     */
    public static void HideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);

        }
    }

    /**
     * 打开键盘
     *
     * @param context
     * @param editText
     */
    public static void openKeyboard(final Context context, final EditText editText) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                editText.requestFocus();
                editText.setSelection(editText.getText().toString().length());
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
            }
        }, 300);
    }

    /**
     * 拷贝文档到黏贴板
     *
     * @param text
     */
    public static void clip(Context context, String text) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.setText(text);
        } else {
            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.setPrimaryClip(ClipData.newPlainText("content", text));
        }
    }

    /**
     * 切换键盘的显示与隐藏
     *
     * @param activity
     */
    public static void toggleKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 处理点击非 EditText 区域时，自动关闭键盘
     *
     * @param isAutoCloseKeyboard 是否自动关闭键盘
     * @param currentFocusView    当前获取焦点的控件
     * @param motionEvent         触摸事件
     * @param dialogOrActivity    Dialog 或 Activity
     */
    public static void handleAutoCloseKeyboard(boolean isAutoCloseKeyboard, View currentFocusView, MotionEvent motionEvent, Object dialogOrActivity) {
        if (isAutoCloseKeyboard && motionEvent.getAction() == MotionEvent.ACTION_DOWN && currentFocusView != null && (currentFocusView instanceof EditText) && dialogOrActivity != null) {
            int[] leftTop = {0, 0};
            currentFocusView.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + currentFocusView.getHeight();
            int right = left + currentFocusView.getWidth();
            if (!(motionEvent.getX() > left && motionEvent.getX() < right && motionEvent.getY() > top && motionEvent.getY() < bottom)) {
                if (dialogOrActivity instanceof Dialog) {
                    closeKeyboard((Dialog) dialogOrActivity);
                } else if (dialogOrActivity instanceof Activity) {
                    closeKeyboard((Activity) dialogOrActivity);
                }
            }
        }
    }

    public static void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            view.requestFocus();
            imm.showSoftInput(view, 0);
        }
    }

    public static void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void toggleSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(0, 0);
        }
    }

    /**
     * 判断是否有虚拟底部按钮
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
            L.e(e.toString());
        }
        return hasNavigationBar;
    }

    /**
     * 获取底部虚拟按键高度
     *
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        int navigationBarHeight = 0;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("navigation_bar_height", "dimen", "android");
        if (id > 0 && checkDeviceHasNavigationBar(context)) {
            navigationBarHeight = rs.getDimensionPixelSize(id);
        }
        return navigationBarHeight;
    }

    /**
     * 保持登录按钮始终不会被覆盖
     *
     * @param root
     * @param subView
     */
    public static void keepLoginBtnNotOver(View root, View subView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect rect = new Rect();
            //获取rootView的可视区域
            root.getWindowVisibleDisplayFrame(rect);
            //获取rootView的不可视区域高度
            int invisibleHeight = root.getRootView().getHeight() - rect.bottom;
            if (invisibleHeight > 150) {
                //键盘显示
                int[] location = new int[2];
                //获取bottomView的坐标
                subView.getLocationInWindow(location);
                //算出需要滚动的高度
                int scrollHeight = (location[1] + subView.getHeight()) - rect.bottom;
                if (scrollHeight != 0) {
                    //防止界面元素改变调用监听，使界面上下跳动，如验证码倒计时
                    root.scrollTo(0, scrollHeight + 10);
                }
            } else {
                root.scrollTo(0, 0);
            }
        });
    }
}