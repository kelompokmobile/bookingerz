package com.jemberonlineservice.bookingerz.app;

import android.graphics.Bitmap;

/**
 * Created by vmmod on 12/28/2016.
 */

public class Config {
    public static String[] judul;
    public static String[] penyelenggara;
    public static Bitmap[] bitmaps;
    public static String[] urls;
    public static String URL_CARDVIEW = "http://192.168.1.8/bookingerz/android_cardview/datajson.php";
    public static final String TAG_JUDUL = "jdl_acara";
    public static final String TAG_PENYELENGGARA = "admin_acara";
    public static final String TAG_IMAGE_URL = "img_acara";
    public static final String TAG_JSON_ARRAY = "result";

    public Config(int i){
        judul = new String[i];
        penyelenggara = new String[i];
        bitmaps = new Bitmap[i];
        urls = new String[i];
    }
}
