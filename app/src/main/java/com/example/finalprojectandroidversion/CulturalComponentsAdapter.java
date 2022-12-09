package com.example.finalprojectandroidversion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CulturalComponentsAdapter extends RecyclerView.Adapter<CulturalComponentsAdapter.CulturalComponentsHolder> {

    private List<CulturalComponentModal> modalList;

    public CulturalComponentsAdapter(List<CulturalComponentModal> modalList) {
        this.modalList = modalList;
    }

    @NonNull
    @Override
    public CulturalComponentsAdapter.CulturalComponentsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        View compponentsView =inflater.inflate(R.layout.cultural_component_to_inflate,parent,false);
        CulturalComponentsHolder culturalComponentsHolder =new CulturalComponentsHolder(compponentsView);
        return culturalComponentsHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CulturalComponentsAdapter.CulturalComponentsHolder holder, int position) {
        CulturalComponentModal modal=modalList.get(position);
        holder.componentName.setText(modal.getComponentName());
    }

    @Override
    public int getItemCount() {
        return modalList.size();
    }
    static class CulturalComponentsHolder extends RecyclerView.ViewHolder{
        private TextView componentName;

        public CulturalComponentsHolder(@NonNull View itemView) {
            super(itemView);
            this.componentName=(TextView) itemView.findViewById(R.id.componentName);
        }
    }
}
