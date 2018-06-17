package com.phirum.realapplication.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phirum.realapplication.InkPageIndicator;
import com.phirum.realapplication.R;
import com.phirum.realapplication.adapter.MyPagerAdapter;

import butterknife.ButterKnife;

public class MainFragment extends Fragment{

    public static final String TAG = MainFragment.class.getSimpleName();

    private ViewPager mPager;
    //private MyPagerAdapter adapter;

    private MyPagerAdapter adapter;
    private InkPageIndicator mIndicator;

    public MainFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mPager = view.findViewById(R.id.pager);

        mIndicator = view.findViewById(R.id.indicator);

        //adapter = new MyPagerAdapter(this);
        //mPager.setOffscreenPageLimit(3);

        adapter = new MyPagerAdapter(getActivity());
        mPager.setAdapter(adapter);

        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG, "onPageScrolled " + position);
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //Log.d(TAG, "onPageScrollStateChanged " + State);
            }
        });

        mIndicator.setViewPager(mPager);
    }
}
