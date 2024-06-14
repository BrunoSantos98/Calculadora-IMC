package com.bruno.imccalculator;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class Calculadora extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);

        Button btnCalc = findViewById(R.id.btnCalcula);
        Button btnClear = findViewById(R.id.btnLimpar);
        TextView weight = findViewById(R.id.weigthField);
        TextView height = findViewById(R.id.heightField);
        LinearLayout textResult01 = findViewById(R.id.layoutText01);
        TextView imcClassification = findViewById(R.id.imcClassification);
        ImageView table = findViewById(R.id.tableImg);

        textResult01.setAlpha(0.0F);
        imcClassification.setAlpha(0.0F);
        table.setAlpha(0.0F);
        btnClear.setEnabled(false);


        weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateButtonState(weight, height, btnClear);
                imcClassification.setAlpha(0.0F);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        height.addTextChangedListener(new TextWatcher()  {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateButtonState(weight, height, btnClear);
                imcClassification.setAlpha(0.0F);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields(weight, height, textResult01, imcClassification, table);
            }
        });
        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateResult(weight, height, textResult01, imcClassification, table);
            }
        });

    }

    private void updateButtonState(TextView weight, TextView height, Button btnClear){
        boolean hasText = weight.getText().length() > 0 || height.getText().length() > 0;
        btnClear.setEnabled(hasText);
        if(hasText){
            btnClear.setBackgroundColor(Color.parseColor("#D70000"));
        }else{
            btnClear.setBackgroundColor(Color.parseColor("#a5a5a5"));
        }
    }

    @SuppressLint("SetTextI18n")
    private void calculateResult(TextView weight, TextView height, LinearLayout textResult01,
                                 TextView imcClassification, ImageView table){

        TextView imcValue = findViewById(R.id.imcValue);
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);

        if(imm.isActive()){
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }

        if(weight.getText().toString().isEmpty() || height.getText().toString().isEmpty()){
            imcClassification.setTextColor(Color.parseColor("#FFE74C"));
            imcClassification.setText(R.string.emptyFields);
            imcClassification.setAlpha(1.0F);
            imcClassification.setAnimation(unhideAnimation());
        }else{
            double imcResult = Double.parseDouble(weight.getText().toString())/Math.pow(
                    Double.parseDouble(height.getText().toString()),2);

            DecimalFormat df = new DecimalFormat("0.00");
            imcValue.setText(df.format(imcResult) + ", ");

            if(imcResult < 18.5){
                imcClassification.setText(R.string.Magreza);
            }else if(imcResult < 24.9){
                imcClassification.setText(R.string.normal);
            }else if(imcResult < 29.9){
                imcClassification.setText(R.string.soprepeso);
            }else if(imcResult < 34.9){
                imcClassification.setText(R.string.obesidade1);
            }else if(imcResult < 39.9){
                imcClassification.setText(R.string.obesidade2);
            }else{
                imcClassification.setText(R.string.obesidade3);
            }

            textResult01.setAlpha(1.0F);
            textResult01.startAnimation(unhideAnimation());
            imcClassification.setAlpha(1.0F);
            imcClassification.startAnimation(unhideAnimation());
            table.setAlpha(1.0F);
            table.startAnimation(unhideAnimation());
        }


    }

    private void clearFields(TextView weight, TextView height, LinearLayout textResult01,
                             TextView imcClassificatrion, ImageView table){
        weight.setText("");
        height.setText("");
        weight.requestFocus();
        textResult01.setAlpha(0.0F);
        imcClassificatrion.setAlpha(0.0F);
        table.setAlpha(0.0F);
    }

    private AlphaAnimation unhideAnimation(){
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(600);
        return alphaAnimation;
    }
}