package com.phirum.realapplication;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.phirum.realapplication.fragment.LoginFragment;
import com.phirum.realapplication.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = getSupportFragmentManager();

        manager.beginTransaction().add(R.id.container, new MainFragment(), MainFragment.TAG).commit();
    }
}
