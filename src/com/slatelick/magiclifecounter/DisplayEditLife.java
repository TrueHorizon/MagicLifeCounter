package com.slatelick.magiclifecounter;

import android.app.*;
import android.os.*;
import android.content.*;
import android.widget.*;
import android.view.*;
import android.support.v4.app.*;

public class DisplayEditLife extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Get the message from the intent
		Intent intent = getIntent();
		String message = intent.getStringExtra("test");

		// Create the text view
		TextView textView = new TextView(this);
		textView.setTextSize(40);
		textView.setText(message);

		// Set the text view as the activity layout
		setContentView(textView);
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
			case android.R.id.home:
				NavUtils.navigateUpFromSameTask(this);
				return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
