package com.phirum.realapplication;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.phirum.realapplication.fragment.LoginFragment;

public class LoginActivity extends AppCompatActivity {


    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        manager = getSupportFragmentManager();

        manager.beginTransaction().add(R.id.container, new LoginFragment(), LoginFragment.TAG).commit();
    }
}
