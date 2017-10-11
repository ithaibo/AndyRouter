package com.andy.annotationdemo;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.andy.annotation.Route;
import com.andy.annotationdemo.databinding.ActivityMainBinding;
import com.andy.router.Router;
import com.andy.router.RouterWareHouse;

import java.util.HashMap;
import java.util.Map;

@Route(path = "/app/main")
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainBinding.setClicker(this);
        Map<String, Object> map = new HashMap<>();
        new RouterWareHouse().gatherActivityClass(map);
        Router.getInstance().init(map);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn4simple:
                Router.getInstance()
                        .navigateTo(this, "/app/test/1");
                break;
            case R.id.btn4result:
                new Router.IntentBuilder(this)
                        .setPath("/app/test/result")
                        .setRequstCode(10)
                        .withString("from", "MainActivity")
                        .buildRouter()
                        .navigate();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            Toast.makeText(this, "get result from /app/test/result", Toast.LENGTH_SHORT).show();
        }
    }
}
