package com.glasshack.androidglass;

import android.os.Bundle;

public class MyActivity extends BluetoothActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    protected void readText() {
        
    }

    @Override
    protected void readAction() {

    }
}
