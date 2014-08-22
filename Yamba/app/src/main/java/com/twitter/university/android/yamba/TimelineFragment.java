/*
 * Copyright (c) 2014 Twitter Inc.
 */
package com.twitter.university.android.yamba;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.twitter.university.android.yamba.service.YambaContract;


/**
 * A placeholder fragment containing a simple view.
 */
public class TimelineFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "TIMELINE";

    private static final int TIMELINE_LOADER = 42;

    private static final String[] FROM = new String[] {
        YambaContract.Timeline.Columns.HANDLE,
        YambaContract.Timeline.Columns.TIMESTAMP,
        YambaContract.Timeline.Columns.TWEET
    };

    private static final int[] TO = new int[] {
        R.id.timeline_handle,
        R.id.timeline_timestamp,
        R.id.timeline_tweet
    };

    private static class TimelineBinder implements SimpleCursorAdapter.ViewBinder {
        @Override
        public boolean setViewValue(View v, Cursor c, int col) {
            if (v.getId() != R.id.timeline_timestamp) { return false; }

            CharSequence s = "long ago";
            long t = c.getLong(col);
            if (0 < t) { s = DateUtils.getRelativeTimeSpanString(t); }
            ((TextView) v).setText(s);
            return true;
        }
    }


    private SimpleCursorAdapter adapter;

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle args) {
        return new CursorLoader(
            getActivity(),
            YambaContract.Timeline.URI,
            null,
            null,
            null,
            YambaContract.Timeline.Columns.TIMESTAMP + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        Log.d(TAG, "cursor: " + cursor.getCount());
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        adapter.swapCursor(null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int p, long id) {
        Cursor c = (Cursor) l.getItemAtPosition(p);

        Intent i = TimelineDetailFragment.marshallDetails(
            getActivity(),
            c.getLong(c.getColumnIndex(YambaContract.Timeline.Columns.TIMESTAMP)),
            c.getString(c.getColumnIndex(YambaContract.Timeline.Columns.HANDLE)),
            c.getString(c.getColumnIndex(YambaContract.Timeline.Columns.TWEET)));

        startActivity(i);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup root, Bundle state) {
        View rootView = super.onCreateView(inflater, root, state);

        adapter = new SimpleCursorAdapter(
            getActivity(),
            R.layout.row_timeline,
            null,
            FROM,
            TO,
            0);
        adapter.setViewBinder(new TimelineBinder());
        setListAdapter(adapter);

        getLoaderManager().initLoader(TIMELINE_LOADER, null, this);

        return rootView;
    }
}
