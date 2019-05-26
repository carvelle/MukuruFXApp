package za.co.makuru.makurufx.ui.addcurrency;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import za.co.makuru.makurufx.data.DataManager;
import za.co.makuru.makurufx.data.network.error.CustomError;
import za.co.makuru.makurufx.data.network.model.request.CurrencyListRequest;
import za.co.makuru.makurufx.ui.base.BasePresenter;
import za.co.makuru.makurufx.utils.rx.SchedulerProvider;

public class AddCurrencyMainPresenter<V extends AddCurrencyView> extends BasePresenter<V> implements AddCurrencyPresenter<V> {

    @Inject
    public AddCurrencyMainPresenter(DataManager dataManager, CompositeDisposable compositeDisposable, SchedulerProvider schedulerProvider) {
        super(dataManager, compositeDisposable, schedulerProvider);
    }

    @Override
    public void getCurrencies() {

        getAttachedView().showLoading();
        CurrencyListRequest currencyListRequest = new CurrencyListRequest();

        getCompositeDisposable().add(getDataManager()
                .getAllCurrencies(currencyListRequest, "currencies.json")
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe((response) ->{

                    getAttachedView().hideLoading();
                    getAttachedView().updateView(response);

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
    public void addCurrency(Map.Entry<String, String> currency) {
        getCompositeDisposable().add(getDataManager()
                .saveCurrency(currency)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe((saved) -> {
                        if (!isViewAttached()) {
                            return;
                        }
                }));
    }
}
