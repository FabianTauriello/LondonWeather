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

public class ForecastBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private RecyclerView rvForecasts;
    private Context context;
    private Presenter presenter;

    public ForecastBottomSheetDialogFragment(Context context, Presenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forecast_recycler_view, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvForecasts = view.findViewById(R.id.rv_forecasts);
        rvForecasts.setLayoutManager(new LinearLayoutManager(context));
        rvForecasts.setAdapter(new ForecastItemAdapter(context, presenter.getForecastItems()));

    }

    //    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        rvForecastList = getView().findViewById(R.id.rv_forecast_sheet);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
//        rvForecastList.setLayoutManager(layoutManager);
//        rvForecastList.setAdapter(new ForecastItemAdapter(context));
//
//    }

    public RecyclerView getRvForecasts() {
        return rvForecasts;
    }
}
