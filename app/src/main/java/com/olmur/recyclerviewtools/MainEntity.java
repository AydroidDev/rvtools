package com.olmur.recyclerviewtools;

public class MainEntity {

    private String mTitle;

    private boolean mSwipeFlag;

    public MainEntity(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public boolean isSwipeFlag() {
        return mSwipeFlag;
    }

    public void changeSwipeFlag() {
        mSwipeFlag = !mSwipeFlag;
    }
}
