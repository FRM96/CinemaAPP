package com.example.cinemaapp;

import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.TextView;
import com.example.cinemaapp.R;
import com.example.cinemaapp.models.Film;
import com.example.cinemaapp.test.Repository;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import cz.msebera.android.httpclient.Header;

import org.json.JSONArray;
import org.json.JSONException;



public class MainActivity extends AppCompatActivity{
    private TextView mTextMessage;
    private DrawerLayout drawer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        drawer = findViewById(R.id.drawer_layout);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Home()).commit();
        }
        DataDownload();
    }

    private void DataDownload(){
        final ProgressDialog progressDialog = new ProgressDialog(
                MainActivity.this);
        progressDialog.setMessage("Cargando Datos...");
        progressDialog.show();
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20000);
        client.get("http://vps687200.ovh.net/query.php", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    progressDialog.dismiss();
                    try {
                        JSONArray jsonarray = new JSONArray(new String(responseBody));
                        for (int i = 0; i < jsonarray.length(); i++) {
                            String superman = (jsonarray.getJSONObject(i).getString("title"));
                            Film film = new Film(jsonarray.getJSONObject(i).getString("title"),
                                    jsonarray.getJSONObject(i).getString("genre"),
                                    jsonarray.getJSONObject(i).getString("description"),
                                    jsonarray.getJSONObject(i).getDouble("rating"),
                                    132);
                            Repository.getHardcodedList().add(film);
                        }
                        System.out.println("safari2" + Repository.getHardcodedList());


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_Home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new Home()).commit();
                    return true;
                case R.id.nav_Reservation:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new Reservations()).commit();
                    return true;
                case R.id.nav_Favourites:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new Favourites()).commit();
                    return true;
            }

            return true;
        }
    };




}
