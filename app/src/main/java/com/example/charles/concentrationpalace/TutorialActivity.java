package com.example.charles.concentrationpalace;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Tutorial Activity
 */

public class TutorialActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ArrayList<View> viewPagerItems;
    private Button btnStart;

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

        setContentView(R.layout.tutorial);
        initView();
        initData();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view1 = inflater.inflate(R.layout.tutorial1, null);
        View view2 = inflater.inflate(R.layout.tutorial2, null);
        View view3 = inflater.inflate(R.layout.tutorial3, null);
        View view4 = inflater.inflate(R.layout.tutorial4, null);
        btnStart = view4.findViewById(R.id.startBtn);

        viewPager = findViewById(R.id.viewpager);
        viewPagerItems = new ArrayList<>();
        viewPagerItems.add(view1);
        viewPagerItems.add(view2);
        viewPagerItems.add(view3);
        viewPagerItems.add(view4);
    }

    private void initData() {
        ViewPagerAdapter vpAdapter = new ViewPagerAdapter(viewPagerItems);
        viewPager.setAdapter(vpAdapter);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TutorialActivity.this,MyPalaceActivity.class);
                startActivity(intent);
                finish();
                int version = Build.VERSION.SDK_INT;
                if(version > 5 ){
                    overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast);
                }
            }
        });
    }

    class ViewPagerAdapter extends PagerAdapter {

        private ArrayList<View> mViewList;
        ViewPagerAdapter (ArrayList<View> views){
            this.mViewList = views;
        }

        @Override
        public int getCount() {
            if (mViewList != null) {
                return mViewList.size();
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object arg1) {
            return (view == arg1);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {//必须实现，实例化
            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {//必须实现，销毁
            container.removeView(mViewList.get(position));
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
