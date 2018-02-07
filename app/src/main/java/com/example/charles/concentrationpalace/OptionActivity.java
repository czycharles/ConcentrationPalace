package com.example.charles.concentrationpalace;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

import com.baidu.mobads.AdSettings;
import com.baidu.mobads.InterstitialAd;
import com.baidu.mobads.InterstitialAdListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Get coin option activity.
 */

public class OptionActivity extends AppCompatActivity {

    int flower_slot1;
    int tree_slot2;
    int stone_slot3;
    int house_slot4;
    int luwei_slot5;

    int AD_time;

    SharedPreferences data;
    SharedPreferences.Editor editor;

    int my_coin;
    int origin_coin = 200;
    int AD_coin;

    TextView coin_display;
    TextView option_title;
    TextView option_msg;
    TextView AD_hint;

    Animation animation1;
    TextView process;

    LinearLayout Option_select_page;
    LinearLayout Time_select_page;

    InterstitialAd interAd;
    Button AD_btn;

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
        option_title = findViewById(R.id.option_title);
        option_msg = findViewById(R.id.option_msg);
        coin_display = findViewById(R.id.coin_bar);
        process = findViewById(R.id.Item_Process);

        data = getSharedPreferences("data", MODE_PRIVATE);

        ImageView item_pic = findViewById(R.id.Finish_item);

        data = getSharedPreferences("data", MODE_PRIVATE);
        flower_slot1 = data.getInt("slot1", 0);
        tree_slot2 = data.getInt("slot2", 0);
        stone_slot3 = data.getInt("slot3", 0);
        house_slot4 = data.getInt("slot4", 0);
        luwei_slot5 = data.getInt("slot5",0);
        AD_time = data.getInt("AD_time",0);

        AD_btn= findViewById(R.id.opt3_button);

        AD_hint = findViewById(R.id.ad_hint);
        AD_hint.setText(String.format(getResources().getString(R.string.AD_hint),AD_time));

        Button info_button = findViewById(R.id.info_button);
        info_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder InfoAlert = new AlertDialog.Builder(OptionActivity.this);
                InfoAlert.setMessage(R.string.lotus_opt3_desc);
                InfoAlert.setCancelable(false);
                InfoAlert.setPositiveButton(R.string.confirm_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface InfoAlert, int i) {
                        InfoAlert.cancel();
                    }
                });
                InfoAlert.show();
            }
        });

        List<String> permissionList = new ArrayList<>();
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()){
            String [] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this, permissions,1);
        }
        else {
            requestAds();
        }

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
            case 15:case 16:case 17:case 18:case 19:
                item_pic.setImageResource(R.drawable.lotus4_gallery);
                option_msg.setText(R.string.finish_msg);
                option_title.setText(R.string.finish_title);
                item_pic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(OptionActivity.this, EndActivity.class);
                        startActivity(intent);
                        int version = Build.VERSION.SDK_INT;
                        if(version > 5 ){
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                    }
                });
                break;
            default:
                item_pic.setBackgroundResource(R.drawable.lotus1_gallery);
        }

//        item_pic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(OptionActivity.this, EndActivity.class);
//                startActivity(intent);
//                int version = Build.VERSION.SDK_INT;
//                if(version > 5 ){
//                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                }
//            }
//        });

        animation1 = AnimationUtils.loadAnimation(OptionActivity.this, R.anim.fade_in);

        process.setText(String.format(getResources().getString(R.string.gallery_process),flower_slot1 + tree_slot2 + stone_slot3 + house_slot4 + luwei_slot5 + 5,20));
        my_coin = data.getInt("my_coin", origin_coin);
        coin_display.setText(String.format(getResources().getString(R.string.coin_bar), my_coin));
        Drawable coin_icon = getResources().getDrawable(R.drawable.coin);
        coin_icon.setBounds(0, 0, coin_icon.getMinimumWidth(), coin_icon.getMinimumHeight());
        coin_display.setCompoundDrawables(coin_icon, null, null, null);

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
                option_title.setText(R.string.lotus_title);
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
                AlertDialog.Builder WarnAlert = new AlertDialog.Builder(OptionActivity.this);
                WarnAlert.setTitle(R.string.focus_title);
                WarnAlert.setMessage(R.string.focus_hint);
                WarnAlert.setCancelable(false);
                WarnAlert.setPositiveButton(R.string.confirm_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface WarnAlert, int i) {
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
                WarnAlert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface WarnAlert, int i) {
                        WarnAlert.cancel();
                    }
                });
                WarnAlert.show();
            }
        });

        time2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                AlertDialog.Builder WarnAlert = new AlertDialog.Builder(OptionActivity.this);
                WarnAlert.setTitle(R.string.focus_title);
                WarnAlert.setMessage(R.string.focus_hint);
                WarnAlert.setCancelable(false);
                WarnAlert.setPositiveButton(R.string.confirm_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface WarnAlert, int i) {
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
                WarnAlert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface WarnAlert, int i) {
                        WarnAlert.cancel();
                    }
                });
                WarnAlert.show();
            }
        });

        time3_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                AlertDialog.Builder WarnAlert = new AlertDialog.Builder(OptionActivity.this);
                WarnAlert.setTitle(R.string.focus_title);
                WarnAlert.setMessage(R.string.focus_hint);
                WarnAlert.setCancelable(false);
                WarnAlert.setPositiveButton(R.string.confirm_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface WarnAlert, int i) {
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
                WarnAlert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface WarnAlert, int i) {
                        WarnAlert.cancel();
                    }
                });
                WarnAlert.show();
            }
        });

        time4_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                AlertDialog.Builder WarnAlert = new AlertDialog.Builder(OptionActivity.this);
                WarnAlert.setTitle(R.string.focus_title);
                WarnAlert.setMessage(R.string.focus_hint);
                WarnAlert.setCancelable(false);
                WarnAlert.setPositiveButton(R.string.confirm_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface WarnAlert, int i) {
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
                WarnAlert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface WarnAlert, int i) {
                        WarnAlert.cancel();
                    }
                });
                WarnAlert.show();
            }
        });

