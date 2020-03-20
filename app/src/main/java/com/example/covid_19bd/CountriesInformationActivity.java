package com.example.covid_19bd;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CountriesInformationActivity extends AppCompatActivity {

    ListView listView;
    ProgressDialog progressDialog;
    ArrayList<CountryEntity>countryEntityArrayList;
    ListViewAdapter listViewAdapter;

    EditText editTextSearch;
    ImageButton searchBtn,clearSearch;
    LinearLayout linearLayout;
    TextView textViewCountry,textViewCases,textViewTodayCases,textViewDeaths,textViewTodayDeaths,textViewActive,textViewRecovered,textViewCritical,textViewCasePerMillion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries_information);


        editTextSearch=(EditText)findViewById(R.id.searchEditText);
        searchBtn=(ImageButton) findViewById(R.id.searchbtn);


        linearLayout=(LinearLayout)findViewById(R.id.searchResult);

        textViewCountry=(TextView)findViewById(R.id.countryName);
        textViewCases=(TextView)findViewById(R.id.txtCases);
        textViewTodayCases=(TextView)findViewById(R.id.todayCases);
        textViewDeaths=(TextView)findViewById(R.id.deaths);
        textViewTodayDeaths=(TextView)findViewById(R.id.todaydeaths);
        textViewActive=(TextView)findViewById(R.id.active);
        textViewRecovered=(TextView)findViewById(R.id.recovered);
        textViewCritical=(TextView)findViewById(R.id.critical);
        textViewCasePerMillion=(TextView)findViewById(R.id.casePerMillion);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text=editTextSearch.getText().toString();
                searchCountry(text);

            }
        });

        clearSearch=(ImageButton)findViewById(R.id.clearsearch);
        clearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayout.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                editTextSearch.setText("");
            }
        });


        listView=(ListView)findViewById(R.id.countriesList);



        showgetData();
        getCountryInformation();


    }
    private void showgetData(){
        this.progressDialog= new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setTitle("Getting Data");
        progressDialog.show();
    }

    private void getCountryInformation(){
        String url="https://coronavirus-19-api.herokuapp.com/countries";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray dataArray = new JSONArray(response);
                    countryEntityArrayList = new ArrayList<>();
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
                    }
                    if(!(DataHold.countryEntityArrayList ==null)){
                        DataHold.countryEntityArrayList.clear();
                    }
                    DataHold.countryEntityArrayList=countryEntityArrayList;
                    if(!(DataHold.countryEntityArrayList==null) && DataHold.countryEntityArrayList.size()>0){
                        listViewAdapter=new ListViewAdapter(CountriesInformationActivity.this,DataHold.countryEntityArrayList);
                        listView.setAdapter(listViewAdapter);
                    }else{
                        listView.setAdapter(null);
                    }
                    progressDialog.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CountriesInformationActivity.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void searchCountry(String s){

        String url="https://coronavirus-19-api.herokuapp.com/countries/"+s;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String country = jsonObject.getString("country");
                    String cases=jsonObject.getString("cases");
                    String tcases=jsonObject.getString("todayCases");
                    String deaths=jsonObject.getString("deaths");
                    String tdeaths=jsonObject.getString("todayDeaths");
                    String active=jsonObject.getString("active");
                    String recovered=jsonObject.getString("recovered");
                    String critical=jsonObject.getString("critical");
                    String casePerMillion=jsonObject.getString("casesPerOneMillion");

                    textViewCountry.setText(country);
                    textViewCases.setText("Cases: "+cases);
                    textViewTodayCases.setText("Today:"+tcases);
                    textViewDeaths.setText("Deaths:"+deaths);
                    textViewTodayDeaths.setText("Today:"+tdeaths);
                    textViewActive.setText("Active:"+active);
                    textViewRecovered.setText("Recovered:"+recovered);
                    textViewCritical.setText("Critical: "+critical);
                    textViewCasePerMillion.setText("CasesPerOneMillion: "+casePerMillion);

                    linearLayout.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.INVISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(CountriesInformationActivity.this);
        requestQueue.add(stringRequest);


    }

}
