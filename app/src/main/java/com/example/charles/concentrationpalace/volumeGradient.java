package com.example.charles.concentrationpalace;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.view.animation.LinearInterpolator;

/**
 * Music fade in and out.
 */

 class volumeGradient {
    interface DoneCallBack {
        void onComplete();
    }

     static void vGradient(final MediaPlayer mediaPlayer, final float from, final float to) {
        ValueAnimator animator = ValueAnimator.ofFloat(from, to);
        animator.setDuration(500);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float volume = (float) animation.getAnimatedValue();
            try

            {
                //此时可能 mediaPlayer 状态发生了改变,所以用try catch包裹,一旦发生错误,立马取消
                mediaPlayer.setVolume(volume, volume);
            } catch (
                    Exception e)

            {
                animation.cancel();
            }
        }
        });
        animator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mediaPlayer.setVolume(from, from);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                try {
                    mediaPlayer.setVolume(from, from);
                } catch (Exception e) {
                    //忽略
                }
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }
}
