package za.co.makuru.makurufx.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import za.co.makuru.makurufx.R;
import za.co.makuru.makurufx.data.db.model.Currency;
import za.co.makuru.makurufx.di.component.ActivityComponent;
import za.co.makuru.makurufx.ui.MakuruFxActivity;
import za.co.makuru.makurufx.ui.base.BaseFragment;


public class DashboardFragment extends BaseFragment implements DashboardView {

    @Inject
    DashboardPresenter<DashboardView> mPresenter;

    @Inject
    ObservedCurrencyAdapter mObservedCurrencyAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.currencyRecyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.add_currency_fab)
    FloatingActionButton addCurrencyFab;

    public static DashboardFragment newInstance() {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_dashboard, container, false);
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

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mObservedCurrencyAdapter.setButtonClickListener((entry, postion) ->

                mPresenter.deleteCurrency(entry)
        );

        mObservedCurrencyAdapter.setItemClickListener((currency, position) ->
            showCurrencyDetailFragment(currency.getCurrencyCode(), currency.getCurrencyName(), currency.getCurrentRate())
        );
        mRecyclerView.setAdapter(mObservedCurrencyAdapter);
        mPresenter.onViewInitialized();
    }

    @Override
    public void replaceFragment(Fragment fragment) {
      throw new UnsupportedOperationException("Method not Implemented");
    }

    @Override
    public void showCurrencyListFragment() {
        throw new UnsupportedOperationException("Method not Implemented");
    }

    @Override
    public void showAddCurrencyFragment() {
        ((MakuruFxActivity)getBaseActivity()).showAddCurrencyFragment();
    }

    @Override
    public void showConvertCurrencyFragment() {
        throw new UnsupportedOperationException("Method not Implemented");
    }

    @Override
    public void updateDashboardList(List<Currency> currencyList) {

        mObservedCurrencyAdapter.addItems(currencyList);
    }

    @Override
    public void scheduleService() {
        throw new UnsupportedOperationException("Method not Implemented");
    }

    @Override
    public void showCurrencyDetailFragment(String currency, String name, String rate) {
        ((MakuruFxActivity)getBaseActivity()).showCurrencyDetailFragment(currency, name, rate);
    }

    @OnClick(R.id.add_currency_fab)
    public void OnClick(View v){
        showAddCurrencyFragment();
    }
}
