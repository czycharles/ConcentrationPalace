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
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Story Ending
 */

public class EndActivity extends AppCompatActivity {

    FrameLayout cover_page;
    TextView prologue_1;
    TextView prologue_2;
    TextView prologue_3;
    TextView prologue_4;
    TextView prologue_5;
    TextView prologue_6;
    TextView prologue_7;
    ImageView final_pic;

    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityCollector.addActivity(this);
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
        view=View.inflate(this, R.layout.activity_ending, null);
        setContentView(view);

        cover_page = findViewById(android.R.id.content);
        cover_page.setClickable(true);

        prologue_1 = findViewById(R.id.prologue_1);
        prologue_2 = findViewById(R.id.prologue_2);
        prologue_3 = findViewById(R.id.prologue_3);
        prologue_4 = findViewById(R.id.prologue_4);
        prologue_5 = findViewById(R.id.prologue_5);
        prologue_6 = findViewById(R.id.prologue_6);
        prologue_7 = findViewById(R.id.prologue_7);
        prologue_1.setText(R.string.end_1);
        prologue_2.setText(R.string.end_2);
        prologue_3.setText(R.string.end_3);
        prologue_4.setText(R.string.end_4);
        prologue_5.setText(R.string.end_5);
        prologue_6.setText(R.string.end_6);
        prologue_7.setText(R.string.end_7);
        final_pic = findViewById(R.id.Final_Pic);

        CountDownTimer mc1;
        CountDownTimer mc2;
        CountDownTimer mc3;
        CountDownTimer mc4;
        CountDownTimer mc5;
        CountDownTimer mc6;
        CountDownTimer mc7;

        mc1 = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                //使用AnimationUtils类的静态方法loadAnimation()来加载XML中的动画XML文件
                Animation animation1= AnimationUtils.loadAnimation(EndActivity.this, R.anim.fade_in);
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

        mc2 = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                //使用AnimationUtils类的静态方法loadAnimation()来加载XML中的动画XML文件
                Animation animation2= AnimationUtils.loadAnimation(EndActivity.this, R.anim.fade_in);
                animation2.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation arg0) {}   //在动画开始时使用

                    @Override
                    public void onAnimationRepeat(Animation arg0) {}  //在动画重复时使用

                    @Override
                    public void onAnimationEnd(Animation arg0) {
                        prologue_2.setVisibility(View.VISIBLE);
                    }
                });
                prologue_2.startAnimation(animation2);
            }
        };
        mc2.start();

        mc3 = new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                //使用AnimationUtils类的静态方法loadAnimation()来加载XML中的动画XML文件
                Animation animation3= AnimationUtils.loadAnimation(EndActivity.this, R.anim.fade_in);
                animation3.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation arg0) {}   //在动画开始时使用

                    @Override
                    public void onAnimationRepeat(Animation arg0) {}  //在动画重复时使用

                    @Override
                    public void onAnimationEnd(Animation arg0) {
                        prologue_3.setVisibility(View.VISIBLE);
                    }
                });
                prologue_3.startAnimation(animation3);
            }
        };
        mc3.start();

        mc4 = new CountDownTimer(8000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                //使用AnimationUtils类的静态方法loadAnimation()来加载XML中的动画XML文件
                Animation animation4= AnimationUtils.loadAnimation(EndActivity.this, R.anim.fade_in);
                animation4.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation arg0) {}   //在动画开始时使用

                    @Override
                    public void onAnimationRepeat(Animation arg0) {}  //在动画重复时使用

                    @Override
                    public void onAnimationEnd(Animation arg0) {
                        prologue_4.setVisibility(View.VISIBLE);
                    }
                });
                prologue_4.startAnimation(animation4);
            }
        };
        mc4.start();

        mc5 = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                //使用AnimationUtils类的静态方法loadAnimation()来加载XML中的动画XML文件
                Animation animation5= AnimationUtils.loadAnimation(EndActivity.this, R.anim.fade_in);
                animation5.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation arg0) {}   //在动画开始时使用

                    @Override
                    public void onAnimationRepeat(Animation arg0) {}  //在动画重复时使用

                    @Override
                    public void onAnimationEnd(Animation arg0) {
                        prologue_5.setVisibility(View.VISIBLE);
                    }
                });
                prologue_5.startAnimation(animation5);
            }
        };
        mc5.start();

        mc6 = new CountDownTimer(12000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                //使用AnimationUtils类的静态方法loadAnimation()来加载XML中的动画XML文件
                Animation animation6= AnimationUtils.loadAnimation(EndActivity.this, R.anim.fade_in);
                animation6.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation arg0) {}   //在动画开始时使用

                    @Override
                    public void onAnimationRepeat(Animation arg0) {}  //在动画重复时使用

                    @Override
                    public void onAnimationEnd(Animation arg0) {
                        prologue_6.setVisibility(View.VISIBLE);
                    }
                });
                prologue_6.startAnimation(animation6);
            }
        };
        mc6.start();


        mc7 = new CountDownTimer(14000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                //使用AnimationUtils类的静态方法loadAnimation()来加载XML中的动画XML文件
                Animation animation7= AnimationUtils.loadAnimation(EndActivity.this, R.anim.fade_in);
                animation7.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation arg0) {}   //在动画开始时使用

                    @Override
                    public void onAnimationRepeat(Animation arg0) {}  //在动画重复时使用

                    @Override
                    public void onAnimationEnd(Animation arg0) {
                        prologue_7.setVisibility(View.VISIBLE);
                        cover_page.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final_pic.setVisibility(View.VISIBLE);
                                Animation animation8= AnimationUtils.loadAnimation(EndActivity.this, R.anim.fade_in);
                                final_pic.startAnimation(animation8);
                            }
                        });
                    }
                });
                prologue_7.startAnimation(animation7);
            }
        };
        mc7.start();


        final_pic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int version = Build.VERSION.SDK_INT;
                Intent intent = new Intent(EndActivity.this, CP_MainActivity.class);
                startActivity(intent);
                if(version > 5 ){
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
