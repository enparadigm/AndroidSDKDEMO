package com.sharpsell.demoapplication;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.enparadigm.sharpsell.sdk.ResultListener;
import com.enparadigm.sharpsell.sdk.Sharpsell;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String data = new JSONObject(remoteMessage.getData()).toString();
        Sharpsell.INSTANCE.isSharpsellNotification(
                getApplicationContext(),
                data,
                new ResultListener<Boolean>() {
                    @Override
                    public void onResult(Boolean result) {
                        if (result) {
                            Sharpsell.INSTANCE.showNotification(getApplicationContext(), data);
                        } else {
                            // show your own notification
                        }
                    }
                }
        );
    }
}
