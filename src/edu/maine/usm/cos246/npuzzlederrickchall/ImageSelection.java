/* Derrick Hall <derrick.c.hall@maine.edu */
package edu.maine.usm.cos246.npuzzlederrickchall;

import java.lang.reflect.Field;
import edu.maine.usm.cos246.npuzzlederrickchall.adapters.SimpleImageAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.app.ListActivity;
import android.content.Intent;

public class ImageSelection extends ListActivity {
	
	private String[] elements;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.elements = getFieldsFromDrawable();
        setListAdapter(new SimpleImageAdapter(this, this.elements));
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	Intent intent = new Intent();
    	intent.setClass(ImageSelection.this, GamePlay.class);
    	
    	Bundle bundle = new Bundle();
    	bundle.putString("puzzle", this.elements[position]);
    	
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
