/* Derrick Hall <derrick.c.hall@maine.edu */
package edu.maine.usm.cos246.npuzzlederrickchall.adapters;

import edu.maine.usm.cos246.npuzzlederrickchall.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SimpleImageAdapter extends ArrayAdapter<String> {
  private final Context context;
  private String[] elements;

  public SimpleImageAdapter(Context context, String[] elements) {
    super(context, R.layout.activity_image_selection, elements);
    this.context = context;
    this.elements = elements;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.activity_image_selection, parent, false);
    
    TextView textView = (TextView) rowView.findViewById(R.id.puzzle);
    ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
    
    textView.setText(this.elements[position]);
    
    String puzzle = this.elements[position];

    int resourceId = this.context.getResources().getIdentifier(puzzle, "drawable", this.context.getApplicationInfo().packageName);

    imageView.setImageResource(resourceId);

    return rowView;
  }
}
