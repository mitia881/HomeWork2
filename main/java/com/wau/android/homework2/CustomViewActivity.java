package com.wau.android.homework2;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;


public class CustomViewActivity extends AppCompatActivity implements DroViewListener {

    private ImageButton backButton;
    private DrawView customViewActivity;
    private TextView snackToastTextView;
    private Switch customViewSwitch;

    private final static String CUSTOM_VIEW_TAG = "CustomViewActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(CUSTOM_VIEW_TAG, "CustomView onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);


        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        customViewActivity = findViewById(R.id.circleCustomView);
        customViewActivity.setListener(this);

        snackToastTextView = findViewById(R.id.snackToastTextView);
        customViewSwitch = findViewById(R.id.customViewSwitch);
        customViewSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int switchTextId = isChecked ? R.string.use_snackbar : R.string.use_toast;
                snackToastTextView.setText(switchTextId);
            }
        });

    }

    @Override
    public void onCircleClick(String message) {
        if (customViewSwitch.isChecked()) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            Snackbar snackbar = Snackbar.make(snackToastTextView, message, Snackbar.LENGTH_SHORT);
            View snackBarView = snackbar.getView();
            TextView tv = snackBarView.findViewById(com.google.android.material.R.id.snackbar_text);
            tv.setTextColor(Color.RED);
            snackbar.show();
        }
    }
}