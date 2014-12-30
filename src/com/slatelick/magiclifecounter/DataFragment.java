package com.slatelick.magiclifecounter;

import android.app.Fragment;
import android.os.Bundle;

public class DataFragment extends Fragment
{
	// data object we want to retain
    private long data;
	private boolean isRunning;

    // this method is only called once for this fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    }

    public void setTime(long data) {
        this.data = data;
    }

    public long getTime() {
        return data;
    }
	
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
	public boolean getRunning() {
		return isRunning;
	}
}
