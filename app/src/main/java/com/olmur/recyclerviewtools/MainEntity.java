package com.olmur.recyclerviewtools;

/**
 * Created by olexiimuraviov on 2/8/17.
 */

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
