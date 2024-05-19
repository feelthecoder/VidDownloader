/*
 * *
 *  * Created by Debarun Lahiri on 3/14/23, 5:07 PM
 *  * Copyright (c) 2023 . All rights reserved.
 *  * Last modified 3/14/23, 5:06 PM
 *
 */

package com.feelthecoder.viddownloader.services;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.feelthecoder.viddownloader.R;
import com.feelthecoder.viddownloader.activities.MainActivity;
import com.feelthecoder.viddownloader.activities.RateUsWebView;
import com.feelthecoder.viddownloader.activities.newUi.MainActivityNewUi;
import com.feelthecoder.viddownloader.utils.OreoNotification;
import com.feelthecoder.viddownloader.utils.iUtils;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    static WindowManager windowManager2;
    static WindowManager.LayoutParams params;

    public static int DefaultSound = R.raw.notification;
    public static String DefaultSoundString = "notification";

    public static int DefaultSoundWaku = R.raw.wakuwaku;
    public static String DefaultSoundWakuString = "wakuwaku";

    public static boolean isWaku = false;

    String MyTag = "MessagingService";

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);

        Log.d("mytoken", "Refreshed token: " + s);
        sendRegistrationToServer(s);

    }

    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(MyTag, "From: " + remoteMessage.getFrom());


        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(MyTag, "Message Notification Body: " + remoteMessage.getNotification().getBody());


            if (remoteMessage.getNotification().getTitle().trim().toLowerCase().equals("rate")) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                    sendOreoNotificationWithURL(this, remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getBody());
                } else {
                    sendNotificationWithURL(this, remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getBody());
                }

            } else if (remoteMessage.getNotification().getTitle().trim().toLowerCase().equals("rate2")) {


                System.out.println("Rate2 Working here");


                new Handler(Looper.getMainLooper()).post(() -> {

                    Toast toast = Toast.makeText(MyFirebaseMessagingService.this, "Rate Us", Toast.LENGTH_SHORT);
                    toast.show();
                    showInAppRateDialog(MyFirebaseMessagingService.this);

                });

            } else {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    sendOreoNotification(this, remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
                } else {
                    sendNotification(this, remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
                }
            }


        }


    }


    public static void sendNotification(Context context, String notificationTitle, String notificationBody) {
        try {
            int j = iUtils.getRandomNumber(5);

            Intent intent;
            if (iUtils.isNewUi) {
                intent = new Intent(context, MainActivityNewUi.class);
            } else {
                intent = new Intent(context, MainActivity.class);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, j, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

            Uri defaultSound = Uri.parse("android.resource://" + context.getPackageName() + "/" + ((!isWaku) ? DefaultSoundWaku : DefaultSound));

            //Define sound URI
            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_download_yellow)
                    .setContentTitle(notificationTitle)
                    .setContentText(notificationBody)
                    .setAutoCancel(true)
                    .setSound(defaultSound)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setContentIntent(pendingIntent);
            NotificationManager noti = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            int num = iUtils.getRandomNumber(5);

            noti.notify(num, builder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void sendOreoNotification(Context context, String notificationTitle, String notificationBody) {
        try {
            int j = iUtils.getRandomNumber(5);

            Intent intent;
            if (iUtils.isNewUi) {
                intent = new Intent(context, MainActivityNewUi.class);
            } else {
                intent = new Intent(context, MainActivity.class);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, j, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
            Uri defaultSound = Uri.parse("android.resource://" + context.getPackageName() + "/" + ((!isWaku) ? DefaultSoundWaku : DefaultSound));
            //Define sound URI
            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            OreoNotification oreoNotification = new OreoNotification(context, ((!isWaku) ? DefaultSoundString : DefaultSoundWakuString));
            Notification.Builder builder = oreoNotification.getOreoNotification(notificationTitle, notificationBody, pendingIntent,
                    defaultSound, R.drawable.ic_download_yellow);

            int num = iUtils.getRandomNumber(5);

            oreoNotification.getManager().notify(num, builder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void sendNotificationWithURL(Context context, String notificationTitle, String notificationBody, String link) {
        int j = iUtils.getRandomNumber(5);

        Intent intent = new Intent(context, RateUsWebView.class);
        intent.putExtra("link", link);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, j, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        Uri defaultSound = Uri.parse("android.resource://" + context.getPackageName() + "/" + ((!isWaku) ? DefaultSoundWaku : DefaultSound));
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_download_yellow)
                .setContentTitle(notificationTitle)
                .setContentText(notificationBody)
                .setAutoCancel(true)
                .setSound(defaultSound)
                .setContentIntent(pendingIntent);
        NotificationManager noti = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int num = iUtils.getRandomNumber(5);

        noti.notify(num, builder.build());
    }


    public void sendOreoNotificationWithURL(Context context, String notificationTitle, String notificationBody, String link) {
        int j = iUtils.getRandomNumber(5);

        Intent intent = new Intent(context, RateUsWebView.class);
        intent.putExtra("link", link);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, j, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
        Uri defaultSound = Uri.parse("android.resource://" + context.getPackageName() + "/" + ((!isWaku) ? DefaultSoundWaku : DefaultSound));

        OreoNotification oreoNotification = new OreoNotification(context, ((!isWaku) ? DefaultSoundString : DefaultSoundWakuString));
        Notification.Builder builder = oreoNotification.getOreoNotification(notificationTitle, notificationBody, pendingIntent,
                defaultSound, R.drawable.ic_download_yellow);

        int num = iUtils.getRandomNumber(5);

        oreoNotification.getManager().notify(num, builder.build());

    }


    public void showInAppRateDialog(Context Mcontext) {
        try {
            final Dialog dialog = new Dialog(Mcontext);

            windowManager2 = (WindowManager) Mcontext.getSystemService(WINDOW_SERVICE);

            int weidthParams = 0, heightParams = 0;

            try {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                ((Activity) Mcontext).getWindowManager()
                        .getDefaultDisplay()
                        .getMetrics(displayMetrics);

                int height = displayMetrics.heightPixels;
                int width = displayMetrics.widthPixels;

                heightParams = height / 2;

                weidthParams = width / 2;

            } catch (Exception e) {
                weidthParams = WindowManager.LayoutParams.WRAP_CONTENT;
                heightParams = WindowManager.LayoutParams.WRAP_CONTENT;

            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                params = new WindowManager.LayoutParams(
                        weidthParams,
                        heightParams,
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                        PixelFormat.TRANSLUCENT);

                params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
                params.x = 0;
                params.y = 100;
            } else {
                params = new WindowManager.LayoutParams(
                        weidthParams,
                        WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.TYPE_PHONE,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                        PixelFormat.TRANSLUCENT);

                params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
                params.x = 0;
                params.y = 100;
            }


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
            } else {
                dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_PHONE);
            }
            dialog.getWindow().setAttributes(params);


            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.custom_dialog_rateus);
            dialog.findViewById(R.id.notnow_dialog).setOnClickListener(view -> dialog.dismiss());
            dialog.findViewById(R.id.rateus_dialog).setOnClickListener(view -> {


                try {
                    Mcontext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + Mcontext.getPackageName())).setFlags(FLAG_ACTIVITY_NEW_TASK));
                } catch (android.content.ActivityNotFoundException e) {

                    Mcontext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + Mcontext.getPackageName())).setFlags(FLAG_ACTIVITY_NEW_TASK));
                }

                dialog.dismiss();
            });


            dialog.show();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


}
