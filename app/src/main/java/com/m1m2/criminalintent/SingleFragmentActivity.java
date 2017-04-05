package com.m1m2.criminalintent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by vinayreddypolati on 2/18/17.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private Fragment fragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        fragmentManager = getSupportFragmentManager();
        fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if(fragment == null){
            fragment = createFragment();
            fragmentManager.beginTransaction().add(R.id.fragment_container,fragment)
                    .commit();
        }
    }

    protected abstract Fragment createFragment();
}
