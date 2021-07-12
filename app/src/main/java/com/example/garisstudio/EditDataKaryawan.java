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
import com.example.garisstudio.App.AppController;
import com.example.garisstudio.adapter.KaryawanAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditDataKaryawan extends AppCompatActivity {
    //variabel untuk edittext
    private EditText editNik, editPw, editNama, editAlamat, editPt, editJabatan;
    //variabel untuk textview
    private TextView edJk, edAgm;
    //variabel untuk button
    private Button simpanBtnedKry;
    //variabel untuk string
    String nik, pw, nama, alm, jk, agm, pt, jabatan, niked, pwed, namaed, almed, jked, agmed, pted, jabataned;
    //variabl untuk integer
    int success;

    //String untuk alamat server, local host android
    private static String url_update_karyawan = "http://10.0.2.2/GarisStudio/updatekaryawan.php";
    //String tag untuk nama pendek dari kelas EditDataKaryawan
    private static final String TAG = EditDataKaryawan.class.getSimpleName();
    //String tag untuk sukses
    private static final String TAG_SUCCES = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data_karyawan);
        //mengeeset judul/title dari activity editdatakaryawan
        setTitle("Edit Data Karyawan");
        //mengganti background pada action bar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.biru)));

        //set id untuk edittext
        editNik = findViewById(R.id.EtNikEdit);
        editPw = findViewById(R.id.EtPwEdit);
        editNama = findViewById(R.id.EtNamaEdit);
        editAlamat = findViewById(R.id.etAlamatEdit);
        edJk = findViewById(R.id.etJkEdit);
        edAgm = findViewById(R.id.etAgamaEdit);
        editPt = findViewById(R.id.etPTEdit);
        editJabatan = findViewById(R.id.etJbtEdit);
        //set id untuk button
        simpanBtnedKry = findViewById(R.id.btnSimpanEditKry);

        //menyimpan data yang dikirim dari activity sebelumnya dengan kunci
        Bundle bundle = getIntent().getExtras();
        nik = bundle.getString("kunci1");
        pw = bundle.getString("kunci2");
        nama = bundle.getString("kunci3");
        alm = bundle.getString("kunci4");
        jk = bundle.getString("kunci5");
        agm = bundle.getString("kunci6");
        pt = bundle.getString("kunci7");
        jabatan = bundle.getString("kunci8");

        //menampilkan value dari variabel string kedalam textview
        editNik.setText(nik);
        editPw.setText(pw);
        editNama.setText(nama);
        editAlamat.setText(alm);
        edJk.setText(jk);
        edAgm.setText(agm);
        editPt.setText(pt);
        editJabatan.setText(jabatan);

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

        //aksi ketika button simpanBtnedKry diklik
        simpanBtnedKry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //memanggil method EditDataKr
                EditDataKr();
            }
        });

    }
    public void EditDataKr(){
        //membaca data dari edittext
        niked = editNik.getText().toString();
        pwed = editPw.getText().toString();
        namaed = editNama.getText().toString();
        almed = editAlamat.getText().toString();
        jked = edJk.getText().toString();
        agmed = edAgm.getText().toString();
        pted = editPt.getText().toString();
        jabataned = editJabatan.getText().toString();

        //antrian request menggunakan library volley
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        //mengirim data ke server
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_update_karyawan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //membaca pesan dari response
                Log.d(TAG, "Response: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCES);
                    //pesan sukses
                    if (success == 1) {
                        Toast.makeText(EditDataKaryawan.this, "Sukses memperbarui data", Toast.LENGTH_SHORT).show();
                    } else {
                        //pesan gagal
                        Toast.makeText(EditDataKaryawan.this, "Gagal memperbarui data", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(EditDataKaryawan.this,"Gagal memperbarui data",Toast.LENGTH_SHORT).show();
            }
        }){
            //mengirim data dalam bentuk array map
            protected Map<String,String> getParams(){
                //membuat objek hashmap
                Map<String,String> params = new HashMap<>();
                //memasukkan data sesuai nama kunci yaitu nama kolom pada tabel database dengan value yang diambil gari edittext
                params.put("nik",niked);
                params.put("password",pwed);
                params.put("nama", namaed);
                params.put("alamat", almed);
                params.put("jenis_kelamin", jked);
                params.put("agama", agmed);
                params.put("pendidikan_terakhir", pted);
                params.put("jabatan", jabataned);

                return params;
            }
        };
        //array dimasukkan ke antrian request
        requestQueue.add(stringRequest);
        //memanggil method callHome1
        callHome1();
    }

    public void callHome1(){
        //intent untuk memanggil activity HomeAdmin
        Intent intent = new Intent(EditDataKaryawan.this,HomeAdmin.class);
        //berpindah dari EditDataKaryawan ke HomeAdmin
        startActivity(intent);
        finish();
    }
}