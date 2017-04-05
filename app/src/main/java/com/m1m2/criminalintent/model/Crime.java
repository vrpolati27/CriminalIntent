package com.m1m2.criminalintent.model;

import java.util.Date;
import java.util.UUID;

/**
 * Created by vinayreddypolati on 2/17/17.
 */

public class Crime extends Object {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mMistrySolved;

    public Date getmDate() {
        return mDate;
    }

    public boolean ismMistrySolved() {
        return mMistrySolved;
    }

    public Crime(){
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public void setmMistrySolved(boolean mMistrySolved) {
        this.mMistrySolved = mMistrySolved;
    }

    public UUID getmId() {
        return mId;
    }
}
