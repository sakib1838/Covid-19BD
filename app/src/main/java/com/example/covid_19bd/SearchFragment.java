package com.example.covid_19bd;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchFragment extends Fragment {

    AutoCompleteTextView autoCompleteTextView;
    ArrayList<CountryEntity> countryEntityArrayList;
    ArrayList<String> country;
    ImageButton searchBtn;
    ProgressDialog progressDialog;
    PieChart pieChart;
    String restaurants[] = {
            "KFC",
            "Dominos",
            "Pizza Hut",
            "Burger King",
            "Subway",
            "Dunkin' Donuts",
            "Starbucks",
            "Cafe Coffee Day"
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_search, container, false);

        autoCompleteTextView=(AutoCompleteTextView)view.findViewById(R.id.autoComplete);
        searchBtn=(ImageButton) view.findViewById(R.id.searchbtn);
        pieChart=(PieChart)view.findViewById(R.id.pieChart);
        getCountryInformation();

//        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,restaurants);
//        autoCompleteTextView.setThreshold(1);
//        autoCompleteTextView.setAdapter(arrayAdapter);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pieChart.clear();
                String text=autoCompleteTextView.getText().toString();
                System.out.println(text);
                searchCountry(text);

            }
        });

        return view;
    }

    private void showgetData(){
        this.progressDialog= new ProgressDialog(getContext());
        progressDialog.setCancelable(true);
        progressDialog.setTitle("Getting Data");
        progressDialog.show();
    }

    private void getCountryInformation(){
        showgetData();
        String url="https://coronavirus-19-api.herokuapp.com/countries";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray dataArray = new JSONArray(response);
                    country=new ArrayList<>();
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject jsonObject = dataArray.getJSONObject(i);
                        country.add(jsonObject.getString("country"));
                    }
                    if(!(DataHold.countries ==null)){
                        DataHold.countries.clear();
                    }
                    DataHold.countries=country;
                    System.out.println(DataHold.countries.size());
                    if(!(DataHold.countries==null) && DataHold.countries.size()>0){
                        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,DataHold.countries);
                        autoCompleteTextView.setThreshold(1);
                        autoCompleteTextView.setAdapter(arrayAdapter);
                    }else{
                        Toast.makeText(getContext(),"NO Data Available",Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }


    private void searchCountry(String s){
        showgetData();
        String url="https://coronavirus-19-api.herokuapp.com/countries/"+s;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    ArrayList<Entry> yvalues = new ArrayList<Entry>();
                    if(yvalues.size()>0 && yvalues!=null){
                        yvalues.clear();
                    }
                    String country = jsonObject.getString("country");
                    String cases=jsonObject.getString("cases");
                    //yvalues.add(new Entry(Integer.valueOf(cases),0));
                    String tcases=jsonObject.getString("todayCases");
                    //yvalues.add(new Entry(Integer.valueOf(tcases),1));
                    String deaths=jsonObject.getString("deaths");
                    float percdeath=Float.parseFloat(deaths)/Float.parseFloat(cases)*100;
                    System.out.println(percdeath);
                    yvalues.add(new Entry(percdeath,0));
                    String tdeaths=jsonObject.getString("todayDeaths");
                    if(tdeaths.equals("0")){
                        yvalues.add(new Entry(0,1));
                    }else{
                        float perctdeath=Float.parseFloat(tdeaths)/Float.parseFloat(tcases)*100;
                        System.out.println(perctdeath);
                        yvalues.add(new Entry(perctdeath,1));
                    }

                    String recovered=jsonObject.getString("recovered");

                    if(recovered.equals("null")){
                        yvalues.add(new Entry(0,2));
                    }else{
                        float percrecovered=Float.parseFloat(recovered)/Float.parseFloat(cases)*100;
                        System.out.println(percrecovered);
                        yvalues.add(new Entry(percrecovered,2));
                    }

                    String active=jsonObject.getString("active");
                    /*
                    if(active.equals("null")){
                        yvalues.add(new Entry(0,3));
                    }else{
                        float percactive=Float.parseFloat(active)/Float.parseFloat(cases)*100;
                        yvalues.add(new Entry(percactive,3));
                    }
*/
                    String critical=jsonObject.getString("critical");
                    float perccritical=Float.parseFloat(critical)/Float.parseFloat(cases)*100;
                    System.out.println(perccritical);
                    yvalues.add(new Entry(perccritical,3));
                    String casePerMillion=jsonObject.getString("casesPerOneMillion");
                    //float percCasePerMillion=Float.parseFloat(casePerMillion)/Float.parseFloat(cases)*100;
                    //yvalues.add(new Entry(percCasePerMillion,5));
                    PieDataSet dataSet = new PieDataSet(yvalues, "");
                    pieChart.setDescription("Cases:"+cases+"\n"+"TodayCases:"+tcases+"\n"+"Active:"+"\n"+active+"CasePerMillion:"+casePerMillion);
                    ArrayList<String> xVals = new ArrayList<String>();
                    xVals.add("Death");
                    xVals.add("TodayDeaths");
                    xVals.add("Recovered");
                    xVals.add("Critical");

                    PieData data = new PieData(xVals,dataSet);
                    data.setValueFormatter(new PercentFormatter());
                    pieChart.setData(data);
                    dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
                    Legend l =pieChart.getLegend();
                    l.setWordWrapEnabled(true);
                    l.setYOffset(5f);
                    l.setFormSize(10f); // set the size of the legend forms/shapes
                    l.setForm(Legend.LegendForm.CIRCLE); // set what type of form/shape should be used
                    l.setTextSize(12f);
                    l.setTextColor(Color.BLACK);
                    data.setValueTextSize(5f);
                    data.setValueTextColor(Color.DKGRAY);
                    pieChart.animateXY(1400, 1400);
                    System.out.println(response);
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new MaterialAlertDialogBuilder(getContext())
                        .setTitle("Warning")
                        .setMessage(error.toString())
                        .setIcon(R.drawable.ic_warning)
                        .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                progressDialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


    }



}
