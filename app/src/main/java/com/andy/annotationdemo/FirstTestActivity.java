package com.andy.annotationdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.andy.annotation.Route;

/**
 * Created by Andy on 2017/10/10.
 */

@Route(path = "/app/test/1")
public class FirstTestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_page_1);

        ((TextView)findViewById(R.id.path)).setText("path of current activity: /app/test/1");
    }
}
