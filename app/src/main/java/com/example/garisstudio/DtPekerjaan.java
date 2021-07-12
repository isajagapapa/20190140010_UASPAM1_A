package com.example.garisstudio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.garisstudio.adapter.PekerjaanAdapter;
import com.example.garisstudio.database.Karyawan;
import com.example.garisstudio.database.Pekerjaan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DtPekerjaan extends AppCompatActivity {
    //mendeklarasikan variabel dengan jenis recuclerview
    private RecyclerView recyclerView;
    //mendeklarasikan variabel adapter
    private PekerjaanAdapter adapter;
    //Deklarasi variabel dengan jenis data array list
    private ArrayList<Pekerjaan> pekerjaanArrayList = new ArrayList<>();
    //deklarasi variabel button
    private Button tambahpk;

    //String tag untuk nama pendek dari kelas DtPekerjaan
    private static final String TAG = DtPekerjaan.class.getSimpleName();
    //String untuk alamat server, local host android
    private static String url_select_pkr = "http://10.0.2.2/GarisStudio/bacapekerjaan.php";
    //String nama-nama kolom dalam tabel database
    private static final String TAG_JBT = "jabatan";
    private static final String TAG_GJP = "gaji_pokok";
    private static final String TAG_TK = "tunjangan_kesehatan";
    private static final String TAG_TP = "tunjangan_pendidikan";
    private static final String TAG_TT = "tunjangan_transportasi";
    private static final String TAG_TOTAL = "total_gaji";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dt_pekerjaan);
        //mengeeset judul/title dari activity Pekerjaan
        setTitle("Pekerjaan");
        //mengganti background pada action bar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.biru)));

        //set id untuk recyclerview
        recyclerView = findViewById(R.id.rv_dtPkr);
        //set id untuk button
        tambahpk = findViewById(R.id.btnTambahPkr);

        //memanggil method baca data
        BacaData();

        //memanggil arraylist
        adapter = new PekerjaanAdapter(pekerjaanArrayList);
        //mengatur item dalam array list menggunakan layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DtPekerjaan.this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        tambahpk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent untuk memanggil activity TambahDataPekerjaan
                Intent intent = new Intent(DtPekerjaan.this, TambahDataPekerjaan.class);
                // berpindah ke activity TambahDataPekerjaan
                startActivity(intent);
            }
        });
    }

    public void BacaData(){
        pekerjaanArrayList.clear();
        //antrian request menggunakan library volley
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        //memulai request dengan jsonarrayrequest karena data kita yang dibaca oleh php itu dalam format json array
        JsonArrayRequest jArr = new JsonArrayRequest(url_select_pkr, new Response.Listener<JSONArray>() {
            @Override
            //hasil pembacaan respon
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                for (int i = 0; i < response.length(); i++) {
                    try {
                        //masuk kedalam objek untuk membaca data yang diambil dalam format json
                        JSONObject obj = response.getJSONObject(i);
                        //data di set sesuai nama masing-masing menggunakan model data di class karyawan
                        //hasil dari passing jsonnya akan disimpan kedalam model data karyawan
                        Pekerjaan item = new Pekerjaan();
                        item.setJabatan(obj.getString(TAG_JBT));
                        item.setGaji_pokok(obj.getString(TAG_GJP));
                        item.setTunjangan_kesehatan(obj.getString(TAG_TK));
                        item.setTunjangan_pendidikan(obj.getString(TAG_TP));
                        item.setTunjangan_transportasi(obj.getString(TAG_TT));
                        item.setTotal_gaji(obj.getString(TAG_TOTAL));

                        //menambah item ke array
                        //hasilnya database gaji disimpan dalam array list agar bisa dibaca dalam adapter
                        pekerjaanArrayList.add(item);
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
                Toast.makeText(DtPekerjaan.this,"gagal",Toast.LENGTH_SHORT).show();
            }
        });
        //json array dimasukkan ke antrian request
        requestQueue.add(jArr);
    }
}