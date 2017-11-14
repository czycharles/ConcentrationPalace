package com.example.charles.concentrationpalace;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;


/**
 * Story Cover
 */

public class CoverActivity extends AppCompatActivity {

    FrameLayout cover_page;
    TextView prologue_1;
    TextView prologue_2;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        view=View.inflate(this, R.layout.activity_cover, null);
        setContentView(view);

        cover_page = findViewById(android.R.id.content);
        cover_page.setClickable(true);

        prologue_1 = findViewById(R.id.prologue_1);
        prologue_2 = findViewById(R.id.prologue_2);

        CountDownTimer mc1;
        CountDownTimer mc2;

        mc1 = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                //使用AnimationUtils类的静态方法loadAnimation()来加载XML中的动画XML文件
                Animation animation1= AnimationUtils.loadAnimation(CoverActivity.this, R.anim.fade_in);
                animation1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation arg0) {}   //在动画开始时使用

                    @Override
                    public void onAnimationRepeat(Animation arg0) {}  //在动画重复时使用

                    @Override
                    public void onAnimationEnd(Animation arg0) {
                        prologue_1.setVisibility(View.VISIBLE);
                    }
                });
                prologue_1.startAnimation(animation1);
            }
        };
        mc1.start();
        mc2 = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                //使用AnimationUtils类的静态方法loadAnimation()来加载XML中的动画XML文件
                Animation animation2= AnimationUtils.loadAnimation(CoverActivity.this, R.anim.fade_in);
                animation2.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation arg0) {}   //在动画开始时使用

                    @Override
                    public void onAnimationRepeat(Animation arg0) {}  //在动画重复时使用

                    @Override
                    public void onAnimationEnd(Animation arg0) {
                        prologue_2.setVisibility(View.VISIBLE);
                        cover_page.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v){
                                int version = Build.VERSION.SDK_INT;
                                Intent intent = new Intent(CoverActivity.this, MyPalaceActivity.class);
                                startActivity(intent);
                                if(version > 5 ){
                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                }
                                finish();
                            }
                        });
                    }
                });
                prologue_2.startAnimation(animation2);
            }
        };
        mc2.start();
    }

}
