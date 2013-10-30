package edu.maine.usm.cos246.npuzzlederrickchall;

import java.util.ArrayList;

import edu.maine.usm.cos246.npuzzlederrickchall.adapters.SimpleGridImageAdapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageView;

public class GamePlay extends Activity {
	
	private ImageView image;
	
	private DIFFICULTY difficulty;
	
	private enum DIFFICULTY {
		EASY (9), MEDIUM (16), HARD (25);
		private int value;
		private DIFFICULTY(int value) {
			this.value = value;
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_play);
		
		Bundle bundle = this.getIntent().getExtras();
		int difficultyValue = bundle.getInt("difficulty");
		
		Log.i("difficulty", Integer.toString(difficultyValue));
		
		if (difficultyValue > 0) {
			setDifficulty(difficultyValue);
		} else {
			difficulty = DIFFICULTY.MEDIUM;
		}
		
		displayImage();	
		
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				splitImage();
			}
		}, 3000L);
	    
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.image_selection, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.i("Menu click", Integer.toString(item.getItemId()));
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.quit:
	            finish();
	            return false;
	        case R.id.reset:
	            reset();
	            return false;
	        case R.id.change_image:
	        	changeImage();
	        	return false;
	        case R.id.easy:
	        	changeDifficulty(DIFFICULTY.EASY);
	        	return false;
	        case R.id.medium:
	        	changeDifficulty(DIFFICULTY.MEDIUM);
	        	return false;
	        case R.id.hard:
	        	changeDifficulty(DIFFICULTY.HARD);
	        	return false;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void setDifficulty(int difficultyValue) {
		if (difficultyValue == DIFFICULTY.EASY.value) {
			difficulty = DIFFICULTY.EASY;
		} else if (difficultyValue == DIFFICULTY.MEDIUM.value) {
			difficulty = DIFFICULTY.MEDIUM;
		} else {
			difficulty = DIFFICULTY.HARD;
		}
	}
	
	private void changeDifficulty(DIFFICULTY diff) {
		Log.i("bundle_diffculty", Integer.toString(diff.value));
		
		Intent intent = new Intent();
		intent.setClass(GamePlay.this, GamePlay.class);
		
		Bundle bundle = new Bundle();
		bundle.putInt("difficulty", diff.value);
		bundle.putString("puzzle", this.getIntent().getExtras().getString("puzzle"));
		
		intent.putExtras(bundle);
		finish();
		startActivity(intent);
		
	}
	
	private void reset() {
		Intent intent = getIntent();
		finish();
		startActivity(intent);
	}
	
	private void changeImage() {
		Intent intent = new Intent();
		intent.setClass(GamePlay.this, ImageSelection.class);
		startActivity(intent);
		finish();
	}
	
	private void displayImage() {
		Bundle bundle = this.getIntent().getExtras();
		String puzzle = bundle.getString("puzzle");
		
		int resourceId = this.getApplicationContext().getResources().getIdentifier(puzzle, "drawable", this.getApplicationContext().getApplicationInfo().packageName);
		
		image = (ImageView) findViewById(R.id.solution);
		image.setImageResource(resourceId);	
	}
	
	private void splitImage() {
		ArrayList<Bitmap> imageChunks = splitImage(image, difficulty.value);
		ArrayList<Bitmap> shuffledChunks = shuffleList(imageChunks);
		setContentView(R.layout.activity_game_play_grid);
		GridView gridView = (GridView) findViewById(R.id.gameBoard);
		gridView.setAdapter(new SimpleGridImageAdapter(GamePlay.this, shuffledChunks));
		gridView.setNumColumns((int) Math.sqrt(difficulty.value));
	}
	
	private ArrayList<Bitmap> shuffleList(ArrayList<Bitmap> imageChunks) {
		ArrayList<Bitmap> shuffledImages = new ArrayList<Bitmap>();
		
		imageChunks.remove(imageChunks.size()-1);
		
		for (int i = (imageChunks.size()-1); i >= 0; i--) {
			shuffledImages.add(imageChunks.get(i));
		}
		

		Bitmap nMinusOne = shuffledImages.get(shuffledImages.size()-1);
		Bitmap n = shuffledImages.get(shuffledImages.size()-2);
		
		shuffledImages.remove(nMinusOne);
		shuffledImages.remove(n);
		
		shuffledImages.add(nMinusOne);
		shuffledImages.add(n);		
		
		return shuffledImages;
	}
	
	private ArrayList<Bitmap> splitImage(ImageView image, int chunkNumbers) {	
		
		//For the number of rows and columns of the grid to be displayed
		int rows,cols;
		
		//For height and width of the small image chunks 
		int chunkHeight,chunkWidth;
		
		//To store all the small image chunks in bitmap format in this list 
		ArrayList<Bitmap> chunkedImages = new ArrayList<Bitmap>(chunkNumbers);
		
		//Getting the scaled bitmap of the source image
		BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
		Bitmap bitmap = drawable.getBitmap();
		Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
		
		rows = cols = (int) Math.sqrt(chunkNumbers);
		chunkHeight = bitmap.getHeight()/rows;
		chunkWidth = bitmap.getWidth()/cols;
		
		//xCoord and yCoord are the pixel positions of the image chunks
		int yCoord = 0;
		for (int x = 0; x < rows; x++){
			int xCoord = 0;
			for (int y = 0; y < cols; y++){
				chunkedImages.add(Bitmap.createBitmap(scaledBitmap, xCoord, yCoord, chunkWidth, chunkHeight));
				xCoord += chunkWidth;
			}
			yCoord += chunkHeight;
		}
		return chunkedImages;
	}

}
