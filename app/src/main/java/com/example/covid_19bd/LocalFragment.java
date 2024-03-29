package com.example.covid_19bd;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class LocalFragment extends Fragment {

    //ListView listView;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    ProgressDialog progressDialog;
    AutoCompleteTextView autoCompleteTextView;
    ImageButton searchBtn,clearSearch;
    LinearLayout linearLayout;
    TextView textViewCountry,textViewCases,textViewTodayCases,textViewDeaths,textViewTodayDeaths,textViewActive,textViewRecovered,textViewCritical,textViewCasePerMillion;
    ListViewAdapter listViewAdapter;
    ArrayList<CountryEntity>countryEntityArrayList;
    ArrayList<String> country;
    DecimalFormat decimalFormat=new DecimalFormat("#,###,###");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_local, container, false);


        autoCompleteTextView=(AutoCompleteTextView) view.findViewById(R.id.searchAutoCompletText);
        searchBtn=(ImageButton) view.findViewById(R.id.searchbtn);

        linearLayout=(LinearLayout)view.findViewById(R.id.searchResult);

        textViewCountry=(TextView)view.findViewById(R.id.countryName);
        textViewCases=(TextView)view.findViewById(R.id.txtCases);
        textViewTodayCases=(TextView)view.findViewById(R.id.todayCases);
        textViewDeaths=(TextView)view.findViewById(R.id.deaths);
        textViewTodayDeaths=(TextView)view.findViewById(R.id.todaydeaths);
        textViewActive=(TextView)view.findViewById(R.id.active);
        textViewRecovered=(TextView)view.findViewById(R.id.recovered);
        textViewCritical=(TextView)view.findViewById(R.id.critical);
        textViewCasePerMillion=(TextView)view.findViewById(R.id.casePerMillion);

        recyclerView=(RecyclerView) view.findViewById(R.id.countriesList);
        getCountryInformation();
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text=autoCompleteTextView.getText().toString();
                System.out.println(text);
                searchCountry(text);

            }
        });



        clearSearch=(ImageButton)view.findViewById(R.id.clearsearch);
        clearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                autoCompleteTextView.setText("");
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
                    countryEntityArrayList = new ArrayList<>();
                    country=new ArrayList<>();
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject jsonObject = dataArray.getJSONObject(i);
                        CountryEntity countryEntity = new CountryEntity();
                        countryEntity.setCountry(jsonObject.getString("country"));
                        countryEntity.setCases(jsonObject.getString("cases"));
                        countryEntity.setTodayCases(jsonObject.getString("todayCases"));
                        countryEntity.setDeaths(jsonObject.getString("deaths"));
                        countryEntity.setTodayDeaths(jsonObject.getString("todayDeaths"));
                        countryEntity.setRecovered(jsonObject.getString("recovered"));
                        countryEntity.setActive(jsonObject.getString("active"));
                        countryEntity.setCritical(jsonObject.getString("critical"));
                        countryEntity.setCaseperMillion(jsonObject.getString("casesPerOneMillion"));
                        System.out.println(jsonObject.toString());
                        countryEntityArrayList.add(countryEntity);
                        country.add(jsonObject.getString("country"));
                    }
                    if(!(DataHold.countryEntityArrayList ==null)){
                        DataHold.countryEntityArrayList.clear();
                        DataHold.countries.clear();
                    }
                    DataHold.countryEntityArrayList=countryEntityArrayList;
                    DataHold.countries=country;
                    System.out.println(DataHold.countryEntityArrayList.size());
                    if(!(DataHold.countryEntityArrayList==null) && DataHold.countryEntityArrayList.size()>0){
                        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,DataHold.countries);
                        autoCompleteTextView.setThreshold(1);
                        autoCompleteTextView.setAdapter(arrayAdapter);
                        //listViewAdapter=new ListViewAdapter(getContext(),DataHold.countryEntityArrayList);
                        //listView.setAdapter(listViewAdapter);
                        recyclerViewAdapter=new RecyclerViewAdapter(getContext(),countryEntityArrayList);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(recyclerViewAdapter);
                    }else{
                        recyclerView.setAdapter(null);

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
                    String country = jsonObject.getString("country");
                    String cases=jsonObject.getString("cases");
                    String tcases=jsonObject.getString("todayCases");
                    String deaths=jsonObject.getString("deaths");
                    String tdeaths=jsonObject.getString("todayDeaths");
                    String recovered=jsonObject.getString("recovered");
                    String active=jsonObject.getString("active");
                    String critical=jsonObject.getString("critical");
                    String casePerMillion=jsonObject.getString("casesPerOneMillion");

                    textViewCountry.setText(country);
                    textViewCases.setText("Cases: "+decimalFormat.format(Integer.valueOf(cases)));
                    textViewTodayCases.setText("Today:"+decimalFormat.format(Integer.valueOf(tcases)));
                    textViewDeaths.setText("Deaths:"+decimalFormat.format(Integer.valueOf(deaths)));
                    textViewTodayDeaths.setText("Today:"+decimalFormat.format(Integer.valueOf(tdeaths)));
                    textViewRecovered.setText("Recovered:"+decimalFormat.format(Integer.valueOf(recovered)));
                    textViewActive.setText("Active:"+decimalFormat.format(Integer.valueOf(active)));
                    textViewCritical.setText("Critical: "+decimalFormat.format(Integer.valueOf(critical)));
                    textViewCasePerMillion.setText("CasesPerOneMillion: "+decimalFormat.format(Integer.valueOf(casePerMillion)));

                    linearLayout.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
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
