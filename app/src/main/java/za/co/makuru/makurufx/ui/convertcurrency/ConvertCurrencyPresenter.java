package za.co.makuru.makurufx.ui.convertcurrency;

import za.co.makuru.makurufx.ui.base.Presenter;

public interface ConvertCurrencyPresenter<V extends ConvertCurrencyView> extends Presenter<V> {

    void getAllCurrencies();

    void getLatestRates(String selectedCurrency, double amount);
}
