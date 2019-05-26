package za.co.makuru.makurufx.ui.dashboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import za.co.makuru.makurufx.configuration.Configuration;
import za.co.makuru.makurufx.data.DataManager;
import za.co.makuru.makurufx.data.db.model.Currency;
import za.co.makuru.makurufx.data.db.model.PersistRate;
import za.co.makuru.makurufx.data.network.error.CustomError;
import za.co.makuru.makurufx.data.network.model.request.LatestRequest;
import za.co.makuru.makurufx.ui.base.BasePresenter;
import za.co.makuru.makurufx.utils.rx.SchedulerProvider;

public class UpdateCurrencyMainPresenter<S extends UpdateCurrency> extends BasePresenter<S> implements UpdateCurrenciesPresenter<S>{

    @Inject
    public UpdateCurrencyMainPresenter(DataManager dataManager, CompositeDisposable compositeDisposable, SchedulerProvider schedulerProvider) {
        super(dataManager, compositeDisposable, schedulerProvider);
    }

    @Override
    public void getLatestCurrencyRate() {

        LatestRequest latestRequest = new LatestRequest(Configuration.AppID);

        getCompositeDisposable().add(getDataManager()
                .getLatest(latestRequest, "latest.json")
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe((response) ->{

                    getAttachedView().UpdateDb(response.getRates(), response.getTimestamp());

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

    @Override
    public void updateDbEntries(HashMap<String, String> update, Integer timestamp) {
        getDbAllDbEntries(update, timestamp);
    }

    @Override
    public void getDbAllDbEntries(HashMap<String, String> update, Integer timestamp) {
        getCompositeDisposable().add(getDataManager()
                .getAllCurrencies()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe((latest) -> {
                    Set<Map.Entry<String, String>> serviceItems = update.entrySet();

                    ArrayList<Map.Entry<String, String>> currencyKVList = new ArrayList<>(serviceItems);

                    for (Map.Entry<String, String> kv : currencyKVList) {
                        if (latest.contains(new Currency(0L,"",kv.getKey(), kv.getValue(),"",""))) {
                            int pos = latest.indexOf(new Currency(0L,"",kv.getKey(), kv.getValue(),"",""));
                            Currency currentItem = latest.get(pos);
                            currentItem.setCurrentRate(kv.getValue());

                            PersistRate persistRate = new PersistRate(kv.getValue(), timestamp);
                            persistRate.setPersistId(currentItem.getId());
                            getDataManager().addPersistedRate(persistRate);
                            currentItem.getPersistRateList().add(persistRate);
                        }
                    }
                    executeDbUpdate(latest);

                }));
    }

    @Override
    public void executeDbUpdate(List<Currency> currencyList) {
        getCompositeDisposable().add(getDataManager()
                .updateAllCurrencies(currencyList)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe((latest) -> {

                }));
    }
}
