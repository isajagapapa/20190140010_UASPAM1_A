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
    private RecyclerView recyclerView;
    private KaryawanAdapter adapter;
    private ArrayList<Karyawan> karyawanArrayList = new ArrayList<>();
    private Button tambahkr;

    private static final String TAG = DtKaryawan.class.getSimpleName();
    private static String url_select_kry = "http://10.0.2.2/GarisStudio/bacakaryawan.php";
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
        setTitle("Karyawan");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.biru)));

        recyclerView = findViewById(R.id.rv_dtKry);
        tambahkr = findViewById(R.id.btnTambahKry);

        BacaData();

        adapter = new KaryawanAdapter(karyawanArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DtKaryawan.this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        tambahkr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DtKaryawan.this, TambahDataKaryawan.class);
                startActivity(intent);
            }
        });
    }

    public void BacaData(){
        karyawanArrayList.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest jArr = new JsonArrayRequest(url_select_kry, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Karyawan item = new Karyawan();
                        item.setNik(obj.getString(TAG_NIK));
                        item.setPassword(obj.getString(TAG_PW));
                        item.setNama(obj.getString(TAG_NAMA));
                        item.setAlamat(obj.getString(TAG_ALAMAT));
                        item.setJenis_kelamin(obj.getString(TAG_JK));
                        item.setAgama(obj.getString(TAG_AGAMA));
                        item.setPendidikan_terakhir(obj.getString(TAG_PT));
                        item.setJabatan(obj.getString(TAG_JABATAN));

                        karyawanArrayList.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Eror: " + error.getMessage());
                error.printStackTrace();
                Toast.makeText(DtKaryawan.this,"gagal",Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jArr);
    }
}