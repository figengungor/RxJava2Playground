package com.figengungor.rxjava2playground;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = MainActivity.class.getSimpleName() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void filteringObservables(View v) {
        startActivity(new Intent(this, FilteringObservablesActivity.class));
    }

    public void transformingObservables(View v) {
        startActivity(new Intent(this, TransformingObservablesActivity.class));
    }
}
