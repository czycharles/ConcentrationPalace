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
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.os.CountDownTimer;
import java.io.File;
import java.io.IOException;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MyPalaceActivity extends AppCompatActivity implements OnClickListener {

    private MyCount mc;
    private TextView slot1_show;
    private TextView tv;
    private TextView coin_display;
    private TextView item_desc;

    private FindSelectedItem flowerImage;
    private FindSelectedItem treeImage;
    private FindSelectedItem stoneImage;
    private FindSelectedItem houseImage;
    private ImageView item_pic;
    private ImageView dark_cover;

    int flower_slot1 = 1;
    int tree_slot2 = 1;
    int stone_slot3 = 1;
    int house_slot4 = 1;

    int slot1_build_time[] = {15000, 25000, 25000};

    int slot2_build_time[] = {15000, 25000, 35000};

    int slot3_build_time[] = {10000, 15000, 20000};

    int slot4_build_time[] = {5000, 10000, 15000};

    int next_building_time = 84000;

    int building_slot = 0;

    boolean slot1_crash = false;
    boolean slot2_crash = false;
    boolean slot3_crash = false;
    boolean slot4_crash = false;

    int origin_coin = 200;
    int my_coin;
    int price_matrix[][] = {{50,100,150},{100,200,300},{150,250,350},{200,350,500}};

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
        MediaPlayer mpMediaPlayer = MediaPlayer.create(this,R.raw.bgm_maoudamashii_healing17);
        try {
            mpMediaPlayer.prepare();
            mpMediaPlayer.setLooping(true);
            mpMediaPlayer.start();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Button AD_button = (Button) findViewById(R.id.AD_button);
        coin_display = (TextView)findViewById(R.id.coin_bar);

        AD_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                my_coin=my_coin+200;
                coin_display.setText(String.format(getResources().getString(R.string.coin_bar), my_coin));
                editor.putInt("my_coin", my_coin);
                editor.apply();
                Toast.makeText(MyPalaceActivity.this,"假设你看完了广告，金币+200",Toast.LENGTH_LONG).show();
            }
            });

        flowerImage = (FindSelectedItem)findViewById(R.id.flower1);
        treeImage = (FindSelectedItem)findViewById(R.id.tree1);
        stoneImage = (FindSelectedItem)findViewById(R.id.stone1);
        houseImage = (FindSelectedItem)findViewById(R.id.house1);

        findViewById(R.id.flower1).setOnClickListener(MyPalaceActivity.this);
        findViewById(R.id.tree1).setOnClickListener(MyPalaceActivity.this);
        findViewById(R.id.stone1).setOnClickListener(MyPalaceActivity.this);
        findViewById(R.id.house1).setOnClickListener(MyPalaceActivity.this);

        item_pic = (ImageView)findViewById(R.id.Item_pic);
        item_desc = (TextView)findViewById(R.id.Item_desc);
        dark_cover = (ImageView)findViewById(R.id.dark_cover);

        findViewById(R.id.Item_pic).setOnClickListener(MyPalaceActivity.this);
        findViewById(R.id.Item_desc).setOnClickListener(MyPalaceActivity.this);
        findViewById(R.id.dark_cover).setOnClickListener(MyPalaceActivity.this);

