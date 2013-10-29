package edu.maine.usm.cos246.npuzzlederrickchall.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class SimpleGridImageAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Bitmap> imageChunks;
	private int imageWidth, imageHeight;
	
	public SimpleGridImageAdapter(Context c, ArrayList<Bitmap> images){
		context = c;
		imageChunks = images;
		imageWidth = images.get(0).getWidth();
		imageHeight = images.get(0).getHeight();
	}
	
	@Override
	public int getCount() {
		return imageChunks.size();
	}

	@Override
	public Object getItem(int position) {
		return imageChunks.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView image;
		if(convertView == null){
			image = new ImageView(context);
			image.setLayoutParams(new GridView.LayoutParams(imageWidth - 10 , imageHeight));
			image.setPadding(0, 0, 0, 0);
			image.setAdjustViewBounds(true);
		}else{
			image = (ImageView) convertView;
		}
		image.setImageBitmap(imageChunks.get(position));
		return image;
	}
}
