package com.example.garisstudio;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class DetailKaryawan extends AppCompatActivity {
    //variabel untuk textview
    private TextView tvNik, tvPw, tvNama, tvAlamat, tvPt, tvJabatan, tvJk, tvAgm;
    //variabel untuk string
    String nik, pw, nama, alm, jk, agm, pt, jabatan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_karyawan);
        //mengeeset judul/title dari activity detailkaryawan
        setTitle("Detail Data Karyawan");
        //mengganti background pada action bar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.biru)));

        //set id untuk textview
        tvNik = findViewById(R.id.tvNIK);
        tvPw = findViewById(R.id.tvPwKry);
        tvNama = findViewById(R.id.tvNamaKry);
        tvAlamat = findViewById(R.id.tvAlamatKry);
        tvJk = findViewById(R.id.tvJkKry);
        tvAgm = findViewById(R.id.tvAgamaKry);
        tvPt = findViewById(R.id.tvPtKry);
        tvJabatan = findViewById(R.id.tvJbtKry);

        //menyimpan data yang dikirim dari activity sebelumnya dengan kunci
        nik = getIntent().getStringExtra("kunci1");
        pw = getIntent().getStringExtra("kunci2");
        nama = getIntent().getStringExtra("kunci3");
        alm = getIntent().getStringExtra("kunci4");
        jk = getIntent().getStringExtra("kunci5");
        agm = getIntent().getStringExtra("kunci6");
        pt = getIntent().getStringExtra("kunci7");
        jabatan = getIntent().getStringExtra("kunci8");

        //menamilkan value dari variabel string kedalam textview
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