package za.co.makuru.makurufx.ui.dashboard;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import za.co.makuru.makurufx.R;
import za.co.makuru.makurufx.data.db.model.Currency;
import za.co.makuru.makurufx.ui.base.BaseViewHolder;

public class ObservedCurrencyAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    private List<Currency> mCurrencyList;

    private ItemClickListener mItemClickListener;

    private ButtonClickListener mButtonClickListener;

    public ObservedCurrencyAdapter(List<Currency> todoItemList) {
        mCurrencyList = todoItemList;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.currency_item_view, parent, false));
            case VIEW_TYPE_EMPTY:
            default:
                return new EmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_item_view, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mCurrencyList != null && mCurrencyList.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public int getItemCount() {
        if (mCurrencyList != null && mCurrencyList.size() > 0) {
            return mCurrencyList.size();
        } else {
            return 1;
        }
    }

    public void addItems(List<Currency> currencyList) {
        mCurrencyList = currencyList;
        notifyDataSetChanged();
    }


    public interface ItemClickListener {
        void onItemClick(Currency item, int position);
    }

    public interface ButtonClickListener {
        void onButtonClick(Currency item, int position);
    }

    public class ViewHolder extends BaseViewHolder{

        @BindView(R.id.currency_code)
        TextView currencyCode;

        @BindView(R.id.currency_name)
        TextView currencyName;

        @BindView(R.id.rate)
        TextView rate;

        @BindView(R.id.remove_item)
        Button removeButton;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {

        }

        public void onBind(final int position) {
            super.onBind(position);

            final Currency currency = mCurrencyList.get(position);


            if (currency.getCurrencyCode() != null) {
                currencyCode.setText(currency.getCurrencyCode());
            }

            if (currency.getCurrencyName() != null) {
                currencyName.setText(currency.getCurrencyName());
            }
            if (currency.getCurrentRate() != null) {
                rate.setText(currency.getCurrentRate());
            }

            itemView.setOnClickListener((v)  -> {
                    if(mItemClickListener != null)
                        mItemClickListener.onItemClick(currency, position);
            });

            removeButton.setOnClickListener((v)  -> {
                if(mButtonClickListener != null)
                    mButtonClickListener.onButtonClick(currency, position);
            });

        }
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        mItemClickListener = itemClickListener;
    }

    public void setButtonClickListener(ButtonClickListener buttonClickListener){
        mButtonClickListener = buttonClickListener;
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
