package com.jemberonlineservice.bookingerz.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.jemberonlineservice.bookingerz.MainActivity;
import com.jemberonlineservice.bookingerz.R;
import com.jemberonlineservice.bookingerz.helper.SQLiteHandler;
import com.jemberonlineservice.bookingerz.helper.SessionManager;

import java.util.HashMap;

/**
 * Created by vmmod on 1/2/2017.
 */

public class ETicketActivity extends AppCompatActivity {

    private SQLiteHandler db;
    private SessionManager session;

    ImageView qrCodeImageview;
    String QRcode;
    public final static int WIDTH=500;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eticket);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new SQLiteHandler(getApplicationContext());

        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        HashMap<String, String> user = db.getUserDetails();

        final String name = user.get("name");

        Intent i = getIntent();
        final String uidacara = i.getStringExtra("uidacara");

        getID();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                QRcode = name+uidacara;

                try {
                    synchronized (this){
                        wait(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Bitmap bitmap = null;
                                    bitmap = encodeAsBitmap(QRcode);
                                    qrCodeImageview.setImageBitmap(bitmap);
                                } catch (WriterException e){
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

        t.start();
    }

    private void getID(){
        qrCodeImageview = (ImageView) findViewById(R.id.img_qr_code_image);
    }

    Bitmap encodeAsBitmap(String str) throws WriterException{
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE,
                    WIDTH, WIDTH, null);
        } catch (IllegalArgumentException iae){
            return null;
        }

        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++){
            int offset = y * w;
            for (int x = 0; x < w; x++){
                pixels[offset + x] = result.get(x, y) ? ContextCompat.getColor(getApplicationContext(), R.color.black):
                    ContextCompat.getColor(getApplicationContext(), R.color.white);
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, 500, 0, 0, w, h);
        return bitmap;
    }

    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        Intent intent = new Intent(ETicketActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
