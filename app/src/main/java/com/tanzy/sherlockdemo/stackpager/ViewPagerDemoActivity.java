package com.tanzy.sherlockdemo.stackpager;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tanzy.sherlockdemo.R;
import com.tanzy.sherlockdemo.stackpager.adapter.MyPagerAdapter;
import com.tanzy.sherlockdemo.stackpager.transformer.AddYTransformer;
import com.tanzy.sherlockdemo.stackpager.transformer.DoubleAddPageTransformer;
import com.tanzy.sherlockdemo.stackpager.transformer.ScaleTransformer;
import com.tanzy.sherlockdemo.stackpager.transformer.SingleAddPageTransformer;
import com.tanzy.sherlockdemo.stackpager.viewpager.ViewPagerSkip;

public class ViewPagerDemoActivity extends AppCompatActivity {

    private Integer[] colors = new Integer[]{
            R.color.tomato,
            R.color.aliceblue,
            R.color.cadetblue,
            R.color.gold,
            R.color.pink,
            R.color.lightsalmon,
            R.color.hotpink,
            R.color.deeppink,
            R.color.magenta,
            R.color.salmon,
            R.color.sandybrown,
            R.color.khaki,
            R.color.crimson,
            R.color.goldenrod,
            R.color.greenyellow,
    };
    private ViewPagerSkip viewPager01, viewPager02, viewPager03, viewPager04,viewPager05;
    private List<Integer> colorList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewa_pager_demo);
        initView();
        initData();
    }

    public void initView() {
        colorList = new ArrayList<>();
        colorList.addAll(Arrays.asList(colors));
        viewPager01 = findViewById(R.id.viewPager01);
        viewPager02 = findViewById(R.id.viewPager02);
        viewPager03 = findViewById(R.id.viewPager03);
        viewPager04 = findViewById(R.id.viewPager04);
        viewPager05 = findViewById(R.id.viewPager05);
        viewPager01.setScrollDuration(2000);
        viewPager02.setScrollDuration(2000);
        viewPager03.setScrollDuration(2000);
        viewPager04.setScrollDuration(2000);
        viewPager05.setScrollDuration(2000);

        initPager01();    //显示几个item可以调整 ViewPager，父布局的长度遮盖不想显示的item
        initPager02();    //显示item的数量，由offscreenPageLimit item缓存数量控制
        initPager03();    //显示item的数量，由offscreenPageLimit item缓存数量控制
        initPager04();
        initPager05();
    }

    private void initPager01() {
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(colorList, this);
        viewPager01.setOffscreenPageLimit(3);
        viewPager01.setPageMargin(20);
        viewPager01.setPageTransformer(false, new ScaleTransformer());
        viewPager01.setAdapter(pagerAdapter);
    }



    private void initPager02() {
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(colorList, this);
        viewPager02.setOffscreenPageLimit(2);
        viewPager02.setScrollDuration(1000);
        viewPager02.setPageMargin(10);
        viewPager02.setPageTransformer(true, new SingleAddPageTransformer(5, 30, true));
        viewPager02.setAdapter(pagerAdapter);
    }








    private void initPager03() {
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(colorList, this);
        viewPager03.setOffscreenPageLimit(5);
        viewPager03.setPageMargin(20);
        viewPager03.setPageTransformer(false, new SingleAddPageTransformer(1, 60, false));
        viewPager03.setAdapter(pagerAdapter);
        viewPager03.setCurrentItem(colorList.size() - 1);
    }


    private void initPager04() {
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(colorList, this);
        viewPager04.setOffscreenPageLimit(5);
        viewPager04.setPageMargin(20);
        viewPager04.setPageTransformer(true, new DoubleAddPageTransformer(5, 60));
        viewPager04.setAdapter(pagerAdapter);
    }


    private void initPager05() {
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(colorList, this);
        viewPager05.setOffscreenPageLimit(5);
        viewPager05.setPageMargin(20);
        viewPager05.setPageTransformer(false, new AddYTransformer());
        viewPager05.setAdapter(pagerAdapter);
    }

    public void initData() {

    }
}
