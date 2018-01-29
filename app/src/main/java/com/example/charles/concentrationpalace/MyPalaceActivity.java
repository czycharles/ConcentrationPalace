package com.example.charles.concentrationpalace;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */

public class MyPalaceActivity extends AppCompatActivity implements OnClickListener {

    private TextView coin_display;
    private ImageView share_cover;

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

    int slot1_build_time[] = {1800000, 3600000, 7200000, 10800000};

    int slot2_build_time[] = {2700000, 3600000, 7200000, 10800000};

    int slot3_build_time[] = {1200000, 3600000, 720000, 108000};

    int slot4_build_time[] = {3600000, 7200000, 10800000, 144000};

    int slot5_build_time[] = {600000, 1800000, 3600000, 7200000};

    int next_building_time = 3600000;

    boolean slot1_crash = false;
    boolean slot2_crash = false;
    boolean slot3_crash = false;
    boolean slot4_crash = false;
    boolean slot5_crash = false;

    int origin_coin = 200;
    int my_coin;
    int price_matrix[][] = {{200,500,1000},{300,600,1200},{200,500,1000},{500,800,1500},{150,300,800}};

    int fix_price = 50;
    String share_file_name = "My_Temple.png";

    SharedPreferences data;
    SharedPreferences.Editor editor;
    //DataCleanManager clean = new DataCleanManager();
    MediaPlayer mpMediaPlayer;

//    boolean share_price = false;

    private void initMapItems(){

        data = getSharedPreferences("data", MODE_PRIVATE);

        my_coin = data.getInt("my_coin", origin_coin);
//        editor = getSharedPreferences("data", MODE_PRIVATE).edit();

//        if(share_price) {
//            my_coin = my_coin + 50;
//            coin_display.setText(String.format(getResources().getString(R.string.coin_bar), my_coin));
//            editor.putInt("my_coin", my_coin);
//            long this_share_time = data.getLong("this_share_time", 0);
//            editor.putLong("last_share_time", this_share_time);
//            editor.apply();
//            share_price = false;
//                    AlertDialog.Builder BuildAlert = new AlertDialog.Builder(MyPalaceActivity.this);
//                    BuildAlert.setTitle("您已获得今日首次分享奖励");
//                    BuildAlert.setMessage("专注度+50");
//                    BuildAlert.setCancelable(false);
//                    BuildAlert.setPositiveButton("好的", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface BuildAlert, int i) {
//                            BuildAlert.cancel();
//                        }
//                    });
//        }

        coin_display.setText(String.format(getResources().getString(R.string.coin_bar), my_coin));
        Drawable coin_icon = getResources().getDrawable(R.drawable.coin);
        coin_icon.setBounds(0, 0, coin_icon.getMinimumWidth(), coin_icon.getMinimumHeight());
        coin_display.setCompoundDrawables(coin_icon, null, null, null);

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
                    flower_slot1 = 0;
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
                    tree_slot2 = 0;
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
                    stone_slot3 = 0;
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
                    house_slot4 = 0;
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
                    luwei_slot5 = 0;
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
        share_cover = findViewById(R.id.share_cover);

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

        final Button share_button = findViewById(R.id.share_button);

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
                share_cover.setVisibility(View.VISIBLE);
                coin_display.setVisibility(View.INVISIBLE);
                share_button.setVisibility(View.INVISIBLE);
                ScreenShot.shoot(MyPalaceActivity.this,share_file_name);

                File f = new File(Environment.getExternalStorageDirectory().getPath(),share_file_name);

                int hint[] = {  R.string.waiting_hint1,R.string.waiting_hint2,
                        R.string.waiting_hint3,R.string.waiting_hint4,
                        R.string.waiting_hint5,R.string.waiting_hint6,
                        R.string.waiting_hint7,R.string.waiting_hint8,
                        R.string.waiting_hint9,R.string.waiting_hint10,
                        R.string.waiting_hint11,R.string.waiting_hint12,
                        R.string.waiting_hint13,R.string.waiting_hint14,
                        R.string.waiting_hint15,R.string.waiting_hint16,
                        R.string.waiting_hint17,R.string.waiting_hint18,
                        R.string.waiting_hint19,R.string.waiting_hint20,
                        R.string.waiting_hint21,R.string.waiting_hint22,  };

                coin_display.setVisibility(View.VISIBLE);
                share_button.setVisibility(View.VISIBLE);
                share_cover.setVisibility(View.GONE);

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                if (f.exists() && f.isFile()) {

                    // Android 4.0 之后不能在主线程中请求HTTP请求
                    new Thread() {
                        public void run() {
                            Looper.prepare();
                            new Handler().post(runnable);//在子线程中直接去new 一个handler
                            Looper.loop();//这种情况下，Runnable对象是运行在子线程中的，可以进行联网操作，但是不能更新UI
                        }
                    }.start();

                    intent.setType("image/*");
                    //Uri u = Uri.fromFile(f);
                    Uri u = FileProvider.getUriForFile(MyPalaceActivity.this, getPackageName() + ".fileprovider", f);
                    intent.putExtra(Intent.EXTRA_STREAM, u);
                    intent.putExtra("Kdescription", hint[(int)(Math.random()*100) % 22]);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    long last_share_time = data.getLong("last_share_time", 0);
//                    long this_share_time = data.getLong("this_share_time", 0);
//                    if((Math.abs(this_share_time-last_share_time) > 10)) {
//                        share_price = true;
//                        Toast.makeText(MyPalaceActivity.this,"分享有效",Toast.LENGTH_LONG).show();
//                    }
                    startActivity(Intent.createChooser(intent, getResources().getString(R.string.share_chooser_title)));
                }
                else
                    Toast.makeText(MyPalaceActivity.this,R.string.share_fail,Toast.LENGTH_LONG).show();
            }
        });

        Intent intent = getIntent();
        int share_action = intent.getIntExtra("Share_Action",0);
        if(share_action == 1){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    share_button.performClick();
                }
            },500);
        }

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            URL url;//取得资源对象
            try {
                url = new URL("http://www.baidu.com");
                URLConnection uc = url.openConnection();//生成连接对象
                uc.connect(); //发出连接
                data = getSharedPreferences("data", MODE_PRIVATE);
                editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                editor.putLong("this_share_time", uc.getDate());
                editor.apply();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MyPalaceActivity.this,R.string.network_time_fail,Toast.LENGTH_LONG).show();
            }
        }
    };

