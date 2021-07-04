package com.example.garisstudio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        CardView crdK, crdP, crdG;
        Button logout;

        crdK = findViewById(R.id.crdKaryawan);
        crdP = findViewById(R.id.crdPekerjaan);
        crdG = findViewById(R.id.crdGajiKaryawan);
        logout = findViewById(R.id.btnLogoutAdm);

        crdK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeAdmin.this,DtKaryawan.class);
                startActivity(intent);
            }
        });

        crdP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeAdmin.this, DtPekerjaan.class);
                startActivity(intent);
            }
        });

        crdG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeAdmin.this, DtGajiKaryawan.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeAdmin.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}