package com.phirum.realapplication;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

    @BindView(R.id.edt_phone)
    TextInputEditText mEdtPhone;

    @BindView(R.id.btn_register)
    Button mBtnRegister;

    @BindView(R.id.edt_username)
    TextInputEditText mEdtUsername;

    @BindView(R.id.edt_pass)
    TextInputEditText mEdtPass;

    private UserDbRepo mUserDbRepo;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        ButterKnife.bind(this);
        manager = getSupportFragmentManager();
        mUserDbRepo = new UserDbRepo(this);

        mEdtPhone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (EditorInfo.IME_ACTION_GO == actionId || EditorInfo.IME_NULL == actionId) {
                    mBtnRegister.performClick();
                    return true;
                }
                return false;
            }
        });
    }

    @OnClick({R.id.img_profile, R.id.btn_register})
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                int random = new Random().nextInt(100);
                Datum datum = new Datum();
                datum.withFirstName(mEdtUsername.getText().toString());
                datum.withPassword(mEdtPass.getText().toString());
                if (mUserDbRepo.insertDataToTable(datum) > 0) {
                    manager.beginTransaction().replace(R.id.container, new SqliteFragment(), SqliteFragment.TAG).commit();
                    mContainer.setVisibility(View.VISIBLE);
                    //successful in insert data to table
                    // <= 0 -> fail.
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    if (inputMethodManager != null)
                        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

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