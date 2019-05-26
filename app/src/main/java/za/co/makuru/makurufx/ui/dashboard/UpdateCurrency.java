package za.co.makuru.makurufx.ui.dashboard;

import java.util.HashMap;

import za.co.makuru.makurufx.ui.base.BaseView;

public interface UpdateCurrency extends BaseView {

    void UpdateDb(HashMap<String, String> update, Integer timestamp);
}