//        ActionBar actionbar = getSupportActionBar();
//        if(actionbar!=null)
//            actionbar.hide();

        data = getSharedPreferences("data", MODE_PRIVATE);
        editor = getSharedPreferences("data", MODE_PRIVATE).edit();

        my_coin = data.getInt("my_coin", origin_coin);

        coin_display.setText(String.format(getResources().getString(R.string.coin_bar), my_coin));

        flower_slot1 = data.getInt("slot1", 1);
        slot1_crash = data.getBoolean("slot1_crash", false);
        if(slot1_crash)
            flowerImage.setBackgroundResource(R.drawable.flower_crush);
        else {
            switch (flower_slot1) {
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
                    flowerImage.setBackgroundResource(R.drawable.flower1);
            }
        }
        tree_slot2 = data.getInt("slot2", 1);
        slot2_crash = data.getBoolean("slot2_crash", false);
        if(slot2_crash)
            treeImage.setBackgroundResource(R.drawable.tree_crush);
        else {
            switch (tree_slot2) {
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
                    treeImage.setBackgroundResource(R.drawable.tree1);
            }
        }
        stone_slot3 = data.getInt("slot3", 1);
        slot3_crash = data.getBoolean("slot3_crash", false);
        if(slot3_crash)
            stoneImage.setBackgroundResource(R.drawable.stone_crush);
        else {
            switch (stone_slot3) {
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
                    stoneImage.setBackgroundResource(R.drawable.stone1);
            }
        }
        house_slot4 = data.getInt("slot4", 1);
        slot4_crash = data.getBoolean("slot4_crash", false);
        if(slot4_crash)
            houseImage.setBackgroundResource(R.drawable.house_crush);
        else {
            switch (house_slot4) {
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
                    houseImage.setBackgroundResource(R.drawable.house1);
            }
        }

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
                            Toast.makeText(MyPalaceActivity.this, "抱歉，你使用了手机，位置" + building_slot + "已经坍塌。", Toast.LENGTH_LONG).show();
                            slot1_show.setText("位置" + building_slot + "目前是废墟状态，点击开始观看一段广告来复原你的建筑");
                            mc.cancel();
                            switch (building_slot) {
                                case 1:
                                    slot1_crash = true;
                                    editor.putBoolean("slot1_crash", true);
                                    editor.apply();
                                    flowerImage.setBackgroundResource(R.drawable.flower_crush);
                                    break;
                                case 2:
                                    slot2_crash = true;
                                    editor.putBoolean("slot2_crash", true);
                                    editor.apply();
                                    treeImage.setBackgroundResource(R.drawable.tree_crush);
                                    break;
                                case 3:
                                    slot3_crash = true;
                                    editor.putBoolean("slot3_crash", true);
                                    editor.apply();
                                    stoneImage.setBackgroundResource(R.drawable.stone_crush);
                                    break;
                                case 4:
                                    slot4_crash = true;
                                    editor.putBoolean("slot4_crash", true);
                                    editor.apply();
                                    houseImage.setBackgroundResource(R.drawable.house_crush);
                                    break;
                            }
                            building_slot = 0;
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
                else{
                    int version = Build.VERSION.SDK_INT;
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
                /**
                 * 动态获取权限，Android 6.0 新特性，一些保护权限，除了要在AndroidManifest中声明权限，还要使用如下代码动态获取
                 */
                if (Build.VERSION.SDK_INT >= 23) {
                    int REQUEST_CODE_CONTACT = 101;
                    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    //验证是否许可权限
                    for (String str : permissions) {
                        if (MyPalaceActivity.this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                            //申请权限
                            MyPalaceActivity.this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                            return;
                        }
                    }
                }
                File directory = new File(Environment.getExternalStorageDirectory().getPath());
                if(!directory.exists()){
                    directory.mkdir();//没有目录先创建目录
                }
                ScreenShot.shoot(MyPalaceActivity.this);
                File f = new File(Environment.getExternalStorageDirectory().getPath(),"Share.png");
                File ft = new File(Environment.getExternalStorageDirectory().getPath(),"Share.txt");

                if(true) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    if (f.exists() && f.isFile()) {
                        intent.setType("image/*");
                        //Uri u = Uri.fromFile(f);
                        Uri u = FileProvider.getUriForFile(MyPalaceActivity.this, getPackageName() + ".fileprovider", f);
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

        coin_display = (TextView)findViewById(R.id.coin_bar);

        switch (v.getId()) {

            case R.id.flower1:

                next_building_time = slot1_build_time[flower_slot1-1];

                if (building_slot > 0) {
                    Toast.makeText(MyPalaceActivity.this, "请等待当前部分建造完再建造", Toast.LENGTH_LONG).show();
                }
                else if (slot1_crash) {
                    AlertDialog.Builder ADAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                    ADAlert.setTitle("观看一段广告来清除掉废墟？");
                    ADAlert.setMessage("废墟清除后会恢复你原先的建造等级");
                    ADAlert.setCancelable(true);
                    ADAlert.setPositiveButton("好的！", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface ADAlert, int i) {
                            //开始播放广告
                            slot1_crash = false;
                            editor.putBoolean("slot1_crash", false);
                            editor.apply();
                            slot1_show.setText("位置1目前建造到了状态：" + flower_slot1);
                            switch (flower_slot1) {
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
                                    flowerImage.setBackgroundResource(R.drawable.flower1);
                                    break;
                            }
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
                        BuildAlert.setMessage("建造花费" + price_matrix[0][flower_slot1-1] + "金币，需要保持" + next_building_time / 1000 + "秒的专注。");
                        BuildAlert.setCancelable(false);
                        BuildAlert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface BuildAlert, int i) {
                                if (my_coin < price_matrix[0][flower_slot1 - 1]) {
                                    Toast.makeText(MyPalaceActivity.this, "抱歉，您的金币不足，每天登录或观看广告可以获得新的金币。", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    mc = new MyCount(next_building_time, 1000);
                                    mc.start();
                                    building_slot = 1;
                                    my_coin = my_coin - price_matrix[0][flower_slot1 - 1];
                                    coin_display.setText(String.format(getResources().getString(R.string.coin_bar), my_coin));
                                    editor.putInt("my_coin", my_coin);
                                    editor.apply();
                                    tv.setVisibility(View.VISIBLE);
                                    Toast.makeText(MyPalaceActivity.this, "位置1已开始建造，请离开手机一段时间，直到倒计时结束", Toast.LENGTH_LONG).show();
                                }
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

                next_building_time = slot4_build_time[house_slot4-1];

                if(building_slot > 0) {
                    Toast.makeText(MyPalaceActivity.this, "请等待当前部分建造完再建造", Toast.LENGTH_LONG).show();
                }
                else if(slot4_crash){
                    AlertDialog.Builder ADAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                    ADAlert.setTitle("观看一段广告来清除掉废墟？");
                    ADAlert.setMessage("废墟清除后会恢复你原先的建造等级");
                    ADAlert.setCancelable(true);
                    ADAlert.setPositiveButton("好的！", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface ADAlert, int i) {
                            //开始播放广告
                            slot4_crash = false;
                            editor.putBoolean("slot4_crash", slot4_crash);
                            editor.apply();
                            slot1_show.setText("位置4目前建造到了状态："+ house_slot4);
                            switch (house_slot4) {
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
                                    houseImage.setBackgroundResource(R.drawable.house1);
                                    break;
                            }
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
                        BuildAlert.setMessage("建造花费" + price_matrix[3][house_slot4-1] + "金币，需要保持" + next_building_time / 1000 + "秒的专注。");
                        BuildAlert.setCancelable(false);
                        BuildAlert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface BuildAlert, int i) {
                                if (my_coin < price_matrix[3][house_slot4 - 1]) {
                                    Toast.makeText(MyPalaceActivity.this, "抱歉，您的金币不足，每天登录或观看广告可以获得新的金币。", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    mc = new MyCount(next_building_time, 1000);
                                    mc.start();
                                    building_slot = 4;
                                    my_coin = my_coin - price_matrix[3][house_slot4 - 1];
                                    coin_display.setText(String.format(getResources().getString(R.string.coin_bar), my_coin));
                                    editor.putInt("my_coin", my_coin);
                                    editor.apply();
                                    tv.setVisibility(View.VISIBLE);
                                    Toast.makeText(MyPalaceActivity.this, "位置4已开始建造，请离开手机一段时间，直到倒计时结束", Toast.LENGTH_LONG).show();
                                }
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

                    next_building_time = slot3_build_time[stone_slot3-1];

                if(building_slot > 0) {
                    Toast.makeText(MyPalaceActivity.this, "请等待当前部分建造完再建造", Toast.LENGTH_LONG).show();
                }
                else if(slot3_crash){
                    AlertDialog.Builder ADAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                    ADAlert.setTitle("观看一段广告来清除掉废墟？");
                    ADAlert.setMessage("废墟清除后会恢复你原先的建造等级");
                    ADAlert.setCancelable(true);
                    ADAlert.setPositiveButton("好的！", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface ADAlert, int i) {
                            //开始播放广告
                            slot3_crash = false;
                            editor.putBoolean("slot3_crash", slot3_crash);
                            editor.apply();
                            slot1_show.setText("位置3目前建造到了状态："+ stone_slot3);
                            switch (stone_slot3) {
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
                                    stoneImage.setBackgroundResource(R.drawable.stone1);
                                    break;
                            }
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
                        BuildAlert.setMessage("建造花费" + price_matrix[2][stone_slot3-1] + "金币，需要保持" + next_building_time / 1000 + "秒的专注。");
                        BuildAlert.setCancelable(false);
                        BuildAlert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface BuildAlert, int i) {
                                if (my_coin < price_matrix[2][stone_slot3-1]) {
                                    Toast.makeText(MyPalaceActivity.this, "抱歉，您的金币不足，每天登录或观看广告可以获得新的金币。", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    mc = new MyCount(next_building_time, 1000);
                                    mc.start();
                                    building_slot = 3;
                                    my_coin = my_coin - price_matrix[2][stone_slot3-1];
                                    coin_display.setText(String.format(getResources().getString(R.string.coin_bar), my_coin));
                                    editor.putInt("my_coin", my_coin);
                                    editor.apply();
                                    tv.setVisibility(View.VISIBLE);
                                    Toast.makeText(MyPalaceActivity.this, "位置3已开始建造，请离开手机一段时间，直到倒计时结束", Toast.LENGTH_LONG).show();
                                }
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

                next_building_time = slot2_build_time[tree_slot2-1];

                if(building_slot > 0) {
                    Toast.makeText(MyPalaceActivity.this, "请等待当前部分建造完再建造", Toast.LENGTH_LONG).show();
                }
                else if(slot2_crash){
                    AlertDialog.Builder ADAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                    ADAlert.setTitle("观看一段广告来清除掉废墟？");
                    ADAlert.setMessage("废墟清除后会恢复你原先的建造等级");
                    ADAlert.setCancelable(true);
                    ADAlert.setPositiveButton("好的！", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface ADAlert, int i) {
                            //开始播放广告
                            slot2_crash = false;
                            editor.putBoolean("slot2_crash", slot2_crash);
                            editor.apply();
                            slot1_show.setText("位置2目前建造到了状态："+ tree_slot2);
                            switch (tree_slot2) {
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
                                    treeImage.setBackgroundResource(R.drawable.tree1);
                                    break;
                            }
                            tv.setVisibility(View.GONE);
                        }
                    });
                    ADAlert.show();
                }
                else {
                    if (next_building_time == -1) {
                        Toast.makeText(MyPalaceActivity.this, "这个部分已经建造到最高等级了。", Toast.LENGTH_LONG).show();
                    }
                    else {
                        AlertDialog.Builder BuildAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                        BuildAlert.setTitle("升级禅院的树？");
                        BuildAlert.setMessage("建造花费" + price_matrix[1][tree_slot2-1] + "金币，需要保持" + next_building_time / 1000 + "秒的专注。");
                        BuildAlert.setCancelable(false);
                        BuildAlert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface BuildAlert, int i) {
                                if (my_coin < price_matrix[1][tree_slot2-1]) {
                                    Toast.makeText(MyPalaceActivity.this, "抱歉，您的金币不足，每天登录或观看广告可以获得新的金币。", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    mc = new MyCount(next_building_time, 1000);
                                    mc.start();
                                    building_slot = 2;
                                    my_coin = my_coin - price_matrix[1][tree_slot2-1];
                                    coin_display.setText(String.format(getResources().getString(R.string.coin_bar), my_coin));
                                    editor.putInt("my_coin", my_coin);
                                    editor.apply();
                                    tv.setVisibility(View.VISIBLE);
                                    Toast.makeText(MyPalaceActivity.this, "位置2已开始建造，请离开手机一段时间，直到倒计时结束", Toast.LENGTH_LONG).show();
                                }
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
            case R.id.Item_pic:case R.id.Item_desc:case R.id.dark_cover:
                item_pic.setVisibility(View.GONE);
                item_desc.setVisibility(View.GONE);
                dark_cover.setVisibility(View.GONE);
                Animation animation5= AnimationUtils.loadAnimation(MyPalaceActivity.this, R.anim.fade_out);
                animation5.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation arg0) {}   //在动画开始时使用

                    @Override
                    public void onAnimationRepeat(Animation arg0) {}  //在动画重复时使用

                    @Override
                    public void onAnimationEnd(Animation arg0) {

                        //item_desc.setVisibility(View.VISIBLE);
                    }
                });
                item_pic.startAnimation(animation5);
                item_desc.startAnimation(animation5);
                dark_cover.startAnimation(animation5);
                break;
        }
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        data = getSharedPreferences("data", MODE_PRIVATE);
        ///editor = getSharedPreferences("data", MODE_PRIVATE).edit();

        flower_slot1 = data.getInt("slot1", 1);
        slot1_crash = data.getBoolean("slot1_crash", false);
        if(slot1_crash)
            flowerImage.setBackgroundResource(R.drawable.flower_crush);
        else {
            switch (flower_slot1) {
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
        }
        tree_slot2 = data.getInt("slot2", 1);
        slot2_crash = data.getBoolean("slot2_crash", false);
        if(slot2_crash)
            treeImage.setBackgroundResource(R.drawable.tree_crush);
        else {
            switch (tree_slot2) {
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
        }
        stone_slot3 = data.getInt("slot3", 1);
        slot3_crash = data.getBoolean("slot3_crash", false);
        if(slot3_crash)
            stoneImage.setBackgroundResource(R.drawable.stone_crush);
        else {
            switch (stone_slot3) {
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
        }
        house_slot4 = data.getInt("slot4", 1);
        slot4_crash = data.getBoolean("slot4_crash", false);
        if(slot4_crash)
            houseImage.setBackgroundResource(R.drawable.house_crush);
        else {
            switch (house_slot4) {
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
        }


        slot1_show = (TextView)findViewById(R.id.slot1_state);
        if(building_slot > 0) {
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
                    Toast.makeText(MyPalaceActivity.this, "抱歉，你使用了手机，位置" + building_slot + "已经坍塌。", Toast.LENGTH_LONG).show();
                    slot1_show.setText("位置" + building_slot + "目前是废墟状态，点击开始观看一段广告来复原你的建筑");
                    mc.cancel();
                    switch (building_slot) {
                        case 1:
                            slot1_crash = true;
                            editor.putBoolean("slot1_crash", true);
                            editor.apply();
                            flowerImage.setBackgroundResource(R.drawable.flower_crush);
                            break;
                        case 2:
                            slot2_crash = true;
                            editor.putBoolean("slot2_crash", true);
                            editor.apply();
                            treeImage.setBackgroundResource(R.drawable.tree_crush);
                            break;
                        case 3:
                            slot3_crash = true;
                            editor.putBoolean("slot3_crash", true);
                            editor.apply();
                            stoneImage.setBackgroundResource(R.drawable.stone_crush);
                            break;
                        case 4:
                            slot4_crash = true;
                            editor.putBoolean("slot4_crash", true);
                            editor.apply();
                            houseImage.setBackgroundResource(R.drawable.house_crush);
                            break;
                    }
                    building_slot = 0;
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
            int version = Build.VERSION.SDK_INT;
            if(version > 5 ){
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
            finish();
        }
    }

    @Override
    public void onTrimMemory(int level) {
        if ((level == TRIM_MEMORY_UI_HIDDEN)&&(building_slot > 0)) {
            mc.cancel();
            tv.setVisibility(View.GONE);
            Toast.makeText(MyPalaceActivity.this, "抱歉，你使用了手机，位置" + building_slot + "已经坍塌。", Toast.LENGTH_LONG).show();
            switch (building_slot) {
                case 1:
                    slot1_crash = true;
                    editor.putBoolean("slot1_crash", true);
                    editor.apply();
                    flowerImage.setBackgroundResource(R.drawable.flower_crush);
                    break;
                case 2:
                    slot2_crash = true;
                    editor.putBoolean("slot2_crash", true);
                    editor.apply();
                    treeImage.setBackgroundResource(R.drawable.tree_crush);
                    break;
                case 3:
                    slot3_crash = true;
                    editor.putBoolean("slot3_crash", true);
                    editor.apply();
                    stoneImage.setBackgroundResource(R.drawable.stone_crush);
                    break;
                case 4:
                    slot4_crash = true;
                    editor.putBoolean("slot4_crash", true);
                    editor.apply();
                    houseImage.setBackgroundResource(R.drawable.house_crush);
                    break;
            }
            building_slot = 0;
            finish();
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
            //WindowManager.LayoutParams lp = getWindow().getAttributes();
            Animation animation1= AnimationUtils.loadAnimation(MyPalaceActivity.this, R.anim.fade_in);
            Animation animation2= AnimationUtils.loadAnimation(MyPalaceActivity.this, R.anim.fade_in);
            Animation animation3= AnimationUtils.loadAnimation(MyPalaceActivity.this, R.anim.fade_in);
            Animation animation4= AnimationUtils.loadAnimation(MyPalaceActivity.this, R.anim.fade_in);

            dark_cover.setVisibility(View.VISIBLE);
            item_pic.setVisibility(View.VISIBLE);
            item_desc.setVisibility(View.VISIBLE);
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
                            item_pic.setBackgroundResource(R.drawable.item1);
                            item_desc.setText(R.string.flower2_desc);
                            break;
                        case 3:
                            flowerImage.setBackgroundResource(R.drawable.flower3);
                            item_pic.setBackgroundResource(R.drawable.item1);
                            item_desc.setText(R.string.flower3_desc);
                            break;
                        default:
                            flowerImage.setBackgroundResource(R.drawable.flower3);
                    }
                    building_slot = 0;

//                    lp.alpha = 0.6f;
//                    getWindow().setAttributes(lp);
//                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

                    animation1.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation arg0) {}   //在动画开始时使用

                        @Override
                        public void onAnimationRepeat(Animation arg0) {}  //在动画重复时使用

                        @Override
                        public void onAnimationEnd(Animation arg0) {

                            //item_desc.setVisibility(View.VISIBLE);
                        }
                    });
                    item_pic.startAnimation(animation1);
                    item_desc.startAnimation(animation1);


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
                            item_pic.setBackgroundResource(R.drawable.item1);
                            item_desc.setText(R.string.tree2_desc);
                            break;
                        case 3:
                            treeImage.setBackgroundResource(R.drawable.tree3);
                            item_pic.setBackgroundResource(R.drawable.item1);
                            item_desc.setText(R.string.tree3_desc);
                            break;
                        default:
                            treeImage.setBackgroundResource(R.drawable.tree3);
                    }
                    building_slot = 0;

                    animation2.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation arg0) {}   //在动画开始时使用

                        @Override
                        public void onAnimationRepeat(Animation arg0) {}  //在动画重复时使用

                        @Override
                        public void onAnimationEnd(Animation arg0) {
                            //item_pic.setVisibility(View.VISIBLE);
                            //item_desc.setVisibility(View.VISIBLE);
                        }
                    });
                    item_pic.startAnimation(animation2);
                    item_desc.startAnimation(animation2);

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
                            item_pic.setBackgroundResource(R.drawable.item1);
                            item_desc.setText(R.string.stone2_desc);
                            break;
                        case 3:
                            stoneImage.setBackgroundResource(R.drawable.stone3);
                            item_pic.setBackgroundResource(R.drawable.item1);
                            item_desc.setText(R.string.stone3_desc);
                            break;
                        default:
                            stoneImage.setBackgroundResource(R.drawable.stone3);
                    }
                    building_slot = 0;

                    animation3.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation arg0) {}   //在动画开始时使用

                        @Override
                        public void onAnimationRepeat(Animation arg0) {}  //在动画重复时使用

                        @Override
                        public void onAnimationEnd(Animation arg0) {
                            //item_pic.setVisibility(View.VISIBLE);
                            //item_desc.setVisibility(View.VISIBLE);
                        }
                    });
                    item_pic.startAnimation(animation3);
                    item_desc.startAnimation(animation3);

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
                            item_pic.setBackgroundResource(R.drawable.item1);
                            item_desc.setText(R.string.house2_desc);
                            break;
                        case 3:
                            houseImage.setBackgroundResource(R.drawable.house3);
                            item_pic.setBackgroundResource(R.drawable.item1);
                            item_desc.setText(R.string.house3_desc);
                            break;
                        default:
                            houseImage.setBackgroundResource(R.drawable.house3);
                    }
                    building_slot = 0;

                    animation4.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation arg0) {}   //在动画开始时使用

                        @Override
                        public void onAnimationRepeat(Animation arg0) {}  //在动画重复时使用

                        @Override
                        public void onAnimationEnd(Animation arg0) {
                            //item_pic.setVisibility(View.VISIBLE);
                            //item_desc.setVisibility(View.VISIBLE);
                        }
                    });
                    item_pic.startAnimation(animation4);
                    item_desc.startAnimation(animation4);

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

         long returnLeftTime(){
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
