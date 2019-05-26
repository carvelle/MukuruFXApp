package za.co.makuru.makurufx.ui.dashboard;

import android.support.v4.app.Fragment;

import java.util.List;

import za.co.makuru.makurufx.data.db.model.Currency;
import za.co.makuru.makurufx.ui.base.BaseView;

public interface DashboardView extends BaseView {

    void replaceFragment(Fragment fragment);
    void showCurrencyListFragment();
    void showAddCurrencyFragment();
    void showConvertCurrencyFragment();
    void updateDashboardList(List<Currency> currencyList);
    void scheduleService();
    void showCurrencyDetailFragment(String currency, String name, String rate);
}
