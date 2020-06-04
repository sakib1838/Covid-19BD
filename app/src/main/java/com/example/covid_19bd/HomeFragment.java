package com.example.covid_19bd;

import android.accessibilityservice.FingerprintGestureController;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;


public class HomeFragment extends Fragment {
private ProgressDialog progressDialog;
private TextView textViewCases,textViewTodayCases,textViewDeaths,textViewTodayDeaths,textViewRecovered,textViewActive,textViewCritical,textViewCasePerMillion,textViewtotalTest,textViewTestPerMillion;
    DecimalFormat decimalFormat=new DecimalFormat("#,###,###");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        textViewCases=(TextView)view.findViewById(R.id.cases);
        textViewTodayCases=(TextView)view.findViewById(R.id.todayCases);
        textViewDeaths=(TextView)view.findViewById(R.id.deaths);
        textViewTodayDeaths=(TextView)view.findViewById(R.id.todaydeaths);
        textViewActive=(TextView)view.findViewById(R.id.active);
        textViewRecovered=(TextView)view.findViewById(R.id.recovered);
        textViewCritical=(TextView)view.findViewById(R.id.critical);
        textViewCasePerMillion=(TextView)view.findViewById(R.id.casePerMillion);
        textViewtotalTest=(TextView)view.findViewById(R.id.totaltest);
        textViewTestPerMillion=(TextView)view.findViewById(R.id.testPerMillion);
        getCountry();
        return  view;
    }

    private void showgetData(){
        this.progressDialog= new ProgressDialog(getContext());
        progressDialog.setCancelable(true);
        progressDialog.setTitle("Getting Data");
        progressDialog.show();
    }

    private void getCountry(){
        showgetData();
        String url="https://coronavirus-19-api.herokuapp.com/countries/Bangladesh";
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
                    String totalTests=jsonObject.getString("totalTests");
                    String testPerMillion=jsonObject.getString("testsPerOneMillion");
                    System.out.println(response);
                    textViewCases.setText(decimalFormat.format(Integer.valueOf(cases)));
                    textViewTodayCases.setText(decimalFormat.format(Integer.valueOf(tcases)));
                    textViewDeaths.setText(decimalFormat.format(Integer.valueOf(deaths)));
                    textViewTodayDeaths.setText(decimalFormat.format(Integer.valueOf(tdeaths)));
                    textViewRecovered.setText(decimalFormat.format(Integer.valueOf(recovered)));
                    textViewActive.setText(decimalFormat.format(Integer.valueOf(active)));
                    textViewCritical.setText(decimalFormat.format(Integer.valueOf(critical)));
                    textViewCasePerMillion.setText(decimalFormat.format(Integer.valueOf(casePerMillion)));
                    textViewtotalTest.setText(decimalFormat.format(Integer.valueOf(totalTests)));
                    textViewTestPerMillion.setText(decimalFormat.format(Integer.valueOf(testPerMillion)));
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
