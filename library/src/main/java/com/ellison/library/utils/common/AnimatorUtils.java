package com.ellison.library.utils.common;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

public class AnimatorUtils {
    public static void startMoveXAnimaLeftToR(View view) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "translationX", new float[]{-800.0f, 0.0f});
        ofFloat.setDuration(500);
        ofFloat.start();
    }

    public static void startMoveXAnimaRightToL(View view) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "translationX", new float[]{800.0f, 0.0f});
        ofFloat.setDuration(500);
        ofFloat.start();
    }

    /**
     * 移动距离
     *
     * @param view
     * @param distance
     */
    public static void startMoveYAnimaBottomToUp(View view, int distance) {
        startMoveYAnimaBottomToUp(view, distance, null);
    }

    /**
     * 带监听移动距离
     *
     * @param view
     * @param distance
     * @param listener
     */
    public static void startMoveYAnimaBottomToUp(View view, int distance, Animator.AnimatorListener listener) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "translationY", new float[]{0.0f, (float) (-distance)});
        if (listener != null) {
            ofFloat.addListener(listener);
        }
        ofFloat.setDuration(180);
        ofFloat.start();
    }

    /**
     * 上移
     *
     * @param view
     */
    public static void startMoveYAnimaDownToUp(View view) {
        startMoveYAnimaDownToUp(view, null);
    }

    /**
     * 带监听上移
     *
     * @param view
     * @param listener
     */
    public static void startMoveYAnimaDownToUp(View view, Animator.AnimatorListener listener) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "translationY", new float[]{1200.0f, 0.0f});
        ofFloat.setDuration(500);
        if (listener != null) {
            ofFloat.addListener(listener);
        }
        ofFloat.start();
    }


    public static void startMoveYAnimaUpToBottom(View view, int i) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "translationY", new float[]{(float) (-i), 0.0f});
        ofFloat.setDuration(180);
        ofFloat.start();
    }

    public static void startMoveYAnimaUpToD(View view) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "translationY", new float[]{-1200.0f, 0.0f});
        ofFloat.setDuration(500);
        ofFloat.start();
    }

    public static void startMoveYAnimaUpToD_m(View view, int i) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "translationY", new float[]{(float) i, 0.0f});
        ofFloat.setDuration(200);
        ofFloat.start();
    }

    public static void startRotateAndScaleAnima(View view) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "rotation", new float[]{0.0f, 720.0f});
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, "alpha", new float[]{0.5f, 1.0f});
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(view, "scaleX", new float[]{0.3f, 1.0f});
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{ofFloat, ofFloat2, ofFloat3});
        animatorSet.setDuration(1500);
        animatorSet.start();
    }

    /**
     * 旋转View
     *
     * @param view
     */
    public static void startRotateAnima(View view) {
        startRotateAnima(view, null);
    }

    /**
     * 旋转View
     *
     * @param view
     * @param listener
     */
    public static void startRotateAnima(View view, Animator.AnimatorListener listener) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "rotation", new float[]{0.0f, 360.0f});
        ofFloat.setDuration(1000);
        if (listener != null) {
            ofFloat.addListener(listener);
        }
        ofFloat.start();
    }

    /**
     * 在Y 轴进行旋转
     *
     * @param view
     */
    public static void startRotateYAnima(View view) {
        startRotateYAnima(view);
    }

    /**
     * 在Y 轴进行旋转
     * @param view
     * @param listener
     */
    public static void startRotateYAnima(View view, Animator.AnimatorListener listener) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "rotationY", new float[]{0.0f, 360.0f});
        ofFloat.setDuration(600);
        ofFloat.setInterpolator(new AccelerateInterpolator());
        if (listener != null) {
            ofFloat.addListener(listener);
        }
        ofFloat.start();
    }


}
