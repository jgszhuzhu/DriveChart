package com.motor.example;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
public class MainActivity extends AppCompatActivity {
    DriveChart slimChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slimChart = findViewById(R.id.slimChart);

        slimChart.setShade(false);
        slimChart.setStrokeWidth(10);
        slimChart.setDegree(120);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slimChart.setShade(true);
                slimChart.setStrokeWidth(13);

                slimChart.setDegree(slimChart.getDegree()+20);
            }
        });
    }
}
