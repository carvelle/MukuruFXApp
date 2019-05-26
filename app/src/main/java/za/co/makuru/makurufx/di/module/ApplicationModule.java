package za.co.makuru.makurufx.di.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import za.co.makuru.makurufx.constants.Constant;
import za.co.makuru.makurufx.data.AppDataManager;
import za.co.makuru.makurufx.data.DataManager;
import za.co.makuru.makurufx.data.db.AppDatabaseHelper;
import za.co.makuru.makurufx.data.db.DatabaseHelper;
import za.co.makuru.makurufx.data.network.httpclient.OkHttpClientBuilderFactory;
import za.co.makuru.makurufx.data.network.httpclient.OkHttpClientFactory;
import za.co.makuru.makurufx.di.ApplicationContext;
import za.co.makuru.makurufx.di.DatabaseInfo;

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return Constant.DB_NAME;
    }


    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    DatabaseHelper provideDbHelper(AppDatabaseHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    OkHttpClientFactory provideOkHttpClientBuilderFactory(OkHttpClientBuilderFactory okHttpClientBuilderFactory)
    {
        return okHttpClientBuilderFactory;
    }
}
