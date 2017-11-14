package com.example.charles.concentrationpalace;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 显示info页面的信息
 */

public class InfoActivity extends AppCompatActivity {
    private List<String> mInfoList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        setContentView(R.layout.activity_info);

        Resources res=getResources();
        Bitmap bmp= BitmapFactory.decodeResource(res, R.drawable.blur_background);
        RenderScript rs = RenderScript.create(InfoActivity.this);
        Allocation overlayAlloc = Allocation.createFromBitmap(rs,bmp);
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, overlayAlloc.getElement());
        blur.setInput(overlayAlloc);
        blur.setRadius(20);
        blur.forEach(overlayAlloc);
        overlayAlloc.copyTo(bmp);
        View v = getWindow().getDecorView();
        v.setBackground(new BitmapDrawable(getResources(), bmp));
        rs.destroy();

        mInfoList.add(getResources().getString(R.string.info1));
        mInfoList.add(getResources().getString(R.string.info2));
        mInfoList.add(getResources().getString(R.string.info3));

        RecyclerView recyclerView = findViewById(R.id.info_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        InfoAdapter adapter = new InfoAdapter(mInfoList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onBackPressed(){

        int version = Build.VERSION.SDK_INT;
        Intent intent = new Intent(InfoActivity.this, CP_MainActivity.class);
        startActivity(intent);
        if(version > 5 ){
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
        finish();
    }

}
