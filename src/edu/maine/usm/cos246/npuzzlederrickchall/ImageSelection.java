/* Derrick Hall <derrick.c.hall@maine.edu */
package edu.maine.usm.cos246.npuzzlederrickchall;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ImageSelection extends Activity {

	static final String[] COLORS = new String[] {
    	"Red",
    	"Orange",
    	"Yellow",
    	"Green",
    	"Blue",
    	"Indigo",
    	"Violet"
    };
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_selection);
        
        ListView list = (ListView)findViewById(R.id.imageSelectionView);
		list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, COLORS));
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		        String item = (String) parent.getItemAtPosition(position);
				Log.i("ListMe", "You clicked:" + item);
			}

		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.image_selection, menu);
        return true;
    }
    
}
