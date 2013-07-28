package com.example.android.BluetoothChat;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

public class ActivityShowPicture extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture);
		Log.i("Activity","");
		if (getIntent().hasExtra(Constants.PARAM_EXTRA)){
			
//			String textBitmap = getIntent().getExtras().getString(Constants.PARAM_EXTRA);
//			((ImageView) findViewById(R.id.imageView)).setImageBitmap(stringToBitmap(
//					textBitmap));
		}
	}
	
	private Bitmap stringToBitmap(String encodedString) {
		try {
			byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
			Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
					encodeByte.length);
			return bitmap;
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}
}
