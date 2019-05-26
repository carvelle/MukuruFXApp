package za.co.makuru.makurufx.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import za.co.makuru.makurufx.R;
import za.co.makuru.makurufx.data.db.model.Currency;
import za.co.makuru.makurufx.ui.addcurrency.AddCurrencyFragment;
import za.co.makuru.makurufx.ui.base.BaseActivity;
import za.co.makuru.makurufx.ui.convertcurrency.ConvertCurrencyFragment;
import za.co.makuru.makurufx.ui.currencydetail.CurrencyDetailFragment;
import za.co.makuru.makurufx.ui.dashboard.DashboardFragment;
import za.co.makuru.makurufx.ui.dashboard.DashboardView;
import za.co.makuru.makurufx.ui.dashboard.UpdateCurrencyService;

public class MakuruFxActivity extends BaseActivity implements DashboardView {
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = (item) -> {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    showCurrencyListFragment();
                    return true;
                case R.id.navigation_notifications:
                    showConvertCurrencyFragment();
                    return true;
            }
            return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makuru_fx);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        showCurrencyListFragment();

        scheduleService();
    }

    @Override
    public void onFragmentAttached() {
    }

    @Override
    public void onFragmentDetached(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            fragmentManager
                    .beginTransaction()
                    .disallowAddToBackStack()
                    .remove(fragment)
                    .commitNow();
        }
    }

    @Override
    protected void setUp() {

    }

    @Override
    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.root_view, fragment, fragment.toString())
                .addToBackStack(fragment.toString())
                .commit();
    }

    @Override
    public void showCurrencyListFragment() {
        replaceFragment(DashboardFragment.newInstance());
    }

    @Override
    public void showAddCurrencyFragment() {
        replaceFragment(AddCurrencyFragment.newInstance());
    }

    @Override
    public void showConvertCurrencyFragment() {
        replaceFragment(ConvertCurrencyFragment.newInstance());
    }

    @Override
    public void updateDashboardList(List<Currency> currencyList) {

    }

    @Override
    public void scheduleService() {

        Calendar calendar = Calendar.getInstance();
        Intent intent =  new Intent(this, UpdateCurrencyService.class);
        PendingIntent servicePI = PendingIntent.getService(this, 0, intent, 0);
        AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 30*1000, servicePI);
    }

    @Override
    public void showCurrencyDetailFragment(String currency, String name, String rate) {
        replaceFragment(CurrencyDetailFragment.newInstance(currency, name, rate ));
    }

}
