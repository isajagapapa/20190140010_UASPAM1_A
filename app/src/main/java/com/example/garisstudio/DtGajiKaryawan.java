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
import com.example.garisstudio.adapter.GajiAdapter;
import com.example.garisstudio.adapter.KaryawanAdapter;
import com.example.garisstudio.database.Gaji;
import com.example.garisstudio.database.Karyawan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DtGajiKaryawan extends AppCompatActivity {
    //mendeklarasikan variabel dengan jenis recuclerview
    private RecyclerView recyclerView;
    //mendeklarasikan variabel adapter
    private GajiAdapter adapter;

    //Deklarasi variabel dengan jenis data array list
    private ArrayList<Gaji> gajiArrayList = new ArrayList<>();
    //deklarasi variabel button
    private Button btntmbahgaji;

    //String tag untuk nama pendek dari kelas DtGajiKaryawan
    private static final String TAG = DtGajiKaryawan.class.getSimpleName();
    //String untuk alamat server, local host android
    private static String url_select_gaji = "http://10.0.2.2/GarisStudio/bacagaji.php";
    //String nama-nama kolom dalam tabel database
    private static final String TAG_NO = "No_Gaji";
    private static final String TAG_TANGGAL = "tanggal_gaji";
    private static final String TAG_NIK = "nik";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dt_gaji_karyawan);
        //mengeeset judul/title dari activity dtgajikaryawan
        setTitle("Tanggal Gaji Karyawan");
        //mengganti background pada action bar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.biru)));

        //set id untuk recyclerview
        recyclerView = findViewById(R.id.rv_dtGajiKaryawan);
        //set id untuk button
        btntmbahgaji = findViewById(R.id.btnTambahGaji);

        //memanggil method baca data gaji
        BacaDataGaji();

        //memanggil arraylist
        adapter = new GajiAdapter(gajiArrayList);
        //mengatur item dalam array list menggunakan layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DtGajiKaryawan.this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        btntmbahgaji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent untuk memanggil activity Tambahdatagajikaryawan
                Intent intent = new Intent(DtGajiKaryawan.this,TambahDataGajiKaryawan.class);
                // berpindah ke activity Tambahdatagajikaryawan
                startActivity(intent);
            }
        });

    }

    public void BacaDataGaji(){
        gajiArrayList.clear();
        //antrian request menggunakan library volley
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        //memulai request dengan jsonarrayrequest karena data kita yang dibaca oleh php itu dalam format json array
        JsonArrayRequest jArr = new JsonArrayRequest(url_select_gaji, new Response.Listener<JSONArray>() {
            @Override
            //hasil pembacaan respon
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

//                parsing json/pilah
                for (int i = 0; i < response.length(); i++) {
                    try {
                        //masuk kedalam objek untuk membaca data yang diambil dalam format json
                        JSONObject obj = response.getJSONObject(i);
                        //data di set sesuai nama masing-masing menggunakan model data di class gaji
                        //hasil dari passing jsonnya akan disimpan kedalam model data gaji
                        Gaji item = new Gaji();
                        item.setNo_Gaji(obj.getString(TAG_NO));
                        item.setTanggal_gaji(obj.getString(TAG_TANGGAL));
                        item.setNik(obj.getString(TAG_NIK));

//                        menambah item ke array
                        //hasilnya database gaji disimpan dalam array list agar bisa dibaca dalam adapter
                        gajiArrayList.add(item);
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
                Toast.makeText(DtGajiKaryawan.this,"gagal",Toast.LENGTH_SHORT).show();
            }
        });
        //json array dimasukkan ke antrian request
        requestQueue.add(jArr);
    }
}