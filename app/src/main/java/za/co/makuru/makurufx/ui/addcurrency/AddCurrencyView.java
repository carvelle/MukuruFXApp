package za.co.makuru.makurufx.ui.addcurrency;

import java.util.HashMap;

import za.co.makuru.makurufx.ui.base.BaseView;

public interface AddCurrencyView extends BaseView {

    void updateView(HashMap<String, String> currencies);
}
