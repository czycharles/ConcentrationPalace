package com.example.charles.concentrationpalace;


import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.os.CountDownTimer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MyPalaceActivity extends AppCompatActivity implements OnClickListener {

    private MyCount mc;
    private TextView slot1_show;
    private TextView tv;

    private FindSelectedItem flowerImage;
    private FindSelectedItem treeImage;
    private FindSelectedItem stoneImage;
    private FindSelectedItem houseImage;

    int flower_slot1 = 1;
    int tree_slot2 = 1;
    int stone_slot3 = 1;
    int house_slot4 = 1;

    int slot1_build_time_1 = 15000;
    int slot1_build_time_2 = 25000;
    int slot1_build_time_3 = 35000;

    int slot2_build_time_1 = 15000;
    int slot2_build_time_2 = 25000;
    int slot2_build_time_3 = 35000;

    int slot3_build_time_1 = 10000;
    int slot3_build_time_2 = 15000;
    int slot3_build_time_3 = 20000;

    int slot4_build_time_1 = 5000;
    int slot4_build_time_2 = 10000;
    int slot4_build_time_3 = 15000;

    int next_building_time = 84000;

    int building_slot = 0;

    boolean slot1_crash = false;
    boolean slot2_crash = false;
    boolean slot3_crash = false;
    boolean slot4_crash = false;

    SharedPreferences data;
    SharedPreferences.Editor editor;
    DataCleanManager clean = new DataCleanManager();

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
        setContentView(R.layout.activity_my_palace);

        flowerImage = (FindSelectedItem)findViewById(R.id.flower1);
        treeImage = (FindSelectedItem)findViewById(R.id.tree1);
        stoneImage = (FindSelectedItem)findViewById(R.id.stone1);
        houseImage = (FindSelectedItem)findViewById(R.id.house1);

        findViewById(R.id.flower1).setOnClickListener(MyPalaceActivity.this);
        findViewById(R.id.tree1).setOnClickListener(MyPalaceActivity.this);
        findViewById(R.id.stone1).setOnClickListener(MyPalaceActivity.this);
        findViewById(R.id.house1).setOnClickListener(MyPalaceActivity.this);


//        ActionBar actionbar = getSupportActionBar();
//        if(actionbar!=null)
//            actionbar.hide();

        data = getSharedPreferences("data", MODE_PRIVATE);
        editor = getSharedPreferences("data", MODE_PRIVATE).edit();

        flower_slot1 = data.getInt("slot1", 1);
        switch(flower_slot1) {
            case 1:
                flowerImage.setBackgroundResource(R.drawable.flower1);
                break;
            case 2:
                flowerImage.setBackgroundResource(R.drawable.flower2);
                break;
            case 3:
                flowerImage.setBackgroundResource(R.drawable.flower3);
                break;
            default:
                flowerImage.setBackgroundResource(R.drawable.flower3);
        }

        tree_slot2 = data.getInt("slot2", 1);
        switch(tree_slot2) {
            case 1:
                treeImage.setBackgroundResource(R.drawable.tree1);
                break;
            case 2:
                treeImage.setBackgroundResource(R.drawable.tree2);
                break;
            case 3:
                treeImage.setBackgroundResource(R.drawable.tree3);
                break;
            default:
                treeImage.setBackgroundResource(R.drawable.tree3);
        }
        stone_slot3 = data.getInt("slot3", 1);
        switch(stone_slot3) {
            case 1:
                stoneImage.setBackgroundResource(R.drawable.stone1);
                break;
            case 2:
                stoneImage.setBackgroundResource(R.drawable.stone2);
                break;
            case 3:
                stoneImage.setBackgroundResource(R.drawable.stone3);
                break;
            default:
                stoneImage.setBackgroundResource(R.drawable.stone3);
        }
        house_slot4 = data.getInt("slot4", 1);
        switch(house_slot4) {
            case 1:
                houseImage.setBackgroundResource(R.drawable.house1);
                break;
            case 2:
                houseImage.setBackgroundResource(R.drawable.house2);
                break;
            case 3:
                houseImage.setBackgroundResource(R.drawable.house3);
                break;
            default:
                houseImage.setBackgroundResource(R.drawable.house3);
        }

        slot1_crash = data.getBoolean("slot1_crash", false);
        slot2_crash = data.getBoolean("slot2_crash", false);
        slot3_crash = data.getBoolean("slot3_crash", false);
        slot4_crash = data.getBoolean("slot4_crash", false);

        slot1_show = (TextView)findViewById(R.id.slot1_state);
        if(slot1_crash)
            slot1_show.setText("位置1目前是废墟状态，点击开始观看一段广告来复原你的建筑");
        else
            slot1_show.setText("位置1目前建造到了状态："+flower_slot1);

        tv = (TextView)findViewById(R.id.countdown_hint);
        if(slot1_crash){
            tv.setVisibility(View.VISIBLE);
            tv.setText("抱歉，你使用了手机，位置1已经坍塌。");
        }

        Button palace_back_button = (Button) findViewById(R.id.palace_back_button);

        palace_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(building_slot > 0) {
                    AlertDialog.Builder failAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                    failAlert.setTitle("坚持就是胜利！");
                    failAlert.setMessage("如果现在退出，正在建造的建筑就会坍塌。");
                    failAlert.setCancelable(false);
                    failAlert.setPositiveButton("退出", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface failAlert, int i) {
                            Toast.makeText(MyPalaceActivity.this, "抱歉，你使用了手机，位置1已经坍塌。", Toast.LENGTH_LONG).show();
                            mc.cancel();
                            slot1_crash = true;
                            editor.putBoolean("slot1_crash", slot1_crash);
                            editor.apply();
                            building_slot = 0;
                            slot1_show.setText("位置1目前是废墟状态，点击开始观看一段广告来复原你的建筑");
                            int version = Integer.valueOf(Build.VERSION.SDK_INT);
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
                else{
                    int version = Integer.valueOf(Build.VERSION.SDK_INT);
                    if(version > 5 ){
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                    finish();
                }
            }
        });


        Button share_button = (Button) findViewById(R.id.share_button);

        share_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ScreenShot.shoot(MyPalaceActivity.this);
                File f = new File("sdcard/Share.png");

                if(checkInstallation("com.tencent.mm")) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    if (f != null && f.exists() && f.isFile()) {
                        intent.setType("image/*");
                        Uri u = Uri.fromFile(f);
                        intent.putExtra(Intent.EXTRA_STREAM, u);
                        intent.putExtra(Intent.EXTRA_SUBJECT, "专注禅院");
                        intent.putExtra(Intent.EXTRA_TEXT, "我在专注禅院保持了专注");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(Intent.createChooser(intent, "请选择分享方式："));
                    }
                    else
                        Toast.makeText(MyPalaceActivity.this,"图片不存在",Toast.LENGTH_LONG).show();
                }


