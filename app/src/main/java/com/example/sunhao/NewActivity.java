package com.example.sunhao;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class NewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        TextView text = (TextView) findViewById(R.id.text);
        text.setText("After Click");
    }
}
