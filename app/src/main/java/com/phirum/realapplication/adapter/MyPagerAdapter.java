package com.phirum.realapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.phirum.realapplication.R;

public class MyPagerAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    private int[] resValues = new int[]{
            R.mipmap.ic_launcher, android.R.mipmap.sym_def_app_icon, R.mipmap.ic_splashscreen
    };

    public MyPagerAdapter(Context context) {
        super();
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public MyPagerAdapter(Context context, String st) {
        super();
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return resValues.length;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        ImageView itemView = (ImageView) mInflater.inflate(R.layout.layout_pager_item, container, false);
        itemView.setImageDrawable(mContext.getDrawable(resValues[position]));
        container.addView(itemView);
        return itemView;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (View) object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "Title " + position;
    }
}
