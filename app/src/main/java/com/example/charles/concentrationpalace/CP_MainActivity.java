package com.example.charles.concentrationpalace;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.File;

public class CP_MainActivity extends AppCompatActivity {

    File file;
    int version = Build.VERSION.SDK_INT;
    MediaPlayer mpMediaPlayer=null;

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
        setContentView(R.layout.activity_cp__main);
        mpMediaPlayer = MediaPlayer.create(this,R.raw.bgm_maoudamashii_piano31);
        try {
            mpMediaPlayer.setLooping(true);
            //volumeGradient.vGradient(mpMediaPlayer,0f, 1f);
            mpMediaPlayer.start();
        } catch (IllegalArgumentException | IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        file= new File(this.getApplication().getFilesDir().getParentFile().getPath()+"/shared_prefs","data.xml");
        //file= new File("data/data/com.example.charles.concentrationpalace/shared_prefs","data.xml");

        Animation animation1 = AnimationUtils.loadAnimation(CP_MainActivity.this, R.anim.fade_in_fast);
        LinearLayout title = findViewById(R.id.title);
        LinearLayout btn1 = findViewById(R.id.btn1);

        title.startAnimation(animation1);
        btn1.startAnimation(animation1);

        Button info_button = findViewById(R.id.info_button);
        info_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CP_MainActivity.this, InfoActivity.class);
                startActivity(intent);
                if(version > 5 ){
                    overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast);
                }
            }
        });

        Button start_button = findViewById(R.id.start_button);
        if(file.exists()) {
            start_button.setText(R.string.continue_button);
        }
        start_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (mpMediaPlayer.isPlaying()) {
                    mpMediaPlayer.stop();
                    mpMediaPlayer.release();
                }
                if (file.exists()) {
                    Intent intent = new Intent(CP_MainActivity.this, MyPalaceActivity.class);
                    startActivity(intent);
                    if (version > 5) {
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                } else {
                    SharedPreferences.Editor editor;
                    editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                    editor.putInt("my_coin", 200);
                    editor.apply();
                    editor.putInt("slot1", 0);
                    editor.apply();
                    editor.putBoolean("slot1_crash", false);
                    editor.apply();
                    editor.putInt("slot2", 0);
                    editor.apply();
                    editor.putBoolean("slot2_crash", false);
                    editor.apply();
                    editor.putInt("slot3", 0);
                    editor.apply();
                    editor.putBoolean("slot3_crash", false);
                    editor.apply();
                    editor.putInt("slot4", 0);
                    editor.apply();
                    editor.putBoolean("slot4_crash", false);
                    editor.apply();
                    editor.putInt("slot5", 0);
                    editor.apply();
                    editor.putBoolean("slot5_crash", false);
                    editor.apply();
                    Intent intent = new Intent(CP_MainActivity.this, TutorialActivity.class);
                    startActivity(intent);
                    if (version > 5) {
                        overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast);
                    }
                }
            }
        });

        Button gallery_button = findViewById(R.id.gallery_button);
        gallery_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(CP_MainActivity.this, GalleryActivity.class);
                startActivity(intent);
                if(version > 5 ){
                    overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast);
                }
            }
        });
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
    protected void onRestart(){
        super.onRestart();
        mpMediaPlayer = MediaPlayer.create(this,R.raw.bgm_maoudamashii_healing17);
        mpMediaPlayer.start();
        mpMediaPlayer.setLooping(true);
        Button start_button = findViewById(R.id.start_button);
        if(file.exists())
            start_button.setText(R.string.continue_button);
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
        ActivityCollector.removeActivity(this);
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder exitAlert = new AlertDialog.Builder(CP_MainActivity.this);
        exitAlert.setTitle(R.string.exit);
        exitAlert.setCancelable(false);
        exitAlert.setPositiveButton(R.string.exit_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface exitAlert, int i) {
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
                ActivityCollector.finishAll();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        exitAlert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface exitAlert, int i) {
                exitAlert.cancel();
            }
        });
        exitAlert.show();
    }

}
