package com.glasshack.androidglass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.glasshack.androidglass.entity.Entity;
import com.glasshack.androidglass.entity.utils.SystemUtils;

public class MyActivity extends BluetoothActivity {

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;

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
                    message.setText("");
                }
            }
        });

        Button imagePick = (Button) findViewById(R.id.image_picker);
        imagePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagePicker();
            }
        });
    }

    @Override
    protected void readText(Entity entity) {
        ((TextView) findViewById(R.id.received_message)).setText(entity.getData().toString());
    }

    @Override
    protected void readImage(Entity entity) {
        ((ImageView) findViewById(R.id.image)).setImageBitmap(SystemUtils.stringToBitmap(
                (String) entity.getData()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent serverIntent = null;
        switch (item.getItemId()) {
            case R.id.secure_connect_scan:
                // Launch the DeviceListActivity to see devices and do scan
                serverIntent = new Intent(this, DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
                return true;
            case R.id.insecure_connect_scan:
                // Launch the DeviceListActivity to see devices and do scan
                serverIntent = new Intent(this, DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_INSECURE);
                return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE_SECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, true);
                }
                break;
            case REQUEST_CONNECT_DEVICE_INSECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, false);
                }
                break;
        }
    }

    /*
     * Write
	 * ObjectMapper mapper = new ObjectMapper();
	 * Entity entity = new Entity(4, "Ok, Glass");
	 * String result = mapper.writeValueAsString(entity);
	 */
    
    /*
     * Write Bitmap
	 * ObjectMapper mapper = new ObjectMapper();
	 * Entity entity = new Entity(4, SystemUtils.bitmapToString(bitmap));
	 * String result = mapper.writeValueAsString(entity);
	 */

	/* Read 
	 * Entity entityResult = mapper.readValue(result, Entity.class);
	 * */
    
    /* Read bitmap
	 * Entity entityResult = mapper.readValue(result, Entity.class);
	 * ((ImageView) findViewById(R.id.image_parse)).setImageBitmap(SystemUtils.stringToBitmap((String)entityResult.getData())
	 * */
}
