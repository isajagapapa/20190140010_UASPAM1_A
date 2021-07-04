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
    private EditText editJabatan,editGp, editTk, editTp, editTt, editTotal;
    Button editBtn;
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
    int sukses;

    private static String url_update_pkr = "http://10.0.2.2/GarisStudio/updatepekerjaan.php";
    private static final String TAG = EditDataPekerjaan.class.getSimpleName();
    private static final String TAG_SUCCES = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data_pekerjaan);
        setTitle("Edit Data Pekerjaan");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.biru)));

        editJabatan = findViewById(R.id.EtJbtEdit);
        editGp = findViewById(R.id.EtGajiPokokEdit);
        editTk = findViewById(R.id.etTKEdit);
        editTp = findViewById(R.id.etTPEdit);
        editTt = findViewById(R.id.etTTEdit);
        editTotal = findViewById(R.id.edittTTotalEdit);
        editBtn = findViewById(R.id.btnSimpanEditKerja);

        Bundle bundle = getIntent().getExtras();
        jbt = bundle.getString("jbt");
        gjp = bundle.getString("gjpk");
        tk = bundle.getString("tk");
        tp = bundle.getString("tp");
        tt = bundle.getString("tt");
        totalgj = bundle.getString("total");

        editJabatan.setText(jbt);
        editGp.setText(gjp);
        editTk.setText(tk);
        editTp.setText(tp);
        editTt.setText(tt);
        editTotal.setText(totalgj);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditDataKerja();
            }
        });
    }
    public void EditDataKerja(){
        edjbt = editJabatan.getText().toString();
        edgjp = editGp.getText().toString();
        edtk = editTk.getText().toString();
        edtp = editTp.getText().toString();
        edtt = editTt.getText().toString();
        edtotal = editTotal.getText().toString();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_update_pkr, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    sukses = jObj.getInt(TAG_SUCCES);
                    if (sukses == 1) {
                        Toast.makeText(EditDataPekerjaan.this, "Sukses memperbarui data", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditDataPekerjaan.this, "Gagal memperbarui data", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: "+error.getMessage());
                Toast.makeText(EditDataPekerjaan.this,"Gagal memperbarui data",Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("jabatan", edjbt);
                params.put("gaji_pokok", edgjp);
                params.put("tunjangan_kesehatan", edtk);
                params.put("tunjangan_pendidikan", edtk);
                params.put("tunjangan_transportasi", edtt);
                params.put("total_gaji", edtotal);

                return params;
            }
        };
        requestQueue.add(stringRequest);
        callHome();
    }
    public void callHome(){
        Intent intent = new Intent(EditDataPekerjaan.this,HomeAdmin.class);
        startActivity(intent);
        finish();
    }
}