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
    private EditText editJabatan,editGp, editTk, editTp, editTt;
    private TextView editTotal;
    private Button btn,simpanEditBtn;
    String jbt;
    String gjpk,tk,tp,tt,total;
    int success;

    private static String url_insert_pkr = "http://10.0.2.2/GarisStudio/tambahpekerjaan.php";
    private static final String TAG = TambahDataKaryawan.class.getSimpleName();
    private static final String TAG_SUCCES = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data_pekerjaan);
        setTitle("Tambah Data Karyawan");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.biru)));

        editJabatan = findViewById(R.id.EtJbtTambah);
        editGp = findViewById(R.id.EtGajiPokokTambah);
        editTk = findViewById(R.id.etTKTambah);
        editTp = findViewById(R.id.etTPTambah);
        editTt = findViewById(R.id.etTTTambah);
        editTotal = findViewById(R.id.etTotalTambah);

        simpanEditBtn = findViewById(R.id.btnSimpanTambahKerja);

        btn = findViewById(R.id.button);
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
        simpanEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpanDataPkr();
            }
        });
    }

    public void SimpanDataPkr(){
        if(editJabatan.getText().toString().equals("")||editGp.getText().toString().equals("")||editTk.getText().toString().equals("")||
                editTp.getText().toString().equals("")||editTotal.getText().toString().equals("")) {
            Toast.makeText(TambahDataPekerjaan.this, "Semua harus diisi data", Toast.LENGTH_SHORT).show();
        }else {
            jbt = editJabatan.getText().toString();
            gjpk = editGp.getText().toString();
            tk = editTk.getText().toString();
            tp = editTp.getText().toString();
            tt = editTt.getText().toString();
            total = editTotal.getText().toString();

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url_insert_pkr, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Response: " + response.toString());
                    try {
                        JSONObject jObj = new JSONObject(response);
                        success = jObj.getInt(TAG_SUCCES);
                        if (success == 1) {
                            Toast.makeText(TambahDataPekerjaan.this, "Sukses menyimpan data", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Error: "+error.getMessage());
                    Toast.makeText(TambahDataPekerjaan.this,"Gagal menyimpan data",Toast.LENGTH_SHORT).show();
                }
            }){
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<>();
                    params.put("jabatan",jbt);
                    params.put("gaji_pokok",gjpk);
                    params.put("tunjangan_kesehatan", tk);
                    params.put("tunjangan_pendidikan", tp);
                    params.put("tunjangan_transportasi", tt);
                    params.put("total_gaji", total);

                    return params;
                }
            };
            requestQueue.add(stringRequest);
            callHome();
        }
    }

    public void callHome() {
        Intent intent = new Intent(TambahDataPekerjaan.this, HomeAdmin.class);
        startActivity(intent);
        finish();
    }
}