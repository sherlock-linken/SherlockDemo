package com.tanzy.sherlockdemo.stackpager.transformer;

import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;

/**
 * @author caicai
 * @create 2019/10/29
 * @Describe
 */
public class AddYTransformer implements ViewPager.PageTransformer {

    private int mOffset = 10;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void transformPage(View page, float position) {
        if (position <= 0) {
            page.setTranslationX(-position * page.getWidth());
            //缩放卡片并调整位置
            float scale = (page.getWidth() + mOffset * position) / page.getWidth();
            page.setScaleX(scale);
            page.setScaleY(scale);
           //移动Y轴坐标
            page.setTranslationY(-position * mOffset);
            page.setTranslationZ(position);
        }
    }
}
