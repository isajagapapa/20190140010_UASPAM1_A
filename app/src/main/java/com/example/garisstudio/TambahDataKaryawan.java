package com.example.garisstudio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.garisstudio.adapter.KaryawanAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TambahDataKaryawan extends AppCompatActivity {
    private EditText editNik, editPw, editNama, editAlamat, editPt, editJabatan;
    private TextView edJk, edAgm;
    private Button simpanBtnKry;
    String nik, pw, nama, alm, jk, agm, pt, jabatan;
    int success;

    private static String url_insert_karyawan = "http://10.0.2.2/GarisStudio/tambahkaryawan.php";
    private static final String TAG = TambahDataKaryawan.class.getSimpleName();
    private static final String TAG_SUCCES = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data_karyawan);
        setTitle("Tambah Data Karyawan");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.biru)));

        editNik = findViewById(R.id.EtNikTambah);
        editPw = findViewById(R.id.EtPwTambah);
        editNama = findViewById(R.id.EtNamaTambah);
        editAlamat = findViewById(R.id.etAlamatTambah);
        edJk = findViewById(R.id.etJkTambah);
        edAgm = findViewById(R.id.etAgamaTambah);
        editPt = findViewById(R.id.etPTTambah);
        editJabatan = findViewById(R.id.etJbtTambah);

        edJk.setGravity(Gravity.CENTER_VERTICAL);
        edAgm.setGravity(Gravity.CENTER_VERTICAL);

        edJk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu pm = new PopupMenu(v.getContext(),v);
                pm.inflate(R.menu.jkpop);

                pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.laki:
                                edJk.setText("Laki-Laki");
                                break;
                            case R.id.perempuan:
                                edJk.setText("Perempuan");
                        }
                        return true;
                    }
                });
                pm.show();
            }
        });
        edAgm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu pm1 = new PopupMenu(v.getContext(), v);
                pm1.inflate(R.menu.agamapop);

                pm1.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.islam:
                                edAgm.setText("Islam");
                                break;
                            case  R.id.kristen:
                                edAgm.setText("Katolik");
                                break;
                            case R.id.katolik:
                                edAgm.setText("Kristen");
                                break;
                            case R.id.hindu:
                                edAgm.setText("Hindu");
                                break;
                            case R.id.budha:
                                edAgm.setText("Budha");
                                break;
                            case R.id.konghucu:
                                edAgm.setText("Konghucu");
                                break;
                        }
                        return true;
                    }
                });
                pm1.show();
            }
        });

        simpanBtnKry = findViewById(R.id.btnSimpanTambahKry);

        simpanBtnKry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpanDataKry();
            }
        });
    }

    public void SimpanDataKry(){
        if(editJabatan.getText().toString().equals("") || editAlamat.getText().toString().equals("") || editNama.getText().toString().equals("")
        || editPw.getText().toString().equals("") || editNik.getText().toString().equals("") || editPt.getText().toString().equals("")
        || edJk.getText().toString().equals("") || edAgm.getText().toString().equals("")){
            Toast.makeText(TambahDataKaryawan.this,"Semua harus diisi data",Toast.LENGTH_SHORT).show();
        }else {
            nik = editNik.getText().toString();
            pw = editPw.getText().toString();
            nama = editNama.getText().toString();
            alm = editAlamat.getText().toString();
            jk = edJk.getText().toString();
            agm = edAgm.getText().toString();
            pt = editPt.getText().toString();
            jabatan = editJabatan.getText().toString();

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url_insert_karyawan, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Response: " + response.toString());
                    try {
                        JSONObject jObj = new JSONObject(response);
                        success = jObj.getInt(TAG_SUCCES);
                        if (success == 1) {
                            Toast.makeText(TambahDataKaryawan.this, "Sukses menyimpan data", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Error: "+error.getMessage());
                    Toast.makeText(TambahDataKaryawan.this,"Gagal menyimpan data",Toast.LENGTH_SHORT).show();
                }
            }){
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<>();
                    params.put("nik",nik);
                    params.put("password",pw);
                    params.put("nama", nama);
                    params.put("alamat", alm);
                    params.put("jenis_kelamin", jk);
                    params.put("agama", agm);
                    params.put("pendidikan_terakhir", pt);
                    params.put("jabatan", jabatan);

                    return params;
                }
            };
            requestQueue.add(stringRequest);
            callHome();
        }
    }

    public void callHome(){
        Intent intent = new Intent(TambahDataKaryawan.this,HomeAdmin.class);
        startActivity(intent);
        finish();
    }
}