package com.jemberonlineservice.bookingerz.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jemberonlineservice.bookingerz.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by vmmod on 1/7/2017.
 */

public class ListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<ListItem> items;

    public ListAdapter(Activity activity, List<ListItem> items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount(){
        return items.size();
    }

    @Override
    public Object getItem(int location){
        return items.get(location);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        TextView ujdlacara = (TextView) convertView.findViewById(R.id.tv_userjdlacara);
        TextView uadmacara = (TextView) convertView.findViewById(R.id.tv_useradminacara);
        ImageView posteracara = (ImageView) convertView.findViewById(R.id.iv_useracaraimg);

        ListItem data = items.get(position);
        String urls = data.getUrls();

        ujdlacara.setText(data.getJudul());
        uadmacara.setText(data.getPenyelenggara());

        getImage(urls, posteracara);

        return convertView;
    }

    private void getImage(String imgurl, final ImageView posteracara) {
        String urls = imgurl;
        class GetImage extends AsyncTask<String,Void,Bitmap> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Bitmap b) {
                super.onPostExecute(b);
                posteracara.setImageBitmap(b);
            }

            @Override
            protected Bitmap doInBackground(String... params) {
                String urls = params[0];
                String add = urls;
                URL url = null;
                Bitmap image = null;
                try {
                    url = new URL(add);
                    image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return image;
            }
        }

        GetImage gi = new GetImage();
        gi.execute(urls);
    }
}
