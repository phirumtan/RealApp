package com.phirum.realapplication.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.phirum.realapplication.InkPageIndicator;
import com.phirum.realapplication.R;
import com.phirum.realapplication.adapter.MyListAdapter;
import com.phirum.realapplication.adapter.MyPagerAdapter;
import com.phirum.realapplication.pojo.Datum;
import com.phirum.realapplication.pojo.UserItem;
import com.phirum.realapplication.webservice.APIClient;
import com.phirum.realapplication.webservice.APIUserInterface;

import butterknife.BindView;
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

    @BindView(R.id.rcv)
    RecyclerView mRcvList;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mRefresh;

    private MyListAdapter myListAdapter;

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

        Fresco.initialize(view.getContext());
        mPager = view.findViewById(R.id.pager);

        mUserInterface = APIClient.getClient().create(APIUserInterface.class);

        myListAdapter = new MyListAdapter(view.getContext());
        mRcvList.setAdapter(myListAdapter);

        /*WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mPager.getLayoutParams();
        params.height = (int) (size.x / 1.3);
        mPager.setLayoutParams(params);*/

        //mIndicator = view.findViewById(R.id.indicator);

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

        //mIndicator.setViewPager(mPager);

        getUserData(view.getContext(), false);


        mRefresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent), getResources().getColor(R.color.colorPrimary));

        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUserData(view.getContext(), true);


            }
        });

    }

    private void getUserData(final Context context, final boolean isRefresh) {
        Call<UserItem> call = mUserInterface.doGetUserList("1");
        call.enqueue(new Callback<UserItem>() {
            @Override
            public void onResponse(Call<UserItem> call, Response<UserItem> response) {
                if (response.isSuccessful()) {
                    UserItem userItem = response.body();
                    if (isRefresh) {
                        myListAdapter.clearItem();
                        myListAdapter.notifyDataSetChanged();
                        Datum testInsert = new Datum();
                        testInsert.id = 0;
                        testInsert.firstName = "phirum";
                        testInsert.avatar = "https://scontent.fpnh8-2.fna.fbcdn.net/v/t1.0-9/27545098_1557130707667769_3036406450539851267_n.jpg?_nc_cat=0&_nc_eui2=AeEkxwRb1HqfXF_O9n8SfdG1levFW6Lsyv124R-FH2ctg1-Gbz0faLOVCSy-SJUag72MkPSPTl-kBYjC1TR37u7thSAZKdNgMvRuUiW6O5cIFg&oh=e4204a4545c432c11359f060a76c3a5e&oe=5BB5077B";
                        userItem.data.add(0, testInsert);
                        Toast.makeText(context, "insert new item", Toast.LENGTH_SHORT).show();
                    }
                    if (userItem != null) {
                        for (Datum ob : userItem.data) {
                            myListAdapter.insertItem(ob);
                        }
                        myListAdapter.notifyDataSetChanged();
                    }

                    if (isRefresh) {
                        mRcvList.smoothScrollToPosition(0);
                        mRefresh.setRefreshing(false);
                    }

                }
            }

            @Override
            public void onFailure(Call<UserItem> call, Throwable t) {
                Toast.makeText(context, "Error hey", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
