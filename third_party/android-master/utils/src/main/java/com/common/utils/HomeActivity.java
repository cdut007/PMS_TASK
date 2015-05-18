package com.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class HomeActivity extends Activity {

    Context mContext;
    Activity mActivity;
    ListView sdkFunctionalityList;
    TextView txtDemo, txtCodeSnip;
    private TextView txtMaterialLibrary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mContext = HomeActivity.this;
        mActivity = HomeActivity.this;

        txtDemo = (TextView) findViewById(R.id.txtDemo);
        txtCodeSnip = (TextView) findViewById(R.id.txtCodeSnip);
        txtMaterialLibrary = (TextView) findViewById(R.id.txtMaterialLibrary);

        txtDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intdemo = new Intent(mContext, MainActivity.class);
                startActivity(intdemo);
            }
        });

        txtCodeSnip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intcode = new Intent(mContext, CodeSnippestHomeActivity.class);
                startActivity(intcode);
            }
        });

        txtMaterialLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intcode = new Intent(mContext, MaterialLibrariesListActivity.class);
                startActivity(intcode);
            }
        });
    }

}
