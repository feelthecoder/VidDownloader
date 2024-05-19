/*
 * *
 *  * Created by Debarun Lahiri on 3/3/23, 4:58 PM
 *  * Copyright (c) 2023 . All rights reserved.
 *  * Last modified 3/3/23, 4:41 PM
 *
 */

package com.feelthecoder.viddownloader.utils;


import android.util.Log;

import androidx.annotation.NonNull;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.feelthecoder.viddownloader.activities.SplashScreen;

import com.onesignal.notifications.IDisplayableMutableNotification;
import com.onesignal.notifications.INotificationReceivedEvent;
import com.onesignal.notifications.INotificationServiceExtension;

import org.json.JSONObject;

@SuppressWarnings("unused")
public class NotificationServiceExtension implements INotificationServiceExtension {

    @Override
    public void onNotificationReceived(@NonNull INotificationReceivedEvent iNotificationReceivedEvent) {


        IDisplayableMutableNotification notification = iNotificationReceivedEvent.getNotification();

        // Example of modifying the notification's accent color
//        OSMutableNotification mutableNotification = notification.mutableCopy();

        try {
            JSONObject data = notification.getAdditionalData();
            Log.i("OneSignalExample", "Received Notification Data: " + data);

            if (data != null && data.has("status") && data.getBoolean("status")) {

                System.out.println("mydatais bacground  = " + data);

                AndroidNetworking.get(Constants.adminApiUrl + "/all")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    System.out.println("Apidata datais  " + response.toString());
                                    JSONObject jsonObject = new JSONObject(response.toString());

                                    if (jsonObject.getBoolean("status")) {
                                        new SharedPrefsMainApp(iNotificationReceivedEvent.getContext()).setPREFERENCE_adminpanelapidata(jsonObject.toString());
                                        SplashScreen.checkExistingAppDataFromAdminPanelAndSetData(iNotificationReceivedEvent.getContext());
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    System.out.println("Apidata error " + e.getMessage());
                                }

                            }

                            @Override
                            public void onError(ANError error) {
                                error.printStackTrace();
                                System.out.println("Apidata error " + error.getMessage());

                            }
                        });

//                iNotificationReceivedEvent.complete(null);
//                return;
            }


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Apidata error " + e.getMessage());
        }


        // this is an example of how to modify the notification by changing the background color to blue
//        notification.setExtender(builder -> builder.setColor(0xFF0000FF));

    }
}
