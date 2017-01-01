package com.yuki.android.tanngohelper;

import java.util.Date;
import java.util.UUID;

/**
 * Created by fxf on 2016/10/24.
 */
public class Tanngo {
    private UUID mId;
    private String mWord;
    private String mMean;
    private Date mDate;
    private boolean mSolved;

    public Tanngo() {
        this(UUID.randomUUID());
    }

    public Tanngo(UUID id) {
        mId = id;
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getWord() {
        return mWord;
    }

    public void setWord(String word) {
        mWord = word;
    }

    public String getMean() {
        return mMean;
    }

    public void setMean(String mean) {
        mMean = mean;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}
