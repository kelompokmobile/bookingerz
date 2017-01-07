package com.jemberonlineservice.bookingerz.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jemberonlineservice.bookingerz.R;
import com.jemberonlineservice.bookingerz.app.AppConfig;
import com.jemberonlineservice.bookingerz.app.AppController;
import com.jemberonlineservice.bookingerz.app.Config;
import com.jemberonlineservice.bookingerz.fragment.ProfileTabDetailFragment;
import com.jemberonlineservice.bookingerz.fragment.ProfileTabInAcaraFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.bitmap;

/**
 * Created by vmmod on 1/2/2017.
 */

public class ProfileActivity extends AppCompatActivity {

    TextView namaL;
    TextView emailL;
    TextView dnama;
    TextView demail;
    TextView dpassword;
    TextView dtgllahir;
    TextView dnotlp;
    TextView dtnama;
    TextView dtemail;
    TextView dttgllahir;
    TextView dtnotlp;
    ImageButton adddp;

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG = ProfileActivity.class.getSimpleName();
    private int PICK_IMAGE_REQUEST = 1;

    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    private ProgressDialog pDialog;
    private Uri filePath;
    private Bitmap bitmap;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private ImageButton dpicture;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        getData();
    }

    private void DialogForm(final String idx, final String uidx, String namax, String emailx, final String passwordx, String tgllahirx, String notlpx, String button) {
        dialog = new AlertDialog.Builder(ProfileActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_editprofile, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Edit Profile");

        adddp = (ImageButton) dialogView.findViewById(R.id.ib_adddp);
        dnama = (TextView) dialogView.findViewById(R.id.txt_nama);
        demail = (TextView) dialogView.findViewById(R.id.txt_email);
        dpassword = (TextView) dialogView.findViewById(R.id.txt_password);
        dtgllahir = (TextView) dialogView.findViewById(R.id.txt_tgllahir);
        dnotlp = (TextView) dialogView.findViewById(R.id.txt_notlp);

        dnama.setText(namax);
        demail.setText(emailx);
        dpassword.setText(passwordx);
        dtgllahir.setText(tgllahirx);
        dnotlp.setText(notlpx);

        /* Button dateView = (Button) dialogView.findViewById(R.id.btn_datepicker2);
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        }); */

        adddp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ambilGambar(v);
            }
        });

        dialog.setPositiveButton(button, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String id = idx;
                String unique_id = uidx;
                String nama = dnama.getText().toString();
                String email = demail.getText().toString();
                String password = dpassword.getText().toString();
                String tgllahir = dtgllahir.getText().toString();
                String notlp = dnotlp.getText().toString();
                String imgdp = getStringImage(bitmap);

                if (!id.isEmpty() && !unique_id.isEmpty() && !imgdp.isEmpty() && !nama.isEmpty() && !email.isEmpty() && !password.isEmpty() && !tgllahir.isEmpty()
                        && !notlp.isEmpty()) {
                    UpdateUser(id, unique_id, imgdp, nama, email, password, tgllahir, notlp);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void ambilGambar(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), PICK_IMAGE_REQUEST);
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                adddp.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* private void profileView(){
        Intent i = getIntent();
        String id = i.getStringExtra("uid");
        String name = i.getStringExtra("name");
        String email = i.getStringExtra("email");

        namaL = (TextView) findViewById(R.id.tx_namalengkap);
        namaL.setText(name);
        emailL = (TextView) findViewById(R.id.tx_email);
        emailL.setText(email);
        dpicture = (ImageButton) findViewById(R.id.btn_pp);
        dpicture.setEnabled(false);
    } */

    public void getData() {
        Intent i = getIntent();
        String uid = i.getStringExtra("uid");

        pDialog = ProgressDialog.show(this, "Tunggu Sebentar...", "Detail...",
                false, false);

        String url = AppConfig.URL_GETPROFIL + uid;

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
                        Toast.makeText(ProfileActivity.this, error.getMessage().toString(),
                                Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response){
        String imguser = "";
        String nama = "";
        String email = "";
        String tgllahir = "";
        String notlp = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_OBJ_U);
            JSONObject collegeData = result.getJSONObject(0);
            imguser = collegeData.getString(Config.TAG_IMG_U);
            nama = collegeData.getString((Config.TAG_NAME_U));
            email = collegeData.getString(Config.TAG_EMAIL_U);
            tgllahir = collegeData.getString(Config.TAG_TGLLAHIR_U);
            notlp = collegeData.getString(Config.TAG_NOTLP_U);
            getImage(imguser);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        namaL = (TextView) findViewById(R.id.tx_namalengkap);
        namaL.setText(nama);
        emailL = (TextView) findViewById(R.id.tx_email);
        emailL.setText(email);

        dtnama = (TextView) findViewById(R.id.tx_namall);
        dtnama.setText(nama);
        dtemail = (TextView) findViewById(R.id.tx_emailll);
        dtemail.setText(email);
        dttgllahir = (TextView) findViewById(R.id.tx_tgllahirll);
        dttgllahir.setText(tgllahir);
        dtnotlp = (TextView) findViewById(R.id.tx_notelpll);
        dtnotlp.setText(notlp);
    }

    private void getImage(String imgurl) {
        String urls = imgurl;
        class GetImage extends AsyncTask<String,Void,Bitmap> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Bitmap b) {
                super.onPostExecute(b);
                dpicture = (ImageButton) findViewById(R.id.btn_pp);
                dpicture.setImageBitmap(b);
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

    /* public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment2();
        newFragment.show(getFragmentManager(), "datePicker");
    } */

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ProfileTabDetailFragment(), "Detail");
        adapter.addFragment(new ProfileTabInAcaraFragment(), "Acara");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void UpdateUser(final String id,
                            final String unique_id,
                            final String imgdp,
                            final String nama,
                            final String email,
                            final String password,
                            final String tgllahir,
                            final String notlp) {

        String url = AppConfig.URL_EDITPROFIL;

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                params.put("unique_id", unique_id);
                params.put("img_user", imgdp);
                params.put("name", nama);
                params.put("email", email);
                params.put("password", password);
                params.put("tgl_lahir", tgllahir);
                params.put("no_tlp", notlp);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, Config.TAG_JSON_OBJ_U2);
    }

    private void Edit(final String uidx){
        String url = AppConfig.URL_GETPROFIL + uidx;
        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_OBJ_U);
                    JSONObject collegeData = result.getJSONObject(0);
                    Log.d("get edit data", jsonObject.toString());
                    String idx = collegeData.getString(Config.TAG_ID_U);
                    String uidx = collegeData.getString(Config.TAG_UID_U);
                    String namax = collegeData.getString(Config.TAG_NAME_U);
                    String emailx = collegeData.getString(Config.TAG_EMAIL_U);
                    String passwordx = null;
                    String tgllahirx = collegeData.getString(Config.TAG_TGLLAHIR_U);
                    String notlpx = collegeData.getString(Config.TAG_NOTLP_U);

                    DialogForm(idx, uidx, namax, emailx, passwordx, tgllahirx, notlpx, "UPDATE");

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(ProfileActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("uid", uidx);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, Config.TAG_JSON_OBJ_U2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_edit:
                Intent i = getIntent();
                String uidx = i.getStringExtra("uid");
                Edit(uidx);
                break;
            default:
                break;
        }

        return true;
    }
}
