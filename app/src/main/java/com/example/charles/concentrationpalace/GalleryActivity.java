package com.example.charles.concentrationpalace;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Gallery Page.
 */

public class GalleryActivity extends AppCompatActivity {


    private List<GalleryItem> mGalleryList = new ArrayList<>();

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
        setContentView(R.layout.activity_gallery);

        int flower_slot1;
        int tree_slot2;
        int stone_slot3;
        int house_slot4;
        int luwei_slot5;


        SharedPreferences data;
        data = getSharedPreferences("data", MODE_PRIVATE);
        flower_slot1 = data.getInt("slot1", 0);
        tree_slot2 = data.getInt("slot2", 0);
        stone_slot3 = data.getInt("slot3", 0);
        house_slot4 = data.getInt("slot4", 0);
        luwei_slot5 = data.getInt("slot5",0);

        initGalleryItems(flower_slot1,tree_slot2,stone_slot3,house_slot4,luwei_slot5);

        RecyclerView recyclerView = findViewById(R.id.gallery_view);
        GridLayoutManager glm = new GridLayoutManager(this,4);
        recyclerView.setLayoutManager(glm);
        GalleryAdapter adapter = new GalleryAdapter(mGalleryList,glm);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        int spacingInPixels = 9;
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        adapter.setOnItemClickListener(new GalleryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                GalleryItem currentItem = mGalleryList.get(position);

                final TextView big_image = findViewById(R.id.big_image);
                Animation animation1 = AnimationUtils.loadAnimation(GalleryActivity.this, R.anim.fade_in_fast);
                final ImageView dark_cover = findViewById(R.id.dark_cover);
                final ImageView dark_background = findViewById(R.id.dark_background);

                if (currentItem.getImageId() == R.drawable.item_unknown)
                    Toast.makeText(GalleryActivity.this, "该物品尚未解锁", Toast.LENGTH_SHORT).show();
                else {
                    big_image.setText(currentItem.getDesc());
                    big_image.setVisibility(View.VISIBLE);
                    Drawable drawable = getResources().getDrawable(currentItem.getImageId());
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth()*3/2, drawable.getMinimumHeight()*3/2);
                    big_image.setCompoundDrawables(null, drawable, null, null);
                    dark_cover.setVisibility(View.VISIBLE);
                    dark_background.setVisibility(View.VISIBLE);
                    //Toast.makeText(GalleryActivity.this, "位置" + position, Toast.LENGTH_SHORT).show();
                    big_image.startAnimation(animation1);
                    dark_cover.startAnimation(animation1);
                    dark_background.startAnimation(animation1);
                    big_image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(Until.isFastClick()) {
                                big_image.setVisibility(View.GONE);
                                dark_cover.setVisibility(View.GONE);
                                dark_background.setVisibility(View.GONE);
                                Animation animation2 = AnimationUtils.loadAnimation(GalleryActivity.this, R.anim.fade_out_fast);
                                big_image.startAnimation(animation2);
                                dark_cover.startAnimation(animation2);
                                dark_background.startAnimation(animation2);
                            }
                        }
                    });
                    dark_cover.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(Until.isFastClick()) {
                                big_image.setVisibility(View.GONE);
                                dark_cover.setVisibility(View.GONE);
                                dark_background.setVisibility(View.GONE);
                                Animation animation2 = AnimationUtils.loadAnimation(GalleryActivity.this, R.anim.fade_out_fast);
                                big_image.startAnimation(animation2);
                                dark_cover.startAnimation(animation2);
                                dark_background.startAnimation(animation2);
                            }
                        }
                    });
                }
            }
        });
    }

    private void initGalleryItems(int slot1, int slot2, int slot3, int slot4, int slot5){

        mGalleryList.add(new GalleryItem(getResources().getString(R.string.flower0_desc),R.drawable.flower_crush));
        if(slot1>0)
            mGalleryList.add(new GalleryItem(getResources().getString(R.string.flower1_desc),R.drawable.flower1_gallery));
        else
            mGalleryList.add(new GalleryItem(getResources().getString(R.string.flower1_desc),R.drawable.item_unknown));
        if(slot1>1)
            mGalleryList.add(new GalleryItem(getResources().getString(R.string.flower2_desc),R.drawable.flower2_gallery));
        else
            mGalleryList.add(new GalleryItem(getResources().getString(R.string.flower2_desc),R.drawable.item_unknown));
        if(slot1>2)
            mGalleryList.add(new GalleryItem(getResources().getString(R.string.flower3_desc),R.drawable.flower3_gallery));
        else
            mGalleryList.add(new GalleryItem(getResources().getString(R.string.flower3_desc),R.drawable.item_unknown));

        mGalleryList.add(new GalleryItem(getResources().getString(R.string.house0_desc),R.drawable.tree_crush_gallery));
        if(slot2>0)
            mGalleryList.add(new GalleryItem(getResources().getString(R.string.house1_desc),R.drawable.tree1_gallery));
        else
            mGalleryList.add(new GalleryItem(getResources().getString(R.string.house1_desc),R.drawable.item_unknown));
        if(slot2>1)
            mGalleryList.add(new GalleryItem(getResources().getString(R.string.house2_desc),R.drawable.tree2_gallery));
        else
            mGalleryList.add(new GalleryItem(getResources().getString(R.string.house2_desc),R.drawable.item_unknown));
        if(slot2>2)
            mGalleryList.add(new GalleryItem(getResources().getString(R.string.house3_desc),R.drawable.tree3_gallery));
        else
            mGalleryList.add(new GalleryItem(getResources().getString(R.string.house3_desc),R.drawable.item_unknown));

        mGalleryList.add(new GalleryItem(getResources().getString(R.string.stone0_desc),R.drawable.stone_crush_gallery));
        if(slot3>0)
            mGalleryList.add(new GalleryItem(getResources().getString(R.string.stone1_desc),R.drawable.stone1_gallery));
        else
            mGalleryList.add(new GalleryItem(getResources().getString(R.string.stone1_desc),R.drawable.item_unknown));
        if(slot3>1)
            mGalleryList.add(new GalleryItem(getResources().getString(R.string.stone2_desc),R.drawable.stone2_gallery));
        else
            mGalleryList.add(new GalleryItem(getResources().getString(R.string.stone2_desc),R.drawable.item_unknown));
        if(slot3>2)
            mGalleryList.add(new GalleryItem(getResources().getString(R.string.stone3_desc),R.drawable.stone3_gallery));
        else
            mGalleryList.add(new GalleryItem(getResources().getString(R.string.stone3_desc),R.drawable.item_unknown));

        mGalleryList.add(new GalleryItem(getResources().getString(R.string.tree0_desc),R.drawable.house_crush_gallery));
        if(slot4>0)
            mGalleryList.add(new GalleryItem(getResources().getString(R.string.tree1_desc),R.drawable.house1_gallery));
        else
            mGalleryList.add(new GalleryItem(getResources().getString(R.string.tree1_desc),R.drawable.item_unknown));
        if(slot4>1)
            mGalleryList.add(new GalleryItem(getResources().getString(R.string.tree2_desc),R.drawable.house2_gallery));
        else
            mGalleryList.add(new GalleryItem(getResources().getString(R.string.tree2_desc),R.drawable.item_unknown));
        if(slot4>2)
            mGalleryList.add(new GalleryItem(getResources().getString(R.string.tree3_desc),R.drawable.house3_gallery));
        else
            mGalleryList.add(new GalleryItem(getResources().getString(R.string.tree2_desc),R.drawable.item_unknown));

        mGalleryList.add(new GalleryItem(getResources().getString(R.string.luwei0_desc),R.drawable.luwei_crush_gallery));
        if(slot5>0)
            mGalleryList.add(new GalleryItem(getResources().getString(R.string.luwei1_desc),R.drawable.luwei1_gallery));
        else
            mGalleryList.add(new GalleryItem(getResources().getString(R.string.luwei1_desc),R.drawable.item_unknown));
        if(slot5>1)
            mGalleryList.add(new GalleryItem(getResources().getString(R.string.luwei2_desc),R.drawable.luwei2_gallery));
        else
            mGalleryList.add(new GalleryItem(getResources().getString(R.string.luwei2_desc),R.drawable.item_unknown));
        if(slot5>2)
            mGalleryList.add(new GalleryItem(getResources().getString(R.string.luwei3_desc),R.drawable.luwei2_gallery));
        else
            mGalleryList.add(new GalleryItem(getResources().getString(R.string.luwei3_desc),R.drawable.item_unknown));

    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;
        public SpacesItemDecoration(int space) {
            this.space = space;
        }
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
            // Add top margin only for the first item to avoid double space between items
//        if(itemPosition == 0)
//            outRect.top = space;
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
