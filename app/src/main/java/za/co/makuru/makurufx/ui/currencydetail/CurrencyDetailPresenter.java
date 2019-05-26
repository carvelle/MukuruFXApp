package za.co.makuru.makurufx.ui.currencydetail;

import za.co.makuru.makurufx.ui.base.Presenter;

public interface CurrencyDetailPresenter<V extends CurrencyDetailView> extends Presenter<V> {

    void getExtendedDbEntries();
}
