package com.jemberonlineservice.bookingerz.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jemberonlineservice.bookingerz.R;
import com.jemberonlineservice.bookingerz.app.AppConfig;
import com.jemberonlineservice.bookingerz.app.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by vmmod on 12/29/2016.
 */

public class DetailAcaraActivity extends AppCompatActivity {

    private TextView tvjdlacara;
    private TextView tvadminacara;
    private TextView tvketacara;
    private TextView tvtglacara;
    private TextView tvjamacara;
    private Button btnenter;
    private ImageView ivposter;
    private Config config;
    private ProgressDialog pDialog;

    String uidacara = "";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailacara);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnenter = (Button) findViewById(R.id.btn_booking);
        btnenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailAcaraActivity.this, ETicketActivity.class);
                i.putExtra("uidacara", uidacara);
                startActivity(i);
            }
        });

        getData();
    }

    public void getData() {
        Intent i = getIntent();
        String uidacara = i.getStringExtra("uidacara");

        pDialog = ProgressDialog.show(this, "Tunggu Sebentar...", "Detail...",
                false, false);

        String url = AppConfig.URL_GETACARA + uidacara;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DetailAcaraActivity.this, error.getMessage().toString(),
                                Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response){
        String imgurl = "";
        String jdlacara = "";
        String adminacara = "";
        String ketacara = "";
        String tglacara = "";
        String jamacara = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY_A);
            JSONObject collegeData = result.getJSONObject(0);
            imgurl = collegeData.getString(Config.TAG_IMG_A);
            jdlacara = collegeData.getString(Config.TAG_JDL_A);
            adminacara = collegeData.getString(Config.TAG_ADMIN_A);
            ketacara = collegeData.getString(Config.TAG_KET_A);
            tglacara = collegeData.getString(Config.TAG_TGL_A);
            jamacara = collegeData.getString(Config.TAG_JAM_A);
            uidacara = collegeData.getString(Config.TAG_UID_A);
            getImage(imgurl);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        tvjdlacara = (TextView) findViewById(R.id.tv_djdulacara);
        tvjdlacara.setText(jdlacara);
        tvadminacara = (TextView) findViewById(R.id.tv_dadminacara);
        tvadminacara.setText(adminacara);
        tvtglacara = (TextView) findViewById(R.id.tv_tglacara);
        tvtglacara.setText(tglacara);
        tvjamacara = (TextView) findViewById(R.id.tv_jamacara);
        tvjamacara.setText(jamacara);
        tvketacara = (TextView) findViewById(R.id.tv_ketacara);
        tvketacara.setText(ketacara);
    }

    private void getImage(String imgurl) {
        String urls = imgurl;
        class GetImage extends AsyncTask<String,Void,Bitmap>{

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Bitmap b) {
                super.onPostExecute(b);
                ivposter = (ImageView) findViewById(R.id.iv_poster);
                ivposter.setImageBitmap(b);
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
