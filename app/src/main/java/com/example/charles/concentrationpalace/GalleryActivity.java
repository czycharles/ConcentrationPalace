package com.example.charles.concentrationpalace;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Gallery Page.
 */

public class GalleryActivity extends AppCompatActivity implements View.OnClickListener {

//    private ImageView flower1;
//    private ImageView flower2;
//    private ImageView flower3;
//    private ImageView flower4;
//    private ImageView tree1;
//    private ImageView tree2;
//    private ImageView tree3;
//    private ImageView tree4;
//    private ImageView stone1;
//    private ImageView stone2;
//    private ImageView stone3;
//    private ImageView stone4;
//    private ImageView house1;
//    private ImageView house2;
//    private ImageView house3;
//    private ImageView house4;

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
        setContentView(R.layout.activity_gallery);

        Resources res=getResources();
        Bitmap bmp= BitmapFactory.decodeResource(res, R.drawable.blur_background);
        RenderScript rs = RenderScript.create(GalleryActivity.this);
        Allocation overlayAlloc = Allocation.createFromBitmap(rs,bmp);
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, overlayAlloc.getElement());
        blur.setInput(overlayAlloc);
        blur.setRadius(20);
        blur.forEach(overlayAlloc);
        overlayAlloc.copyTo(bmp);
        View v = getWindow().getDecorView();
        v.setBackground(new BitmapDrawable(getResources(), bmp));
        rs.destroy();

        ImageView flower1;
        ImageView flower2;
        ImageView flower3;
        ImageView flower4;
        ImageView tree1;
        ImageView tree2;
        ImageView tree3;
        ImageView tree4;
        ImageView stone1;
        ImageView stone2;
        ImageView stone3;
        ImageView stone4;
        ImageView house1;
        ImageView house2;
        ImageView house3;
        ImageView house4;

        int flower_slot1;
        int tree_slot2;
        int stone_slot3;
        int house_slot4;

        flower1 = findViewById(R.id.flower1);
        flower2 = findViewById(R.id.flower2);
        flower3 = findViewById(R.id.flower3);
        flower4 = findViewById(R.id.flower4);
        tree1 = findViewById(R.id.tree1);
        tree2 = findViewById(R.id.tree2);
        tree3 = findViewById(R.id.tree3);
        tree4 = findViewById(R.id.tree4);
        stone1 = findViewById(R.id.stone1);
        stone2 = findViewById(R.id.stone2);
        stone3 = findViewById(R.id.stone3);
        stone4 = findViewById(R.id.stone4);
        house1 = findViewById(R.id.house1);
        house2 = findViewById(R.id.house2);
        house3 = findViewById(R.id.house3);
        house4 = findViewById(R.id.house4);

        SharedPreferences data;
        data = getSharedPreferences("data", MODE_PRIVATE);
        flower_slot1 = data.getInt("slot1", 1);
        tree_slot2 = data.getInt("slot2", 1);
        stone_slot3 = data.getInt("slot3", 1);
        house_slot4 = data.getInt("slot4", 1);
        if(flower_slot1>=2) {
            flower2.setImageResource(R.drawable.item1);
        }
        if(flower_slot1>=3) {
            flower2.setImageResource(R.drawable.item1);
        }
        if(flower_slot1>=4) {
            flower2.setImageResource(R.drawable.item1);
        }
        if(tree_slot2>=2) {
            flower2.setImageResource(R.drawable.item1);
        }
        if(tree_slot2>=3) {
            flower2.setImageResource(R.drawable.item1);
        }
        if(tree_slot2>=4) {
            flower2.setImageResource(R.drawable.item1);
        }
        if(stone_slot3>=2) {
            flower2.setImageResource(R.drawable.item1);
        }
        if(stone_slot3>=3) {
            flower2.setImageResource(R.drawable.item1);
        }
        if(stone_slot3>=4) {
            flower2.setImageResource(R.drawable.item1);
        }
        if(house_slot4>=2) {
            flower2.setImageResource(R.drawable.item1);
        }
        if(house_slot4>=3) {
            flower2.setImageResource(R.drawable.item1);
        }
        if(house_slot4>=4) {
            flower2.setImageResource(R.drawable.item1);
        }

        flower1.setOnClickListener(GalleryActivity.this);
        flower2.setOnClickListener(GalleryActivity.this);
        flower3.setOnClickListener(GalleryActivity.this);
        flower4.setOnClickListener(GalleryActivity.this);
        tree1.setOnClickListener(GalleryActivity.this);
        tree2.setOnClickListener(GalleryActivity.this);
        tree3.setOnClickListener(GalleryActivity.this);
        tree4.setOnClickListener(GalleryActivity.this);
        stone1.setOnClickListener(GalleryActivity.this);
        stone2.setOnClickListener(GalleryActivity.this);
        stone3.setOnClickListener(GalleryActivity.this);
        stone4.setOnClickListener(GalleryActivity.this);
        house1.setOnClickListener(GalleryActivity.this);
        house2.setOnClickListener(GalleryActivity.this);
        house3.setOnClickListener(GalleryActivity.this);
        house4.setOnClickListener(GalleryActivity.this);
    }

    @Override
    public void onClick(View v) {

        Animation animation1= AnimationUtils.loadAnimation(GalleryActivity.this, R.anim.fade_in_fast);
        ImageView dark_cover = findViewById(R.id.dark_cover);
        ImageView big_image = findViewById(R.id.big_item);

        big_image.setOnClickListener(GalleryActivity.this);
        dark_cover.setOnClickListener(GalleryActivity.this);

        switch (v.getId()) {
            case R.id.flower1:
                dark_cover.setVisibility(View.VISIBLE);
                big_image.setVisibility(View.VISIBLE);
//                animation1.setAnimationListener(new Animation.AnimationListener() {
//                    @Override
//                    public void onAnimationStart(Animation arg0) {}   //在动画开始时使用
//
//                    @Override
//                    public void onAnimationRepeat(Animation arg0) {}  //在动画重复时使用
//
//                    @Override
//                    public void onAnimationEnd(Animation arg0) {
//
//                        //dark_cover.setVisibility(View.GONE);
//                    }
//                });
                big_image.startAnimation(animation1);
                break;
            case R.id.flower2:
                dark_cover.setVisibility(View.VISIBLE);
                big_image.setVisibility(View.VISIBLE);
//                animation1.setAnimationListener(new Animation.AnimationListener() {
//                    @Override
//                    public void onAnimationStart(Animation arg0) {}   //在动画开始时使用
//
//                    @Override
//                    public void onAnimationRepeat(Animation arg0) {}  //在动画重复时使用
//
//                    @Override
//                    public void onAnimationEnd(Animation arg0) {
//
//                        //dark_cover.setVisibility(View.GONE);
//                    }
//                });
                big_image.startAnimation(animation1);
                break;
            case R.id.flower3:
                dark_cover.setVisibility(View.VISIBLE);
                big_image.setVisibility(View.VISIBLE);
//                animation1.setAnimationListener(new Animation.AnimationListener() {
//                    @Override
//                    public void onAnimationStart(Animation arg0) {}   //在动画开始时使用
//
//                    @Override
//                    public void onAnimationRepeat(Animation arg0) {}  //在动画重复时使用
//
//                    @Override
//                    public void onAnimationEnd(Animation arg0) {
//
//                        //dark_cover.setVisibility(View.GONE);
//                    }
//                });
                big_image.startAnimation(animation1);
                break;
            case R.id.flower4:
                dark_cover.setVisibility(View.VISIBLE);
                big_image.setVisibility(View.VISIBLE);
//                animation1.setAnimationListener(new Animation.AnimationListener() {
//                    @Override
//                    public void onAnimationStart(Animation arg0) {}   //在动画开始时使用
//
//                    @Override
//                    public void onAnimationRepeat(Animation arg0) {}  //在动画重复时使用
//
//                    @Override
//                    public void onAnimationEnd(Animation arg0) {
//
//                        //dark_cover.setVisibility(View.GONE);
//                    }
//                });
                big_image.startAnimation(animation1);
                break;
            case R.id.big_item:case R.id.dark_cover:
                big_image.setVisibility(View.GONE);
                dark_cover.setVisibility(View.GONE);
                Animation animation2= AnimationUtils.loadAnimation(GalleryActivity.this, R.anim.fade_out_fast);
//                animation5.setAnimationListener(new Animation.AnimationListener() {
//                    @Override
//                    public void onAnimationStart(Animation arg0) {}   //在动画开始时使用
//
//                    @Override
//                    public void onAnimationRepeat(Animation arg0) {}  //在动画重复时使用
//
//                    @Override
//                    public void onAnimationEnd(Animation arg0) {
//
//                        //item_desc.setVisibility(View.VISIBLE);
//                    }
//                });
                big_image.startAnimation(animation2);
                dark_cover.startAnimation(animation2);
                break;
        }

    }
    @Override
    public void onBackPressed(){

        int version = Build.VERSION.SDK_INT;
        Intent intent = new Intent(GalleryActivity.this, CP_MainActivity.class);
        startActivity(intent);
        if(version > 5 ){
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
        finish();
    }

}
