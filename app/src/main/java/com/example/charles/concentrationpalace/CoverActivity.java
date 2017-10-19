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
import android.widget.FrameLayout;
import android.widget.TextView;


/**
 * Created by charles on 10/19/2017.
 */

public class CoverActivity extends AppCompatActivity {

    FrameLayout cover_page;
    TextView prologue_1;
    TextView prologue_2;


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
        setContentView(R.layout.activity_cover);

        cover_page = (FrameLayout) findViewById(android.R.id.content);
        cover_page.setClickable(true);

        prologue_1 = (TextView)findViewById(R.id.prologue_1);
        prologue_2 = (TextView)findViewById(R.id.prologue_2);

        CountDownTimer mc1;
        CountDownTimer mc2;

        mc1 = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                prologue_1.setVisibility(View.VISIBLE);
            }
        };
        mc1.start();
        mc2 = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                prologue_2.setVisibility(View.VISIBLE);
            }
        };
        mc2.start();
        cover_page.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int version = Integer.valueOf(Build.VERSION.SDK_INT);
                Intent intent = new Intent(CoverActivity.this, MyPalaceActivity.class);
                startActivity(intent);
                if(version > 5 ){
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });
    }

}
