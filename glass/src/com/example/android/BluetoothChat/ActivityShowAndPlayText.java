package com.example.android.BluetoothChat;

import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityShowAndPlayText extends Activity implements OnInitListener {
	private TextToSpeech tts;
	TextView textView ;
	String textToShow;
	FrameLayout flRoot;
	//ScrollView scrollView;
	private boolean isInited;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activty_text);
		isInited = false;
		tts = new TextToSpeech(this, this);
		flRoot = (FrameLayout)findViewById(R.id.flRoot);
		if (getIntent().hasExtra(Constants.PARAM_EXTRA)){
			textToShow = getIntent().getExtras().getString(Constants.PARAM_EXTRA);
			textView = (TextView) findViewById(R.id.textView);
			textView.setText(textToShow);
			flRoot.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (isInited)
						speakOut(textToShow);
				}
			});
		}
		
	}
	
	private void speakOut(String text) {
		// Get the text typed
		//String text = textToShow;
		// If no text is typed, tts will read out 'You haven't typed text'
		// else it reads out the text you typed
		if (text.length() == 0) {
			tts.speak("You haven't typed text", TextToSpeech.QUEUE_FLUSH, null);
		} else {
			tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
		}

	}

	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			// Setting speech language
			int result = tts.setLanguage(Locale.US);
			// If your device doesn't support language you set above
			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				// Cook simple toast message with message
				Toast.makeText(this, "Language not supported",
						Toast.LENGTH_LONG).show();
				Log.e("TTS", "Language is not supported");
			}
			// Enable the button - It was disabled in main.xml (Go back and
			// Check it)
			else {
				isInited = true;
			}
			// TTS is not initialized properly
		} else {
			Toast.makeText(this, "TTS Initilization Failed", Toast.LENGTH_LONG)
					.show();
			Log.e("TTS", "Initilization Failed");
		}
	}
	
	public void onDestroy() {
		// Don't forget to shutdown!
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}
		super.onDestroy();
	}
}
