package com.example.chaithra.mytriplebyte;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by chaithra on 3/12/18.
 */

public class MeowAdapter extends ArrayAdapter<Meow> {

    public MeowAdapter(Context context, int resource) {
        super(context, resource);
    }
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v=convertView;
        Meow meow=getItem(position);
        ViewHolder viewHolder;

        if(v==null)
        {
            viewHolder=new ViewHolder();
            v= LayoutInflater.from(getContext()).inflate(R.layout.list,parent,false);
            viewHolder.date=(TextView)v.findViewById(R.id.date);
            viewHolder.image=(ImageView)v.findViewById(R.id.image);
            viewHolder.title=(TextView)v.findViewById(R.id.title);
            viewHolder.description=(TextView)v.findViewById(R.id.description);
            v.setTag(viewHolder);
        }
        else{
            viewHolder=(ViewHolder)v.getTag();
        }
        viewHolder.date.setText(meow.getDate());
        viewHolder.image.setImageResource(R.drawable.ic_launcher_background);
        new DownloadImageTask(viewHolder.image).execute(meow.getImage());
        viewHolder.title.setText(meow.getTitle());
        viewHolder.description.setText(meow.getDescription());

        return v;
    }
    public class ViewHolder
    {
        TextView date;
        ImageView image;
        TextView title;
        TextView description;

    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }



}
