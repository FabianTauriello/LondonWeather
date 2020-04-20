package com.novafutur.londonweather.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.novafutur.londonweather.R;
import com.novafutur.londonweather.presenter.Presenter;

/**
 * This class represents the bottom sheet fragment, which shows a 5-day weather forecast for London.
 */
public class ForecastBottomSheetDialogFragment extends BottomSheetDialogFragment {
    private Context context;
    private Presenter presenter;

    ForecastBottomSheetDialogFragment(Context context, Presenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // only show the recycler view if weather forecast data retrieval was successful. Otherwise,
        // show a different layout that displays an error message.
        if (presenter.getForecastItems() != null) {
            return inflater.inflate(R.layout.forecast_recycler_view, container, false);
        } else {
            return inflater.inflate(R.layout.forecast_data_empty, container, false);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (presenter.getForecastItems() != null) {
            RecyclerView rvForecasts = view.findViewById(R.id.rv_forecasts);
            rvForecasts.setLayoutManager(new LinearLayoutManager(context));
            rvForecasts.setAdapter(new ForecastItemAdapter(context, presenter.getForecastItems()));
        }
    }

}
