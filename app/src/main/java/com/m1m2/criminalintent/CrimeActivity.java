package com.m1m2.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {
    private static final String TAG = "CrimeActivity";
    private static final String EXTRA_CRIME_ID = "crimeNumber";

    public static Intent getIntent(Context applicationContext,UUID crimeNumber){
        Intent intent = new Intent(applicationContext,CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID,crimeNumber);
        return  intent;
    }
    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG," onStart() invoked");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG," onResume() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG," onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG," onDestroy() called");
    }

    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        return CrimeFragment.getInstance(crimeId);
    }
}
