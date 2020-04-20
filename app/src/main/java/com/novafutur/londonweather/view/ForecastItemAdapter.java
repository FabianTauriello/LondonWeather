package com.novafutur.londonweather.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.novafutur.londonweather.R;
import com.novafutur.londonweather.model.Forecast;

import java.util.ArrayList;
import java.util.List;

/**
 * This class acts as the adapter for the weather forecast recycler view.
 */
public class ForecastItemAdapter extends RecyclerView.Adapter<ForecastItemAdapter.ForecastViewHolder> {

    /**
     * Inner class to represent a single forecast day.
     */
    static class ForecastViewHolder extends RecyclerView.ViewHolder {
        TextView tvDay;
        TextView tvTemp;
        TextView tvDescription;
        View divider;

        ForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tv_day);
            tvTemp = itemView.findViewById(R.id.tv_forecast_temp);
            tvDescription = itemView.findViewById(R.id.tv_description);
            divider = itemView.findViewById(R.id.divider);
        }
    }

    private Context context;
    private List<Forecast> forecastItems;

    ForecastItemAdapter(Context context, ArrayList<Forecast> forecastItems) {
        this.context = context;
        this.forecastItems = forecastItems;
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_recycler_view_item, parent, false);
        return new ForecastViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        // retrieve current forecast item
        Forecast forecastItem = forecastItems.get(position);

        if (forecastItem != null) {
            // fill the card views with data
            holder.tvDay.setText(forecastItem.getDay());
            holder.tvTemp.setText(context.getString(R.string.degree_celsius_symbol, Integer.toString(forecastItem.getWeatherTempCurrent())));
            holder.tvDescription.setText(forecastItem.getWeatherDescription());
        }

        // remove bottom divider for last item in recycler view
        if (position == forecastItems.size() - 1) {
            holder.divider.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (forecastItems != null) {
            return forecastItems.size();
        } else {
            return 0;
        }
    }

}
