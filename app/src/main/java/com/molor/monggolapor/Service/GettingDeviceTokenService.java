package com.molor.monggolapor.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

public class GettingDeviceTokenService extends FirebaseInstanceIdService {

    public static final String TAG = "mFirebaseIIDService";
    public static final String SUBCRIBE_TO = "userABC";

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();

        FirebaseMessaging.getInstance().subscribeToTopic(SUBCRIBE_TO);
        Log.i(TAG, "onTokenRefresh completed with token : " + token);
    }
}