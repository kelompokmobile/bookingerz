package com.jemberonlineservice.bookingerz.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jemberonlineservice.bookingerz.MainActivity;
import com.jemberonlineservice.bookingerz.R;
import com.jemberonlineservice.bookingerz.activity.DetailAcaraActivity;
import com.jemberonlineservice.bookingerz.app.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static android.content.ContentValues.TAG;

/**
 * Created by vmmod on 12/28/2016.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    List<ListItem> items;

    private Context mContext;
    private String[] mList;
    private Config config;

    public CardAdapter(Context contexts, String[] uidacara, String[] judul, String[] penyelenggara, Bitmap[] poster){
        super();
        this.mContext = contexts;
        this.mList = uidacara;
        items = new ArrayList<ListItem>();
        for (int i=0; i<judul.length; i++){
            ListItem item = new ListItem();
            item.setJudul(judul[i]);
            item.setPenyelenggara(penyelenggara[i]);
            item.setPoster(poster[i]);
            items.add(item);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_card_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ListItem list =  items.get(position);
        holder.poster.setImageBitmap(list.getPoster());
        holder.judul.setText(list.getJudul());
        holder.penyelenggara.setText(list.getPenyelenggara());
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(mContext, DetailAcaraActivity.class);
                i.putExtra("uidacara",mList[position]);
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView poster;
        public TextView judul;
        public TextView penyelenggara;
        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            poster = (ImageView) itemView.findViewById(R.id.iv_poster);
            judul = (TextView) itemView.findViewById(R.id.tv_jdl);
            penyelenggara = (TextView) itemView.findViewById(R.id.tv_penyelenggara);

        }

        @Override
        public void onClick(View itemView) {
            clickListener.onClick(itemView, getLayoutPosition());
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }
    }
}
