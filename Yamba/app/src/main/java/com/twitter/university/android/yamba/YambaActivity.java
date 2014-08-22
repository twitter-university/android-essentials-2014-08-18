/*
 * Copyright (c) 2014 Twitter Inc.
 */
package com.twitter.university.android.yamba;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public abstract class YambaActivity extends Activity {
    private static final String TAG = "ACT";
    public static final String FRAGMENT_ROOT = "YambaActivity.ROOT";

    private final int layout;

    public YambaActivity() { this(R.layout.activity_yamba); }

    public YambaActivity(int layout) { this.layout = layout; }

    protected abstract Fragment getRootFragment();


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.yamba, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_tweet:
                nextPage(TweetActivity.class);
                break;
            case R.id.action_timeline:
                nextPage(TimelineActivity.class);
                break;
            case R.id.action_about:
                Toast.makeText(this, R.string.about, Toast.LENGTH_LONG).show();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(layout);

        FragmentManager mgr = getFragmentManager();

        if (null != mgr.findFragmentByTag(FRAGMENT_ROOT)) { return; }

        mgr.beginTransaction()
            .add(R.id.fragment_root, getRootFragment(), FRAGMENT_ROOT)
            .commit();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    private void nextPage(Class<?> page) {
        Intent i = new Intent(this, page);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(i);
    }
}
