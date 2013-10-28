package edu.maine.usm.cos246.npuzzlederrickchall;

import android.app.Activity;
import android.os.Bundle;
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
		
		image = (ImageView) findViewById(R.id.imageView1);
		image.setImageResource(resourceId);
	}

}
