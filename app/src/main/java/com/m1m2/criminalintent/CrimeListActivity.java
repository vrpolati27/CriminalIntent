package com.m1m2.criminalintent;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by vinayreddypolati on 2/18/17.
 */

public class CrimeListActivity extends SingleFragmentActivity {
    private static final String TAG = "CrimeListActivity";
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG," onActivityResult() called");
    }
}
