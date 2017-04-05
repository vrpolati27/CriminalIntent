package com.m1m2.criminalintent.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by vinayreddypolati on 2/18/17.
 */

public class CrimeLab extends Object {
    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimes;

    public static CrimeLab getInstance(Context context){
        if(sCrimeLab == null)
            sCrimeLab = new CrimeLab(context);
        return sCrimeLab;
    }
    private CrimeLab(Context context){
        mCrimes = new ArrayList<Crime>();
        Crime crime = null;
        for(int count=0;count<43;count++){
            crime = new Crime();
            crime.setmTitle("crime #"+count);
            crime.setmMistrySolved(count%2 == 0);
            mCrimes.add(crime);
        }
    }

    public List<Crime> getCrimes(){
        return mCrimes;
    }
    public Crime getCrime(UUID id){
        for(Crime crime:mCrimes){
            if(crime.getmId().equals(id))
                return crime;
        }
        return null;
    }

    public void addCrime(Crime crime){
        mCrimes.add(0,crime);
    }
}
