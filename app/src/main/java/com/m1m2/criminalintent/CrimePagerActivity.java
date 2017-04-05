package com.m1m2.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.m1m2.criminalintent.model.Crime;
import com.m1m2.criminalintent.model.CrimeLab;

import java.util.List;
import java.util.UUID;

/**
 * Created by vinayreddypolati on 2/20/17.
 */

public class CrimePagerActivity extends FragmentActivity {
    private ViewPager crimeViewPager;
    private List<Crime> mCrimes;
    private FragmentManager fragmentManager;
    private static final String EXTRA_CRIME_ID = "crimeNumber";
    private static final String TAG = "CrimePagerActivity";

    public static Intent getIntent(Context packageContext,UUID crimeId){
        Intent intent = new Intent(packageContext,CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID,crimeId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        setContentView(R.layout.activity_crime_pager);
        crimeViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_viewpager);
        //Log.d(TAG,crimeViewPager.getOffscreenPageLimit()+"");
        /* set number of neighboring pages are loaded using setOffScreenPageLimit
          Default value is: 1*/
        crimeViewPager.setOffscreenPageLimit(2);
        mCrimes = CrimeLab.getInstance(this).getCrimes();
        fragmentManager = getSupportFragmentManager();
        crimeViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.getInstance(crime.getmId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        int count = 0;
        for(Crime crime:mCrimes){
            if(crime.getmId().equals(crimeId)){
                break;
            }
            count++;
        } crimeViewPager.setCurrentItem(count);
    }


}
