package com.twitter.university.android.yamba;

import android.app.Fragment;


public class TimelineDetailActivity extends YambaActivity {
    @Override
    protected Fragment getRootFragment() {
        return TimelineDetailFragment.newInstance(getIntent().getExtras());
    }
}
