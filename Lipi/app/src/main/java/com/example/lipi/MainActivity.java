package com.example.lipi;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView addError = (TextView) findViewById(R.id.add_error);
        addError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(MainActivity.this,AddErrorActivity.class);
                startActivity(addIntent);
            }
        });
        TextView viewError = (TextView) findViewById(R.id.view_error);
        viewError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent(MainActivity.this,ViewErrorActivity.class);
                startActivity(viewIntent);
            }
        });
        TextView viewMachineError = (TextView) findViewById(R.id.search_machine);
        viewMachineError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent(MainActivity.this,ViewActivity.class);
                startActivity(viewIntent);
            }
        });
        TextView searchError = (TextView) findViewById(R.id.search_error);
        searchError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent(MainActivity.this,View2Activity.class);
                startActivity(viewIntent);
            }
        });

        TextView pieChart = (TextView) findViewById(R.id.pie_chart);
        pieChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent(MainActivity.this,ChartActivity.class);
                startActivity(viewIntent);
            }
        });
    }
}
