package com.example.dell.assignment.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.dell.assignment.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void QlThu(View view) {
        startActivity(new Intent(MainActivity.this,ThuActivity.class));
    }

    public void QlChi(View view) {
        startActivity(new Intent(MainActivity.this,ChiActivity.class));
    }
}
