package za.co.makuru.makurufx.di.component;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import za.co.makuru.makurufx.MakuruFxApp;
import za.co.makuru.makurufx.data.DataManager;
import za.co.makuru.makurufx.di.ApplicationContext;
import za.co.makuru.makurufx.di.module.ApplicationModule;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(MakuruFxApp app);

    @ApplicationContext
    Context context();

    DataManager getDataManager();
}
