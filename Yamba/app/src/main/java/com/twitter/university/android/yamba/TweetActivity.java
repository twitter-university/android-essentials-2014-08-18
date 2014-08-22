package com.twitter.university.android.yamba;

import android.app.Fragment;


public class TweetActivity extends YambaActivity {
    @Override
    protected Fragment getRootFragment() { return new TweetFragment(); }
}
