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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import java.io.File;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MyPalaceActivity extends AppCompatActivity implements OnClickListener {

    private TextView coin_display;

    private FindSelectedItem flowerImage;
    private FindSelectedItem treeImage;
    private FindSelectedItem stoneImage;
    private FindSelectedItem houseImage;
    private FindSelectedItem luweiImage;
    private FindSelectedItem lotusImage;

    int flower_slot1 = 0;
    int tree_slot2 = 0;
    int stone_slot3 = 0;
    int house_slot4 = 0;
    int luwei_slot5 = 0;
    int lotus_slot = 0;

    int slot1_build_time[] = {15000, 25000, 25000, 25000};

    int slot2_build_time[] = {15000, 25000, 35000, 25000};

    int slot3_build_time[] = {10000, 15000, 20000, 25000};

    int slot4_build_time[] = {5000, 10000, 15000, 25000};

    int slot5_build_time[] = {5000, 10000, 15000, 25000};

    int next_building_time = 84000;

    boolean slot1_crash = false;
    boolean slot2_crash = false;
    boolean slot3_crash = false;
    boolean slot4_crash = false;
    boolean slot5_crash = false;

    int origin_coin = 200;
    int my_coin;
    int price_matrix[][] = {{50,100,150},{100,200,300},{150,250,350},{200,350,500},{200,350,500}};

    SharedPreferences data;
    SharedPreferences.Editor editor;
    //DataCleanManager clean = new DataCleanManager();
    MediaPlayer mpMediaPlayer;

    private void initMapItems(){

        data = getSharedPreferences("data", MODE_PRIVATE);


        my_coin = data.getInt("my_coin", origin_coin);

        coin_display.setText(String.format(getResources().getString(R.string.coin_bar), my_coin));

        flower_slot1 = data.getInt("slot1", 0);
        slot1_crash = data.getBoolean("slot1_crash", false);
        if(slot1_crash)
            flowerImage.setBackgroundResource(R.drawable.flower_crush);
        else {
            switch (flower_slot1) {
                case 0:
                    flowerImage.setBackgroundResource(R.drawable.flower_crush);
                    break;
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
                    flowerImage.setBackgroundResource(R.drawable.flower_crush);
            }
        }
        tree_slot2 = data.getInt("slot2", 0);
        slot2_crash = data.getBoolean("slot2_crash", false);
        if(slot2_crash)
            treeImage.setBackgroundResource(R.drawable.tree_crush);
        else {
            switch (tree_slot2) {
                case 0:
                    treeImage.setBackgroundResource(R.drawable.tree_crush);
                    break;
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
                    treeImage.setBackgroundResource(R.drawable.tree_crush);
            }
        }
        stone_slot3 = data.getInt("slot3", 0);
        slot3_crash = data.getBoolean("slot3_crash", false);
        if(slot3_crash)
            stoneImage.setBackgroundResource(R.drawable.stone_crush);
        else {
            switch (stone_slot3) {
                case 0:
                    stoneImage.setBackgroundResource(R.drawable.stone_crush);
                    break;
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
                    stoneImage.setBackgroundResource(R.drawable.stone_crush);
            }
        }
        house_slot4 = data.getInt("slot4", 0);
        slot4_crash = data.getBoolean("slot4_crash", false);
        if(slot4_crash)
            houseImage.setBackgroundResource(R.drawable.house_crush);
        else {
            switch (house_slot4) {
                case 0:
                    houseImage.setBackgroundResource(R.drawable.house_crush);
                    break;
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
                    houseImage.setBackgroundResource(R.drawable.house_crush);
            }
        }
        luwei_slot5 = data.getInt("slot5", 0);
        slot5_crash = data.getBoolean("slot5_crash", false);
        if(slot5_crash)
            luweiImage.setBackgroundResource(R.drawable.luwei_crush);
        else {
            switch (luwei_slot5) {
                case 0:
                    luweiImage.setBackgroundResource(R.drawable.luwei_crush);
                    break;
                case 1:
                    luweiImage.setBackgroundResource(R.drawable.luwei1);
                    break;
                case 2:
                    luweiImage.setBackgroundResource(R.drawable.luwei2);
                    break;
                case 3:
                    luweiImage.setBackgroundResource(R.drawable.luwei3);
                    break;
                default:
                    luweiImage.setBackgroundResource(R.drawable.luwei_crush);
            }
        }

        switch (flower_slot1 + tree_slot2 + stone_slot3 + house_slot4 + luwei_slot5) {
            case 0:case 1:case 2:case 3:case 4:
                lotusImage.setBackgroundResource(R.drawable.lotus1);
                lotus_slot = R.drawable.lotus1_gallery;
                break;
            case 5:case 6:case 7:case 8:case 9:
                lotusImage.setBackgroundResource(R.drawable.lotus2);
                lotus_slot = R.drawable.lotus2_gallery;
                break;
            case 10:case 11:case 12:case 13:case 14:
                lotusImage.setBackgroundResource(R.drawable.lotus3);
                lotus_slot = R.drawable.lotus3_gallery;
                break;
            case 15:
                lotusImage.setBackgroundResource(R.drawable.lotus4);
                lotus_slot = R.drawable.lotus4_gallery;
                break;
            default:
                lotusImage.setBackgroundResource(R.drawable.lotus1);
                lotus_slot = R.drawable.lotus1_gallery;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
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

        flowerImage = findViewById(R.id.flower1);
        treeImage = findViewById(R.id.tree1);
        stoneImage = findViewById(R.id.stone1);
        houseImage = findViewById(R.id.house1);
        luweiImage = findViewById(R.id.luwei1);
        lotusImage = findViewById(R.id.lotus1);


        coin_display = findViewById(R.id.coin_bar);

        initMapItems();

        if((flower_slot1+tree_slot2+stone_slot3+house_slot4+luwei_slot5)>=5)
            mpMediaPlayer = MediaPlayer.create(this,R.raw.bgm_maoudamashii_healing08);
        else
            mpMediaPlayer = MediaPlayer.create(this,R.raw.bgm_maoudamashii_healing17);
        try {
            mpMediaPlayer.setLooping(true);
            mpMediaPlayer.start();
        } catch (IllegalArgumentException|IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        flowerImage.setOnClickListener(MyPalaceActivity.this);
        treeImage.setOnClickListener(MyPalaceActivity.this);
        stoneImage.setOnClickListener(MyPalaceActivity.this);
        houseImage.setOnClickListener(MyPalaceActivity.this);
        luweiImage.setOnClickListener(MyPalaceActivity.this);
        lotusImage.setOnClickListener(MyPalaceActivity.this);

        editor = getSharedPreferences("data", MODE_PRIVATE).edit();

        Button share_button = findViewById(R.id.share_button);

        share_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //* 动态获取权限，Android 6.0 新特性，一些保护权限，除了要在AndroidManifest中声明权限，还要使用如下代码动态获取
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
                ScreenShot.shoot(MyPalaceActivity.this);
                File f = new File(Environment.getExternalStorageDirectory().getPath(),"Share.png");

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                if (f.exists() && f.isFile()) {
                    intent.setType("image/*");
                    //Uri u = Uri.fromFile(f);
                    Uri u = FileProvider.getUriForFile(MyPalaceActivity.this, getPackageName() + ".fileprovider", f);
                    intent.putExtra(Intent.EXTRA_STREAM, u);
                    intent.putExtra(Intent.EXTRA_SUBJECT, "专注禅院");
                    intent.putExtra("Kdescription", "我在专注禅院保持了专注");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(Intent.createChooser(intent, "请选择分享方式："));
                }
                else
                    Toast.makeText(MyPalaceActivity.this,"生成分享图失败",Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onClick(View v) {

        coin_display = findViewById(R.id.coin_bar);

        if(Until.isFastClick()) {
            switch (v.getId()) {

                case R.id.flower1:

                    next_building_time = slot1_build_time[flower_slot1];

                    if (slot1_crash) {
                        AlertDialog.Builder ADAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                        ADAlert.setTitle("花费" + price_matrix[0][flower_slot1] + "金币，并观看一段广告来清除掉废墟？");
                        ADAlert.setMessage("废墟清除后会恢复你原先的建造等级");
                        ADAlert.setCancelable(true);
                        ADAlert.setPositiveButton("好的！", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface ADAlert, int i) {
                                if (my_coin < price_matrix[0][flower_slot1]) {
                                    Toast.makeText(MyPalaceActivity.this, "抱歉，您的金币不足，每天登录或观看广告可以获得新的金币。", Toast.LENGTH_LONG).show();
                                } else {
                                    //开始播放广告
                                    slot1_crash = false;
                                    editor.putBoolean("slot1_crash", false);
                                    editor.apply();
                                    my_coin = my_coin - price_matrix[0][flower_slot1];
                                    coin_display.setText(String.format(getResources().getString(R.string.coin_bar), my_coin));
                                    editor.putInt("my_coin", my_coin);
                                    editor.apply();
                                }
                            }
                        });
                        ADAlert.show();
                    } else {
                        if (flower_slot1 == 3) {
                            Toast.makeText(MyPalaceActivity.this, "这个部分已经建造到最高等级了。", Toast.LENGTH_LONG).show();
                        } else {
                            AlertDialog.Builder BuildAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                            BuildAlert.setTitle("升级禅院的荷花？");
                            BuildAlert.setMessage("建造花费" + price_matrix[0][flower_slot1] + "金币，需要保持" + next_building_time / 1000 + "秒的专注。");
                            BuildAlert.setCancelable(false);
                            BuildAlert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface BuildAlert, int i) {
                                    if (my_coin < price_matrix[0][flower_slot1]) {
                                        Toast.makeText(MyPalaceActivity.this, "抱歉，您的金币不足，每天登录或观看广告可以获得新的金币。", Toast.LENGTH_LONG).show();
                                    } else {
                                        my_coin = my_coin - price_matrix[0][flower_slot1];
                                        coin_display.setText(String.format(getResources().getString(R.string.coin_bar), my_coin));
                                        editor.putInt("my_coin", my_coin);
                                        editor.apply();
                                        Toast.makeText(MyPalaceActivity.this, "位置1已开始建造，请离开手机一段时间，直到倒计时结束", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(MyPalaceActivity.this,WaitingActivity.class);
                                        intent.putExtra("building_slot",1);
                                        intent.putExtra("building_time",next_building_time);
                                        startActivity(intent);
                                        int version = Build.VERSION.SDK_INT;
                                        if(version > 5 ){
                                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                        }
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

                    next_building_time = slot4_build_time[house_slot4];

                    if (slot4_crash) {
                        AlertDialog.Builder ADAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                        ADAlert.setTitle("花费" + price_matrix[3][house_slot4] + "金币，并观看一段广告来清除掉废墟？");
                        ADAlert.setMessage("废墟清除后会恢复你原先的建造等级");
                        ADAlert.setCancelable(true);
                        ADAlert.setPositiveButton("好的！", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface ADAlert, int i) {
                                if (my_coin < price_matrix[3][house_slot4]) {
                                    Toast.makeText(MyPalaceActivity.this, "抱歉，您的金币不足，每天登录或观看广告可以获得新的金币。", Toast.LENGTH_LONG).show();
                                } else {
                                    //开始播放广告
                                    slot4_crash = false;
                                    editor.putBoolean("slot4_crash", false);
                                    editor.apply();
                                    my_coin = my_coin - price_matrix[3][house_slot4];
                                    coin_display.setText(String.format(getResources().getString(R.string.coin_bar), my_coin));
                                    editor.putInt("my_coin", my_coin);
                                    editor.apply();
                                }
                            }
                        });
                        ADAlert.show();
                    } else {
                        if (house_slot4 == 3) {
                            Toast.makeText(MyPalaceActivity.this, "这个部分已经建造到最高等级了。", Toast.LENGTH_LONG).show();
                        } else {
                            AlertDialog.Builder BuildAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                            BuildAlert.setTitle("升级建造禅院？");
                            BuildAlert.setMessage("建造花费" + price_matrix[3][house_slot4] + "金币，需要保持" + next_building_time / 1000 + "秒的专注。");
                            BuildAlert.setCancelable(false);
                            BuildAlert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface BuildAlert, int i) {
                                    if (my_coin < price_matrix[3][house_slot4]) {
                                        Toast.makeText(MyPalaceActivity.this, "抱歉，您的金币不足，每天登录或观看广告可以获得新的金币。", Toast.LENGTH_LONG).show();
                                    } else {
                                        my_coin = my_coin - price_matrix[3][house_slot4];
                                        coin_display.setText(String.format(getResources().getString(R.string.coin_bar), my_coin));
                                        editor.putInt("my_coin", my_coin);
                                        editor.apply();
                                        Toast.makeText(MyPalaceActivity.this, "位置4已开始建造，请离开手机一段时间，直到倒计时结束", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(MyPalaceActivity.this,WaitingActivity.class);
                                        intent.putExtra("building_slot",4);
                                        intent.putExtra("building_time",next_building_time);
                                        startActivity(intent);
                                        int version = Build.VERSION.SDK_INT;
                                        if(version > 5 ){
                                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                        }
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

                    next_building_time = slot3_build_time[stone_slot3];

                    if (slot3_crash) {
                        AlertDialog.Builder ADAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                        ADAlert.setTitle("花费" + price_matrix[2][stone_slot3] + "金币，并观看一段广告来清除掉废墟？");
                        ADAlert.setMessage("废墟清除后会恢复你原先的建造等级");
                        ADAlert.setCancelable(true);
                        ADAlert.setPositiveButton("好的！", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface ADAlert, int i) {
                                if (my_coin < price_matrix[2][stone_slot3]) {
                                    Toast.makeText(MyPalaceActivity.this, "抱歉，您的金币不足，每天登录或观看广告可以获得新的金币。", Toast.LENGTH_LONG).show();
                                } else {
                                    //开始播放广告
                                    slot3_crash = false;
                                    editor.putBoolean("slot3_crash", false);
                                    editor.apply();
                                    my_coin = my_coin - price_matrix[2][stone_slot3];
                                    coin_display.setText(String.format(getResources().getString(R.string.coin_bar), my_coin));
                                    editor.putInt("my_coin", my_coin);
                                    editor.apply();
                                }
                            }
                        });
                        ADAlert.show();
                    } else {
                        if (stone_slot3 == 3) {
                            Toast.makeText(MyPalaceActivity.this, "这个部分已经建造到最高等级了。", Toast.LENGTH_LONG).show();
                        } else {
                            AlertDialog.Builder BuildAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                            BuildAlert.setTitle("升级建造禅院的岩石？");
                            BuildAlert.setMessage("建造花费" + price_matrix[2][stone_slot3] + "金币，需要保持" + next_building_time / 1000 + "秒的专注。");
                            BuildAlert.setCancelable(false);
                            BuildAlert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface BuildAlert, int i) {
                                    if (my_coin < price_matrix[2][stone_slot3]) {
                                        Toast.makeText(MyPalaceActivity.this, "抱歉，您的金币不足，每天登录或观看广告可以获得新的金币。", Toast.LENGTH_LONG).show();
                                    } else {
                                        my_coin = my_coin - price_matrix[2][stone_slot3];
                                        coin_display.setText(String.format(getResources().getString(R.string.coin_bar), my_coin));
                                        editor.putInt("my_coin", my_coin);
                                        editor.apply();
                                        Toast.makeText(MyPalaceActivity.this, "位置3已开始建造，请离开手机一段时间，直到倒计时结束", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(MyPalaceActivity.this,WaitingActivity.class);
                                        intent.putExtra("building_slot",3);
                                        intent.putExtra("building_time",next_building_time);
                                        startActivity(intent);
                                        int version = Build.VERSION.SDK_INT;
                                        if(version > 5 ){
                                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                        }
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

                    next_building_time = slot2_build_time[tree_slot2];

                    if (slot2_crash) {
                        AlertDialog.Builder ADAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                        ADAlert.setTitle("花费" + price_matrix[1][tree_slot2] + "金币，并观看一段广告来清除掉废墟？");
                        ADAlert.setMessage("废墟清除后会恢复你原先的建造等级");
                        ADAlert.setCancelable(true);
                        ADAlert.setPositiveButton("好的！", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface ADAlert, int i) {
                                if (my_coin < price_matrix[1][tree_slot2]) {
                                    Toast.makeText(MyPalaceActivity.this, "抱歉，您的金币不足，每天登录或观看广告可以获得新的金币。", Toast.LENGTH_LONG).show();
                                } else {
                                    //开始播放广告
                                    slot2_crash = false;
                                    editor.putBoolean("slot2_crash", false);
                                    editor.apply();
                                    my_coin = my_coin - price_matrix[1][tree_slot2];
                                    coin_display.setText(String.format(getResources().getString(R.string.coin_bar), my_coin));
                                    editor.putInt("my_coin", my_coin);
                                    editor.apply();
                                }
                            }
                        });
                        ADAlert.show();
                    } else {
                        if (tree_slot2 == 3) {
                            Toast.makeText(MyPalaceActivity.this, "这个部分已经建造到最高等级了。", Toast.LENGTH_LONG).show();
                        } else {
                            AlertDialog.Builder BuildAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                            BuildAlert.setTitle("升级禅院的树？");
                            BuildAlert.setMessage("建造花费" + price_matrix[1][tree_slot2] + "金币，需要保持" + next_building_time / 1000 + "秒的专注。");
                            BuildAlert.setCancelable(false);
                            BuildAlert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface BuildAlert, int i) {
                                    if (my_coin < price_matrix[1][tree_slot2]) {
                                        Toast.makeText(MyPalaceActivity.this, "抱歉，您的金币不足，每天登录或观看广告可以获得新的金币。", Toast.LENGTH_LONG).show();
                                    } else {
                                        my_coin = my_coin - price_matrix[1][tree_slot2];
                                        coin_display.setText(String.format(getResources().getString(R.string.coin_bar), my_coin));
                                        editor.putInt("my_coin", my_coin);
                                        editor.apply();
                                        Toast.makeText(MyPalaceActivity.this, "位置2已开始建造，请离开手机一段时间，直到倒计时结束", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(MyPalaceActivity.this,WaitingActivity.class);
                                        intent.putExtra("building_slot",2);
                                        intent.putExtra("building_time",next_building_time);
                                        startActivity(intent);
                                        int version = Build.VERSION.SDK_INT;
                                        if(version > 5 ){
                                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                        }
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
                case R.id.luwei1:

                    next_building_time = slot5_build_time[luwei_slot5];

                    if (slot5_crash) {
                        AlertDialog.Builder ADAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                        ADAlert.setTitle("花费" + price_matrix[4][luwei_slot5] + "金币，并观看一段广告来清除掉废墟？");
                        ADAlert.setMessage("废墟清除后会恢复你原先的建造等级");
                        ADAlert.setCancelable(true);
                        ADAlert.setPositiveButton("好的！", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface ADAlert, int i) {
                                if (my_coin < price_matrix[4][luwei_slot5]) {
                                    Toast.makeText(MyPalaceActivity.this, "抱歉，您的金币不足，每天登录或观看广告可以获得新的金币。", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    //开始播放广告
                                    slot5_crash = false;
                                    editor.putBoolean("slot5_crash", false);
                                    editor.apply();
                                    my_coin = my_coin - price_matrix[4][luwei_slot5];
                                    coin_display.setText(String.format(getResources().getString(R.string.coin_bar), my_coin));
                                    editor.putInt("my_coin", my_coin);
                                    editor.apply();
                                }
                            }
                        });
                        ADAlert.show();
                    } else {
                        if (luwei_slot5 == 3) {
                            Toast.makeText(MyPalaceActivity.this, "这个部分已经建造到最高等级了。", Toast.LENGTH_LONG).show();
                        } else {
                            AlertDialog.Builder BuildAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                            BuildAlert.setTitle("升级禅院的鹿威？");
                            BuildAlert.setMessage("建造花费" + price_matrix[4][luwei_slot5] + "金币，需要保持" + next_building_time / 1000 + "秒的专注。");
                            BuildAlert.setCancelable(false);
                            BuildAlert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface BuildAlert, int i) {
                                    if (my_coin < price_matrix[4][luwei_slot5]) {
                                        Toast.makeText(MyPalaceActivity.this, "抱歉，您的金币不足，每天登录或观看广告可以获得新的金币。", Toast.LENGTH_LONG).show();
                                    } else {
                                        my_coin = my_coin - price_matrix[4][luwei_slot5];
                                        coin_display.setText(String.format(getResources().getString(R.string.coin_bar), my_coin));
                                        editor.putInt("my_coin", my_coin);
                                        editor.apply();
                                        Toast.makeText(MyPalaceActivity.this, "位置5已开始建造，请离开手机一段时间，直到倒计时结束", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(MyPalaceActivity.this,WaitingActivity.class);
                                        intent.putExtra("building_slot",5);
                                        intent.putExtra("building_time",next_building_time);
                                        startActivity(intent);
                                        int version = Build.VERSION.SDK_INT;
                                        if(version > 5 ){
                                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                        }
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
                case R.id.lotus1:

                    final AlertDialog.Builder builder;
                    final AlertDialog.Builder builder1;
                    builder=new AlertDialog.Builder(this);
                    builder1=new AlertDialog.Builder(this);
                    builder.setIcon(lotus_slot);
                    builder.setTitle(R.string.lotus_title);
                    //builder.setMessage(R.string.lotus_msg);
                    builder1.setIcon(R.drawable.coin);
                    builder1.setTitle(R.string.time_title);

                    final String[] items={getResources().getString(R.string.lotus_opt1),getResources().getString(R.string.lotus_opt2),getResources().getString(R.string.lotus_opt3)};
                    final int[] time_num = {1,10,30};
                    final int[] coin_gain = {100,200,500};
                    final String[] times={String.format(getResources().getString(R.string.time_opt), time_num[0],coin_gain[0]),String.format(getResources().getString(R.string.time_opt), time_num[1],coin_gain[1]),String.format(getResources().getString(R.string.time_opt), time_num[2],coin_gain[2])};
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            switch (i) {
                                case 0:
                                    builder1.setItems(times, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int j) {
                                            Intent intent = new Intent(MyPalaceActivity.this, WaitingActivity.class);
                                            intent.putExtra("building_slot", 0);
                                            intent.putExtra("building_time", time_num[j] * 60 * 1000);
                                            intent.putExtra("building_coin", coin_gain[j]);
                                            startActivity(intent);
                                            int version = Build.VERSION.SDK_INT;
                                            if (version > 5) {
                                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                            }
                                        }
                                    });
                                    builder1.setCancelable(true);
                                    AlertDialog dialog=builder1.create();
                                    dialog.show();
                                    break;
                                case 1:
                                    //* 动态获取权限，Android 6.0 新特性，一些保护权限，除了要在AndroidManifest中声明权限，还要使用如下代码动态获取
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
                                    ScreenShot.shoot(MyPalaceActivity.this);
                                    File f = new File(Environment.getExternalStorageDirectory().getPath(), "Share.png");

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
                                    } else
                                        Toast.makeText(MyPalaceActivity.this, "生成分享图失败", Toast.LENGTH_LONG).show();
                                    break;
                                case 2:
                                    my_coin = my_coin + 10;
                                    coin_display.setText(String.format(getResources().getString(R.string.coin_bar), my_coin));
                                    editor.putInt("my_coin", my_coin);
                                    editor.apply();
                                    Toast.makeText(MyPalaceActivity.this, "假设你看完了广告，金币+" + 10, Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }
                    });

                    builder.setCancelable(true);
                    AlertDialog dialog=builder.create();
                    dialog.show();
            }
            initMapItems();
        }
    }
    @Override
    protected void onRestart(){
        super.onRestart();

        initMapItems();

        if((flower_slot1+tree_slot2+stone_slot3+house_slot4+luwei_slot5)>=5)
            mpMediaPlayer = MediaPlayer.create(this,R.raw.bgm_maoudamashii_healing17);
        else
            mpMediaPlayer = MediaPlayer.create(this,R.raw.bgm_maoudamashii_healing08);
        try {
            mpMediaPlayer.setLooping(true);
            mpMediaPlayer.start();
        } catch (IllegalArgumentException|IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed(){
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
            Intent intent = new Intent(MyPalaceActivity.this, CP_MainActivity.class);
            startActivity(intent);
            int version = Build.VERSION.SDK_INT;
            if(version > 5 ){
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
            finish();
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
        //clean.cleanExternalCache(MyPalaceActivity.this);
        ActivityCollector.removeActivity(this);
    }
}
