package za.co.makuru.makurufx.ui.dashboard;

import android.content.Intent;

import java.util.HashMap;

import javax.inject.Inject;

import za.co.makuru.makurufx.di.component.ServiceComponent;
import za.co.makuru.makurufx.service.SyncService;

public class UpdateCurrencyService extends SyncService implements UpdateCurrency{

    @Inject
    UpdateCurrenciesPresenter<UpdateCurrency> mPresenter;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        ServiceComponent component = getServiceComponent();
        if (component != null) {
            component.inject(this);
            mPresenter.onAttach(this);
        }
        mPresenter.getLatestCurrencyRate();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void UpdateDb(HashMap<String, String> update, Integer timestamp) {

        mPresenter.getDbAllDbEntries(update, timestamp);
    }
}
