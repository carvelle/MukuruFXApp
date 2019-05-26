package za.co.makuru.makurufx.di.component;

import dagger.Component;
import za.co.makuru.makurufx.di.PerActivity;
import za.co.makuru.makurufx.di.module.ActivityModule;
import za.co.makuru.makurufx.ui.MakuruFxActivity;
import za.co.makuru.makurufx.ui.addcurrency.AddCurrencyFragment;
import za.co.makuru.makurufx.ui.convertcurrency.ConvertCurrencyFragment;
import za.co.makuru.makurufx.ui.currencydetail.CurrencyDetailFragment;
import za.co.makuru.makurufx.ui.dashboard.DashboardFragment;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MakuruFxActivity activity);
    void inject(DashboardFragment fragment);
    void inject(AddCurrencyFragment fragment);
    void inject(ConvertCurrencyFragment fragment);
    void inject(CurrencyDetailFragment fragment);
}
