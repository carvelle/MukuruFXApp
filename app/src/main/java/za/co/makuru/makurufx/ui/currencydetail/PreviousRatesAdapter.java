package za.co.makuru.makurufx.ui.currencydetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import za.co.makuru.makurufx.R;

public class PreviousRatesAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<PreviousRate> previousRates;

    public PreviousRatesAdapter(Context context, ArrayList<PreviousRate> items) {
        this.context = context;
        this.previousRates = items;
    }

    public void updateResults(ArrayList<PreviousRate> results) {
        previousRates = results;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return previousRates.size();
    }

    @Override
    public Object getItem(int position) {
        return previousRates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).
                    inflate(R.layout.previous_rate_item, parent, false);
        }

        PreviousRate currentItem = (PreviousRate) getItem(position);

        TextView date = view.findViewById(R.id.time);
        TextView rate = view.findViewById(R.id.rate);

        date.setText(currentItem.getDate());
        rate.setText(currentItem.getRate());

        return view;
    }
}
