package za.co.makuru.makurufx.ui.currencydetail;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import za.co.makuru.makurufx.R;
import za.co.makuru.makurufx.data.db.model.Currency;
import za.co.makuru.makurufx.data.db.model.PersistRate;
import za.co.makuru.makurufx.di.component.ActivityComponent;
import za.co.makuru.makurufx.ui.base.BaseFragment;
import za.co.makuru.makurufx.utils.AppUtils;

public class CurrencyDetailFragment extends BaseFragment implements CurrencyDetailView {

    public static final String CURRENCY_KEY = "CURRENCY";
    public static final String CURRENCY_NAME = "NAME";
    public static final String CURRENCY_RATE = "RATE";

    @Inject
    CurrencyDetailPresenter<CurrencyDetailView> mPresenter;

    @Inject
    PreviousRatesAdapter mPreviousRatesAdapter;

    private String mCurrency;
    private String mName;
    private String mRate;

    @BindView(R.id.page_title)
    TextView PageTitle;

    @BindView(R.id.currency_code)
    TextView currencyCode;

    @BindView(R.id.currency_name)
    TextView currencyName;

    @BindView(R.id.current_rate)
    TextView currentRate;

    @BindView(R.id.previous_rates)
    ListView mListView;

    public static CurrencyDetailFragment newInstance(String currency, String name, String rate) {
        CurrencyDetailFragment fragment = new CurrencyDetailFragment();
        Bundle args = new Bundle();
        args.putString(CURRENCY_KEY, currency);
        args.putString(CURRENCY_NAME, name);
        args.putString(CURRENCY_RATE, rate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCurrency = getArguments().getString(CURRENCY_KEY);
            mName = getArguments().getString(CURRENCY_NAME);
            mRate = getArguments().getString(CURRENCY_RATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_currency_detail, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    protected void setUp(View view) {

        mPresenter.getExtendedDbEntries();
        mListView.setAdapter(mPreviousRatesAdapter);

        currencyCode.setText(mCurrency);
        currencyName.setText(mName);
        currentRate.setText(mRate);

    }

    @Override
    public void updateListContent(List<Currency> currencyList) {

        Currency chosenCurrency  = null;
        for(Currency cr :  currencyList)
        {
            if(cr.getCurrencyCode().equals(mCurrency)){
                chosenCurrency = cr;
            }
        }
        ArrayList<PreviousRate> adapterList = new ArrayList<>();
        for(PersistRate pr : chosenCurrency.getPersistRateList()){
            adapterList.add(new PreviousRate(AppUtils.getDateFromTimestamp(pr.getTimestamp().toString()), pr.getThenRate()));
        }
        mPreviousRatesAdapter.updateResults(adapterList);
    }
}
