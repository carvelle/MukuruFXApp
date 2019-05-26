package za.co.makuru.makurufx.di.module;

import android.app.Service;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import za.co.makuru.makurufx.ui.dashboard.UpdateCurrenciesPresenter;
import za.co.makuru.makurufx.ui.dashboard.UpdateCurrency;
import za.co.makuru.makurufx.ui.dashboard.UpdateCurrencyMainPresenter;
import za.co.makuru.makurufx.utils.rx.SchedulerProvider;

@Module
public class ServiceModule {

    private final Service mService;

    public ServiceModule(Service service) {
        mService = service;
    }

    @Provides
    UpdateCurrenciesPresenter<UpdateCurrency> provideConvertCurrencyPresenter(
            UpdateCurrencyMainPresenter<UpdateCurrency> presenter) {
        return presenter;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new SchedulerProvider();
    }

}
