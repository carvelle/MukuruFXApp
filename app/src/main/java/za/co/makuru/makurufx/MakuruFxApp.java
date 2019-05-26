package za.co.makuru.makurufx;

import android.app.Application;

import javax.inject.Inject;

import za.co.makuru.makurufx.data.DataManager;
import za.co.makuru.makurufx.di.component.ApplicationComponent;
import za.co.makuru.makurufx.di.component.DaggerApplicationComponent;
import za.co.makuru.makurufx.di.module.ApplicationModule;

public class MakuruFxApp extends Application {
    @Inject
    DataManager mDataManager;

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        mApplicationComponent.inject(this);

    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

}
