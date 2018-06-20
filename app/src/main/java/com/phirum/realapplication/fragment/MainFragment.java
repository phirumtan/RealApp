package com.phirum.realapplication.fragment;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.phirum.realapplication.InkPageIndicator;
import com.phirum.realapplication.R;
import com.phirum.realapplication.adapter.MyPagerAdapter;
import com.phirum.realapplication.pojo.UserItem;
import com.phirum.realapplication.webservice.APIClient;
import com.phirum.realapplication.webservice.APIUserInterface;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends Fragment {

    public static final String TAG = MainFragment.class.getSimpleName();

    private ViewPager mPager;
    //private MyPagerAdapter adapter;

    private MyPagerAdapter adapter;
    private InkPageIndicator mIndicator;

    APIUserInterface mUserInterface;

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
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        mPager = view.findViewById(R.id.pager);

        mUserInterface = APIClient.getClient().create(APIUserInterface.class);

        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mPager.getLayoutParams();
        params.height = (int) (size.x / 1.3);
        mPager.setLayoutParams(params);

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

        Call<UserItem> call = mUserInterface.doGetUserList("1");
        call.enqueue(new Callback<UserItem>() {
            @Override
            public void onResponse(Call<UserItem> call, Response<UserItem> response) {
                if (response.isSuccessful()) {
                    UserItem userItem = response.body();
                    String avatar = userItem.data.get(0).avatar;
                    //JSONObject jsonObject = new JSONObject(userItem.toString());
                    Log.d("phirum", "Avatar " + avatar);
                    Log.d("phirum", "tota; " + userItem.total);

                }
            }

            @Override
            public void onFailure(Call<UserItem> call, Throwable t) {
                Toast.makeText(view.getContext(), "Error hey", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