//                    ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
//                    intent.setComponent(comp);
                else
                    Toast.makeText(MyPalaceActivity.this,"不好意思，你好像没有安装微信等分享软件",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.flower1:
                switch (flower_slot1) {
                    case 1:
                        next_building_time = slot1_build_time_1;
                        break;
                    case 2:
                        next_building_time = slot1_build_time_2;
                        break;
                    case 3:
                        next_building_time = slot1_build_time_3;
                        break;
                    default:
                        next_building_time = -1;
                        break;
                }
                if (building_slot > 0) {
                    Toast.makeText(MyPalaceActivity.this, "请等待当前部分建造完再建造", Toast.LENGTH_LONG).show();
                }
                else if (slot1_crash) {
                    AlertDialog.Builder ADAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                    ADAlert.setMessage("好了，现在假设你看完了广告");
                    ADAlert.setCancelable(false);
                    ADAlert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface ADAlert, int i) {
                            slot1_crash = false;
                            editor.putBoolean("slot1_crash", slot1_crash);
                            editor.apply();
                            slot1_show.setText("位置1目前建造到了状态：" + flower_slot1);
                            tv.setVisibility(View.GONE);
                        }
                    });
                    ADAlert.show();
                }
                else{
                    if(next_building_time == -1){
                        Toast.makeText(MyPalaceActivity.this, "这个部分已经建造到最高等级了。", Toast.LENGTH_LONG).show();
                    }
                    else {
                        AlertDialog.Builder BuildAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                        BuildAlert.setTitle("升级建造禅院的花朵？");
                        BuildAlert.setMessage("建造花费30金币，需要保持" + next_building_time / 1000 + "秒的专注。");
                        BuildAlert.setCancelable(false);
                        BuildAlert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface BuildAlert, int i) {
                                mc = new MyCount(next_building_time, 1000);
                                mc.start();
                                building_slot = 1;
                                tv.setVisibility(View.VISIBLE);
                                Toast.makeText(MyPalaceActivity.this, "位置1已开始建造，请离开手机一段时间，直到倒计时结束", Toast.LENGTH_LONG).show();
                            }
                        });
                        BuildAlert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface BuildAlert, int i) {
                                BuildAlert.cancel();
                            }
                        });
                        BuildAlert.show();
                    }
                }
                break;
            case R.id.house1:
                switch (house_slot4) {
                    case 1:
                        next_building_time = slot4_build_time_1;
                        break;
                    case 2:
                        next_building_time = slot4_build_time_2;
                        break;
                    case 3:
                        next_building_time = slot4_build_time_3;
                        break;
                    default:
                        next_building_time = -1;
                        break;
                }
                if(building_slot > 0) {
                    Toast.makeText(MyPalaceActivity.this, "请等待当前部分建造完再建造", Toast.LENGTH_LONG).show();
                }
                else if(slot4_crash){
                    AlertDialog.Builder ADAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                    ADAlert.setMessage("好了，现在假设你看完了广告");
                    ADAlert.setCancelable(false);
                    ADAlert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface ADAlert, int i) {
                            slot1_crash = false;
                            editor.putBoolean("slot4_crash", slot4_crash);
                            editor.apply();
                            slot1_show.setText("位置4目前建造到了状态："+ house_slot4);
                            tv.setVisibility(View.GONE);
                        }
                    });
                    ADAlert.show();
                }
                else{
                    if(next_building_time == -1){
                        Toast.makeText(MyPalaceActivity.this, "这个部分已经建造到最高等级了。", Toast.LENGTH_LONG).show();
                    }
                    else {
                        AlertDialog.Builder BuildAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                        BuildAlert.setTitle("升级建造禅院？");
                        BuildAlert.setMessage("建造花费30金币，需要保持" + next_building_time / 1000 + "秒的专注。");
                        BuildAlert.setCancelable(false);
                        BuildAlert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface BuildAlert, int i) {
                                mc = new MyCount(next_building_time, 1000);
                                mc.start();
                                building_slot = 4;
                                tv.setVisibility(View.VISIBLE);
                                Toast.makeText(MyPalaceActivity.this, "位置4已开始建造，请离开手机一段时间，直到倒计时结束", Toast.LENGTH_LONG).show();
                            }
                        });
                        BuildAlert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface BuildAlert, int i) {
                                BuildAlert.cancel();
                            }
                        });
                        BuildAlert.show();
                    }
                }
                break;
            case R.id.stone1:
                switch (stone_slot3) {
                    case 1:
                        next_building_time = slot3_build_time_1;
                        break;
                    case 2:
                        next_building_time = slot3_build_time_2;
                        break;
                    case 3:
                        next_building_time = slot3_build_time_3;
                        break;
                    default:
                        next_building_time = -1;
                        break;
                }
                if(building_slot > 0) {
                    Toast.makeText(MyPalaceActivity.this, "请等待当前部分建造完再建造", Toast.LENGTH_LONG).show();
                }
                else if(slot3_crash){
                    AlertDialog.Builder ADAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                    ADAlert.setMessage("好了，现在假设你看完了广告");
                    ADAlert.setCancelable(false);
                    ADAlert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface ADAlert, int i) {
                            slot1_crash = false;
                            editor.putBoolean("slot3_crash", slot3_crash);
                            editor.apply();
                            slot1_show.setText("位置3目前建造到了状态："+ stone_slot3);
                            tv.setVisibility(View.GONE);
                        }
                    });
                    ADAlert.show();
                }
                else {
                    if (next_building_time == -1) {
                        Toast.makeText(MyPalaceActivity.this, "这个部分已经建造到最高等级了。", Toast.LENGTH_LONG).show();
                    } else {
                        AlertDialog.Builder BuildAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                        BuildAlert.setTitle("升级建造禅院的岩石？");
                        BuildAlert.setMessage("建造花费30金币，需要保持" + next_building_time / 1000 + "秒的专注。");
                        BuildAlert.setCancelable(false);
                        BuildAlert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface BuildAlert, int i) {
                                mc = new MyCount(next_building_time, 1000);
                                mc.start();
                                building_slot = 3;
                                tv.setVisibility(View.VISIBLE);
                                Toast.makeText(MyPalaceActivity.this, "位置3已开始建造，请离开手机一段时间，直到倒计时结束", Toast.LENGTH_LONG).show();
                            }
                        });
                        BuildAlert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface BuildAlert, int i) {
                                BuildAlert.cancel();
                            }
                        });
                        BuildAlert.show();
                    }
                }
                break;
            case R.id.tree1:
                switch (tree_slot2) {
                    case 1:
                        next_building_time = slot2_build_time_1;
                        break;
                    case 2:
                        next_building_time = slot2_build_time_2;
                        break;
                    case 3:
                        next_building_time = slot2_build_time_3;
                        break;
                    default:
                        next_building_time = -1;
                        break;
                }
                if(building_slot > 0) {
                    Toast.makeText(MyPalaceActivity.this, "请等待当前部分建造完再建造", Toast.LENGTH_LONG).show();
                }
                else if(slot3_crash){
                    AlertDialog.Builder ADAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                    ADAlert.setMessage("好了，现在假设你看完了广告");
                    ADAlert.setCancelable(false);
                    ADAlert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface ADAlert, int i) {
                            slot1_crash = false;
                            editor.putBoolean("slot2_crash", slot2_crash);
                            editor.apply();
                            slot1_show.setText("位置2目前建造到了状态："+ tree_slot2);
                            tv.setVisibility(View.GONE);
                        }
                    });
                    ADAlert.show();
                }
                else {
                    if (next_building_time == -1) {
                        Toast.makeText(MyPalaceActivity.this, "这个部分已经建造到最高等级了。", Toast.LENGTH_LONG).show();
                    } else {
                        AlertDialog.Builder BuildAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                        BuildAlert.setTitle("升级禅院的树？");
                        BuildAlert.setMessage("建造花费30金币，需要保持" + next_building_time / 1000 + "秒的专注。");
                        BuildAlert.setCancelable(false);
                        BuildAlert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface BuildAlert, int i) {
                                mc = new MyCount(next_building_time, 1000);
                                mc.start();
                                building_slot = 2;
                                tv.setVisibility(View.VISIBLE);
                                Toast.makeText(MyPalaceActivity.this, "位置2已开始建造，请离开手机一段时间，直到倒计时结束", Toast.LENGTH_LONG).show();
                            }
                        });
                        BuildAlert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface BuildAlert, int i) {
                                BuildAlert.cancel();
                            }
                        });
                        BuildAlert.show();
                    }
                }
                break;
        }
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        data = getSharedPreferences("data", MODE_PRIVATE);
        editor = getSharedPreferences("data", MODE_PRIVATE).edit();

        flower_slot1 = data.getInt("slot1", 1);
        switch(flower_slot1) {
            case 1:
                flowerImage.setBackgroundResource(R.drawable.flower1);
                break;
            case 2:
                flowerImage.setBackgroundResource(R.drawable.flower2);
                break;
            case 3:
                flowerImage.setBackgroundResource(R.drawable.flower3);
                break;
            default:
                flowerImage.setBackgroundResource(R.drawable.flower3);
        }

        tree_slot2 = data.getInt("slot2", 1);
        switch(tree_slot2) {
            case 1:
                flowerImage.setBackgroundResource(R.drawable.tree1);
                break;
            case 2:
                flowerImage.setBackgroundResource(R.drawable.tree2);
                break;
            case 3:
                flowerImage.setBackgroundResource(R.drawable.tree3);
                break;
            default:
                flowerImage.setBackgroundResource(R.drawable.tree3);
        }
        stone_slot3 = data.getInt("slot3", 1);
        switch(stone_slot3) {
            case 1:
                flowerImage.setBackgroundResource(R.drawable.stone1);
                break;
            case 2:
                flowerImage.setBackgroundResource(R.drawable.stone2);
                break;
            case 3:
                flowerImage.setBackgroundResource(R.drawable.stone3);
                break;
            default:
                flowerImage.setBackgroundResource(R.drawable.stone3);
        }
        house_slot4 = data.getInt("slot4", 1);
        switch(house_slot4) {
            case 1:
                flowerImage.setBackgroundResource(R.drawable.house1);
                break;
            case 2:
                flowerImage.setBackgroundResource(R.drawable.house2);
                break;
            case 3:
                flowerImage.setBackgroundResource(R.drawable.house3);
                break;
            default:
                flowerImage.setBackgroundResource(R.drawable.house3);
        }

        slot1_crash = data.getBoolean("slot1_crash", false);
        slot2_crash = data.getBoolean("slot2_crash", false);
        slot3_crash = data.getBoolean("slot3_crash", false);
        slot4_crash = data.getBoolean("slot4_crash", false);

        slot1_show = (TextView)findViewById(R.id.slot1_state);
        if(slot1_crash) {
            slot1_show.setText("位置1目前是废墟状态，点击开始观看一段广告来复原你的建筑");
            tv.setVisibility(View.VISIBLE);
            tv.setText("抱歉，你使用了手机，位置1已经坍塌。");
        }
        else if(building_slot > 0) {
            tv.setText("请等待30秒(" + mc.returnLeftTime() / 1000 + ")...");
        }
        else {
            tv.setVisibility(View.GONE);
            slot1_show.setText("位置1目前建造到了状态："+flower_slot1);
        }
    }

    @Override
    public void onBackPressed(){
        if(building_slot > 0) {
            AlertDialog.Builder failAlert = new AlertDialog.Builder(MyPalaceActivity.this);
            failAlert.setTitle("坚持就是胜利！");
            failAlert.setMessage("如果现在退出，正在建造的宫殿就会坍塌。");
            failAlert.setCancelable(false);
            failAlert.setPositiveButton("退出", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface failAlert, int i) {
                    Toast.makeText(MyPalaceActivity.this, "抱歉，你使用了手机，专注宫殿已经坍塌。", Toast.LENGTH_LONG).show();
                    mc.cancel();
                    switch (building_slot){
                        case 1:
                            slot1_crash = true;
                            editor.putBoolean("slot1_crash", slot1_crash);
                            editor.apply();
                            slot1_show.setText("位置1目前是废墟状态，点击开始观看一段广告来复原你的建筑");
                            break;
                        case 2:
                            slot2_crash = true;
                            editor.putBoolean("slot2_crash", slot2_crash);
                            editor.apply();
                            slot1_show.setText("位置2目前是废墟状态，点击开始观看一段广告来复原你的建筑");
                            break;
                        case 3:
                            slot3_crash = true;
                            editor.putBoolean("slot3_crash", slot3_crash);
                            editor.apply();
                            slot1_show.setText("位置3目前是废墟状态，点击开始观看一段广告来复原你的建筑");
                            break;
                        case 4:
                            slot3_crash = true;
                            editor.putBoolean("slot4_crash", slot4_crash);
                            editor.apply();
                            slot1_show.setText("位置4目前是废墟状态，点击开始观看一段广告来复原你的建筑");
                            break;
                    }
                    building_slot = 0;
                    int version = Integer.valueOf(Build.VERSION.SDK_INT);
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
            int version = Integer.valueOf(Build.VERSION.SDK_INT);
            if(version > 5 ){
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
            finish();
        }
    }

    @Override
    public void onTrimMemory(int level) {
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            mc.cancel();
            tv.setVisibility(View.GONE);
            switch (building_slot){
                case 1:
                    slot1_crash = true;
                    editor.putBoolean("slot1_crash", slot1_crash);
                    editor.apply();
                    slot1_show.setText("位置1目前是废墟状态，点击开始观看一段广告来复原你的建筑");
                    break;
                case 2:
                    slot2_crash = true;
                    editor.putBoolean("slot2_crash", slot2_crash);
                    editor.apply();
                    slot1_show.setText("位置2目前是废墟状态，点击开始观看一段广告来复原你的建筑");
                    break;
                case 3:
                    slot3_crash = true;
                    editor.putBoolean("slot3_crash", slot3_crash);
                    editor.apply();
                    slot1_show.setText("位置3目前是废墟状态，点击开始观看一段广告来复原你的建筑");
                    break;
                case 4:
                    slot3_crash = true;
                    editor.putBoolean("slot4_crash", slot4_crash);
                    editor.apply();
                    slot1_show.setText("位置4目前是废墟状态，点击开始观看一段广告来复原你的建筑");
                    break;
            }
            building_slot = 0;
            finish();
            Toast.makeText(MyPalaceActivity.this, "抱歉，你使用了手机，正在建造的部分已经坍塌。", Toast.LENGTH_LONG).show();
        }
        super.onTrimMemory(level);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clean.cleanExternalCache(MyPalaceActivity.this);
        ActivityCollector.removeActivity(this);
    }

    //    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event){
//        if(mc != null && mc.returnLeftTime()/1000 > 0) {
//            AlertDialog.Builder failAlert = new AlertDialog.Builder(MyPalaceActivity.this);
//            failAlert.setTitle("坚持就是胜利！");
//            failAlert.setMessage("如果现在操作手机，正在建造的宫殿就会坍塌。");
//            failAlert.setCancelable(false);
//            failAlert.setPositiveButton("继续操作", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface failAlert, int i) {
//                    Toast.makeText(MyPalaceActivity.this, "抱歉，你使用了手机，专注宫殿已经坍塌。", Toast.LENGTH_LONG).show();
//                    mc.cancel();
//                    build_success = 1;
//                    Intent intent = new Intent();
//                    intent.putExtra("data_return", true);
//                    setResult(RESULT_OK, intent);
//                    finish();
//                }
//            });
//            failAlert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface failAlert, int i) {
//                    failAlert.cancel();
//                }
//            });
//            failAlert.show();
//        }
//        else {
//            return super.onKeyUp(keyCode, event);
//        }
//        return true;
//    }
    private class MyCount extends CountDownTimer {

        long totalTime = 0;

        private MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            totalTime = millisInFuture;
        }
        private long leftTime = 0;

        @Override
        public void onFinish() {
            switch(building_slot){
                case 1:
                    flower_slot1++;
                    editor.putInt("slot1", flower_slot1);
                    editor.apply();
                    switch(flower_slot1) {
                        case 1:
                            flowerImage.setBackgroundResource(R.drawable.flower1);
                            break;
                        case 2:
                            flowerImage.setBackgroundResource(R.drawable.flower2);
                            break;
                        case 3:
                            flowerImage.setBackgroundResource(R.drawable.flower3);
                        default:
                            flowerImage.setBackgroundResource(R.drawable.flower3);
                    }
                    building_slot = 0;
                    slot1_show.setText("位置1目前建造到了状态："+ flower_slot1);
                    break;
                case 2:
                    tree_slot2++;
                    editor.putInt("slot2", tree_slot2);
                    editor.apply();
                    switch(tree_slot2) {
                        case 1:
                            treeImage.setBackgroundResource(R.drawable.tree1);
                            break;
                        case 2:
                            treeImage.setBackgroundResource(R.drawable.tree2);
                            break;
                        case 3:
                            treeImage.setBackgroundResource(R.drawable.tree3);
                            break;
                        default:
                            treeImage.setBackgroundResource(R.drawable.tree3);
                    }
                    building_slot = 0;
                    slot1_show.setText("位置2目前建造到了状态："+ tree_slot2);
                    break;
                case 3:
                    stone_slot3++;
                    editor.putInt("slot3", stone_slot3);
                    editor.apply();
                    switch(stone_slot3) {
                        case 1:
                            stoneImage.setBackgroundResource(R.drawable.stone1);
                            break;
                        case 2:
                            stoneImage.setBackgroundResource(R.drawable.stone2);
                            break;
                        case 3:
                            stoneImage.setBackgroundResource(R.drawable.stone3);
                            break;
                        default:
                            stoneImage.setBackgroundResource(R.drawable.stone3);
                    }
                    building_slot = 0;
                    slot1_show.setText("位置3目前建造到了状态："+ stone_slot3);
                    break;
                case 4:
                    house_slot4++;
                    editor.putInt("slot4", house_slot4);
                    editor.apply();
                    switch(house_slot4) {
                        case 1:
                            houseImage.setBackgroundResource(R.drawable.house1);
                            break;
                        case 2:
                            houseImage.setBackgroundResource(R.drawable.house2);
                            break;
                        case 3:
                            houseImage.setBackgroundResource(R.drawable.house3);
                            break;
                        default:
                            houseImage.setBackgroundResource(R.drawable.house3);
                    }
                    building_slot = 0;
                    slot1_show.setText("位置4目前建造到了状态："+ house_slot4);
                    break;
            }

            tv.setText("恭喜！你保持了专注，禅院已经建造完成。");
            leftTime = 0;
            mc.cancel();
        }
        @Override
        public void onTick(long millisUntilFinished) {
            leftTime = millisUntilFinished;
            tv.setText("请等待" + totalTime/1000 + "秒(" + millisUntilFinished / 1000 + ")...");
        }

        public long returnLeftTime(){
            return leftTime;
        }

    }

    /**
     * 判断是否安装腾讯、新浪等指定的分享应用
     * @param packageName 应用的包名
     */
    public boolean checkInstallation(String packageName){
        try {
            this.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}
