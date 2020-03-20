package com.example.covid_19bd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //txtViewCase=(TextView)findViewById(R.id.textViewCase);
        //txtViewDeath=(TextView)findViewById(R.id.textViewDeath);
       // txtViewRecovered=(TextView)findViewById(R.id.textViewRecover);
        //getvalue();

        frameLayout=(FrameLayout)findViewById(R.id.container);
        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,new GlobalFragment()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment  selectedFragment=null;
                switch (item.getItemId()){
                    case R.id.global:
                        selectedFragment=new GlobalFragment();
                        break;

                    case R.id.local:
                        selectedFragment=new LocalFragment();

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container,selectedFragment).commit();
                return true;
            }
        });

    }



}
