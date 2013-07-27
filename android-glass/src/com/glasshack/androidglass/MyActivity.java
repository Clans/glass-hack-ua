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

	/*
	 * Write 
	 * ObjectMapper mapper = new ObjectMapper();
	 * Entity entity = new
	 * Entity(4, "Ok, Glass");
	 * String result = mapper.writeValueAsString(entity);
	 */

	/* Read 
	 * Entity entityResult = mapper.readValue(resultJsonString, Entity.class);
	 * */
}
