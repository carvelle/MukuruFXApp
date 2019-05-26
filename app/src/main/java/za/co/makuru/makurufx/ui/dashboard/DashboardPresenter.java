package za.co.makuru.makurufx.ui.dashboard;

import java.util.List;

import za.co.makuru.makurufx.data.db.model.Currency;
import za.co.makuru.makurufx.ui.base.Presenter;

public interface DashboardPresenter<V extends DashboardView> extends Presenter<V> {

    void onViewInitialized();

    void deleteCurrency(Currency currency);
    void refreshList();
    void getLatest(List<Currency> dbEntries);
}
