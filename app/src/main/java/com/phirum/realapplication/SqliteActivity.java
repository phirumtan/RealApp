package com.phirum.realapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.phirum.realapplication.db.UserDbRepo;
import com.phirum.realapplication.fragment.SqliteFragment;
import com.phirum.realapplication.pojo.Datum;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SqliteActivity extends AppCompatActivity {
    @BindView(R.id.img_profile)
    ImageView mImgProfile;
    @BindView(R.id.container)
    View mContainer;
    private UserDbRepo mUserDbRepo;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        ButterKnife.bind(this);
        manager = getSupportFragmentManager();
        mUserDbRepo = new UserDbRepo(this);
    }

    @OnClick({R.id.img_profile, R.id.btn_register})
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:

                int random = new Random().nextInt(100);
                Datum datum = new Datum();
                datum.withFirstName("phirum_" + random);
                datum.withPassword("123_" + random);
                if (mUserDbRepo.insertDataToTable(datum) > 0) {
                    manager.beginTransaction().replace(R.id.container, new SqliteFragment(), SqliteFragment.TAG).commit();
                    mContainer.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "insert success", Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "insert fail", Toast.LENGTH_SHORT).show();
                    break;
                }
        }
    }

    @Override
    public void onBackPressed() {
        Fragment f = manager.findFragmentByTag(SqliteFragment.TAG);
        if (f != null) {
            manager.beginTransaction().remove(f).commit();
            mContainer.setVisibility(View.GONE);
        } else
            super.onBackPressed();
    }
}