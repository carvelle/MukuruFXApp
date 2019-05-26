package za.co.makuru.makurufx.data.db;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import za.co.makuru.makurufx.data.db.model.Currency;
import za.co.makuru.makurufx.data.db.model.DaoMaster;
import za.co.makuru.makurufx.data.db.model.DaoSession;
import za.co.makuru.makurufx.data.db.model.PersistRate;

public class AppDatabaseHelper implements DatabaseHelper {

    private final DaoSession mDaoSession;

    @Inject
    public AppDatabaseHelper(DatabaseOpenHelper dbOpenHelper) {
        this.mDaoSession = new DaoMaster(dbOpenHelper.getWritableDb()).newSession();
    }

    @Override
    public Observable<Boolean> saveCurrency(Map.Entry<String, String> currency) {
        return Observable.fromCallable(() -> {
                Currency item = new Currency();
                item.setCurrencyCode(currency.getKey());
                item.setCurrencyName(currency.getValue());
                mDaoSession.getCurrencyDao().insert(item);
                return true;
        });
    }

    @Override
    public Observable<List<Currency>> getAllCurrencies() {
        return Observable.fromCallable(() ->
             mDaoSession.getCurrencyDao().loadAll()
        );
    }

    @Override
    public Observable<Boolean> updateAllCurrencies(final List<Currency> currencyList) {
        return Observable.fromCallable(() -> {
            mDaoSession.getCurrencyDao().updateInTx(currencyList);
            return true;
        }
        );
    }

    @Override
    public Observable<Boolean> deleteCurrency(final Currency currency) {
        return Observable.fromCallable(() -> {
                mDaoSession.getCurrencyDao().delete(currency);
                return true;
        });
    }

    @Override
    public boolean addPersistedRate(PersistRate persistRate) {
        mDaoSession.insert(persistRate);
        return true;
    }
}
