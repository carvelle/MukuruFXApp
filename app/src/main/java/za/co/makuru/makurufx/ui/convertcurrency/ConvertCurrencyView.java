package za.co.makuru.makurufx.ui.convertcurrency;

import java.util.HashMap;

import za.co.makuru.makurufx.ui.base.BaseView;

public interface ConvertCurrencyView extends BaseView {

    void setCurrencyMap(HashMap<String, String> currencyMap);
    void setOutputView(double amount);
}
