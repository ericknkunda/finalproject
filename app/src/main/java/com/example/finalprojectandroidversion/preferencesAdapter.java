package com.example.finalprojectandroidversion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class preferencesAdapter extends RecyclerView.Adapter<preferencesAdapter.preferencesHolder> {

    List<preferedItemsModal> preferencesList;
    private Context context;

    public preferencesAdapter(List<preferedItemsModal> preferencesList) {
        this.preferencesList = preferencesList;
    }

    @NonNull
    @Override
    public preferencesAdapter.preferencesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        View itemsView =inflater.inflate(R.layout.preferences_to_inflate,parent,false);
        preferencesAdapter.preferencesHolder preferedItemsHolder =new preferencesAdapter.preferencesHolder(itemsView);
        return preferedItemsHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull preferencesHolder holder, int position) {
        preferedItemsModal modal=preferencesList.get(position);
        String itemName =modal.getItemName();
        holder.itemNames.setText(itemName);
    }

    @Override
    public int getItemCount() {
        return preferencesList.size();
    }

    static class preferencesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView itemNames;
        public preferencesHolder(@NonNull View itemView) {
            super(itemView);
            itemNames =(TextView) itemNames.findViewById(R.id.preferences);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
