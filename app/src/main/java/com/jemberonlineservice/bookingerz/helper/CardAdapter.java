package com.jemberonlineservice.bookingerz.helper;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jemberonlineservice.bookingerz.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vmmod on 12/28/2016.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    List<ListItem> items;

    public CardAdapter(String[] judul, String[] penyelenggara, Bitmap[] poster){
        super();
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
        ListItem list =  items.get(position);
        holder.poster.setImageBitmap(list.getPoster());
        holder.judul.setText(list.getJudul());
        holder.penyelenggara.setText(list.getPenyelenggara());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView poster;
        public TextView judul;
        public TextView penyelenggara;

        public ViewHolder(View itemView) {
            super(itemView);

            poster = (ImageView) itemView.findViewById(R.id.iv_poster);
            judul = (TextView) itemView.findViewById(R.id.tv_jdl);
            penyelenggara = (TextView) itemView.findViewById(R.id.tv_penyelenggara);

        }
    }
}
