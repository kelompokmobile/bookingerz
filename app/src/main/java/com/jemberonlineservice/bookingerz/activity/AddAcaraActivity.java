package com.jemberonlineservice.bookingerz.activity;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jemberonlineservice.bookingerz.MainActivity;
import com.jemberonlineservice.bookingerz.R;
import com.jemberonlineservice.bookingerz.app.AppConfig;
import com.jemberonlineservice.bookingerz.app.AppController;
import com.jemberonlineservice.bookingerz.fragment.DatePickerFragment;
import com.jemberonlineservice.bookingerz.fragment.TimePickerFragment;
import com.jemberonlineservice.bookingerz.helper.SQLiteHandler;
import com.jemberonlineservice.bookingerz.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vmmod on 12/14/2016.
 */

public class AddAcaraActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private static final String TAG = AddAcaraActivity.class.getSimpleName();
    private EditText txjudulacara;
    private EditText txpenyelenggara;
    private EditText txhargatiket;
    private TextView tvdateview;
    private TextView tvtimeview;
    private EditText txketacara;
    private int PICK_IMAGE_REQUEST = 1;
    private Uri filePath;
    private Bitmap bitmap;
    private SQLiteHandler db;
    private SessionManager session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addacara);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton imgbtn = (ImageButton) findViewById(R.id.btn_addposter);
        txjudulacara = (EditText) findViewById(R.id.tx_judulacara);
        txpenyelenggara = (EditText) findViewById(R.id.tx_penyelenggara);
        txhargatiket = (EditText) findViewById(R.id.tx_hargatiket);
        tvdateview = (TextView) findViewById(R.id.tv_dateview);
        tvtimeview = (TextView) findViewById(R.id.tv_timeview);
        txketacara = (EditText) findViewById(R.id.tx_ketacara);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        Button dateView = (Button) findViewById(R.id.btn_datepicker);
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        Button timeView = (Button) findViewById(R.id.btn_timepicker);
        timeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v);
            }
        });

        Button submitAcara = (Button) findViewById(R.id.btn_submitevent);
        submitAcara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jdlacara = txjudulacara.getText().toString().trim();
                String adminacara = txpenyelenggara.getText().toString().trim();
                String hrgtiket = txhargatiket.getText().toString().trim();
                String tglacara = tvdateview.getText().toString().trim();
                String jamacara = tvtimeview.getText().toString().trim();
                String ketacara = txketacara.getText().toString().trim();
                String imgacara = getStringImage(bitmap);
                db = new SQLiteHandler(getApplicationContext());
                session = new SessionManager(getApplicationContext());
                HashMap<String, String> user = db.getUserDetails();
                String uiduser = user.get("uid");

                if (!uiduser.isEmpty() && !imgacara.isEmpty() && !jdlacara.isEmpty() && !adminacara.isEmpty() && !hrgtiket.isEmpty() && !tglacara.isEmpty() && !jamacara.isEmpty()
                        && !ketacara.isEmpty()) {
                    registerAcara(uiduser, imgacara, jdlacara, adminacara, hrgtiket, tglacara, jamacara, ketacara);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ambilGambar(v);
            }
        });
    }

    private void ambilGambar(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                ImageButton imgbtn = (ImageButton) findViewById(R.id.btn_addposter);
                imgbtn.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void registerAcara( final String uiduser,
                                final String imgacara,
                              final String jdlacara,
                              final String adminacara,
                              final String hrgtiket,
                              final String tglacara,
                              final String jamacara,
                              final String ketacara) {

        String tag_string_req = "req_addacara";

        pDialog.setMessage("Adding ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ADDACARA, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();
                Intent intent = new Intent(AddAcaraActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("uiduser", uiduser);
                params.put("imgacara", imgacara);
                params.put("jdlacara", jdlacara);
                params.put("adminacara", adminacara);
                params.put("hrgtiket", hrgtiket);
                params.put("tglacara", tglacara);
                params.put("jamacara", jamacara);
                params.put("ketacara", ketacara);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }
}
