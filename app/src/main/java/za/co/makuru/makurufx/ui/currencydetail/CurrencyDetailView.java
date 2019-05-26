package za.co.makuru.makurufx.ui.currencydetail;

import java.util.List;

import za.co.makuru.makurufx.data.db.model.Currency;
import za.co.makuru.makurufx.ui.base.BaseView;

public interface CurrencyDetailView extends BaseView {

    void updateListContent(List<Currency> currencyList);
    //void updateLatestFields(String )
}
