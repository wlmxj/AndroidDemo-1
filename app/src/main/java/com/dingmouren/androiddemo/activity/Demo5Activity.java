package com.dingmouren.androiddemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.dingmouren.androiddemo.R;
import com.dingmouren.androiddemo.listener.ToggleListener;
import com.dingmouren.androiddemo.widgets.ToggleButton;

/**
 * Created by dingmouren on 2017/4/6.
 */

public class Demo5Activity  extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo5);
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggle_btn);
        toggleButton.setOnToggledListener(new ToggleListener() {
            @Override
            public void onToggled(boolean isOpened) {
                if (isOpened)
                    Toast.makeText(Demo5Activity.this,"开",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Demo5Activity.this,"关",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