//        Button share_btn = findViewById(R.id.opt2_button);
//
//        share_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v){
//                Intent intent = new Intent(OptionActivity.this, MyPalaceActivity.class);
//                intent.putExtra("Share_Action",1);
//                startActivity(intent);
//            }
//        });
    }

    private void requestAds(){

        final CountDownTimer mc1;

        mc1 = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                int random = (int)(Math.random()*10);
                if(random >= 9)
                    AD_coin = 15;
                else if(random >= 8)
                    AD_coin = 12;
                else if(random >= 5)
                    AD_coin = 10;
                else if(random >= 2)
                    AD_coin = 8;
                else if(random >= 0)
                    AD_coin = 5;

                my_coin = my_coin+AD_coin;
                editor.putInt("my_coin", my_coin);
                editor.apply();
                coin_display.setText(String.format(getResources().getString(R.string.coin_bar), my_coin));
                Toast.makeText(OptionActivity.this,String.format(getResources().getString(R.string.AD_coin_hint),AD_coin),Toast.LENGTH_LONG).show();
            }
        };

        editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        AdSettings.setSupportHttps(true);
        String adPlaceId = "5544509";//重要： 请填上你的代码位ID,否则无法请求到广告
        interAd = new InterstitialAd(this, adPlaceId);
        interAd.setListener(new InterstitialAdListener(){
            @Override
            public void onAdClick(InterstitialAd arg0) {
                //Toast.makeText(OptionActivity.this,"onAdClick",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdDismissed() {
                //Toast.makeText(OptionActivity.this,"onAdDismissed",Toast.LENGTH_SHORT).show();
                mc1.cancel();
                interAd.loadAd();
            }

            @Override
            public void onAdFailed(String arg0) {
                Toast.makeText(OptionActivity.this,R.string.AD_fail_hint,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdPresent() {
                //Toast.makeText(OptionActivity.this,"onAdPresent",Toast.LENGTH_SHORT).show();
                Toast.makeText(OptionActivity.this,R.string.AD_begin_hint,Toast.LENGTH_SHORT).show();
                AD_time--;
                editor.putInt("AD_time", AD_time);
                editor.apply();
                AD_hint.setText(String.format(getResources().getString(R.string.AD_hint),AD_time));
                mc1.start();
            }

            @Override
            public void onAdReady() {
                Toast.makeText(OptionActivity.this,R.string.AD_load_hint,Toast.LENGTH_LONG).show();
            }
        });

        interAd.loadAd();

        AD_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AD_time<=0){
                    Toast.makeText(OptionActivity.this,R.string.AD_no_time_hint,Toast.LENGTH_LONG).show();
                }
                else if (interAd.isAdReady()) {
                    interAd.showAd(OptionActivity.this);
                } else {
                    interAd.loadAd();
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(OptionActivity.this, MyPalaceActivity.class);
        startActivity(intent);
        int version = Build.VERSION.SDK_INT;
        if(version > 5 ){
            overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast);
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //clean.cleanExternalCache(MyPalaceActivity.this);
        ActivityCollector.removeActivity(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        switch (requestCode){
            case 1:
                if (grantResults.length > 0){
                    for (int result:grantResults){
                        if(result != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,R.string.permission_hint,Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    requestAds();
                }
                else{
                    Toast.makeText(this,R.string.AD_error,Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}
