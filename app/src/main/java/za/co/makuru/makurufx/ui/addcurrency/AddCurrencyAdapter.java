package za.co.makuru.makurufx.ui.addcurrency;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import za.co.makuru.makurufx.R;
import za.co.makuru.makurufx.data.db.model.Currency;
import za.co.makuru.makurufx.ui.base.BaseViewHolder;
import za.co.makuru.makurufx.ui.dashboard.ObservedCurrencyAdapter;

public class AddCurrencyAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    private HashMap<String, String> currenciesMap;

    private AddCurrencyAdapter.ItemClickListener mItemClickListener;

    public AddCurrencyAdapter(HashMap<String, String> currencies) {
        currenciesMap = currencies;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new AddCurrencyAdapter.ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.add_currency_item, parent, false));
            case VIEW_TYPE_EMPTY:
            default:
                return new AddCurrencyAdapter.EmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_item_view, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (currenciesMap != null && currenciesMap.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public int getItemCount() {
        if (currenciesMap != null && currenciesMap.size() > 0) {
            return currenciesMap.size();
        } else {
            return 1;
        }
    }

    public void addItems(HashMap<String, String> currencies) {
        currenciesMap = currencies;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClick(Map.Entry<String, String> entry, int position);
    }

    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.currency_code)
        TextView currencyCode;

        @BindView(R.id.currency_name)
        TextView currencyName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {

        }

        public void onBind(final int position) {
            super.onBind(position);

            Set<Map.Entry<String, String>> currencyEntrySet = currenciesMap.entrySet();

            ArrayList<Map.Entry<String, String>> currencyKVList = new ArrayList<>(currencyEntrySet);
            final Map.Entry<String, String> currency = currencyKVList.get(position);

            if (currency.getKey() != null) {
                currencyCode.setText(currency.getKey());
            }

            if (currency.getValue() != null) {
                currencyName.setText(currency.getValue());
            }


            itemView.setOnClickListener((v) -> {

                if(mItemClickListener != null)
                    mItemClickListener.onItemClick(currency, position);

            });
        }
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        mItemClickListener = itemClickListener;
    }


    public class EmptyViewHolder extends BaseViewHolder {

        @BindView(R.id.tv_message)
        TextView messageTextView;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {

        }
    }
}
