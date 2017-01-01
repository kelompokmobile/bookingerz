package com.jemberonlineservice.bookingerz.app;

import android.graphics.Bitmap;

/**
 * Created by vmmod on 12/28/2016.
 */

public class Config {
    public static String[] judul;
    public static String[] penyelenggara;
    public static Bitmap[] bitmaps;
    public static Bitmap bitmap2;
    public static String[] urls;
    public static String[] uidacara;

    public static String URL_CARDVIEW = "http://192.168.42.248/bookingerz/android_cardview/datajson.php";
    public static final String TAG_UID_ACARA = "uid";
    public static final String TAG_JUDUL = "jdl_acara";
    public static final String TAG_PENYELENGGARA = "admin_acara";
    public static final String TAG_IMAGE_URL = "img_acara";
    public static final String TAG_JSON_ARRAY = "result";

    public static final String TAG_ID_A = "id";
    public static final String TAG_UID_A = "uid";
    public static final String TAG_UID_USER_A = "uid_user";
    public static final String TAG_IMG_A = "img_acara";
    public static final String TAG_JDL_A = "jdl_acara";
    public static final String TAG_ADMIN_A = "admin_acara";
    public static final String TAG_HRG_TIKET_A = "hrg_tiket";
    public static final String TAG_TGL_A = "tgl_acara";
    public static final String TAG_JAM_A = "jam_acara";
    public static final String TAG_KET_A = "ket_acara";
    public static final String TAG_CREATED_AT_A = "created_at";
    public static final String TAG_JSON_ARRAY_A = "result";

    public Config(int i){
        judul = new String[i];
        penyelenggara = new String[i];
        bitmaps = new Bitmap[i];
        urls = new String[i];
        uidacara = new String[i];
    }
}
