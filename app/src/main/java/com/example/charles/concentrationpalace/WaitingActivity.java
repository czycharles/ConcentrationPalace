package com.example.charles.concentrationpalace;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Waiting for countdown activity.
 */

public class WaitingActivity extends AppCompatActivity {

    MediaPlayer mpMediaPlayer;

    TextView countdown;

    private MyCount mc;

    int building_slot = 0;

    boolean slot1_crash = false;
    boolean slot2_crash = false;
    boolean slot3_crash = false;
    boolean slot4_crash = false;
    boolean slot5_crash = false;

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

    Button crush_button;

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
        setContentView(R.layout.activity_waiting);

        data = getSharedPreferences("data", MODE_PRIVATE);
        editor = getSharedPreferences("data", MODE_PRIVATE).edit();

        Intent intent = getIntent();
        building_slot = intent.getIntExtra("building_slot",2);
        int building_time = intent.getIntExtra("building_time",0);
        mc = new MyCount(building_time, 1000);
        mc.start();
        mpMediaPlayer = MediaPlayer.create(this,R.raw.bgm_maoudamashii_piano41);
        try {
            mpMediaPlayer.setLooping(true);
            mpMediaPlayer.start();
        } catch (IllegalArgumentException|IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        ImageView item_pic;
        ImageView pre_item_pic;
        TextView item_desc;

        data = getSharedPreferences("data", MODE_PRIVATE);
        flower_slot1 = data.getInt("slot1", 0);
        tree_slot2 = data.getInt("slot2", 0);
        stone_slot3 = data.getInt("slot3", 0);
        house_slot4 = data.getInt("slot4", 0);
        luwei_slot5 = data.getInt("slot5",0);

        item_pic = findViewById(R.id.Item_pic);
        pre_item_pic = findViewById(R.id.Item_pic);
        item_desc = findViewById(R.id.Item_desc);
        countdown = findViewById(R.id.Countdown_hint);
        countdown.setText(String.format(getResources().getString(R.string.countdown_timer),0,0,0,0));
        coin_display = findViewById(R.id.coin_bar);
        my_coin = data.getInt("my_coin", origin_coin);
        coin_display.setText(String.format(getResources().getString(R.string.coin_bar), my_coin));

        switch(building_slot){
            case 1:
                switch(flower_slot1+1) {
                    case 0:
                        item_pic.setImageResource(R.drawable.item_unknown);
                        pre_item_pic.setImageResource(R.drawable.item_unknown);
                        break;
                    case 1:
                        item_pic.setImageResource(R.drawable.flower1_gallery);
                        pre_item_pic.setImageResource(R.drawable.flower_crush_gallery);
                        item_desc.setText(R.string.flower1_desc);
                        break;
                    case 2:
                        item_pic.setImageResource(R.drawable.flower2_gallery);
                        pre_item_pic.setImageResource(R.drawable.flower1_gallery);
                        item_desc.setText(R.string.flower2_desc);
                        break;
                    case 3:
                        item_pic.setImageResource(R.drawable.flower3_gallery);
                        pre_item_pic.setImageResource(R.drawable.flower2_gallery);
                        item_desc.setText(R.string.flower3_desc);
                        break;
                }

                break;
            case 2:
                switch(tree_slot2+1) {
                    case 0:
                        item_pic.setImageResource(R.drawable.item_unknown);
                        pre_item_pic.setImageResource(R.drawable.item_unknown);
                        break;
                    case 1:
                        item_pic.setImageResource(R.drawable.tree1_gallery);
                        pre_item_pic.setImageResource(R.drawable.tree_crush_gallery);
                        item_desc.setText(R.string.tree1_desc);
                        break;
                    case 2:
                        item_pic.setImageResource(R.drawable.tree2_gallery);
                        pre_item_pic.setImageResource(R.drawable.tree1_gallery);
                        item_desc.setText(R.string.tree2_desc);
                        break;
                    case 3:
                        item_pic.setImageResource(R.drawable.tree3_gallery);
                        pre_item_pic.setImageResource(R.drawable.tree2_gallery);
                        item_desc.setText(R.string.tree3_desc);
                        break;
                }

                break;
            case 3:
                switch(stone_slot3+1) {
                    case 0:
                        item_pic.setImageResource(R.drawable.item_unknown);
                        pre_item_pic.setImageResource(R.drawable.item_unknown);
                        break;
                    case 1:
                        item_pic.setImageResource(R.drawable.stone1_gallery);
                        pre_item_pic.setImageResource(R.drawable.stone_crush_gallery);
                        item_desc.setText(R.string.stone1_desc);
                        break;
                    case 2:
                        item_pic.setImageResource(R.drawable.stone2_gallery);
                        pre_item_pic.setImageResource(R.drawable.stone1_gallery);
                        item_desc.setText(R.string.stone2_desc);
                        break;
                    case 3:
                        item_pic.setImageResource(R.drawable.stone3_gallery);
                        pre_item_pic.setImageResource(R.drawable.stone2_gallery);
                        item_desc.setText(R.string.stone3_desc);
                        break;
                }
                break;
            case 4:
                switch(house_slot4+1) {
                    case 0:
                        item_pic.setImageResource(R.drawable.item_unknown);
                        pre_item_pic.setImageResource(R.drawable.item_unknown);
                        break;
                    case 1:
                        item_pic.setImageResource(R.drawable.house1_gallery);
                        pre_item_pic.setImageResource(R.drawable.house_crush_gallery);
                        item_desc.setText(R.string.house1_desc);
                        break;
                    case 2:
                        item_pic.setImageResource(R.drawable.house2_gallery);
                        pre_item_pic.setImageResource(R.drawable.house1_gallery);
                        item_desc.setText(R.string.house2_desc);
                        break;
                    case 3:
                        item_pic.setImageResource(R.drawable.house3_gallery);
                        pre_item_pic.setImageResource(R.drawable.house2_gallery);
                        item_desc.setText(R.string.house3_desc);
                        break;
                }
                break;
            case 5:
                switch(luwei_slot5+1) {
                    case 0:
                        item_pic.setImageResource(R.drawable.item_unknown);
                        pre_item_pic.setImageResource(R.drawable.item_unknown);
                        break;
                    case 1:
                        item_pic.setImageResource(R.drawable.luwei1_gallery);
                        pre_item_pic.setImageResource(R.drawable.luwei_crush_gallery);
                        item_desc.setText(R.string.luwei1_desc);
                        break;
                    case 2:
                        item_pic.setImageResource(R.drawable.luwei2_gallery);
                        pre_item_pic.setImageResource(R.drawable.luwei1_gallery);
                        item_desc.setText(R.string.luwei2_desc);
                        break;
                    case 3:
                        item_pic.setImageResource(R.drawable.luwei3_gallery);
                        pre_item_pic.setImageResource(R.drawable.luwei2_gallery);
                        item_desc.setText(R.string.luwei3_desc);
                        break;
                }
                break;
        }

        Button AD_button = findViewById(R.id.AD_button);
        coin_display = findViewById(R.id.coin_bar);

        AD_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                my_coin=my_coin+200;
                coin_display.setText(String.format(getResources().getString(R.string.coin_bar), my_coin));
                editor.putInt("my_coin", my_coin);
                editor.apply();
                Toast.makeText(WaitingActivity.this,"假设你看完了广告，金币+200",Toast.LENGTH_LONG).show();
            }
        });

        crush_button = findViewById(R.id.Crush_button);

        crush_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if(building_slot > 0) {
                    AlertDialog.Builder failAlert = new AlertDialog.Builder(WaitingActivity.this);
                    failAlert.setTitle("坚持就是胜利！");
                    failAlert.setMessage("如果现在退出，正在建造的部分就会坍塌。");
                    failAlert.setCancelable(false);
                    failAlert.setPositiveButton("退出", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface failAlert, int i) {
                            Toast.makeText(WaitingActivity.this, "抱歉，你使用了手机，位置" + building_slot + "已经坍塌。", Toast.LENGTH_LONG).show();
                            mc.cancel();
                            switch (building_slot) {
                                case 1:
                                    slot1_crash = true;
                                    editor.putBoolean("slot1_crash", true);
                                    editor.apply();
                                    break;
                                case 2:
                                    slot2_crash = true;
                                    editor.putBoolean("slot2_crash", true);
                                    editor.apply();
                                    break;
                                case 3:
                                    slot3_crash = true;
                                    editor.putBoolean("slot3_crash", true);
                                    editor.apply();
                                    break;
                                case 4:
                                    slot4_crash = true;
                                    editor.putBoolean("slot4_crash", true);
                                    editor.apply();
                                    break;
                                case 5:
                                    slot5_crash = true;
                                    editor.putBoolean("slot5_crash", true);
                                    editor.apply();
                                    break;
                            }
                            building_slot = 0;
                            if(mpMediaPlayer!=null) {
                                try{
                                    mpMediaPlayer.stop();
                                    Log.d("Media","stop success");
                                }catch(IllegalStateException e) {
                                    mpMediaPlayer.release();
                                    Log.d("Media", "release success");
                                    mpMediaPlayer = null;
                                    Log.d("Media", "null success");
                                }
                            }
                            Intent intent = new Intent(WaitingActivity.this, MyPalaceActivity.class);
                            startActivity(intent);
                            int version = Build.VERSION.SDK_INT;
                            if(version > 5 ){
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            }
                            finish();
                        }
                    });
                    failAlert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface failAlert, int i) {
                            failAlert.cancel();
                        }
                    });
                    failAlert.show();
                }
                else {
                    if(mpMediaPlayer!=null) {
                        try{
                            mpMediaPlayer.stop();
                            Log.d("Media","stop success");
                        }catch(IllegalStateException e) {
                            mpMediaPlayer.release();
                            Log.d("Media", "release success");
                            mpMediaPlayer = null;
                            Log.d("Media", "null success");
                        }
                    }
                    Intent intent = new Intent(WaitingActivity.this, MyPalaceActivity.class);
                    startActivity(intent);
                    int version = Build.VERSION.SDK_INT;
                    if(version > 5 ){
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                    finish();
                }
            }
        });
    }

    @Override
    public void onTrimMemory(int level) {
        if ((level == TRIM_MEMORY_UI_HIDDEN)&&(building_slot > 0)) {
            countdown.setText(R.string.fail_hint);
            mc.cancel();
            Toast.makeText(WaitingActivity.this, "抱歉，你使用了手机，位置" + building_slot + "已经坍塌。", Toast.LENGTH_LONG).show();
            switch (building_slot) {
                case 1:
                    slot1_crash = true;
                    editor.putBoolean("slot1_crash", true);
                    editor.apply();
                    break;
                case 2:
                    slot2_crash = true;
                    editor.putBoolean("slot2_crash", true);
                    editor.apply();
                    break;
                case 3:
                    slot3_crash = true;
                    editor.putBoolean("slot3_crash", true);
                    editor.apply();
                    break;
                case 4:
                    slot4_crash = true;
                    editor.putBoolean("slot4_crash", true);
                    editor.apply();
                    break;
                case 5:
                    slot5_crash = true;
                    editor.putBoolean("slot5_crash", true);
                    editor.apply();
                    break;
            }
            building_slot = 0;
            if(mpMediaPlayer!=null) {
                try{
                    mpMediaPlayer.stop();
                    Log.d("Media","stop success");
                }catch(IllegalStateException e) {
                    mpMediaPlayer.release();
                    Log.d("Media", "release success");
                    mpMediaPlayer = null;
                    Log.d("Media", "null success");
                }
            }
            finish();
        }
        super.onTrimMemory(level);
    }

    @Override
    protected void onStop() {
        if(mpMediaPlayer!=null) {
            try{
                mpMediaPlayer.stop();
                Log.d("Media","stop success");
            }catch(IllegalStateException e) {
                mpMediaPlayer.release();
                Log.d("Media", "release success");
                mpMediaPlayer = null;
                Log.d("Media", "null success");
            }
        }
        super.onStop();
    }

    @Override
    public void onBackPressed(){
        if(building_slot > 0) {
            AlertDialog.Builder failAlert = new AlertDialog.Builder(WaitingActivity.this);
            failAlert.setTitle("坚持就是胜利！");
            failAlert.setMessage("如果现在退出，正在建造的部分就会坍塌。");
            failAlert.setCancelable(false);
            failAlert.setPositiveButton("退出", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface failAlert, int i) {
                    Toast.makeText(WaitingActivity.this, "抱歉，你使用了手机，位置" + building_slot + "已经坍塌。", Toast.LENGTH_LONG).show();
                    mc.cancel();
                    switch (building_slot) {
                        case 1:
                            slot1_crash = true;
                            editor.putBoolean("slot1_crash", true);
                            editor.apply();
                            break;
                        case 2:
                            slot2_crash = true;
                            editor.putBoolean("slot2_crash", true);
                            editor.apply();
                            break;
                        case 3:
                            slot3_crash = true;
                            editor.putBoolean("slot3_crash", true);
                            editor.apply();
                            break;
                        case 4:
                            slot4_crash = true;
                            editor.putBoolean("slot4_crash", true);
                            editor.apply();
                            break;
                        case 5:
                            slot5_crash = true;
                            editor.putBoolean("slot5_crash", true);
                            editor.apply();
                            break;
                    }
                    if(mpMediaPlayer!=null) {
                        try{
                            mpMediaPlayer.stop();
                            Log.d("Media","stop success");
                        }catch(IllegalStateException e) {
                            mpMediaPlayer.release();
                            Log.d("Media", "release success");
                            mpMediaPlayer = null;
                            Log.d("Media", "null success");
                        }
                    }
                    Intent intent = new Intent(WaitingActivity.this, MyPalaceActivity.class);
                    startActivity(intent);
                    int version = Build.VERSION.SDK_INT;
                    if(version > 5 ){
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                    finish();
                }
            });
            failAlert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface failAlert, int i) {
                    failAlert.cancel();
                }
            });
            failAlert.show();
        }
        else {
            if(mpMediaPlayer!=null) {
                try{
                    mpMediaPlayer.stop();
                    Log.d("Media","stop success");
                }catch(IllegalStateException e) {
                    mpMediaPlayer.release();
                    Log.d("Media", "release success");
                    mpMediaPlayer = null;
                    Log.d("Media", "null success");
                }
            }
            Intent intent = new Intent(WaitingActivity.this, MyPalaceActivity.class);
            startActivity(intent);
            int version = Build.VERSION.SDK_INT;
            if(version > 5 ){
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        if(mpMediaPlayer!=null) {
            try{
                mpMediaPlayer.stop();
                Log.d("Media","stop success");
            }catch(IllegalStateException e) {
                mpMediaPlayer.release();
                Log.d("Media", "release success");
                mpMediaPlayer = null;
                Log.d("Media", "null success");
            }
        }
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }


    private class MyCount extends CountDownTimer {

        long totalTime = 0;

        private MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            totalTime = millisInFuture;
        }

        @Override
        public void onFinish() {

            switch(building_slot){
                case 1:
                    flower_slot1++;
                    editor.putInt("slot1", flower_slot1);
                    editor.apply();
                    break;
                case 2:
                    tree_slot2++;
                    editor.putInt("slot2", tree_slot2);
                    editor.apply();
                    break;
                case 3:
                    stone_slot3++;
                    editor.putInt("slot3", stone_slot3);
                    editor.apply();
                    break;
                case 4:
                    house_slot4++;
                    editor.putInt("slot4", house_slot4);
                    editor.apply();
                    break;
                case 5:
                    luwei_slot5++;
                    editor.putInt("slot5", luwei_slot5);
                    editor.apply();
                    break;
            }

            mc.cancel();
            crush_button.setText(R.string.OK_button);
            countdown.setText(R.string.finish_hint);
            building_slot = 0;
        }
        @Override
        public void onTick(long millisUntilFinished) {

            countdown.setText(String.format(getResources().getString(R.string.countdown_timer),millisUntilFinished / 1000 / 60 /10 , millisUntilFinished / 1000 / 60 %10 ,millisUntilFinished / 1000 / 10 , millisUntilFinished / 1000 %10 ));
        }

    }
}
