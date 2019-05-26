package za.co.makuru.makurufx.ui.dashboard;

import java.util.HashMap;
import java.util.List;

import za.co.makuru.makurufx.data.db.model.Currency;
import za.co.makuru.makurufx.ui.base.Presenter;

public interface UpdateCurrenciesPresenter<S extends UpdateCurrency> extends Presenter<S> {

    void getLatestCurrencyRate();
    void updateDbEntries(HashMap<String, String> update, Integer timestamp);
    void getDbAllDbEntries(HashMap<String, String> update, Integer timestamp);
    void executeDbUpdate(List<Currency> currencyList);
}
