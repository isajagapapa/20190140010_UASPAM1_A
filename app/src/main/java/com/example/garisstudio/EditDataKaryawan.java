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
    private EditText editNik, editPw, editNama, editAlamat, editPt, editJabatan;
    private TextView edJk, edAgm;
    private Button simpanBtnedKry;
    String nik, pw, nama, alm, jk, agm, pt, jabatan, niked, pwed, namaed, almed, jked, agmed, pted, jabataned;
    int success;

    private static String url_update_karyawan = "http://10.0.2.2/GarisStudio/updatekaryawan.php";
    private static final String TAG = EditDataKaryawan.class.getSimpleName();
    private static final String TAG_SUCCES = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data_karyawan);
        setTitle("Edit Data Karyawan");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.biru)));

        editNik = findViewById(R.id.EtNikEdit);
        editPw = findViewById(R.id.EtPwEdit);
        editNama = findViewById(R.id.EtNamaEdit);
        editAlamat = findViewById(R.id.etAlamatEdit);
        edJk = findViewById(R.id.etJkEdit);
        edAgm = findViewById(R.id.etAgamaEdit);
        editPt = findViewById(R.id.etPTEdit);
        editJabatan = findViewById(R.id.etJbtEdit);
        simpanBtnedKry = findViewById(R.id.btnSimpanEditKry);

        Bundle bundle = getIntent().getExtras();
        nik = bundle.getString("kunci1");
        pw = bundle.getString("kunci2");
        nama = bundle.getString("kunci3");
        alm = bundle.getString("kunci4");
        jk = bundle.getString("kunci5");
        agm = bundle.getString("kunci6");
        pt = bundle.getString("kunci7");
        jabatan = bundle.getString("kunci8");

        editNik.setText(nik);
        editPw.setText(pw);
        editNama.setText(nama);
        editAlamat.setText(alm);
        edJk.setText(jk);
        edAgm.setText(agm);
        editPt.setText(pt);
        editJabatan.setText(jabatan);

        edJk.setGravity(Gravity.CENTER_VERTICAL);
        edAgm.setGravity(Gravity.CENTER_VERTICAL);

        edJk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu pm = new PopupMenu(v.getContext(),v);
                pm.inflate(R.menu.jkpop);

                pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.laki:
                                edJk.setText("Laki-Laki");
                                break;
                            case R.id.perempuan:
                                edJk.setText("Perempuan");
                        }
                        return true;
                    }
                });
                pm.show();
            }
        });
        edAgm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu pm1 = new PopupMenu(v.getContext(), v);
                pm1.inflate(R.menu.agamapop);

                pm1.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.islam:
                                edAgm.setText("Islam");
                                break;
                            case  R.id.kristen:
                                edAgm.setText("Katolik");
                                break;
                            case R.id.katolik:
                                edAgm.setText("Kristen");
                                break;
                            case R.id.hindu:
                                edAgm.setText("Hindu");
                                break;
                            case R.id.budha:
                                edAgm.setText("Budha");
                                break;
                            case R.id.konghucu:
                                edAgm.setText("Konghucu");
                                break;
                        }
                        return true;
                    }
                });
                pm1.show();
            }
        });

        simpanBtnedKry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditDataKr();
            }
        });

    }
    public void EditDataKr(){
        niked = editNik.getText().toString();
        pwed = editPw.getText().toString();
        namaed = editNama.getText().toString();
        almed = editAlamat.getText().toString();
        jked = edJk.getText().toString();
        agmed = edAgm.getText().toString();
        pted = editPt.getText().toString();
        jabataned = editJabatan.getText().toString();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_update_karyawan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCES);
                    if (success == 1) {
                        Toast.makeText(EditDataKaryawan.this, "Sukses memperbarui data", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditDataKaryawan.this, "Gagal memperbarui data", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: "+error.getMessage());
                Toast.makeText(EditDataKaryawan.this,"Gagal memperbarui data",Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
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
        requestQueue.add(stringRequest);
        callHome1();
    }

    public void callHome1(){
        Intent intent = new Intent(EditDataKaryawan.this,HomeAdmin.class);
        startActivity(intent);
        finish();
    }
}