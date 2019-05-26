package za.co.makuru.makurufx.ui.addcurrency;

import java.util.Map;

import za.co.makuru.makurufx.ui.base.Presenter;

public interface AddCurrencyPresenter<V extends AddCurrencyView> extends Presenter<V> {

    void getCurrencies();
    void addCurrency(Map.Entry<String, String> currency );
}
