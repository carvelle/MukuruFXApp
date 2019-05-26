package za.co.makuru.makurufx.ui.convertcurrency;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import za.co.makuru.makurufx.configuration.Configuration;
import za.co.makuru.makurufx.data.DataManager;
import za.co.makuru.makurufx.data.network.error.CustomError;
import za.co.makuru.makurufx.data.network.model.request.CurrencyListRequest;
import za.co.makuru.makurufx.data.network.model.request.LatestRequest;
import za.co.makuru.makurufx.ui.base.BasePresenter;
import za.co.makuru.makurufx.utils.rx.SchedulerProvider;

public class ConvertCurrencyMainPresenter<V extends ConvertCurrencyView> extends BasePresenter<V> implements ConvertCurrencyPresenter<V> {

    @Inject
    public ConvertCurrencyMainPresenter(DataManager dataManager, CompositeDisposable compositeDisposable, SchedulerProvider schedulerProvider) {
        super(dataManager, compositeDisposable, schedulerProvider);
    }

    @Override
    public void getAllCurrencies() {
        getAttachedView().showLoading();
        CurrencyListRequest currencyListRequest = new CurrencyListRequest();

        getCompositeDisposable().add(getDataManager()
                .getAllCurrencies(currencyListRequest, "currencies.json")
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe((response) ->{

                    getAttachedView().hideLoading();
                    getAttachedView().setCurrencyMap(response);

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
    public void getLatestRates(String selectedCurrency, double amount) {
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
                        if(kv.getKey().equals(selectedCurrency)) {
                            double convertedAmount = amount * Double.valueOf(kv.getValue());
                            double markedUp = convertedAmount + convertedAmount*0.07;
                            getAttachedView().setOutputView(markedUp);
                        }

                    }


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
