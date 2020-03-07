package com.wau.android.homework2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.ButtonPhonebookView).setOnClickListener(this);
        findViewById(R.id.ButtonCustomView).setOnClickListener(this);
        findViewById(R.id.ButtonWebPageView).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ButtonPhonebookView:
                Intent intent1 = new Intent(MainActivity.this, ContactViewActivity.class);
                startActivity(intent1);
                break;
            case R.id.ButtonCustomView:
                Intent intent2 = new Intent(MainActivity.this, CustomViewActivity.class);
                startActivity(intent2);
                break;
            case R.id.ButtonWebPageView:
                Intent intent3 = new Intent(MainActivity.this, WebPageActivity.class);
                startActivity(intent3);
                break;
        }
    }
}