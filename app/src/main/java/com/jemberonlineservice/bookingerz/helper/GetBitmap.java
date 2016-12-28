package com.jemberonlineservice.bookingerz.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.jemberonlineservice.bookingerz.MainActivity;
import com.jemberonlineservice.bookingerz.app.Config;

import java.net.URL;

/**
 * Created by vmmod on 12/28/2016.
 */

public class GetBitmap extends AsyncTask<Void,Void,Void> {
    private Context context;
    private String[] urls;
    private ProgressDialog loading;
    private MainActivity mainActivity;

    public GetBitmap(Context context, MainActivity mainActivity, String[] urls){
        this.context = context;
        this.urls = urls;
        this.mainActivity = mainActivity;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        loading = ProgressDialog.show(context, "Memproses Gambar", "Tunggu Sebentar...", false, false);

    }

    @Override
    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);
        loading.dismiss();
        mainActivity.showData();
    }

    @Override
    protected Void doInBackground(Void... params){
        for (int i=0; i<urls.length; i++){
            Config.bitmaps[i] = getImage(urls[i]);
        }
        return null;
    }

    private Bitmap getImage(String bitmapUrl){
        URL url;
        Bitmap image = null;
        try {
            url = new URL(bitmapUrl);
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (Exception e){}
        return image;
    }
}
