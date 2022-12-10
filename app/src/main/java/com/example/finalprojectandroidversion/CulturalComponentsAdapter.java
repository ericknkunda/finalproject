package com.example.finalprojectandroidversion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CulturalComponentsAdapter extends RecyclerView.Adapter<CulturalComponentsAdapter.CulturalComponentsHolder> {

    private static  List<CulturalComponentModal> modalList;
    private Context context;

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
       String className =modal.getComponentName();
        holder.box.setText(className);
    }

    @Override
    public int getItemCount() {
        return modalList.size();
    }

    //holder class
     static class CulturalComponentsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView componentName;
        private CheckBox box;

        //list to hold cliked preferences
        private  static List<String> componentsList;

        public CulturalComponentsHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            //this.componentName=(TextView) itemView.findViewById(R.id.componentName);
            this.box =(CheckBox) itemView.findViewById(R.id.userChoice);
            this.box.setOnClickListener(this);
            this.componentsList=new ArrayList<>();
        }

        @Override
        public void onClick(View view){
            int position =this.getAdapterPosition();
            CulturalComponentModal componentModal =modalList.get(position);
            String value =box.getText().toString();
            Toast.makeText(view.getContext(),value,Toast.LENGTH_LONG).show();
            addPreferences(value);

        }
         public void addPreferences(String value){
             componentsList.add(value);
//             return componentsList;
         }

         //getting the list
        public static List<String> preferencesList(){
            return  CulturalComponentsHolder.componentsList;
        }
    }

}
