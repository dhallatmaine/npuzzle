/* Derrick Hall <derrick.c.hall@maine.edu */
package edu.maine.usm.cos246.npuzzlederrickchall;

import java.util.ArrayList;

import edu.maine.usm.cos246.npuzzlederrickchall.adapters.SimpleGridImageAdapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class GamePlay extends Activity {
	
	private GridView gridView;
	private ImageView image;
	private DIFFICULTY difficulty;
	private int empty;
	private int moves;
	private ArrayList<Bitmap> gameGrid;
	private ArrayList<Bitmap> solution;
	
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
		
		gameGrid = new ArrayList<Bitmap>();
		
		moves = 0;
		
		Bundle bundle = this.getIntent().getExtras();
		int difficultyValue = bundle.getInt("difficulty");
		
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
		
		Bundle bundle = new Bundle();
		bundle.putString("changeImage", "change");
		
		intent.putExtras(bundle);
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
		gameGrid = shuffleList(imageChunks);
		setContentView(R.layout.activity_game_play_grid);
		gridView = (GridView) findViewById(R.id.gameBoard);
		SimpleGridImageAdapter adapter = new SimpleGridImageAdapter(GamePlay.this, gameGrid);
		adapter.notifyDataSetChanged();
		gridView.setAdapter(adapter);
		gridView.setNumColumns((int) Math.sqrt(difficulty.value));
		
		gridView.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	        	move(position);
	        	if (puzzleSolved()) {
	        		
	        	}
	        }
	    });
	}
	
	private void move(int position) {
		int sqrt = (int)getDifficultySquareRoot();
		int size = gameGrid.size()-1;
		if (position == empty) {
			return;
		}
		
		if (position != 0 && position-1 == empty) {
			swap(position, empty);
			return;
		}
		
		if (position != size && position+1 == empty) {
			swap(position, empty);
			return;
		}
		
		if ((position-sqrt >= 0) && (gameGrid.get(position-sqrt) != null) && (position-sqrt == empty)) {
			swap(position, empty);
			return;
		}
		
		if ((position+sqrt < gameGrid.size()) && (gameGrid.get(position+sqrt) != null) && (position+sqrt == empty)) {
			swap(position, empty);
			return;
		}
	}
	
	private void swap(int from, int to) {
		if (from > to) {
			swap(to, from);
			empty = from;
			return;
		}
		Bitmap toImage = gameGrid.remove(to);
		Bitmap fromImage = gameGrid.remove(from);
		
		gameGrid.add(from, toImage);
		gameGrid.add(to, fromImage);
		
		empty = from;
		moves++;
		gridView.invalidateViews();
	}
	
	private double getDifficultySquareRoot() {
		return Math.sqrt(difficulty.value);
	}
	
	private boolean puzzleSolved() {
		return solution.equals(gameGrid);
	}
	
	private ArrayList<Bitmap> shuffleList(ArrayList<Bitmap> imageChunks) {
		ArrayList<Bitmap> shuffledImages = new ArrayList<Bitmap>();
		
		imageChunks.remove(imageChunks.size()-1);
		
		Bitmap icon = BitmapFactory.decodeResource(this.getApplicationContext().getResources(), R.drawable.firefox);
		
		solution = new ArrayList<Bitmap>(shuffledImages);
		solution.add(icon);
		
		//add in reverse
		for (int i = (imageChunks.size()-1); i >= 0; i--) {
			shuffledImages.add(imageChunks.get(i));
		}
		

		Bitmap last = shuffledImages.get(shuffledImages.size()-1);
		Bitmap secondToLast = shuffledImages.get(shuffledImages.size()-2);
		
		shuffledImages.remove(last);
		shuffledImages.remove(secondToLast);
		
		shuffledImages.add(last);
		shuffledImages.add(secondToLast);
		shuffledImages.add(icon);
		empty = shuffledImages.size() - 1;
		
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
