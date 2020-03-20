package com.example.covid_19bd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView txtViewCase,txtViewDeath,txtViewRecovered;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtViewCase=(TextView)findViewById(R.id.textViewCase);
        txtViewDeath=(TextView)findViewById(R.id.textViewDeath);
        txtViewRecovered=(TextView)findViewById(R.id.textViewRecover);
        getvalue();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.local:
                startActivity(new Intent(MainActivity.this,CountriesInformationActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

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
        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }
}
