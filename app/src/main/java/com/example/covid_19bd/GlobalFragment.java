package com.example.covid_19bd;

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

import org.json.JSONException;
import org.json.JSONObject;


public class GlobalFragment extends Fragment {

    TextView txtViewCase,txtViewDeath,txtViewRecovered;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_global, container, false);
        txtViewCase=(TextView)view.findViewById(R.id.textViewCase);
        txtViewDeath=(TextView)view.findViewById(R.id.textViewDeath);
        txtViewRecovered=(TextView)view.findViewById(R.id.textViewRecover);
        getvalue();

        return  view;

    }



    private  void getvalue(){
        String url="https://coronavirus-19-api.herokuapp.com/all";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String cases=jsonObject.getString("cases");
                    String death=jsonObject.getString("deaths");
                    String recover=jsonObject.getString("recovered");
                    txtViewCase.setText(cases);
                    txtViewDeath.setText(death);
                    txtViewRecovered.setText(recover);
                    System.out.println(cases+death+recover);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }


}
