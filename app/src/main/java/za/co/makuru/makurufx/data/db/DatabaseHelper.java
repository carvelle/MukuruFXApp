package za.co.makuru.makurufx.data.db;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import za.co.makuru.makurufx.data.db.model.Currency;
import za.co.makuru.makurufx.data.db.model.PersistRate;

public interface DatabaseHelper {

    Observable<Boolean> saveCurrency(Map.Entry<String, String> currency);
    Observable<Boolean> deleteCurrency(final Currency currency);
    Observable<List<Currency>> getAllCurrencies();
    Observable<Boolean> updateAllCurrencies(List<Currency> currencyList);
    boolean addPersistedRate(PersistRate persistRate);


}
