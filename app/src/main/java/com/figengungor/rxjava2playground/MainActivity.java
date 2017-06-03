package com.figengungor.rxjava2playground;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by figengungor on 6/3/2017.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button disposableBtn = (Button) findViewById(R.id.disposable_btn);
        Button compositeDisposableBtn = (Button) findViewById(R.id.composite_disposable_btn);

        disposableBtn.setOnClickListener(this);
        compositeDisposableBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.disposable_btn: startActivity(new Intent(MainActivity.this, DisposableActivity.class)); break;
            case R.id.composite_disposable_btn: startActivity(new Intent(MainActivity.this, CompositeDisposableActivity.class)); break;
        }
    }
}
