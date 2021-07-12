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
    //variabel untuk edittext
    private EditText editNik, editPw, editNama, editAlamat, editPt, editJabatan;
    //variabel untuk textview
    private TextView edJk, edAgm;
    //variabel untuk button
    private Button simpanBtnKry;
    //variabel untuk string
    String nik, pw, nama, alm, jk, agm, pt, jabatan;
    //variabl untuk integer
    int success;

    //String untuk alamat server, local host android
    private static String url_insert_karyawan = "http://10.0.2.2/GarisStudio/tambahkaryawan.php";
    //String tag untuk nama pendek dari kelas TambahDataKaryawan
    private static final String TAG = TambahDataKaryawan.class.getSimpleName();
    //String tag untuk sukses
    private static final String TAG_SUCCES = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data_karyawan);
        //mengeeset judul/title dari activity TambahDataKaryawan
        setTitle("Tambah Data Karyawan");
        //mengganti background pada action bar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.biru)));

        //set id untuk edittext
        editNik = findViewById(R.id.EtNikTambah);
        editPw = findViewById(R.id.EtPwTambah);
        editNama = findViewById(R.id.EtNamaTambah);
        editAlamat = findViewById(R.id.etAlamatTambah);
        edJk = findViewById(R.id.etJkTambah);
        edAgm = findViewById(R.id.etAgamaTambah);
        editPt = findViewById(R.id.etPTTambah);
        editJabatan = findViewById(R.id.etJbtTambah);

        //set posisi edittext
        edJk.setGravity(Gravity.CENTER_VERTICAL);
        edAgm.setGravity(Gravity.CENTER_VERTICAL);

        //aksi ketika edittext edjk diklik
        edJk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //membuat objek popup menu
                PopupMenu pm = new PopupMenu(v.getContext(),v);
                //menampilkan popup menu dari layout menu
                pm.inflate(R.menu.jkpop);

                //membuat event untuk popup menu ketika dipilih
                pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.laki:
                                //menampilkan value
                                edJk.setText("Laki-Laki");
                                break;
                            case R.id.perempuan:
                                //menampilkan value
                                edJk.setText("Perempuan");
                        }
                        return true;
                    }
                });
                //menmapilkan popup menu
                pm.show();
            }
        });
        //aksi ketika edittext edagm diklik
        edAgm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //membuat objek popup menu
                PopupMenu pm1 = new PopupMenu(v.getContext(), v);
                //menampilkan popup menu dari layout menu
                pm1.inflate(R.menu.agamapop);

                //membuat event untuk popup menu ketika dipilih
                pm1.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.islam:
                                //menampilkan value
                                edAgm.setText("Islam");
                                break;
                            case  R.id.kristen:
                                //menampilkan value
                                edAgm.setText("Katolik");
                                break;
                            case R.id.katolik:
                                //menampilkan value
                                edAgm.setText("Kristen");
                                break;
                            case R.id.hindu:
                                //menampilkan value
                                edAgm.setText("Hindu");
                                break;
                            case R.id.budha:
                                //menampilkan value
                                edAgm.setText("Budha");
                                break;
                            case R.id.konghucu:
                                //menampilkan value
                                edAgm.setText("Konghucu");
                                break;
                        }
                        return true;
                    }
                });
                //menmapilkan popup menu
                pm1.show();
            }
        });

        //set id untuk button
        simpanBtnKry = findViewById(R.id.btnSimpanTambahKry);

        //aksi ketika button simpanBtnKry diklik
        simpanBtnKry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //memanggil method SimpanDataKry
                SimpanDataKry();
            }
        });
    }

    public void SimpanDataKry(){
        //pesan ketika edittext kosong
        if(editJabatan.getText().toString().equals("") || editAlamat.getText().toString().equals("") || editNama.getText().toString().equals("")
        || editPw.getText().toString().equals("") || editNik.getText().toString().equals("") || editPt.getText().toString().equals("")
        || edJk.getText().toString().equals("") || edAgm.getText().toString().equals("")){
            Toast.makeText(TambahDataKaryawan.this,"Semua harus diisi data",Toast.LENGTH_SHORT).show();
        }else {
            //membaca data dari edittext
            nik = editNik.getText().toString();
            pw = editPw.getText().toString();
            nama = editNama.getText().toString();
            alm = editAlamat.getText().toString();
            jk = edJk.getText().toString();
            agm = edAgm.getText().toString();
            pt = editPt.getText().toString();
            jabatan = editJabatan.getText().toString();

            //antrian request menggunakan library volley
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            //mengirim data ke server
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url_insert_karyawan, new Response.Listener<String>() {
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
                            Toast.makeText(TambahDataKaryawan.this, "Sukses menyimpan data", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(TambahDataKaryawan.this,"Gagal menyimpan data",Toast.LENGTH_SHORT).show();
                }
            }){
                //mengirim data dalam bentuk array map
                protected Map<String,String> getParams(){
                    //membuat objek hashmap
                    Map<String,String> params = new HashMap<>();
                    //memasukkan data sesuai nama kunci yaitu nama kolom pada tabel database dengan parameter
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
            //array dimasukkan ke antrian request
            requestQueue.add(stringRequest);
            //memanggil method callHome1
            callHome();
        }
    }

    public void callHome(){
        //intent untuk memanggil activity HomeAdmin
        Intent intent = new Intent(TambahDataKaryawan.this,HomeAdmin.class);
        //berpindah dari TambahDataKaryawan ke HomeAdmin
        startActivity(intent);
        finish();
    }
}