package com.example.covid_19bd;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


    private ArrayList<CountryEntity> countryEntities;
    DecimalFormat decimalFormat=new DecimalFormat("#,###,###");
    private Context mcontext;
    public RecyclerViewAdapter(Context context,ArrayList<CountryEntity>countryEntities){
        mcontext=context;
        this.countryEntities=countryEntities;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(countryEntities!=null){
            holder.textViewCountry.setText(countryEntities.get(position).getCountry());
            holder.textViewCases.setText("Cases:"+(decimalFormat.format(Integer.valueOf(countryEntities.get(position).getCases()))));
            holder.textViewTodayCases.setText("Today:"+(decimalFormat.format(Integer.valueOf(countryEntities.get(position).getTodayCases()))));
            holder.textViewDeaths.setText("Deaths:"+(decimalFormat.format(Integer.valueOf(countryEntities.get(position).getDeaths()))));
            holder.textViewTodayDeaths.setText("Today:"+(decimalFormat.format(Integer.valueOf(countryEntities.get(position).getTodayDeaths()))));
            System.out.println(countryEntities.get(position).getRecovered());
            try{
                String recVal=decimalFormat.format(Integer.valueOf(countryEntities.get(position).getRecovered()));
                holder.textViewRecovered.setText("Recovered:"+(recVal));
            }catch (NumberFormatException e){
                e.printStackTrace();
                holder.textViewRecovered.setText("Recovered:"+"null");
            }


            holder.textViewActive.setText("Active:"+(decimalFormat.format(Integer.valueOf(countryEntities.get(position).getActive()))));
            holder.textViewCritical.setText("Critical:"+(decimalFormat.format(Integer.valueOf(countryEntities.get(position).getCritical()))));
            holder.textViewCasePerMillion.setText("CasesPerMillon:"+(decimalFormat.format(Integer.valueOf(countryEntities.get(position).getCaseperMillion()))));

        }
    }

    @Override
    public int getItemCount() {
        if(countryEntities==null) {
            return 0;
        }else
        {
            return  countryEntities.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        protected TextView textViewCountry,textViewCases,textViewTodayCases,textViewDeaths,textViewTodayDeaths,textViewActive,textViewRecovered,textViewCritical,textViewCasePerMillion;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCountry=(TextView)itemView.findViewById(R.id.countryName);
            textViewCases=(TextView)itemView.findViewById(R.id.txtCases);
            textViewTodayCases=(TextView)itemView.findViewById(R.id.todayCases);
            textViewDeaths=(TextView)itemView.findViewById(R.id.deaths);
            textViewTodayDeaths=(TextView)itemView.findViewById(R.id.todaydeaths);
            textViewActive=(TextView)itemView.findViewById(R.id.active);
            textViewRecovered=(TextView)itemView.findViewById(R.id.recoveredPeople);
            textViewCritical=(TextView)itemView.findViewById(R.id.critical);
            textViewCasePerMillion=(TextView)itemView.findViewById(R.id.casePerMillion);


        }
    }
}
