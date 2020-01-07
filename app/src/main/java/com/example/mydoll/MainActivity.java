package com.example.mydoll;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.view.ViewGroup;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    DrawingView dv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        dv = new DrawingView(this.getBaseContext());
        ViewGroup view_group = (ViewGroup) findViewById(R.id.drawing);
        view_group.addView(dv);

        Button resetButton = findViewById(R.id.reset);
        Button aboutButton = findViewById(R.id.about);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dv.reset();
            }
        });

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog ad = new AlertDialog.Builder(v.getContext())
                        .setTitle("About")
                        .setMessage("My Doll\nYufei Chen\n20707983")
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });
    }
}
