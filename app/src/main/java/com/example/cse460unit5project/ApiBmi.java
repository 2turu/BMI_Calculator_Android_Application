package com.example.cse460unit5project;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.AsyncTask;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiBmi extends AppCompatActivity {

    private TextView resultBmiText;
    private TextView outputMessage;
    private Button calcBmi;
    private Button educate;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.api_calculator);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        calcBmi = findViewById(R.id.calculate_bmi);
        educate = findViewById(R.id.educate_me);
        resultBmiText = findViewById(R.id.bmi_output);
        outputMessage = findViewById(R.id.message);

        requestQueue = Volley.newRequestQueue(this);

        calcBmi.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                getBmi();
            }
        });

        educate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.cdc.gov/healthyweight/assessing/bmi/index.html"));
                startActivity(browserIntent);
            }
        });
    }

    private void getBmi() {
        EditText height = findViewById(R.id.height);
        EditText weight = findViewById(R.id.weight);

        float numHeight = Integer.parseInt(height.getText().toString());
        float numWeight = Integer.parseInt(weight.getText().toString());


        String url = String.format("http://webstrar99.fulton.asu.edu/page3/Service1.svc/calculateBMI?height=%s&weight=%s", numHeight, numWeight);

        JsonObjectRequest objReq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String bmiResult = response.getString("bmi").toString();
                            resultBmiText.setText(bmiResult + "");
                            String riskOutput = response.getString("risk").toString();
                            outputMessage.setText(riskOutput + "");
                            float result = Float.parseFloat(resultBmiText.getText().toString());
                            if(result < 18) {
                                outputMessage.setTextColor(Color.BLUE);
                            } else if (result >= 18 && result <= 25) {
                                outputMessage.setTextColor(Color.GREEN);
                            } else if (result >= 25 && result < 30) {
                                outputMessage.setTextColor(Color.parseColor("#800080"));
                            } else if (result >= 30) {
                                outputMessage.setTextColor(Color.RED);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                    }
                }
        );
        requestQueue.add(objReq);
    }
}