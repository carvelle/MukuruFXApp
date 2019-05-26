package za.co.makuru.makurufx.ui.addcurrency;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import za.co.makuru.makurufx.R;
import za.co.makuru.makurufx.di.component.ActivityComponent;
import za.co.makuru.makurufx.ui.base.BaseFragment;


public class AddCurrencyFragment extends BaseFragment implements AddCurrencyView {

    @Inject
    AddCurrencyPresenter<AddCurrencyView> mPresenter;

    @Inject
    AddCurrencyAdapter mAddCurrencyAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.currencyRecyclerView)
    RecyclerView mRecyclerView;

    public static AddCurrencyFragment newInstance() {
        AddCurrencyFragment fragment = new AddCurrencyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_add_currency, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.getCurrencies();
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

        mAddCurrencyAdapter.setItemClickListener((entry, postion) -> {
                    mPresenter.addCurrency(entry);
                    getBaseActivity().showMessage("Currency Added: " +entry.getKey());
                }
        );
        mRecyclerView.setAdapter(mAddCurrencyAdapter);
        mPresenter.getCurrencies();

    }

    @Override
    public void updateView(HashMap<String, String> currencies) {
        mAddCurrencyAdapter.addItems(currencies);
    }
}
