package com.example.charles.concentrationpalace;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class CP_MainActivity extends AppCompatActivity {

    File file;
    int version = Build.VERSION.SDK_INT;
    MediaPlayer mpMediaPlayer;

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
        setContentView(R.layout.activity_cp__main);
        mpMediaPlayer = MediaPlayer.create(this,R.raw.bgm_maoudamashii_healing17);
        try {
            mpMediaPlayer.setLooping(true);
            mpMediaPlayer.start();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        ActivityCollector.addActivity(this);
        file= new File(this.getApplication().getFilesDir().getParentFile().getPath()+"/shared_prefs","data.xml");
        //file= new File("data/data/com.example.charles.concentrationpalace/shared_prefs","data.xml");
        Button continue_button = (Button)findViewById(R.id.continue_button);
        if(file.exists())
            continue_button.setVisibility(View.VISIBLE);
        //continue_button.setVisibility(View.VISIBLE);
        continue_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(mpMediaPlayer.isPlaying()) {
                    mpMediaPlayer.stop();
                    mpMediaPlayer.release();
                }
                Intent intent = new Intent(CP_MainActivity.this, MyPalaceActivity.class);
                startActivity(intent);
                if(version > 5 ){
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });
        Button start_button = (Button)findViewById(R.id.start_button);
        start_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if(file.exists()) {
                    AlertDialog.Builder failAlert = new AlertDialog.Builder(CP_MainActivity.this);
                    failAlert.setTitle("删除记录？");
                    failAlert.setMessage("你目前的游戏进度会被清空");
                    failAlert.setCancelable(false);
                    failAlert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface failAlert, int i) {
//                            data = getSharedPreferences("data", MODE_PRIVATE);
//                            data.edit().clear().apply();
                            if (file.delete()){
                                Toast.makeText(CP_MainActivity.this, "删除成功", Toast.LENGTH_LONG).show();
                            }
                            Button continue_button = (Button)findViewById(R.id.continue_button);
                            continue_button.setVisibility(View.GONE);
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
                    if(mpMediaPlayer.isPlaying()) {
                        mpMediaPlayer.stop();
                        mpMediaPlayer.release();
                    }
                    Intent intent = new Intent(CP_MainActivity.this, CoverActivity.class);
                    startActivity(intent);
                    if(version > 5 ){
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                }
            }
        });
        Log.d("CP_MainActivity","onCreate: done");

        Button gallery_button = (Button)findViewById(R.id.gallery_button);
        gallery_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(CP_MainActivity.this, GalleryActivity.class);
                startActivity(intent);
                if(version > 5 ){
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mpMediaPlayer.isPlaying()) {
            mpMediaPlayer.stop();
            //mpMediaPlayer.release();
        }
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        mpMediaPlayer = MediaPlayer.create(this,R.raw.bgm_maoudamashii_healing17);
        mpMediaPlayer.start();
        mpMediaPlayer.setLooping(true);
        Button continue_button = (Button)findViewById(R.id.continue_button);
        if(file.exists())
            continue_button.setVisibility(View.VISIBLE);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        //拦截返回键
//        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK){
//            //判断触摸UP事件才会进行返回事件处理
//            if (event.getAction() == KeyEvent.ACTION_UP) {
//                onBackPressed();
//            }
//            //只要是返回事件，直接返回true，表示消费掉
//            return true;
//        }
//        return super.dispatchKeyEvent(event);
//    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder exitAlert = new AlertDialog.Builder(CP_MainActivity.this);
        exitAlert.setTitle("退出游戏？");
        exitAlert.setCancelable(false);
        exitAlert.setPositiveButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface exitAlert, int i) {
                if(mpMediaPlayer.isPlaying()) {
                    mpMediaPlayer.stop();
                    mpMediaPlayer.release();
                }
                ActivityCollector.finishAll();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        exitAlert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface exitAlert, int i) {
                exitAlert.cancel();
            }
        });
        exitAlert.show();
    }

}
