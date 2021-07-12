package com.example.garisstudio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.garisstudio.adapter.KaryawanAdapter;
import com.example.garisstudio.database.Karyawan;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DtKaryawan extends AppCompatActivity {
    //mendeklarasikan variabel dengan jenis recuclerview
    private RecyclerView recyclerView;
    //mendeklarasikan variabel adapter
    private KaryawanAdapter adapter;
    //Deklarasi variabel dengan jenis data array list
    private ArrayList<Karyawan> karyawanArrayList = new ArrayList<>();
    //deklarasi variabel button
    private Button tambahkr;

    //String tag untuk nama pendek dari kelas DtKaryawan
    private static final String TAG = DtKaryawan.class.getSimpleName();
    //String untuk alamat server, local host android
    private static String url_select_kry = "http://10.0.2.2/GarisStudio/bacakaryawan.php";
    //String nama-nama kolom dalam tabel database
    private static final String TAG_NIK = "nik";
    private static final String TAG_PW = "password";
    private static final String TAG_NAMA = "nama";
    private static final String TAG_ALAMAT = "alamat";
    private static final String TAG_JK = "jenis_kelamin";
    private static final String TAG_AGAMA = "agama";
    private static final String TAG_PT = "pendidikan_terakhir";
    private static final String TAG_JABATAN = "jabatan";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dt_karyawan);
        //mengeeset judul/title dari activity Karyawan
        setTitle("Karyawan");
        //mengganti background pada action bar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.biru)));

        //set id untuk recyclerview
        recyclerView = findViewById(R.id.rv_dtKry);
        //set id untuk button
        tambahkr = findViewById(R.id.btnTambahKry);

        //memanggil method baca data
        BacaData();

        //memanggil arraylist
        adapter = new KaryawanAdapter(karyawanArrayList);
        //mengatur item dalam array list menggunakan layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DtKaryawan.this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        tambahkr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent untuk memanggil activity TambahDataKaryawan
                Intent intent = new Intent(DtKaryawan.this, TambahDataKaryawan.class);
                // berpindah ke activity TambahDataKaryawan
                startActivity(intent);
            }
        });
    }

    public void BacaData(){
        karyawanArrayList.clear();
        //antrian request menggunakan library volley
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        //memulai request dengan jsonarrayrequest karena data kita yang dibaca oleh php itu dalam format json array
        JsonArrayRequest jArr = new JsonArrayRequest(url_select_kry, new Response.Listener<JSONArray>() {
            @Override
            //hasil pembacaan respon
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                //parsing json/pilah
                for (int i = 0; i < response.length(); i++) {
                    try {
                        //masuk kedalam objek untuk membaca data yang diambil dalam format json
                        JSONObject obj = response.getJSONObject(i);
                        //data di set sesuai nama masing-masing menggunakan model data di class karyawan
                        //hasil dari passing jsonnya akan disimpan kedalam model data karyawan
                        Karyawan item = new Karyawan();
                        item.setNik(obj.getString(TAG_NIK));
                        item.setPassword(obj.getString(TAG_PW));
                        item.setNama(obj.getString(TAG_NAMA));
                        item.setAlamat(obj.getString(TAG_ALAMAT));
                        item.setJenis_kelamin(obj.getString(TAG_JK));
                        item.setAgama(obj.getString(TAG_AGAMA));
                        item.setPendidikan_terakhir(obj.getString(TAG_PT));
                        item.setJabatan(obj.getString(TAG_JABATAN));

                        //menambah item ke array
                        //hasilnya database gaji disimpan dalam array list agar bisa dibaca dalam adapter
                        karyawanArrayList.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //redraw recyclerview dengan data baru
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            // akan memunculkan pesan jika koneksi gagal
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Eror: " + error.getMessage());
                error.printStackTrace();
                Toast.makeText(DtKaryawan.this,"gagal",Toast.LENGTH_SHORT).show();
            }
        });
        //json array dimasukkan ke antrian request
        requestQueue.add(jArr);
    }
}