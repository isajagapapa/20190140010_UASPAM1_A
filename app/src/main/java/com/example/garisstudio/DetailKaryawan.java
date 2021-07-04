package com.example.garisstudio;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class DetailKaryawan extends AppCompatActivity {
    private TextView tvNik, tvPw, tvNama, tvAlamat, tvPt, tvJabatan, tvJk, tvAgm;
    String nik, pw, nama, alm, jk, agm, pt, jabatan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_karyawan);
        setTitle("Detail Data Karyawan");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.biru)));

        tvNik = findViewById(R.id.tvNIK);
        tvPw = findViewById(R.id.tvPwKry);
        tvNama = findViewById(R.id.tvNamaKry);
        tvAlamat = findViewById(R.id.tvAlamatKry);
        tvJk = findViewById(R.id.tvJkKry);
        tvAgm = findViewById(R.id.tvAgamaKry);
        tvPt = findViewById(R.id.tvPtKry);
        tvJabatan = findViewById(R.id.tvJbtKry);

        nik = getIntent().getStringExtra("kunci1");
        pw = getIntent().getStringExtra("kunci2");
        nama = getIntent().getStringExtra("kunci3");
        alm = getIntent().getStringExtra("kunci4");
        jk = getIntent().getStringExtra("kunci5");
        agm = getIntent().getStringExtra("kunci6");
        pt = getIntent().getStringExtra("kunci7");
        jabatan = getIntent().getStringExtra("kunci8");

        tvNik.setText(nik);
        tvPw.setText(pw);
        tvNama.setText(nama);
        tvAlamat.setText(alm);
        tvJk.setText(jk);
        tvAgm.setText(agm);
        tvPt.setText(pt);
        tvJabatan.setText(jabatan);
    }
}