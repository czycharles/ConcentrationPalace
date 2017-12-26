package com.example.charles.concentrationpalace;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

/**
 * Get coin option activity.
 */

public class OptionActivity extends AppCompatActivity {

    int flower_slot1;
    int tree_slot2;
    int stone_slot3;
    int house_slot4;
    int luwei_slot5;

    SharedPreferences data;
    SharedPreferences.Editor editor;

    int my_coin;
    int origin_coin = 200;

    TextView coin_display;
    TextView option_msg;

    Animation animation1;
    TextView process;

    LinearLayout Option_select_page;
    LinearLayout Time_select_page;

    int[] time_num = {10,30,60,120};
    int[] coin_gain = {10,40,100,250};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityCollector.addActivity(this);
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
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
        setContentView(R.layout.activity_option);

        Option_select_page = findViewById(R.id.option_page);
        Time_select_page = findViewById(R.id.time_select_page);

        data = getSharedPreferences("data", MODE_PRIVATE);

        ImageView item_pic = findViewById(R.id.Finish_item);

        data = getSharedPreferences("data", MODE_PRIVATE);
        flower_slot1 = data.getInt("slot1", 0);
        tree_slot2 = data.getInt("slot2", 0);
        stone_slot3 = data.getInt("slot3", 0);
        house_slot4 = data.getInt("slot4", 0);
        luwei_slot5 = data.getInt("slot5",0);

        switch (flower_slot1 + tree_slot2 + stone_slot3 + house_slot4 + luwei_slot5) {
            case 0:case 1:case 2:case 3:case 4:
                item_pic.setImageResource(R.drawable.lotus1_gallery);
                break;
            case 5:case 6:case 7:case 8:case 9:
                item_pic.setImageResource(R.drawable.lotus2_gallery);
                break;
            case 10:case 11:case 12:case 13:case 14:
                item_pic.setImageResource(R.drawable.lotus3_gallery);
                break;
            case 15:
                item_pic.setImageResource(R.drawable.lotus4_gallery);
                break;
            default:
                item_pic.setBackgroundResource(R.drawable.lotus1_gallery);
        }

        animation1 = AnimationUtils.loadAnimation(OptionActivity.this, R.anim.fade_in);

        process = findViewById(R.id.Item_Process);
        process.setText(String.format(getResources().getString(R.string.gallery_process),flower_slot1 + tree_slot2 + stone_slot3 + house_slot4 + luwei_slot5 + 5,20));
        coin_display = findViewById(R.id.coin_bar);
        my_coin = data.getInt("my_coin", origin_coin);
        coin_display.setText(String.format(getResources().getString(R.string.coin_bar), my_coin));
        option_msg = findViewById(R.id.option_msg);

        Button concentration_btn = findViewById(R.id.opt1_button);
        Button time1_btn = findViewById(R.id.time1_button);
        Button time2_btn = findViewById(R.id.time2_button);
        Button time3_btn = findViewById(R.id.time3_button);
        Button time4_btn = findViewById(R.id.time4_button);

        concentration_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Option_select_page.setVisibility(View.GONE);
                Time_select_page.setVisibility(View.VISIBLE);
                option_msg.setText(R.string.time_title);

            }
        });

        time1_btn.setText(String.format(getResources().getString(R.string.time_opt), time_num[0]));
        time2_btn.setText(String.format(getResources().getString(R.string.time_opt), time_num[1]));
        time3_btn.setText(String.format(getResources().getString(R.string.time_opt), time_num[2]));
        time4_btn.setText(String.format(getResources().getString(R.string.time_opt), time_num[3]));

        time1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent intent = new Intent(OptionActivity.this, WaitingActivity.class);
                intent.putExtra("building_slot", 0);
                intent.putExtra("building_time", time_num[0] * 60 * 1000);
                intent.putExtra("building_coin", coin_gain[0]);
                startActivity(intent);
                int version = Build.VERSION.SDK_INT;
                if (version > 5) {
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });

        time2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent intent = new Intent(OptionActivity.this, WaitingActivity.class);
                intent.putExtra("building_slot", 0);
                intent.putExtra("building_time", time_num[1] * 60 * 1000);
                intent.putExtra("building_coin", coin_gain[1]);
                startActivity(intent);
                int version = Build.VERSION.SDK_INT;
                if (version > 5) {
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });

        time3_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent intent = new Intent(OptionActivity.this, WaitingActivity.class);
                intent.putExtra("building_slot", 0);
                intent.putExtra("building_time", time_num[2] * 60 * 1000);
                intent.putExtra("building_coin", coin_gain[2]);
                startActivity(intent);
                int version = Build.VERSION.SDK_INT;
                if (version > 5) {
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });

        time4_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent intent = new Intent(OptionActivity.this, WaitingActivity.class);
                intent.putExtra("building_slot", 0);
                intent.putExtra("building_time", time_num[3] * 60 * 1000);
                intent.putExtra("building_coin", coin_gain[3]);
                startActivity(intent);
                int version = Build.VERSION.SDK_INT;
                if (version > 5) {
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });

        Button share_btn = findViewById(R.id.opt2_button);

        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(OptionActivity.this, MyPalaceActivity.class);
                intent.putExtra("Share_Action",1);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(OptionActivity.this, MyPalaceActivity.class);
        startActivity(intent);
        int version = Build.VERSION.SDK_INT;
        if(version > 5 ){
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //clean.cleanExternalCache(MyPalaceActivity.this);
        ActivityCollector.removeActivity(this);
    }
}
