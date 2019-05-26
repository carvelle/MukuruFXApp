package za.co.makuru.makurufx.di.component;

import dagger.Component;
import za.co.makuru.makurufx.di.PerService;
import za.co.makuru.makurufx.di.module.ServiceModule;
import za.co.makuru.makurufx.service.SyncService;
import za.co.makuru.makurufx.ui.dashboard.UpdateCurrencyService;

@PerService
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {

    void inject(SyncService service);

    void inject(UpdateCurrencyService service);

}