/* Derrick Hall <derrick.c.hall@maine.edu */
package edu.maine.usm.cos246.npuzzlederrickchall;

import java.lang.reflect.Field;

import edu.maine.usm.cos246.npuzzlederrickchall.adapters.SimpleImageAdapter;
import android.os.Bundle;
import android.util.Log;
import android.app.ListActivity;

public class ImageSelection extends ListActivity {
	
	private String[] elements;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.elements = getFieldsFromDrawable();
        
        setListAdapter(new SimpleImageAdapter(this, this.elements));
    }
    
    private String[] getFieldsFromDrawable() {
    	Field[] fields = R.drawable.class.getFields();
    	String[] drawables = new String[fields.length];
    	for (int i = 0; i < fields.length; i++) {
    		drawables[i] = fields[i].getName();
    		Log.i("Image", fields[i].getName());
    	}
    	return drawables;
    }
    
}
