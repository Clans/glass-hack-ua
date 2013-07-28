package com.example.glass3;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.glass3.entity.Entity;
import com.example.glass3.utils.SystemUtils;

public class MyActivity extends BluetoothActivity implements OnInitListener,
		OnClickListener {

	// Intent request codes
	private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
	private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
	TextToSpeech tts;
	RelativeLayout rrTransparent;
	boolean isInited;
	ImageView imageView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		isInited = false;
		tts = new TextToSpeech(this, this);
		rrTransparent = (RelativeLayout) findViewById(R.id.rrTransparent);
		rrTransparent.setOnClickListener(this);
	}

	private void speakOut(String text) {
		if (text.length() == 0) {
			tts.speak("You haven't typed text", TextToSpeech.QUEUE_FLUSH, null);
		} else {
			tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
		}
	}

	@Override
	protected void readText(final Entity entity) {
		TextView textView = ((TextView) findViewById(R.id.received_message));
		textView.setVisibility(View.VISIBLE);
		textView.setText(entity.getData().toString());
		speakOut(entity.getData().toString());
		textView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				speakOut(entity.getData().toString());
			}
		});
		((ImageView) findViewById(R.id.image)).setVisibility(View.GONE);

	}

	@Override
	protected void readImage(Entity entity) {
		imageView = ((ImageView) findViewById(R.id.image));
		imageView.setVisibility(View.VISIBLE);
		imageView.setImageBitmap(SystemUtils.stringToBitmap((String) entity
				.getData()));
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				rrTransparent.setVisibility(View.VISIBLE);
			}
		});
		((TextView) findViewById(R.id.received_message))
				.setVisibility(View.GONE);
	}

	@Override
	public void onClick(View v) {
		rrTransparent.setVisibility(View.GONE);
		Bitmap bm = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
		sendImage(bm);
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
			startActivityForResult(serverIntent,
					REQUEST_CONNECT_DEVICE_INSECURE);
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

	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			int result = tts.setLanguage(Locale.US);
			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Toast.makeText(this, "Language not supported",
						Toast.LENGTH_LONG).show();
				Log.e("TTS", "Language is not supported");
			} else {
				isInited = true;
			}
		} else {
			Toast.makeText(this, "TTS Initilization Failed", Toast.LENGTH_LONG)
					.show();
			Log.e("TTS", "Initilization Failed");
		}
	}

}
