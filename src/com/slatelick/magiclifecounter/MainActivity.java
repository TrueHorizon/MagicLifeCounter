package com.slatelick.magiclifecounter;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.view.View.*;
import android.content.res.*;
import android.content.*;
import android.support.v4.app.DialogFragment;

public class MainActivity extends Activity {
	private TextView textTimer;
	private Button startStopButton;
	private CountDownTimer countDownTimer;
	
	// Boolean to determine if timer is started or paused
	private boolean isStarted = false;
	// Saves the timer for the pause resume function
	private long millisecondsSaved = 3000000;
	private DataFragment dataFragment;
	
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		// Get timer by id
		textTimer = (TextView) findViewById(R.id.roundtimer);
		startStopButton = (Button) findViewById(R.id.start_stop_timer);
		
		// find the retained fragment on activity restarts
        FragmentManager fm = getFragmentManager();
        dataFragment = (DataFragment) fm.findFragmentByTag("data");

        // create the fragment and data the first time
        if (dataFragment == null) {
            // add the fragment
            dataFragment = new DataFragment();
            fm.beginTransaction().add(dataFragment, "data").commit();
            // load the data from the web
            dataFragment.setTime(millisecondsSaved);
			dataFragment.setRunning(isStarted);
        } else {
			millisecondsSaved = dataFragment.getTime();
			isStarted = dataFragment.getRunning();
			if (isStarted) {
				countDownTimer = new MyCountDownTimer(millisecondsSaved, 10);
				countDownTimer.start();
				isStarted = true;
				startStopButton.setText("PAUSE");
			} else {
				SetTime(millisecondsSaved);
				startStopButton.setText("START");
			}
		}
	}
	
	@Override
    public void onDestroy() {
        super.onDestroy();
        // store the data in the fragment
        dataFragment.setTime(millisecondsSaved);
		dataFragment.setRunning(isStarted);
    }
	
	// Adds one to the players life total
	public void addOneLifePlayer(View view) {
		addOneById(R.id.player_life_total);
	}
	
	// Adds one to the opponents life total
	public void addOneLifeOpp(View view) {
		addOneById(R.id.opponent_life_total);
	}
	
	// Generic add method which works on id
	private void addOneById(int id) {
		// get opponent life total
		TextView textLifeTotal = (TextView) findViewById(id);
		int numLifeTotal = Integer.parseInt(textLifeTotal.getText().toString());
		// add one to the life total
		numLifeTotal++;
		// set the life total
		textLifeTotal.setText(Integer.toString(numLifeTotal));
	}
	
	// Subtracts one from the players life total
	public void subtractOneLifePlayer(View view) {
		subtractOneById(R.id.player_life_total);
	}

	// Subtracts one from the opponents life total
	public void subtractOneLifeOpp(View view) {
		subtractOneById(R.id.opponent_life_total);
	}
	
	// Generic subtraction method which works on id 
	private void subtractOneById(int id) {
		// get opponent life total
		TextView textLifeTotal = (TextView) findViewById(id);
		int numLifeTotal = Integer.parseInt(textLifeTotal.getText().toString());
		// subtract one from the life total
		numLifeTotal--;
		// set the life total
		textLifeTotal.setText(Integer.toString(numLifeTotal));
	}
	
	public void addOneCmdDamageOpp(View view) {
		addOneById(R.id.opponent_cmd_damage_total);
		subtractOneById(R.id.opponent_life_total);
	}
	
	public void subtractOneCmdDamageOpp(View view) {
		subtractOneById(R.id.opponent_cmd_damage_total);
		addOneById(R.id.opponent_life_total);
	}
	
	public void addOneCmdDamagePlayer(View view) {
		addOneById(R.id.player_cmd_damage_total);
		subtractOneById(R.id.player_life_total);
	}

	public void subtractOneCmdDamagePlayer(View view) {
		subtractOneById(R.id.player_cmd_damage_total);
		addOneById(R.id.player_life_total);
	}
	
	public void addOneInfectDamageOpp(View view) {
		addOneById(R.id.opponent_infect_total);
	}
	
	public void subtractOneInfectDamageOpp(View view) {
		subtractOneById(R.id.opponent_infect_total);
	}
	
	public void addOneInfectDamagePlayer(View view) {
		addOneById(R.id.player_infect_total);
	}

	public void subtractOneInfectDamagePlayer(View view) {
		subtractOneById(R.id.player_infect_total);
	}
	
	public void setOppLifeTotal(View view) {
		setLifeTotal(R.id.opponent_life_total);
	}
	
	public void setPlayerLifeTotal(View view) {
		setLifeTotal(R.id.player_life_total);
	}
	
	private void setLifeTotal(int id) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		final TextView textLifeTotal = (TextView) findViewById(id);
		final EditText input = new EditText(this);
		input.setText(textLifeTotal.getText().toString());
		input.setSelection(textLifeTotal.getText().length());
		
		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		builder
			.setTitle("Set Life Total")
			.setView(input)
			.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) { 
					// Set life total on ok
					textLifeTotal.setText(Integer.toString(Integer.parseInt(input.getText().toString())));
				}
			})
			.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) { 
					// do nothing
				}
			})
			.show().getWindow().setLayout(200,200);
	}
	
	// Start and pause timer functionality
	public void startStopTimer(View view) {
		if (!isStarted) {
			countDownTimer = new MyCountDownTimer(millisecondsSaved, 10);
			countDownTimer.start();
			isStarted = true;
			startStopButton.setText("PAUSE");
		} else {
			countDownTimer.cancel();
			isStarted = false;
			startStopButton.setText("START");
		}
	}
	
	// Reset the timer to 50 minutes
	public void resetTimer(View view) {
		if (!isStarted) {
			millisecondsSaved = 3000000;
			textTimer.setText("50:00");
		}
	}
	
	public class MyCountDownTimer extends CountDownTimer
	{
		public MyCountDownTimer(long startTime, long interval) {
			super(startTime, interval);
		}
		
		@Override
		public void onFinish() {
			textTimer.setText("Round's up!");
		}
	
		@Override
		public void onTick(long millisUntilFinished) {
			SetTime(millisUntilFinished);
		}
	}
	
	public void SetTime(long milliSeconds) {
		// Save the milliseconds for pause resume functionality
		millisecondsSaved = milliSeconds;
		int secs = (int) (milliSeconds / 1000);
		int mins = secs / 60;
		secs = secs % 60;
		textTimer.setText(mins + ":" + String.format("%02d", secs));
	}
}

/* TODO

icon
+5 / -5 life
mana pool
keep running total of damage or lifegain
add card name search to write down what did damage
upkeep or end step trigger reminder
die roll
coin flip
flip opp life total and buttons
*/
