package com.dingmouren.androiddemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dingmouren.androiddemo.R;

/**
 * Created by dingmouren on 2017/4/4.
 */

public class Demo3Activity extends AppCompatActivity {
    private TextView textView;
    private EditText editText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo3);
        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);
    }

    private void sayHello(View view){
        textView.setText("Hello,"+ editText.getText().toString());
    }
}
