package com.zhuwb.mybobaoge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.zcy.hnkjxy.moudle_app.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "app", Toast.LENGTH_SHORT).show();
    }
}
