package za.co.makuru.makurufx.ui.dashboard;

import java.util.ArrayList;
import java.util.List;
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

public class DashBoardMainPresenter<V extends DashboardView> extends BasePresenter<V> implements DashboardPresenter<V> {

    @Inject
    public DashBoardMainPresenter(DataManager dataManager, CompositeDisposable compositeDisposable, SchedulerProvider schedulerProvider) {
        super(dataManager, compositeDisposable, schedulerProvider);
    }

    @Override
    public void onViewInitialized() {
        refreshList();
    }

    @Override
    public void deleteCurrency(Currency currency) {
        getCompositeDisposable().add(getDataManager()
            .deleteCurrency(currency)
            .subscribeOn(getSchedulerProvider().io())
            .observeOn(getSchedulerProvider().ui())
            .subscribe((didDelete) ->{

                if (!isViewAttached()) {
                    return;
                }
                refreshList();
            }));
    }

    @Override
    public void refreshList() {
        getCompositeDisposable().add(getDataManager()
            .getAllCurrencies()
            .subscribeOn(getSchedulerProvider().io())
            .observeOn(getSchedulerProvider().ui())
            .subscribe((saved) -> {
                if (!isViewAttached()) {
                    return;
                }
                getLatest(saved);
            }));
    }

    @Override
    public void getLatest(List<Currency> dbEntried) {

        getAttachedView().showLoading();
        LatestRequest latestRequest = new LatestRequest(Configuration.AppID);

        getCompositeDisposable().add(getDataManager()
                .getLatest(latestRequest, "latest.json")
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe((response) ->{

                    if (!isViewAttached()) {
                        return;
                    }
                    getAttachedView().hideLoading();
                    Set<Map.Entry<String, String>> currencyEntrySet = response.getRates().entrySet();

                    ArrayList<Map.Entry<String, String>> currencyKVList = new ArrayList<>(currencyEntrySet);

                    for (Map.Entry<String, String> kv : currencyKVList) {
                        if (dbEntried.contains(new Currency(0L,"",kv.getKey(), kv.getValue(),"",""))) {
                            int pos = dbEntried.indexOf(new Currency(0L,"",kv.getKey(), kv.getValue(),"",""));
                            Currency currentItem = dbEntried.get(pos);
                            currentItem.setCurrentRate(kv.getValue());
                        }
                    }
                    getAttachedView().updateDashboardList(dbEntried);

                }, (throwable) -> {

                    if (!isViewAttached()) {
                        return;
                    }
                    getAttachedView().hideLoading();

                    if (throwable instanceof CustomError) {
                        CustomError anError = (CustomError) throwable;
                        //handleApiError(anError);
                    }
                }));
    }
}
