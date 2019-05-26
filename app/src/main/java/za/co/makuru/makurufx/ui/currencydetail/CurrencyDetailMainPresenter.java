package za.co.makuru.makurufx.ui.currencydetail;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import za.co.makuru.makurufx.configuration.Configuration;
import za.co.makuru.makurufx.data.DataManager;
import za.co.makuru.makurufx.data.db.model.Currency;
import za.co.makuru.makurufx.data.network.error.CustomError;
import za.co.makuru.makurufx.data.network.model.request.LatestRequest;
import za.co.makuru.makurufx.ui.base.BasePresenter;
import za.co.makuru.makurufx.utils.rx.SchedulerProvider;

public class CurrencyDetailMainPresenter<V extends CurrencyDetailView> extends BasePresenter<V> implements CurrencyDetailPresenter<V> {


    @Inject
    public CurrencyDetailMainPresenter(DataManager dataManager, CompositeDisposable compositeDisposable, SchedulerProvider schedulerProvider) {
        super(dataManager, compositeDisposable, schedulerProvider);
    }

    @Override
    public void getExtendedDbEntries() {

        getCompositeDisposable().add(getDataManager()
                .getAllCurrencies()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe((latest) -> {
                    getAttachedView().updateListContent(latest);
                }));
    }
}
