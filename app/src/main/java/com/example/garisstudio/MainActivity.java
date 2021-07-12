package com.example.garisstudio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //variabel untuk button
        Button adm;

        //set id untuk button
        adm = findViewById(R.id.btnAdmin);

        //aksi ketika mengeklik button
        adm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent untuk memanggil activity LoginAdmin
                Intent intent = new Intent(MainActivity.this,LoginAdmin.class);
                //berpindah dari MainActivity ke LoginAdmin
                startActivity(intent);
            }
        });
    }
}