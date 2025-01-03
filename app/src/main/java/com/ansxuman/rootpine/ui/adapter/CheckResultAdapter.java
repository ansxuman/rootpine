package com.ansxuman.rootpine.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ansxuman.rootpine.R;
import com.ansxuman.rootpine.data.model.RootCheckResult;

import java.util.ArrayList;
import java.util.List;

public class CheckResultAdapter extends RecyclerView.Adapter<CheckResultAdapter.ViewHolder> {
    private List<RootCheckResult> results = new ArrayList<>();

    public void setResults(List<RootCheckResult> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_check_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RootCheckResult result = results.get(position);
        holder.titleText.setText(result.getCheckType().getTitle());
        holder.detailsText.setText(result.getDetails());
        holder.statusText.setText(result.isDetected() ? "Detected" : "Not Detected");
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleText;
        TextView detailsText;
        TextView statusText;

        ViewHolder(View view) {
            super(view);
            titleText = view.findViewById(R.id.text_title);
            detailsText = view.findViewById(R.id.text_details);
            statusText = view.findViewById(R.id.text_status);
        }
    }
}