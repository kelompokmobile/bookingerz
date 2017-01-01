package com.jemberonlineservice.bookingerz;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.jemberonlineservice.bookingerz.activity.AddAcaraActivity;
import com.jemberonlineservice.bookingerz.activity.DetailAcaraActivity;
import com.jemberonlineservice.bookingerz.activity.LoginActivity;
import com.jemberonlineservice.bookingerz.activity.SettingActivity;
import com.jemberonlineservice.bookingerz.app.Config;
import com.jemberonlineservice.bookingerz.helper.CardAdapter;
import com.jemberonlineservice.bookingerz.helper.GetBitmap;
import com.jemberonlineservice.bookingerz.helper.SQLiteHandler;
import com.jemberonlineservice.bookingerz.helper.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private SQLiteHandler db;
    private SessionManager session;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private Config config;
    private GridLayoutManager mlayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.rv_cardacara);
        mlayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mlayoutManager);
        getData();

        db = new SQLiteHandler(getApplicationContext());

        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");
    }

    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    // public void showDetailAcara(View v){
    //     Intent intent = new Intent(MainActivity.this, DetailAcaraActivity.class);
    //    intent.putExtra("uidacara", "test");
    //    startActivity(intent);
    // }

    private void getData(){
        class GetData extends AsyncTask<Void,Void,String>{
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                progressDialog = ProgressDialog.show(MainActivity.this, "Mengambil Data", "Tunggu Sebentar...", false, false);
            }

            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                progressDialog.dismiss();
                parseJSON(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(Config.URL_CARDVIEW);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }
            }
        }
        GetData gd = new GetData();
        gd.execute();
    }

    public void showData(){
        adapter = new CardAdapter(getApplicationContext(), Config.uidacara, Config.judul,Config.penyelenggara, Config.bitmaps);
        recyclerView.setAdapter(adapter);
    }

    private void parseJSON(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            config = new Config(array.length());

            for(int i=0; i<array.length(); i++){
                JSONObject j = array.getJSONObject(i);
                Config.uidacara[i] = getUIDAcara(j);
                Config.urls[i] = getUrls(j);
                Config.judul[i] = getJudul(j);
                Config.penyelenggara[i] = getPenyelenggara(j);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        GetBitmap gb = new GetBitmap(this,this, Config.urls);
        gb.execute();
    }

    private String getJudul(JSONObject j){
        String judul = null;
        try {
            judul = j.getString(Config.TAG_JUDUL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return judul;
    }

    private String getUIDAcara(JSONObject j){
        String uidacara = null;
        try {
            uidacara = j.getString(Config.TAG_UID_ACARA);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return uidacara;
    }

    private String getPenyelenggara(JSONObject j){
        String penyelenggara = null;
        try {
            penyelenggara = j.getString(Config.TAG_PENYELENGGARA);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return penyelenggara;
    }

    private String getUrls(JSONObject j){
        String urls = null;
        try {
            urls = j.getString(Config.TAG_IMAGE_URL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return urls;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Intent isetting = new Intent(this, SettingActivity.class);
                startActivity(isetting);
                break;
            case R.id.action_addevent:
                Intent iaddevent = new Intent(this, AddAcaraActivity.class);
                startActivity(iaddevent);
                break;
            case R.id.action_logout:
                logoutUser();
                break;
            default:
                break;
        }

        return true;
    }
}
