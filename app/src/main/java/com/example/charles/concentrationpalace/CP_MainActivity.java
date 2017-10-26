package com.example.charles.concentrationpalace;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBar;
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
    SharedPreferences data;
    int version = Build.VERSION.SDK_INT;

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
//        ActionBar actionbar = getSupportActionBar();
//        if(actionbar!=null)
//            actionbar.hide();
        ActivityCollector.addActivity(this);
        file= new File("/data/data/com.example.charles.concentrationpalace/shared_prefs","data.xml");

        Button continue_button = (Button)findViewById(R.id.continue_button);
        if(file.exists())
            continue_button.setVisibility(View.VISIBLE);
        continue_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

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
                            data = getSharedPreferences("data",MODE_PRIVATE);
                            data.edit().clear().apply();
                            file.delete();
                            Toast.makeText(CP_MainActivity.this, "删除成功", Toast.LENGTH_LONG).show();
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

        Button exit_button = (Button)findViewById(R.id.exit_button);
        exit_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Toast.makeText(CP_MainActivity.this, "Success by now",Toast.LENGTH_LONG).show();
                ActivityCollector.finishAll();
                android.os.Process.killProcess(android.os.Process.myPid());
                Log.d("CP_MainActivity","Kill All: done");
            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data){
//        Button continue_button = (Button)findViewById(R.id.continue_button);
//        Log.d("CP_MainActivity","onActivityResult: done");
//        switch (requestCode){
//            case 1:
//                if(resultCode == RESULT_OK){
//                    Boolean returnedData = data.getBooleanExtra("data_return", false);
//                    if (returnedData == true)
//                        continue_button.setVisibility(View.VISIBLE);
//                }
//        }
//        continue_button.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//                Intent intent = new Intent(CP_MainActivity.this, MyPalaceActivity.class);
//                startActivity(intent);
//            }
//        });
//    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Button continue_button = (Button)findViewById(R.id.continue_button);
        if(file.exists())
            continue_button.setVisibility(View.VISIBLE);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
