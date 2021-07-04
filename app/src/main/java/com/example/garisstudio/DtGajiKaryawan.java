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
    private RecyclerView recyclerView;
    private GajiAdapter adapter;
    private ArrayList<Gaji> gajiArrayList = new ArrayList<>();
    private Button btntmbahgaji;

    private static final String TAG = DtGajiKaryawan.class.getSimpleName();
    private static String url_select_gaji = "http://10.0.2.2/GarisStudio/bacagaji.php";
    private static final String TAG_NO = "No_Gaji";
    private static final String TAG_TANGGAL = "tanggal_gaji";
    private static final String TAG_NIK = "nik";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dt_gaji_karyawan);
        setTitle("Tanggal Gaji Karyawan");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.biru)));

        recyclerView = findViewById(R.id.rv_dtGajiKaryawan);
        btntmbahgaji = findViewById(R.id.btnTambahGaji);

        BacaDataGaji();

        adapter = new GajiAdapter(gajiArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DtGajiKaryawan.this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        btntmbahgaji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DtGajiKaryawan.this,TambahDataGajiKaryawan.class);
                startActivity(intent);
            }
        });

    }

    public void BacaDataGaji(){
        gajiArrayList.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest jArr = new JsonArrayRequest(url_select_gaji, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

//                parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Gaji item = new Gaji();
                        item.setNo_Gaji(obj.getString(TAG_NO));
                        item.setTanggal_gaji(obj.getString(TAG_TANGGAL));
                        item.setNik(obj.getString(TAG_NIK));

//                        menambah item ke array
                        gajiArrayList.add(item);
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
                Toast.makeText(DtGajiKaryawan.this,"gagal",Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jArr);
    }
}