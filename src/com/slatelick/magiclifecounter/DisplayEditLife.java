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
		setContentView(R.layout.setlife);
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
