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
    int slot1 = 1;
    int building_slot = 0;
    boolean slot1_crash = false;
    SharedPreferences data;
    SharedPreferences.Editor editor;
    DataCleanManager clean = new DataCleanManager();
    private Toast mToast;

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

        findViewById(R.id.flower1).setOnClickListener(MyPalaceActivity.this);
        findViewById(R.id.tree1).setOnClickListener(MyPalaceActivity.this);
        findViewById(R.id.stone1).setOnClickListener(MyPalaceActivity.this);
        findViewById(R.id.house1).setOnClickListener(MyPalaceActivity.this);


//        ActionBar actionbar = getSupportActionBar();
//        if(actionbar!=null)
//            actionbar.hide();

        data = getSharedPreferences("data", MODE_PRIVATE);
        editor = getSharedPreferences("data", MODE_PRIVATE).edit();

        slot1 = data.getInt("slot1", 1);
        slot1_crash = data.getBoolean("slot1_crash", false);
        slot1_show = (TextView)findViewById(R.id.slot1_state);
        if(slot1_crash)
            slot1_show.setText("位置1目前是废墟状态，点击开始观看一段广告来复原你的建筑");
        else
            slot1_show.setText("位置1目前建造到了状态："+slot1);

        tv = (TextView)findViewById(R.id.countdown_hint);
        if(slot1_crash){
            tv.setVisibility(View.VISIBLE);
            tv.setText("抱歉，你使用了手机，位置1已经坍塌。");
        }
        Button start_build_button = (Button) findViewById(R.id.start_build_button);
        Button palace_back_button = (Button) findViewById(R.id.palace_back_button);

        start_build_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(building_slot > 0) {
                    Toast.makeText(MyPalaceActivity.this, "请等待当前部分建造完再建造", Toast.LENGTH_LONG).show();
                }
                else if(slot1_crash){
                    AlertDialog.Builder ADAlert = new AlertDialog.Builder(MyPalaceActivity.this);
                    ADAlert.setMessage("好了，现在假设你看完了广告");
                    ADAlert.setCancelable(false);
                    ADAlert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface ADAlert, int i) {
                            slot1_crash = false;
                            editor.putBoolean("slot1_crash", slot1_crash);
                            editor.apply();
                            slot1_show.setText("位置1目前建造到了状态："+slot1);
                            tv.setVisibility(View.GONE);
                        }
                    });
                    ADAlert.show();
                }
                else{
                    mc = new MyCount(15000, 1000);
                    mc.start();
                    building_slot = 1;
                    tv.setVisibility(View.VISIBLE);
                    Toast.makeText(MyPalaceActivity.this, "位置1已开始建造，请离开手机一段时间，直到倒计时结束", Toast.LENGTH_LONG).show();
                }
            }
        });
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
        if (null != mToast) {
            mToast.cancel();
        }
        switch (v.getId()) {
            case R.id.flower1:
                mToast = Toast.makeText(this, "你点击了花", Toast.LENGTH_SHORT);
                break;
            case R.id.house1:
                mToast = Toast.makeText(this, "你点击了房子", Toast.LENGTH_SHORT);
                break;
            case R.id.stone1:
                mToast = Toast.makeText(this, "你点击了石头", Toast.LENGTH_SHORT);
                break;
            case R.id.tree1:
                mToast = Toast.makeText(this, "你点击了树", Toast.LENGTH_SHORT);
                break;
        }
        mToast.show();
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        data = getSharedPreferences("data", MODE_PRIVATE);
        editor = getSharedPreferences("data", MODE_PRIVATE).edit();

        slot1 = data.getInt("slot1", 1);
        slot1_crash = data.getBoolean("slot1_crash", false);
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
            slot1_show.setText("位置1目前建造到了状态："+slot1);
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
                    slot1_crash = true;
                    editor.putBoolean("slot1_crash", slot1_crash);
                    editor.apply();
                    slot1_show.setText("位置1目前是废墟状态，点击开始观看一段广告来复原你的建筑");
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
            slot1_crash = true;
            editor.putBoolean("slot1_crash", slot1_crash);
            editor.apply();
            slot1_show.setText("位置1目前是废墟状态，点击开始观看一段广告来复原你的建筑");
            building_slot = 0;
            finish();
            Toast.makeText(MyPalaceActivity.this, "抱歉，你使用了手机，专注宫殿已经坍塌。", Toast.LENGTH_LONG).show();
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
        private MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        private long leftTime = 0;
        @Override
        public void onFinish() {
            slot1++;
            editor.putInt("slot1", slot1);
            editor.apply();
            building_slot = 0;
            slot1_show.setText("位置1目前建造到了状态："+slot1);
            tv.setText("恭喜！你保持了专注，宫殿已经建造完成。");
            leftTime = 0;
            mc.cancel();
        }
        @Override
        public void onTick(long millisUntilFinished) {
            leftTime = millisUntilFinished;
            tv.setText("请等待15秒(" + millisUntilFinished / 1000 + ")...");
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
