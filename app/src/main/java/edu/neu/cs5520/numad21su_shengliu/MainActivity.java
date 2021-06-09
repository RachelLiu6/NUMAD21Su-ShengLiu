package edu.neu.cs5520.numad21su_shengliu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    private Button button;
    private Button buttonLink; // week4's button
    private Button buttonLocation; // week5's button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonLocation = (Button) findViewById(R.id.buttonLocation);
        button = (Button) findViewById(R.id.launcher2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2();
            }
        });

        buttonLink = (Button) findViewById(R.id.links);
        buttonLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLinkCollector();
            }
        });

        buttonLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLocator();
            }
        });
    }

    //week3 code
    private void openActivity2() {
        Intent intent = new Intent(this, ActivityWeek3.class);
        startActivity(intent);
    }

    //Week1 code
    public void introduceMyself(View view) {
        TextView textView = findViewById(R.id.myInfo);
        textView.setVisibility(View.VISIBLE);
    }

    //week4 code
    public void openLinkCollector() {
        Intent intent = new Intent(this, LinksListWeek4.class);
        startActivity(intent);
    }

    //week5 code
    private void openLocator() {
        Intent intent = new Intent(this, LocatorWeek5.class);
        startActivity(intent);
    }
}