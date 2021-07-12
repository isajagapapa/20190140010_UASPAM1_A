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

        //variabel untuk cardview
        CardView crdK, crdP, crdG;
        //variabel untuk button logout
        Button logout;

        //set id utnuk cardview
        crdK = findViewById(R.id.crdKaryawan);
        crdP = findViewById(R.id.crdPekerjaan);
        crdG = findViewById(R.id.crdGajiKaryawan);
        //set id untuk button logout
        logout = findViewById(R.id.btnLogoutAdm);

        //aksi ketika mengeklik cardK
        crdK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent untuk memanggil activity
                Intent intent = new Intent(HomeAdmin.this,DtKaryawan.class);
                //berpindah dari
                startActivity(intent);
            }
        });

        //aksi ketika mengeklik cardP
        crdP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent untuk memanggil activity DtPekerjaan
                Intent intent = new Intent(HomeAdmin.this, DtPekerjaan.class);
                //berpindah dari HomeAdmin ke DtPekerjaan
                startActivity(intent);
            }
        });

        //aksi ketika mengeklik cardG
        crdG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent untuk memanggil activity DtGajiKaryawan
                Intent intent = new Intent(HomeAdmin.this, DtGajiKaryawan.class);
                //berpindah dari HomeAdmin ke DtGajiKaryawan
                startActivity(intent);
            }
        });

        //aksi ketika mengeklik button logout
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent untuk memanggil activity MainActivity
                Intent intent = new Intent(HomeAdmin.this, MainActivity.class);
                //berpindah dari HomeAdmin ke MainActivity
                startActivity(intent);
            }
        });
    }
}