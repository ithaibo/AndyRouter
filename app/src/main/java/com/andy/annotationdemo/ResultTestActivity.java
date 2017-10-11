package com.andy.annotationdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.andy.annotation.Route;

/**
 * Created by Andy on 2017/10/10.
 */

@Route(path = "/app/test/result")
public class ResultTestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_page_result);

        ((TextView)findViewById(R.id.path)).setText("path of current activity: /app/test/1");

        findViewById(R.id.btnReturn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });

        Bundle data = getIntent().getBundleExtra("data");
        if (data !=null) {
            String from = data.getString("from");
            if (from!=null) {
                ((TextView)findViewById(R.id.value)).setText(from);
            }
        }
    }
}
