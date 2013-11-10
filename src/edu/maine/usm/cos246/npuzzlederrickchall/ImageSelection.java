/* Derrick Hall <derrick.c.hall@maine.edu */
package edu.maine.usm.cos246.npuzzlederrickchall;

import java.lang.reflect.Field;
import java.util.List;

import edu.maine.usm.cos246.npuzzlederrickchall.adapters.SimpleImageAdapter;
import edu.maine.usm.cos246.npuzzlederrickchall.dao.Puzzle;
import edu.maine.usm.cos246.npuzzlederrickchall.dao.PuzzleDao;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.app.ListActivity;
import android.content.Intent;

public class ImageSelection extends ListActivity {
	
	private String[] elements;
	private PuzzleDao puzzleDao;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if (getIntent().getExtras().getString("changeImage") == null) {
        	//checkForSavedGame();
        //}
        this.elements = getFieldsFromDrawable();
        setListAdapter(new SimpleImageAdapter(this, this.elements));
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	createGame(this.elements[position]);
    }
    
    private void checkForSavedGame() {
    	puzzleDao = new PuzzleDao(this);
    	puzzleDao.open();
    	
    	List<Puzzle> puzzles = puzzleDao.getAllPuzzles();
    	
    	if (puzzles.size() > 0) {
    		Puzzle puzzle = puzzles.get(0);
    		createGame(puzzle.getImage());
    	}
    }
    
    private void createGame(String puzzle) {
    	Intent intent = new Intent();
    	intent.setClass(ImageSelection.this, GamePlay.class);
    	
    	Bundle bundle = new Bundle();
    	bundle.putString("puzzle", puzzle);
    	
    	intent.putExtras(bundle);
    	
    	startActivity(intent);
    	finish();
    }
    
    private String[] getFieldsFromDrawable() {
    	Field[] fields = R.drawable.class.getFields();
    	String[] drawables = new String[fields.length];
    	for (int i = 0; i < fields.length; i++) {
    		drawables[i] = fields[i].getName();
    	}
    	return drawables;
    }
    
}
