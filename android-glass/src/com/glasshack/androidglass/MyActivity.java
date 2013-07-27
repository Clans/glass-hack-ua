package com.glasshack.androidglass;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MyActivity extends BluetoothActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final EditText message = (EditText) findViewById(R.id.message);

        Button button = (Button) findViewById(R.id.send);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(message.getText())) {
                    sendText(message.getText().toString());
                }
            }
        });
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
