package com.llw.music.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.llw.music.R;

public class MusicService extends Service {

    private static final String TAG = "MusicService";

    private static NotificationManager manager;

    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
//        super.onBind(intent);
        return new MusicBinder();
    }

    @Override
    public void onCreate(){
        super.onCreate();

        showNotification();

        Log.d(TAG,"onCreate");
    }
    /**
     * 显示通知
     */
    private void showNotification() {
        String channelId = "play_control";
        String channelName = "播放控制";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        createNotificationChannel(channelId, channelName, importance);
        RemoteViews remoteViews = new RemoteViews(this.getPackageName(), R.layout.notification);

        Notification notification = new NotificationCompat.Builder(this, "play_control")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.icon_big_logo)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_big_logo))
                .setCustomContentView(remoteViews)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(false)
                .setOnlyAlertOnce(true)
                .setOngoing(true)
                .build();
        //发送通知
        manager.notify(1, notification);
    }
    /**
     * 创建通知渠道
     *
     * @param channelId   渠道id
     * @param channelName 渠道名称
     * @param importance  渠道重要性
     */
    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        channel.enableLights(false);
        channel.enableVibration(false);
        channel.setVibrationPattern(new long[]{0});
        channel.setSound(null, null);
        //获取系统通知服务
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
    }

}