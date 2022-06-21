package com.example.finalproj_2022_1;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class WeatherFragment extends Fragment {
    TextView countryName, min, max;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        super.onCreate(savedInstanceState);

        countryName = (TextView) view.findViewById(R.id.country_name);
        min = (TextView) view.findViewById(R.id.temp_min);
        max = (TextView) view.findViewById(R.id.temp_max);

        DecimalFormat df = new DecimalFormat("0.0");


        String url = "https://api.openweathermap.org/data/2.5/weather?q=Osaka&appid=2b7453af9772f8479eddcdb7494c3185";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("main");

                            double temp_min = jsonObject1.getDouble("temp_min") - 273.15;
                            min.setText("🔻최저기온 : " + df.format(temp_min));

                            double temp_max = jsonObject1.getDouble("temp_max") - 273.15;
                            max.setText("🔺최고기온 : " + df.format(temp_max));

                            String cityName = jsonObject.getString("name");

                            countryName.setText(cityName);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);
        return  view;
    }
}