package com.bruno.imccalculator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btncCalcula = findViewById(R.id.btnCalcula);

        btncCalcula.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Calculadora.class);
            startActivity(intent);
        });
    }
}