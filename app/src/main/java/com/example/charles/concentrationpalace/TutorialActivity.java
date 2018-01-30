package com.example.charles.concentrationpalace;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;


/**
 * Tutorial Activity
 */

public class TutorialActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ArrayList<View> viewPagerItems;
    private Button btnStart;
    private ArrayList<ImageView> mDots;//定义一个集合存储三个dot
    private int last_pos = 0;

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
        FrameLayout root = findViewById(R.id.guide);
        View view1 = inflater.inflate(R.layout.tutorial1, root, false);
        View view2 = inflater.inflate(R.layout.tutorial2, root, false);
        View view3 = inflater.inflate(R.layout.tutorial3, root, false);
        View view4 = inflater.inflate(R.layout.tutorial4, root, false);
        btnStart = view4.findViewById(R.id.startBtn);

        viewPager = findViewById(R.id.viewpager);
        viewPagerItems = new ArrayList<>();
        viewPagerItems.add(view1);
        viewPagerItems.add(view2);
        viewPagerItems.add(view3);
        viewPagerItems.add(view4);

        mDots = new ArrayList<>();
        ImageView dot1 = findViewById(R.id.dot_1);
        ImageView dot2 = findViewById(R.id.dot_2);
        ImageView dot3 = findViewById(R.id.dot_3);
        ImageView dot4 = findViewById(R.id.dot_4);
        mDots.add(dot1);
        mDots.add(dot2);
        mDots.add(dot3);
        mDots.add(dot4);
    }

    private void initData() {
        ViewPagerAdapter vpAdapter = new ViewPagerAdapter(viewPagerItems);
        viewPager.setAdapter(vpAdapter);

        mDots.get(0).setImageResource(R.drawable.dot_focused);

        final ViewPager.OnPageChangeListener mInternalPageChangeListener = new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override public void onPageSelected(int position) {//这里是动画的核心
                mDots.get(last_pos).setImageResource(R.drawable.dot_normal);
                mDots.get(position).setImageResource(R.drawable.dot_focused);
                last_pos = position;
            }

            @Override public void onPageScrollStateChanged(int state) {
            }
        };

        viewPager.removeOnPageChangeListener(mInternalPageChangeListener);
        viewPager.addOnPageChangeListener(mInternalPageChangeListener);//绑定上内部实现的PageChangeListener
        mInternalPageChangeListener.onPageSelected(viewPager.getCurrentItem());

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TutorialActivity.this,CoverActivity.class);
                startActivity(intent);
                finish();
                int version = Build.VERSION.SDK_INT;
                if(version > 5 ){
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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
