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

public class ForecastItemAdapter extends RecyclerView.Adapter<ForecastItemAdapter.ForecastViewHolder> {

    /**
     * Inner class to represent a single forecast day.
     */
    public class ForecastViewHolder extends RecyclerView.ViewHolder {
        TextView tvDay;
        TextView tvTemp;
        TextView tvDescription;

        public ForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tv_day);
            tvTemp = itemView.findViewById(R.id.tv_icon_and_temp);
            tvDescription = itemView.findViewById(R.id.tv_description);
        }
    }

    private Context context;
    private List<Forecast> forecastItems;

    public ForecastItemAdapter(Context context, ArrayList<Forecast> forecastItems) {
        this.context = context;
        this.forecastItems = forecastItems;
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_recycler_view_item, parent, false);
        ForecastViewHolder viewHolder = new ForecastViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        // retrieve current forecast item
        Forecast forecastItem = forecastItems.get(position);

        if (forecastItem != null) {
            // fill the card views with data
            holder.tvDay.setText(forecastItem.getDay());
            holder.tvTemp.setText(Integer.toString(forecastItem.getWeatherTempCurrent()));
            holder.tvDescription.setText(forecastItem.getWeatherDescription());
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

    public void setForecastItems(ArrayList<Forecast> forecastItems) {
        this.forecastItems = forecastItems;
        notifyDataSetChanged();
    }

}
