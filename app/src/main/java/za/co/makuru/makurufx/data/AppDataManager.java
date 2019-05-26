package za.co.makuru.makurufx.data;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import za.co.makuru.makurufx.data.db.DatabaseHelper;
import za.co.makuru.makurufx.data.db.model.Currency;
import za.co.makuru.makurufx.data.db.model.PersistRate;
import za.co.makuru.makurufx.data.network.AppServiceHelper;
import za.co.makuru.makurufx.data.network.ServiceHelper;
import za.co.makuru.makurufx.data.network.model.request.CurrencyListRequest;
import za.co.makuru.makurufx.data.network.model.request.LatestRequest;
import za.co.makuru.makurufx.data.network.model.response.CurrencyListResponse;
import za.co.makuru.makurufx.data.network.model.response.LatestResponse;

public class AppDataManager implements DataManager {

    private final DatabaseHelper mDatabaseHelper;
    private final ServiceHelper mServiceHelper;


    @Inject
    public AppDataManager(DatabaseHelper mDatabaseHelper, AppServiceHelper mServiceHelper) {
        this.mDatabaseHelper = mDatabaseHelper;
        this.mServiceHelper = mServiceHelper;
    }

    @Override
    public Observable<Boolean> saveCurrency(Map.Entry<String, String> currency) {
        return mDatabaseHelper.saveCurrency(currency);
    }

    @Override
    public Observable<Boolean> deleteCurrency(Currency currency) {
        return mDatabaseHelper.deleteCurrency(currency);
    }

    @Override
    public Observable<List<Currency>> getAllCurrencies() {
        return mDatabaseHelper.getAllCurrencies();
    }

    @Override
    public Observable<Boolean> updateAllCurrencies(List<Currency> currencyList) {
        return mDatabaseHelper.updateAllCurrencies(currencyList);
    }

    @Override
    public boolean addPersistedRate(PersistRate persistRate) {
        return mDatabaseHelper.addPersistedRate(persistRate);
    }

    @Override
    public Single<CurrencyListResponse> getAllCurrencies(CurrencyListRequest request, String path) {
        return mServiceHelper.getAllCurrencies(request,path);
    }

    @Override
    public Single<LatestResponse> getLatest(LatestRequest request, String path) {
        return mServiceHelper.getLatest(request,path);
    }
}
