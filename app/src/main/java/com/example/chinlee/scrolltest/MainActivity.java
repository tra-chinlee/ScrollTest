package com.example.chinlee.scrolltest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button first = (Button)findViewById(R.id.scroll_to_first);
        Button second = (Button)findViewById(R.id.scroll_to_second);
        Button third = (Button)findViewById(R.id.scroll_to_third);
        final ScrollLayout1 layout1 = (ScrollLayout1)findViewById(R.id.scroll_layout1);

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout1.setCurrentItem(0);
            }
        });

        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout1.setCurrentItem(1);
            }
        });

        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout1.setCurrentItem(2);
            }
        });
    }
}
