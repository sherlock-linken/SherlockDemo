package com.tanzy.sherlockdemo.stackpager.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.List;

import com.tanzy.sherlockdemo.R;
import com.tanzy.sherlockdemo.stackpager.viewpager.BasePagerAdapter;

public class MyPagerAdapter extends BasePagerAdapter<Integer, MyPagerAdapter.MyHolder> {

    private String TAG = "MyPagerAdapter";
    private HashMap<Integer, MyHolder> allHolder;//缓存所有的holder

    public MyPagerAdapter(List<Integer> dataList, Context mContext) {
        super(dataList, mContext);
        allHolder = new HashMap<>();
        createAllItemView(dataList);
    }

    //此刷新有些Viewpager 效果会出现问题  当数据有增减，重新new adapter就好
    public void refreshAll(List<Integer> colors) {
        createAllItemView(colors);
        notifyDataSetChanged();
    }


    //item数量不变刷新单个item内View
    public void referOnItem(List<Integer> newList, int position) {
        MyHolder holder = allHolder.get(position);
        if (holder != null) {
            onBindView(holder, newList.get(position), position);
        }
    }

    /**
     * 缓存所有的holder是为了，使ViewPager加载大图时滑动不卡顿，，适用于少量大图模式
     * 正常情况下，，createHolder每次去创建就好，，destroyItem回收
     * @param dataList
     */
    private void createAllItemView(List<Integer> dataList) {
        allHolder.clear();
        for (int i = 0; i < dataList.size(); i++) {
            MyHolder itemH = createHolder(mContext, i);
            allHolder.put(i, itemH);
        }
    }

    @NonNull
    @Override
    protected MyHolder createHolder(Context context, int position) {
        MyHolder holder = allHolder.get(position);
        if (holder == null) {
            holder = new MyHolder(View.inflate(context, R.layout.item_vp, null));
            allHolder.put(position, holder);
        }
        return holder;
    }

    @Override
    protected void onBindView(MyHolder holder, Integer data, int position) {
        holder.tvLeft.setText(String.valueOf(position));
        holder.tvRight.setText(String.valueOf(position));
        holder.itemView.setBackgroundResource(data);
    }

    static class MyHolder extends BasePagerAdapter.BaseHolder {

        private final TextView tvLeft;
        private final TextView tvRight;

        public MyHolder(View itemView) {
            super(itemView);
            tvLeft = (TextView) itemView.findViewById(R.id.tv_left);
            tvRight = (TextView) itemView.findViewById(R.id.tv_right);
        }
    }
}