//    public final float[] BT_SELECTED = new float[] {1,0,0,0,99,0,1,0,0,99,0,0,1,0,99,0,0,0,1,0};
//    public final float[] BT_NOT_SELECTED = new float[]  {1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0};;
    public final static float[] BT_SELECTED1 = new float[] {
            1,0,0,0,-50,
            0,1,0,0,-50,
            0,0,1,0,-50,
            0,0,0,1,0
    };

    @Override
    public void onClick(View v) {

        coin_display = findViewById(R.id.coin_bar);
        editor = getSharedPreferences("data", MODE_PRIVATE).edit();

        if(Until.isFastClick()) {

            final FindSelectedItem current_pic = findViewById(v.getId());
            current_pic.ReturnDrawable().setColorFilter(new ColorMatrixColorFilter(BT_SELECTED1));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    current_pic.ReturnDrawable().clearColorFilter();
                }
            },100);
            switch (v.getId()) {

                case R.id.flower1:

                    next_building_time = slot1_build_time[flower_slot1];

                    if (slot1_crash) {
                        AlertDialog.Builder ADAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                        ADAlert.setTitle(String.format(getResources().getString(R.string.clean_crush_title),fix_price));
                        ADAlert.setMessage(R.string.clean_crush_hint);
                        ADAlert.setCancelable(true);
                        ADAlert.setPositiveButton(R.string.OK_button, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface ADAlert, int i) {
                                if (my_coin < fix_price) {
                                    Toast.makeText(MyPalaceActivity.this, R.string.focus_point_not_enough, Toast.LENGTH_LONG).show();
                                } else {
                                    //开始播放广告
                                    slot1_crash = false;
                                    editor.putBoolean("slot1_crash", false);
                                    editor.apply();
                                    my_coin = my_coin - fix_price;
                                    coin_display.setText(String.format(getResources().getString(R.string.coin_bar), my_coin));
                                    editor.putInt("my_coin", my_coin);
                                    editor.apply();
                                    initMapItems();
                                }
                            }
                        });
                        ADAlert.show();
                    } else {
                        if (flower_slot1 == 3) {
                            Toast.makeText(MyPalaceActivity.this, R.string.max_level_hint, Toast.LENGTH_LONG).show();
                        } else {
                            AlertDialog.Builder BuildAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                            BuildAlert.setTitle(R.string.upgrade_flower_title);
                            BuildAlert.setMessage(String.format(getResources().getString(R.string.upgrade_msg), price_matrix[0][flower_slot1], (next_building_time / 1000 /60) ));
                            BuildAlert.setCancelable(false);
                            BuildAlert.setPositiveButton(R.string.confirm_button, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface BuildAlert, int i) {
                                    if (my_coin < price_matrix[0][flower_slot1]) {
                                        Toast.makeText(MyPalaceActivity.this, R.string.focus_point_not_enough, Toast.LENGTH_LONG).show();
                                    } else {
                                        AlertDialog.Builder WarnAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                                        WarnAlert.setTitle(R.string.build_title);
                                        WarnAlert.setMessage(R.string.focus_hint);
                                        WarnAlert.setCancelable(false);
                                        WarnAlert.setPositiveButton(R.string.confirm_button, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface WarnAlert, int i) {
                                                my_coin = my_coin - price_matrix[0][flower_slot1];
                                                coin_display.setText(String.format(getResources().getString(R.string.coin_bar), my_coin));
                                                editor.putInt("my_coin", my_coin);
                                                editor.apply();
                                                Toast.makeText(MyPalaceActivity.this, R.string.build_begin_hint, Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(MyPalaceActivity.this, WaitingActivity.class);
                                                intent.putExtra("building_slot", 1);
                                                intent.putExtra("building_time", next_building_time);
                                                startActivity(intent);
                                                int version = Build.VERSION.SDK_INT;
                                                if (version > 5) {
                                                    overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast);
                                                }
                                            }
                                        });
                                        WarnAlert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface WarnAlert, int i) {
                                                WarnAlert.cancel();
                                            }
                                        });
                                        WarnAlert.show();
                                    }
                                }
                            });
                            BuildAlert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface BuildAlert, int i) {
                                    BuildAlert.cancel();
                                }
                            });
                            BuildAlert.show();
                        }
                    }
                    initMapItems();
                    break;
                case R.id.house1:

                    next_building_time = slot4_build_time[house_slot4];

                    if (slot4_crash) {
                        AlertDialog.Builder ADAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                        ADAlert.setTitle(String.format(getResources().getString(R.string.clean_crush_title),fix_price));
                        ADAlert.setMessage(R.string.clean_crush_hint);
                        ADAlert.setCancelable(true);
                        ADAlert.setPositiveButton(R.string.OK_button, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface ADAlert, int i) {
                                if (my_coin < fix_price) {
                                    Toast.makeText(MyPalaceActivity.this, R.string.focus_point_not_enough, Toast.LENGTH_LONG).show();
                                } else {
                                    //开始播放广告
                                    slot4_crash = false;
                                    editor.putBoolean("slot4_crash", false);
                                    editor.apply();
                                    my_coin = my_coin - fix_price;
                                    coin_display.setText(String.format(getResources().getString(R.string.coin_bar), my_coin));
                                    editor.putInt("my_coin", my_coin);
                                    editor.apply();
                                    initMapItems();
                                }
                            }
                        });
                        ADAlert.show();
                    } else {
                        if (house_slot4 == 3) {
                            Toast.makeText(MyPalaceActivity.this, R.string.max_level_hint, Toast.LENGTH_LONG).show();
                        } else {
                            AlertDialog.Builder BuildAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                            BuildAlert.setTitle(R.string.upgrade_house_title);
                            BuildAlert.setMessage(String.format(getResources().getString(R.string.upgrade_msg),price_matrix[3][house_slot4], (next_building_time / 1000 /60) ));
                            BuildAlert.setCancelable(false);
                            BuildAlert.setPositiveButton(R.string.confirm_button, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface BuildAlert, int i) {
                                    if (my_coin < price_matrix[3][house_slot4]) {
                                        Toast.makeText(MyPalaceActivity.this, R.string.focus_point_not_enough, Toast.LENGTH_LONG).show();
                                    } else {
                                        AlertDialog.Builder WarnAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                                        WarnAlert.setTitle(R.string.build_title);
                                        WarnAlert.setMessage(R.string.focus_hint);
                                        WarnAlert.setCancelable(false);
                                        WarnAlert.setPositiveButton(R.string.confirm_button, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface WarnAlert, int i) {
                                                my_coin = my_coin - price_matrix[3][house_slot4];
                                                coin_display.setText(String.format(getResources().getString(R.string.coin_bar), my_coin));
                                                editor.putInt("my_coin", my_coin);
                                                editor.apply();
                                                Toast.makeText(MyPalaceActivity.this, R.string.build_begin_hint, Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(MyPalaceActivity.this,WaitingActivity.class);
                                                intent.putExtra("building_slot",4);
                                                intent.putExtra("building_time",next_building_time);
                                                startActivity(intent);
                                                int version = Build.VERSION.SDK_INT;
                                                if(version > 5 ){
                                                    overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast);
                                                }
                                            }
                                        });
                                        WarnAlert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface WarnAlert, int i) {
                                                WarnAlert.cancel();
                                            }
                                        });
                                        WarnAlert.show();
                                    }
                                }
                            });
                            BuildAlert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface BuildAlert, int i) {
                                    BuildAlert.cancel();
                                }
                            });
                            BuildAlert.show();
                        }
                    }
                    initMapItems();
                    break;
                case R.id.stone1:

                    next_building_time = slot3_build_time[stone_slot3];

                    if (slot3_crash) {
                        AlertDialog.Builder ADAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                        ADAlert.setTitle(String.format(getResources().getString(R.string.clean_crush_title),fix_price));
                        ADAlert.setMessage(R.string.clean_crush_hint);
                        ADAlert.setCancelable(true);
                        ADAlert.setPositiveButton(R.string.OK_button, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface ADAlert, int i) {
                                if (my_coin < fix_price) {
                                    Toast.makeText(MyPalaceActivity.this, R.string.focus_point_not_enough, Toast.LENGTH_LONG).show();
                                } else {
                                    //开始播放广告
                                    slot3_crash = false;
                                    editor.putBoolean("slot3_crash", false);
                                    editor.apply();
                                    my_coin = my_coin - fix_price;
                                    coin_display.setText(String.format(getResources().getString(R.string.coin_bar), my_coin));
                                    editor.putInt("my_coin", my_coin);
                                    editor.apply();
                                    initMapItems();
                                }
                            }
                        });
                        ADAlert.show();
                    } else {
                        if (stone_slot3 == 3) {
                            Toast.makeText(MyPalaceActivity.this, R.string.max_level_hint, Toast.LENGTH_LONG).show();
                        } else {
                            AlertDialog.Builder BuildAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                            BuildAlert.setTitle(R.string.upgrade_stone_title);
                            BuildAlert.setMessage(String.format(getResources().getString(R.string.upgrade_msg),price_matrix[2][stone_slot3], (next_building_time / 1000 /60) ));
                            BuildAlert.setCancelable(false);
                            BuildAlert.setPositiveButton(R.string.confirm_button, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface BuildAlert, int i) {
                                    if (my_coin < price_matrix[2][stone_slot3]) {
                                        Toast.makeText(MyPalaceActivity.this, R.string.focus_point_not_enough, Toast.LENGTH_LONG).show();
                                    } else {
                                        AlertDialog.Builder WarnAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                                        WarnAlert.setTitle(R.string.build_title);
                                        WarnAlert.setMessage(R.string.focus_hint);
                                        WarnAlert.setCancelable(false);
                                        WarnAlert.setPositiveButton(R.string.confirm_button, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface WarnAlert, int i) {
                                                my_coin = my_coin - price_matrix[2][stone_slot3];
                                                coin_display.setText(String.format(getResources().getString(R.string.coin_bar), my_coin));
                                                editor.putInt("my_coin", my_coin);
                                                editor.apply();
                                                Toast.makeText(MyPalaceActivity.this, R.string.build_begin_hint, Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(MyPalaceActivity.this,WaitingActivity.class);
                                                intent.putExtra("building_slot",3);
                                                intent.putExtra("building_time",next_building_time);
                                                startActivity(intent);
                                                int version = Build.VERSION.SDK_INT;
                                                if(version > 5 ){
                                                    overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast);
                                                }
                                            }
                                         });
                                        WarnAlert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface WarnAlert, int i) {
                                                WarnAlert.cancel();
                                            }
                                        });
                                        WarnAlert.show();
                                    }
                                }
                            });
                            BuildAlert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface BuildAlert, int i) {
                                    BuildAlert.cancel();
                                }
                            });
                            BuildAlert.show();
                        }
                    }
                    initMapItems();
                    break;
                case R.id.tree1:

                    next_building_time = slot2_build_time[tree_slot2];

                    if (slot2_crash) {
                        AlertDialog.Builder ADAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                        ADAlert.setTitle(String.format(getResources().getString(R.string.clean_crush_title),fix_price));
                        ADAlert.setMessage(R.string.clean_crush_hint);
                        ADAlert.setCancelable(true);
                        ADAlert.setPositiveButton(R.string.OK_button, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface ADAlert, int i) {
                                if (my_coin < fix_price) {
                                    Toast.makeText(MyPalaceActivity.this, R.string.focus_point_not_enough, Toast.LENGTH_LONG).show();
                                } else {
                                    //开始播放广告
                                    slot2_crash = false;
                                    editor.putBoolean("slot2_crash", false);
                                    editor.apply();
                                    my_coin = my_coin - fix_price;
                                    coin_display.setText(String.format(getResources().getString(R.string.coin_bar), my_coin));
                                    editor.putInt("my_coin", my_coin);
                                    editor.apply();
                                    initMapItems();
                                }
                            }
                        });
                        ADAlert.show();
                    } else {
                        if (tree_slot2 == 3) {
                            Toast.makeText(MyPalaceActivity.this, R.string.max_level_hint, Toast.LENGTH_LONG).show();
                        } else {
                            AlertDialog.Builder BuildAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                            BuildAlert.setTitle(R.string.upgrade_tree_title);
                            BuildAlert.setMessage(String.format(getResources().getString(R.string.upgrade_msg),price_matrix[1][tree_slot2], (next_building_time / 1000 /60) ));
                            BuildAlert.setCancelable(false);
                            BuildAlert.setPositiveButton(R.string.confirm_button, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface BuildAlert, int i) {
                                    if (my_coin < price_matrix[1][tree_slot2]) {
                                        Toast.makeText(MyPalaceActivity.this, R.string.focus_point_not_enough, Toast.LENGTH_LONG).show();
                                    } else {
                                        AlertDialog.Builder WarnAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                                        WarnAlert.setTitle(R.string.build_title);
                                        WarnAlert.setMessage(R.string.focus_hint);
                                        WarnAlert.setCancelable(false);
                                        WarnAlert.setPositiveButton(R.string.confirm_button, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface WarnAlert, int i) {
                                                my_coin = my_coin - price_matrix[1][tree_slot2];
                                                coin_display.setText(String.format(getResources().getString(R.string.coin_bar), my_coin));
                                                editor.putInt("my_coin", my_coin);
                                                editor.apply();
                                                Toast.makeText(MyPalaceActivity.this, R.string.build_begin_hint, Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(MyPalaceActivity.this,WaitingActivity.class);
                                                intent.putExtra("building_slot",2);
                                                intent.putExtra("building_time",next_building_time);
                                                startActivity(intent);
                                                int version = Build.VERSION.SDK_INT;
                                                if(version > 5 ){
                                                    overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast);
                                                }
                                            }
                                        });
                                        WarnAlert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface WarnAlert, int i) {
                                                WarnAlert.cancel();
                                            }
                                        });
                                        WarnAlert.show();
                                    }
                                }
                            });
                            BuildAlert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface BuildAlert, int i) {
                                    BuildAlert.cancel();
                                }
                            });
                            BuildAlert.show();
                        }
                    }
                    initMapItems();
                    break;
                case R.id.luwei1:

                    next_building_time = slot5_build_time[luwei_slot5];

                    if (slot5_crash) {
                        AlertDialog.Builder ADAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                        ADAlert.setTitle(String.format(getResources().getString(R.string.clean_crush_title),fix_price));
                        ADAlert.setMessage(R.string.clean_crush_hint);
                        ADAlert.setCancelable(true);
                        ADAlert.setPositiveButton(R.string.OK_button, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface ADAlert, int i) {
                                if (my_coin < fix_price) {
                                    Toast.makeText(MyPalaceActivity.this, R.string.focus_point_not_enough, Toast.LENGTH_LONG).show();
                                }
                                else {
                                    //开始播放广告
                                    slot5_crash = false;
                                    editor.putBoolean("slot5_crash", false);
                                    editor.apply();
                                    my_coin = my_coin - fix_price;
                                    coin_display.setText(String.format(getResources().getString(R.string.coin_bar), my_coin));
                                    editor.putInt("my_coin", my_coin);
                                    editor.apply();
                                    initMapItems();
                                }
                            }
                        });
                        ADAlert.show();
                    } else {
                        if (luwei_slot5 == 3) {
                            Toast.makeText(MyPalaceActivity.this, R.string.max_level_hint, Toast.LENGTH_LONG).show();
                        } else {
                            AlertDialog.Builder BuildAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                            BuildAlert.setTitle(R.string.upgrade_luwei_title);
                            BuildAlert.setMessage(String.format(getResources().getString(R.string.upgrade_msg),price_matrix[4][luwei_slot5], (next_building_time / 1000 /60) ));
                            BuildAlert.setCancelable(false);
                            BuildAlert.setPositiveButton(R.string.confirm_button, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface BuildAlert, int i) {
                                    if (my_coin < price_matrix[4][luwei_slot5]) {
                                        Toast.makeText(MyPalaceActivity.this, R.string.focus_point_not_enough, Toast.LENGTH_LONG).show();
                                    } else {
                                        AlertDialog.Builder WarnAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                                        WarnAlert.setTitle(R.string.build_title);
                                        WarnAlert.setMessage(R.string.focus_hint);
                                        WarnAlert.setCancelable(false);
                                        WarnAlert.setPositiveButton(R.string.confirm_button, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface WarnAlert, int i) {
                                                my_coin = my_coin - price_matrix[4][luwei_slot5];
                                                coin_display.setText(String.format(getResources().getString(R.string.coin_bar), my_coin));
                                                editor.putInt("my_coin", my_coin);
                                                editor.apply();
                                                Toast.makeText(MyPalaceActivity.this, R.string.build_begin_hint, Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(MyPalaceActivity.this,WaitingActivity.class);
                                                intent.putExtra("building_slot",5);
                                                intent.putExtra("building_time",next_building_time);
                                                startActivity(intent);
                                                int version = Build.VERSION.SDK_INT;
                                                if(version > 5 ){
                                                    overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast);
                                                }
                                            }
                                        });
                                        WarnAlert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface WarnAlert, int i) {
                                                WarnAlert.cancel();
                                            }
                                        });
                                        WarnAlert.show();
                                    }
                                }
                            });
                            BuildAlert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface BuildAlert, int i) {
                                    BuildAlert.cancel();
                                }
                            });
                            BuildAlert.show();
                        }
                    }
                    initMapItems();
                    break;
                case R.id.lotus1:

                    Intent intent = new Intent(MyPalaceActivity.this, OptionActivity.class);
                    intent.putExtra("building_slot",0);
                    startActivity(intent);
                    int version = Build.VERSION.SDK_INT;
                    if (version > 5) {
                        overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast);
                    }
                    initMapItems();
                    break;
            }
        }
    }
    @Override
    protected void onRestart(){
        super.onRestart();

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
    }

    @Override
    public void onBackPressed(){
            if(mpMediaPlayer!=null) {
                try{
                    mpMediaPlayer.stop();
                    //Log.d("Media","stop success");
                }catch(IllegalStateException e) {
                    mpMediaPlayer.release();
                    //Log.d("Media", "release success");
                    mpMediaPlayer = null;
                    //Log.d("Media", "null success");
                }
            }
            Intent intent = new Intent(MyPalaceActivity.this, CP_MainActivity.class);
            startActivity(intent);
            int version = Build.VERSION.SDK_INT;
            if(version > 5 ){
                overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast);
            }
            finish();
    }

    @Override
    protected void onStop() {
        if(mpMediaPlayer!=null) {
            try{
                mpMediaPlayer.stop();
                //Log.d("Media","stop success");
            }catch(IllegalStateException e) {
                mpMediaPlayer.release();
                //Log.d("Media", "release success");
                mpMediaPlayer = null;
                //Log.d("Media", "null success");
            }
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if(mpMediaPlayer!=null) {
            try{
                mpMediaPlayer.stop();
                //Log.d("Media","stop success");
            }catch(IllegalStateException e) {
                mpMediaPlayer.release();
                //Log.d("Media", "release success");
                mpMediaPlayer = null;
                //Log.d("Media", "null success");
            }
        }
        super.onDestroy();
        //clean.cleanExternalCache(MyPalaceActivity.this);
        ActivityCollector.removeActivity(this);
    }


}
