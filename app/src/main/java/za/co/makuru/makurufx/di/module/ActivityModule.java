package za.co.makuru.makurufx.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import za.co.makuru.makurufx.di.ActivityContext;
import za.co.makuru.makurufx.ui.addcurrency.AddCurrencyAdapter;
import za.co.makuru.makurufx.ui.addcurrency.AddCurrencyMainPresenter;
import za.co.makuru.makurufx.ui.addcurrency.AddCurrencyPresenter;
import za.co.makuru.makurufx.ui.addcurrency.AddCurrencyView;
import za.co.makuru.makurufx.ui.convertcurrency.ConvertCurrencyMainPresenter;
import za.co.makuru.makurufx.ui.convertcurrency.ConvertCurrencyPresenter;
import za.co.makuru.makurufx.ui.convertcurrency.ConvertCurrencyView;
import za.co.makuru.makurufx.ui.currencydetail.CurrencyDetailMainPresenter;
import za.co.makuru.makurufx.ui.currencydetail.CurrencyDetailPresenter;
import za.co.makuru.makurufx.ui.currencydetail.CurrencyDetailView;
import za.co.makuru.makurufx.ui.currencydetail.PreviousRatesAdapter;
import za.co.makuru.makurufx.ui.dashboard.DashBoardMainPresenter;
import za.co.makuru.makurufx.ui.dashboard.DashboardPresenter;
import za.co.makuru.makurufx.ui.dashboard.DashboardView;
import za.co.makuru.makurufx.ui.dashboard.ObservedCurrencyAdapter;
import za.co.makuru.makurufx.utils.rx.SchedulerProvider;

@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new SchedulerProvider();
    }


    @Provides
    LinearLayoutManager provideLinearLayoutManager(AppCompatActivity activity) {
        return new LinearLayoutManager(activity);
    }

    @Provides
    AddCurrencyPresenter<AddCurrencyView> provideAddCurrencyPresenter(
                    AddCurrencyMainPresenter<AddCurrencyView> presenter) {
        return presenter;
    }

    @Provides
    DashboardPresenter<DashboardView> provideDashboardPresenter(
            DashBoardMainPresenter<DashboardView> presenter) {
        return presenter;
    }

    @Provides
    ConvertCurrencyPresenter<ConvertCurrencyView> provideConvertCurrencyPresenter(
            ConvertCurrencyMainPresenter<ConvertCurrencyView> presenter) {
        return presenter;
    }

    @Provides
    CurrencyDetailPresenter<CurrencyDetailView> provideCurrencyDetailPresenter(
            CurrencyDetailMainPresenter<CurrencyDetailView> presenter) {
        return presenter;
    }

    @Provides
    AddCurrencyAdapter provideAddCurrencyAdapter() {
        return new AddCurrencyAdapter(new HashMap<>());
    }

    @Provides
    ObservedCurrencyAdapter provideObservedCurrencyAdapter() {
        return new ObservedCurrencyAdapter(new ArrayList<>());
    }

    @Provides
    PreviousRatesAdapter providePreviousRatesAdapter() {
        return new PreviousRatesAdapter(provideContext(), new ArrayList<>());
    }
}
