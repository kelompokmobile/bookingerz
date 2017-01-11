package com.jemberonlineservice.bookingerz.activity;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.jemberonlineservice.bookingerz.MainActivity;
import com.jemberonlineservice.bookingerz.R;

import java.util.Set;

/**
 * Created by vmmod on 12/14/2016.
 */

public class SettingActivity extends AppCompatActivity {
    Button btnabout;

    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnabout = (Button) findViewById(R.id.btn_about);
        btnabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogForm("KELUAR");
            }
        });
    }

    private void DialogForm(String button) {
        dialog = new AlertDialog.Builder(SettingActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_about, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        dialog.setPositiveButton(button, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
