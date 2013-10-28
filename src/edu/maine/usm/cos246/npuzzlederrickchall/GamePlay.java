package edu.maine.usm.cos246.npuzzlederrickchall;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class GamePlay extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle bundle = this.getIntent().getExtras();
		
		String puzzle = bundle.getString("puzzle");
		
		Log.i("puzzle", puzzle);
	}

}
