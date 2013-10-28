package edu.maine.usm.cos246.npuzzlederrickchall;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class GamePlay extends Activity {
	
	ImageView image;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_play);
		
		Bundle bundle = this.getIntent().getExtras();
		String puzzle = bundle.getString("puzzle");
		
		int resourceId = this.getApplicationContext().getResources().getIdentifier(puzzle, "drawable", this.getApplicationContext().getApplicationInfo().packageName);
		
		final int resourceId2 = this.getApplicationContext().getResources().getIdentifier("puzzle_2", "drawable", this.getApplicationContext().getApplicationInfo().packageName);
		
		image = (ImageView) findViewById(R.id.solution);
		image.setImageResource(resourceId);
		
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				image.setImageResource(resourceId2);
			}
		}, 3000L);
	    
	}

}
