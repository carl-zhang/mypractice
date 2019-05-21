package com.example.my.eventbus;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.Random;

public class RandomService extends Service {
    public RandomService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("karl", "onStartCommand");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    double num;
                    do {
                        Thread.sleep(1000);
                        Random random = new Random();
                        num = random.nextDouble();
                        Log.i("karl", "num=" + num);
                        EventBus.getDefault().post(new MessageEvent(num));
                    } while(num - 0.5 > 0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
