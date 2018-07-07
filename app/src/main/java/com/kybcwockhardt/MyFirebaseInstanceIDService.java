package com.kybcwockhardt;

import android.util.Log;

import com.kybcwockhardt.application.AppConstants;
import com.kybcwockhardt.application.MyApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static com.kybcwockhardt.application.AppConstants.TAG;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        MyApp.setSharedPrefString(AppConstants.DEVICE_TOKEN, refreshedToken);
    }
}
