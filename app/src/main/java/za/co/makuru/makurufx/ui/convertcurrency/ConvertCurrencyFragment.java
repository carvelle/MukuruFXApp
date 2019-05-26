package za.co.makuru.makurufx.ui.convertcurrency;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import za.co.makuru.makurufx.R;
import za.co.makuru.makurufx.di.component.ActivityComponent;
import za.co.makuru.makurufx.ui.base.BaseFragment;


public class ConvertCurrencyFragment extends BaseFragment implements ConvertCurrencyView {

    @Inject
    ConvertCurrencyPresenter<ConvertCurrencyView> mPresenter;

    @BindView(R.id.choose_currency)
    AutoCompleteTextView chooseCurrency;

    @BindView(R.id.converiosn_amount)
    EditText conversionAmount;

    @BindView(R.id.output_amount)
    TextView outputAmount;

    @BindView(R.id.convert_but)
    Button converBut;

    private Context mContext;

    public static ConvertCurrencyFragment newInstance() {
        ConvertCurrencyFragment fragment = new ConvertCurrencyFragment();
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
        View view =  inflater.inflate(R.layout.fragment_convert_currency, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }

        mContext = view.getContext();
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
        mPresenter.getAllCurrencies();
    }

    @Override
    public void setCurrencyMap(HashMap<String, String> currencyMap) {
        String[] currencyArray = currencyMap.keySet().toArray(new String[currencyMap.size()]);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext,android.R.layout.simple_list_item_1,currencyArray);
        chooseCurrency.setAdapter(adapter);
    }

    @Override
    public void setOutputView(double amount) {
        outputAmount.setText(chooseCurrency.getText().toString() +" " +Double.valueOf(amount).toString());
    }

    @OnClick(R.id.convert_but)
    public void onClick(View v){
        if(chooseCurrency.getText().toString() != "" && conversionAmount.getText().toString() != "")
        mPresenter.getLatestRates(chooseCurrency.getText().toString(), Double.valueOf(conversionAmount.getText().toString()));
    }
}
