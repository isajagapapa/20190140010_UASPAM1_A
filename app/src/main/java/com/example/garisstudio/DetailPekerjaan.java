package com.example.garisstudio;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class DetailPekerjaan extends AppCompatActivity {

    private TextView editJabatan,editGp, editTk, editTp, editTt, editTotal;
    String jb, gp, tk,tp, tt, ttl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pekerjaan);
        setTitle("Detail Data Pekerjaan");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.biru)));

        editJabatan = findViewById(R.id.tvNmJbt);
        editGp = findViewById(R.id.tvGajiPokok);
        editTk = findViewById(R.id.tvTK);
        editTp = findViewById(R.id.tvTP);
        editTt = findViewById(R.id.tvTT);
        editTotal = findViewById(R.id.tvTotal);

        jb = getIntent().getStringExtra("jbt");
        gp = getIntent().getStringExtra("gjpk");
        tk = getIntent().getStringExtra("tk");
        tp = getIntent().getStringExtra("tp");
        tt = getIntent().getStringExtra("tt");
        ttl = getIntent().getStringExtra("total");

        editJabatan.setText("Rp "+FormatAngka(gp));
        editGp.setText("Rp "+FormatAngka(gp));
        editTk.setText("Rp "+FormatAngka(tk));
        editTp.setText("Rp "+FormatAngka(tp));
        editTt.setText("Rp "+FormatAngka(tt));;
        editTotal.setText("TOTAL: Rp "+FormatAngka(ttl));
    }

    private static String FormatAngka(String angka){
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatrp = new DecimalFormatSymbols();
        formatrp.setGroupingSeparator('.');
        decimalFormat.setDecimalFormatSymbols(formatrp);

        return decimalFormat.format(Double.parseDouble(angka));
    }
}