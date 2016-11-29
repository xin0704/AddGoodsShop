package com.demo.addgoodsshop;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private CustomAdapter mAdapter;
    private ImageView img;
    private ViewGroup anim_layout;
    private  Handler handler;

    private List<Goods> mData = new ArrayList<>();

    private void init(){

        img = (ImageView) findViewById(R.id.shop_img);
        anim_layout = (ViewGroup) findViewById(R.id.layout);


        mListView = (ListView) findViewById(R.id.list_view);
        mAdapter = new CustomAdapter(this,mData);
        mListView.setAdapter(mAdapter);


    }
    private void initData(){
        for (int i = 0; i < 20; i++) {
            Goods goods = new Goods();
            goods.setName("这是第"+i+"项");
            mData.add(goods);
        }
        handler = new Handler();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        initData();
        init();
    }


    public void playAnimation(int[] start_location){
        ImageView img = new ImageView(this);
        img.setImageResource(R.mipmap.ic_storeadd);
        setAnim(img, start_location);
    }


    //创建动画 平移动画直接传递偏移量
    private Animation createAnim(int startX,int startY){
        int[] des = new int[2];
        img.getLocationInWindow(des);

        AnimationSet set = new AnimationSet(false);

        Animation translationX = new TranslateAnimation(0, des[0]-startX, 0, 0);
        //线性插值器 默认就是线性
        translationX.setInterpolator(new LinearInterpolator());
        Animation translationY = new TranslateAnimation(0, 0, 0, des[1]-startY);
        //设置加速插值器
        translationY.setInterpolator(new AccelerateInterpolator());
        Animation alpha = new AlphaAnimation(1,0.5f);
        set.addAnimation(translationX);
        set.addAnimation(translationY);
        set.addAnimation(alpha);
        set.setDuration(500);

        return set;
    }
    //计算动画view在根部局中的坐标 添加到根部局中
    private void addViewToAnimLayout(final ViewGroup vg, final View view,
                                     int[] location) {

        int x = location[0];
        int y = location[1];
        int[] loc = new int[2];
        vg.getLocationInWindow(loc);
        view.setX(x);
        view.setY(y - loc[1]);
        vg.addView(view);
    }
    //设置动画结束移除动画view
    private void setAnim(final View v, int[] start_location) {

        addViewToAnimLayout(anim_layout, v, start_location);
        Animation set = createAnim(start_location[0],start_location[1]);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(final Animation animation) {
                //直接remove可能会因为界面仍在绘制中成而报错
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        anim_layout.removeView(v);
                    }
                },100);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(set);
    }

}
