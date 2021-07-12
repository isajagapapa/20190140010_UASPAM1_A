package com.example.garisstudio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class EditDataPekerjaan extends AppCompatActivity {
    //variabel untuk edittext
    private EditText editJabatan,editGp, editTk, editTp, editTt, editTotal;
    //variabel untuk button
    Button editBtn;
    //variabel untuk string
    String jbt;
    String gjp;
    String tk;
    String tp;
    String tt;
    String totalgj;
    String edjbt;
    String edgjp;
    String edtk;
    String edtp;
    String edtt;
    String edtotal;
    //variabl untuk integer
    int sukses;

    //String untuk alamat server, local host android
    private static String url_update_pkr = "http://10.0.2.2/GarisStudio/updatepekerjaan.php";
    //String tag untuk nama pendek dari kelas EditDataPekerjaan
    private static final String TAG = EditDataPekerjaan.class.getSimpleName();
    //String tag untuk sukses
    private static final String TAG_SUCCES = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data_pekerjaan);
        //mengeeset judul/title dari activity EditDataGajiKAryawan
        setTitle("Edit Data Pekerjaan");
        //mengganti background pada action bar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.biru)));

        //set id untuk edittext
        editJabatan = findViewById(R.id.EtJbtEdit);
        editGp = findViewById(R.id.EtGajiPokokEdit);
        editTk = findViewById(R.id.etTKEdit);
        editTp = findViewById(R.id.etTPEdit);
        editTt = findViewById(R.id.etTTEdit);
        editTotal = findViewById(R.id.edittTTotalEdit);
        //set id untuk button
        editBtn = findViewById(R.id.btnSimpanEditKerja);

        //menyimpan data yang dikirim dari activity sebelumnya dengan kunci
        Bundle bundle = getIntent().getExtras();
        jbt = bundle.getString("jbt");
        gjp = bundle.getString("gjpk");
        tk = bundle.getString("tk");
        tp = bundle.getString("tp");
        tt = bundle.getString("tt");
        totalgj = bundle.getString("total");

        //menamilkan value dari variabel string kedalam textview
        editJabatan.setText(jbt);
        editGp.setText(gjp);
        editTk.setText(tk);
        editTp.setText(tp);
        editTt.setText(tt);
        editTotal.setText(totalgj);
        //aksi ketika klik editBtn
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //memanggil method EditDataKerja
                EditDataKerja();
            }
        });
    }
    public void EditDataKerja(){
        //membaca data dari edittext
        edjbt = editJabatan.getText().toString();
        edgjp = editGp.getText().toString();
        edtk = editTk.getText().toString();
        edtp = editTp.getText().toString();
        edtt = editTt.getText().toString();
        edtotal = editTotal.getText().toString();

        //antrian request menggunakan library volley
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        //mengirim data ke server
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_update_pkr, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //membaca pesan dari response
                Log.d(TAG, "Response: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    sukses = jObj.getInt(TAG_SUCCES);
                    //pesan sukses
                    if (sukses == 1) {
                        Toast.makeText(EditDataPekerjaan.this, "Sukses memperbarui data", Toast.LENGTH_SHORT).show();
                    } else {
                        //pesan gagal
                        Toast.makeText(EditDataPekerjaan.this, "Gagal memperbarui data", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(EditDataPekerjaan.this,"Gagal memperbarui data",Toast.LENGTH_SHORT).show();
            }
        }){
            //mengirim data dalam bentuk array map
            protected Map<String,String> getParams(){
                //membuat objek hashmap
                Map<String,String> params = new HashMap<>();
                //memasukkan data sesuai nama kunci yaitu nama kolom pada tabel database dengan parameter
                params.put("jabatan", edjbt);
                params.put("gaji_pokok", edgjp);
                params.put("tunjangan_kesehatan", edtk);
                params.put("tunjangan_pendidikan", edtk);
                params.put("tunjangan_transportasi", edtt);
                params.put("total_gaji", edtotal);

                return params;
            }
        };
        //array dimasukkan ke antrian request
        requestQueue.add(stringRequest);
        //memanggil method callHome
        callHome();
    }
    public void callHome(){
        //intent untuk memanggil activity HomeAdmin
        Intent intent = new Intent(EditDataPekerjaan.this,HomeAdmin.class);
        //berpindah dari EditDataPekerjaan ke HomeAdmin
        startActivity(intent);
        finish();
    }
}