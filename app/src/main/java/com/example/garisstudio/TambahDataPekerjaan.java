package com.example.garisstudio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TambahDataPekerjaan extends AppCompatActivity {
    //variabel untuk edittext
    private EditText editJabatan,editGp, editTk, editTp, editTt;
    //variabel untuk textview
    private TextView editTotal;
    //variabel untuk buttton
    private Button btn,simpanEditBtn;
    //variabel untuk string
    String jbt;
    String gjpk,tk,tp,tt,total;
    //variabl untuk integer
    int success;

    //String untuk alamat server, local host android
    private static String url_insert_pkr = "http://10.0.2.2/GarisStudio/tambahpekerjaan.php";
    //String tag untuk nama pendek dari kelas TambahDataKaryawan
    private static final String TAG = TambahDataKaryawan.class.getSimpleName();
    //String tag untuk sukses
    private static final String TAG_SUCCES = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data_pekerjaan);
        //mengeeset judul/title dari activity TambahDataKaryawan
        setTitle("Tambah Data Karyawan");
        //mengganti background pada action bar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.biru)));

        //set id untuk edittext
        editJabatan = findViewById(R.id.EtJbtTambah);
        editGp = findViewById(R.id.EtGajiPokokTambah);
        editTk = findViewById(R.id.etTKTambah);
        editTp = findViewById(R.id.etTPTambah);
        editTt = findViewById(R.id.etTTTambah);
        editTotal = findViewById(R.id.etTotalTambah);

        //set id untuk button
        simpanEditBtn = findViewById(R.id.btnSimpanTambahKerja);

        //set id untuk button
        btn = findViewById(R.id.button);
        //aksi ketika button btn diklik
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int gjpk,tk,tp,tt,hasil;

                jbt = editJabatan.getText().toString();
                gjpk = Integer.parseInt(editGp.getText().toString());
                tk =Integer.parseInt(editGp.getText().toString());
                tp = Integer.parseInt(editTp.getText().toString());
                tt = Integer.parseInt(editTt.getText().toString());
                hasil = gjpk + tk + tp + tt;
                editTotal.setText(String.valueOf(hasil));
            }
        });
        //aksi ketika button
        simpanEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //memanggil method SimpanDataPkr
                SimpanDataPkr();
            }
        });
    }

    public void SimpanDataPkr(){
        //pesan ketika edittext kosong
        if(editJabatan.getText().toString().equals("")||editGp.getText().toString().equals("")||editTk.getText().toString().equals("")||
                editTp.getText().toString().equals("")||editTotal.getText().toString().equals("")) {
            Toast.makeText(TambahDataPekerjaan.this, "Semua harus diisi data", Toast.LENGTH_SHORT).show();
        }else {
            //membaca data dari edittext
            jbt = editJabatan.getText().toString();
            gjpk = editGp.getText().toString();
            tk = editTk.getText().toString();
            tp = editTp.getText().toString();
            tt = editTt.getText().toString();
            total = editTotal.getText().toString();

            //antrian request menggunakan library volley
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            //mengirim data ke server
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url_insert_pkr, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //membaca pesan dari response
                    Log.d(TAG, "Response: " + response.toString());
                    try {
                        JSONObject jObj = new JSONObject(response);
                        success = jObj.getInt(TAG_SUCCES);
                        //pesan sukses
                        if (success == 1) {
                            //pesan gagal
                            Toast.makeText(TambahDataPekerjaan.this, "Sukses menyimpan data", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        //memunculkan pesan kesalahan
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //membaca pesan eror jika jaringan bermasalah
                    Log.e(TAG, "Error: "+error.getMessage());
                    Toast.makeText(TambahDataPekerjaan.this,"Gagal menyimpan data",Toast.LENGTH_SHORT).show();
                }
            }){
                //mengirim data dalam bentuk array map
                protected Map<String,String> getParams(){
                    //membuat objek hashmap
                    Map<String,String> params = new HashMap<>();
                    //memasukkan data sesuai nama kunci yaitu nama kolom pada tabel database dengan parameter
                    params.put("jabatan",jbt);
                    params.put("gaji_pokok",gjpk);
                    params.put("tunjangan_kesehatan", tk);
                    params.put("tunjangan_pendidikan", tp);
                    params.put("tunjangan_transportasi", tt);
                    params.put("total_gaji", total);

                    return params;
                }
            };
            //array dimasukkan ke antrian request
            requestQueue.add(stringRequest);
            //memanggil method callHome1
            callHome();
        }
    }

    public void callHome() {
        //intent untuk memanggil activity HomeAdmin
        Intent intent = new Intent(TambahDataPekerjaan.this, HomeAdmin.class);
        //berpindah dari TambahDataPekerjaan ke HomeAdmin
        startActivity(intent);
        finish();
    }
}