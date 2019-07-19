package com.example.cse460unit5project;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MyBMIActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_bmi_calculator);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button calcBmi = findViewById(R.id.calculate_bmi);

        calcBmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText height = findViewById(R.id.height);
                EditText weight = findViewById(R.id.weight);
                TextView resultBmiText = findViewById(R.id.bmi_output);
                TextView outputMessage = findViewById(R.id.message);
                float numHeight = Integer.parseInt(height.getText().toString());
                float numWeight = Integer.parseInt(weight.getText().toString());

                float result = numWeight / numHeight / numHeight * 703;

                //float resultBMI = ((numWeight / (numHeight * numHeight)));
                resultBmiText.setText(result + "");

                if(result < 18) {
                    outputMessage.setTextColor(Color.BLUE);
                    outputMessage.setText("You are underweight");
                } else if (result >= 18 && result <= 25) {
                    outputMessage.setTextColor(Color.GREEN);
                    outputMessage.setText("You are normal");
                } else if (result >= 25 && result < 30) {
                    outputMessage.setTextColor(Color.parseColor("#800080"));
                    outputMessage.setText("You are pre-obese");
                } else if (result >= 30) {
                    outputMessage.setTextColor(Color.RED);
                    outputMessage.setText("You are obese");
                }
            }
        });
    }
}
