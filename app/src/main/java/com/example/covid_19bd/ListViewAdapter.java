package com.example.covid_19bd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private Context context;
    ArrayList<CountryEntity>countryEntityArrayList;

    public ListViewAdapter(Context context,ArrayList<CountryEntity>countryEntityArrayList){
        this.context=context;
        this.countryEntityArrayList=countryEntityArrayList;
    }

    @Override
    public int getCount() {
        return countryEntityArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return countryEntityArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ListViewAdapter.ViewHolder holder;
        if(view == null){
            holder=new ListViewAdapter.ViewHolder();

            LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(R.layout.listitem,null,true);
            holder.textViewCountry=(TextView)view.findViewById(R.id.countryName);
            holder.textViewCases=(TextView)view.findViewById(R.id.txtCases);
            holder.textViewTodayCases=(TextView)view.findViewById(R.id.todayCases);
            holder.textViewDeaths=(TextView)view.findViewById(R.id.deaths);
            holder.textViewTodayDeaths=(TextView)view.findViewById(R.id.todaydeaths);
            holder.textViewActive=(TextView)view.findViewById(R.id.active);
            holder.textViewRecovered=(TextView)view.findViewById(R.id.recovered);
            holder.textViewCritical=(TextView)view.findViewById(R.id.critical);
            holder.textViewCasePerMillion=(TextView)view.findViewById(R.id.casePerMillion);
            view.setTag(holder);
        }else {
            holder=(ListViewAdapter.ViewHolder)view.getTag();
        }

        holder.textViewCountry.setText(countryEntityArrayList.get(i).getCountry());
        holder.textViewCases.setText("Cases: "+countryEntityArrayList.get(i).getCases());
        holder.textViewTodayCases.setText("Today: "+countryEntityArrayList.get(i).getTodayCases());
        holder.textViewDeaths.setText("Deaths: "+countryEntityArrayList.get(i).getDeaths());
        holder.textViewTodayDeaths.setText("Today: "+countryEntityArrayList.get(i).getTodayDeaths());
        holder.textViewActive.setText("Active: "+countryEntityArrayList.get(i).getActive());
        holder.textViewRecovered.setText("Recovered: "+countryEntityArrayList.get(i).getRecovered());
        holder.textViewCritical.setText("Critical: "+countryEntityArrayList.get(i).getCritical());
        holder.textViewCasePerMillion.setText("CasePerOneMillion: "+countryEntityArrayList.get(i).getCaseperMillion());

        return view;
    }

    private class ViewHolder {
        protected TextView textViewCountry,textViewCases,textViewTodayCases,textViewDeaths,textViewTodayDeaths,textViewActive,textViewRecovered,textViewCritical,textViewCasePerMillion;
}
}
