package za.co.makuru.makurufx.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import javax.inject.Inject;

import za.co.makuru.makurufx.MakuruFxApp;
import za.co.makuru.makurufx.data.DataManager;
import za.co.makuru.makurufx.di.component.DaggerServiceComponent;
import za.co.makuru.makurufx.di.component.ServiceComponent;
import za.co.makuru.makurufx.di.module.ServiceModule;
import za.co.makuru.makurufx.ui.base.BaseView;

public class SyncService extends Service implements BaseView {

    private static final String TAG = "SyncService";

    private ServiceComponent mServiceComponent;

    @Inject
    DataManager mDataManager;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SyncService.class);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, SyncService.class);
        context.startService(starter);
    }

    public static void stop(Context context) {
        context.stopService(new Intent(context, SyncService.class));
    }

    @Override
    public void onCreate() {
        super.onCreate();
         mServiceComponent = DaggerServiceComponent.builder()
                .serviceModule(new ServiceModule(this))
                .applicationComponent(((MakuruFxApp) getApplication()).getComponent())
                .build();
        mServiceComponent.inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Log.d(TAG, "SyncService started");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "SyncService stopped");
        super.onDestroy();
    }

    public ServiceComponent getServiceComponent(){
        return mServiceComponent;
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void hideKeyboard() {

    }

    @Override
    public void showMessage(String message) {

    }
}