/*
 * Copyright (c) 2014 Twitter Inc.
 */
package com.twitter.university.android.yamba;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;


public class YambaService extends IntentService {
    private static final String TAG = "SVC";

    private static final int OP_STATUS = 65;
    private static final String PARAM_TWEET = "YambaService.TWEET";

    public static void post(Activity ctxt, String tweet) {
        Intent i = new Intent(ctxt, YambaService.class);
        i.putExtra(PARAM_TWEET, tweet);
        ctxt.startService(i);
    }

    private final class StatusHdlr extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case OP_STATUS:
                    postComplete(msg.arg1);
            }
        }
    }


    private StatusHdlr hdlr;

    public YambaService() { super(TAG); }

    @Override
    public void onCreate() {
        super.onCreate();
        hdlr = new StatusHdlr();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String tweet = intent.getStringExtra(PARAM_TWEET);

        int msg = R.string.failure;

        try { Thread.sleep(1000 * 20); }
        catch (InterruptedException e) {}

        hdlr.obtainMessage(OP_STATUS, msg, -1, null).sendToTarget();
    }

    void postComplete(int msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
