package edu.maine.usm.cos246.npuzzlederrickchall;

import java.util.ArrayList;

import edu.maine.usm.cos246.npuzzlederrickchall.adapters.SimpleGridImageAdapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.GridView;
import android.widget.ImageView;

public class GamePlay extends Activity {
	
	private ImageView image;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_play);
		
		displayImage();	
		
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				splitImage();
			}
		}, 3000L);
	    
	}
	
	private void displayImage() {
		Bundle bundle = this.getIntent().getExtras();
		String puzzle = bundle.getString("puzzle");
		
		int resourceId = this.getApplicationContext().getResources().getIdentifier(puzzle, "drawable", this.getApplicationContext().getApplicationInfo().packageName);
		
		image = (ImageView) findViewById(R.id.solution);
		image.setImageResource(resourceId);	
	}
	
	private void splitImage() {
		ArrayList<Bitmap> imageChunks = splitImage(image, 25);
		setContentView(R.layout.activity_game_play_grid);
		GridView gridView = (GridView) findViewById(R.id.gameBoard);
		gridView.setAdapter(new SimpleGridImageAdapter(GamePlay.this, imageChunks));
		gridView.setNumColumns((int) Math.sqrt(25));
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
