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

import com.twitter.university.android.yamba.service.YambaContract;


public class YambaServiceHelper {
    private static final String TAG = "SVC";

    private static final int OP_STATUS = 65;
    private static final String PARAM_TWEET = "YambaService.TWEET";

    public static void post(Activity ctxt, String tweet) {
        Intent i = new Intent(YambaContract.Service.ACTION_EXECUTE);
        i.putExtra(YambaContract.Service.PARAM_OP, YambaContract.Service.OP_POST);
        i.putExtra(YambaContract.Service.PARAM_TWEET, tweet);
        ctxt.startService(i);
    }
}
