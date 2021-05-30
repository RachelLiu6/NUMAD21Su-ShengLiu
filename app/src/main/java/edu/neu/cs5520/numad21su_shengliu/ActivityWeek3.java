package edu.neu.cs5520.numad21su_shengliu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class ActivityWeek3 extends AppCompatActivity{
    String pressed = "Pressed: None";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week3);
        if (savedInstanceState !=null){
            pressed = savedInstanceState.getString("show");
        }
        TextView show = findViewById(R.id.showTextPress);
        show.setText(pressed);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.A_button:
                TextView show = findViewById(R.id.showTextPress);
                pressed = "Pressed: A";
                show.setText(pressed);
                break;

            case R.id.B_button:
                show = findViewById(R.id.showTextPress);
                pressed = "Pressed: B";
                show.setText(pressed);
                break;

            case R.id.C_button:
                show = findViewById(R.id.showTextPress);
                pressed = "Pressed: C";
                show.setText(pressed);
                break;

            case R.id.D_button:
                show = findViewById(R.id.showTextPress);
                pressed = "Pressed: D";
                show.setText(pressed);
                break;


            case R.id.E_button:
                show = findViewById(R.id.showTextPress);
                pressed = "Pressed: E";
                show.setText(pressed);
                break;

            case R.id.F_button:
                show = findViewById(R.id.showTextPress);
                pressed = "Pressed: F";
                show.setText(pressed);
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString("show", pressed);
    }
}
