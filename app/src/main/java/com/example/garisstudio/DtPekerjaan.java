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
    private RecyclerView recyclerView;
    private PekerjaanAdapter adapter;
    private ArrayList<Pekerjaan> pekerjaanArrayList = new ArrayList<>();
    private Button tambahpk;

    private static final String TAG = DtPekerjaan.class.getSimpleName();
    private static String url_select_pkr = "http://10.0.2.2/GarisStudio/bacapekerjaan.php";
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
        setTitle("Pekerjaan");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.biru)));

        recyclerView = findViewById(R.id.rv_dtPkr);
        tambahpk = findViewById(R.id.btnTambahPkr);

        BacaData();

        adapter = new PekerjaanAdapter(pekerjaanArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DtPekerjaan.this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        tambahpk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DtPekerjaan.this, TambahDataPekerjaan.class);
                startActivity(intent);
            }
        });
    }

    public void BacaData(){
        pekerjaanArrayList.clear();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest jArr = new JsonArrayRequest(url_select_pkr, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Pekerjaan item = new Pekerjaan();
                        item.setJabatan(obj.getString(TAG_JBT));
                        item.setGaji_pokok(obj.getString(TAG_GJP));
                        item.setTunjangan_kesehatan(obj.getString(TAG_TK));
                        item.setTunjangan_pendidikan(obj.getString(TAG_TP));
                        item.setTunjangan_transportasi(obj.getString(TAG_TT));
                        item.setTotal_gaji(obj.getString(TAG_TOTAL));

                        pekerjaanArrayList.add(item);
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
                Toast.makeText(DtPekerjaan.this,"gagal",Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jArr);
    }
}