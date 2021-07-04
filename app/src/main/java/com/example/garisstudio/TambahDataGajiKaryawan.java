package com.example.garisstudio;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TambahDataGajiKaryawan extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private ImageView imgDatePicker;

    private EditText tglgj, nikgj;
    private Button tmbhgj;
    String strtglgj, strnikgj;
    int success;

    private static String url_insert_gaji = "http://10.0.2.2/GarisStudio/tambahgaji.php";
    private static final String TAG = TambahDataGajiKaryawan.class.getSimpleName();
    private static final String TAG_SUCCES = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data_gaji_karyawan);
        setTitle("Tambah Data Tanggal Gaji Karyawan");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.biru)));

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        imgDatePicker = findViewById(R.id.dateImgTmbh);
        tglgj = findViewById(R.id.EtTanggalGajiTambah);
        nikgj = findViewById(R.id.EtNikGajiTambah);

        tmbhgj = findViewById(R.id.btnSimpanTambahGaji);

        imgDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        tmbhgj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpanDataGaji();
            }
        });
    }

    private void showDateDialog(){
        Calendar newCalender = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year,month,dayOfMonth);

                tglgj.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalender.get(Calendar.YEAR), newCalender.get(Calendar.MONTH), newCalender.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void SimpanDataGaji(){
        if(tglgj.getText().toString().equals("")||nikgj.getText().toString().equals("")){
            Toast.makeText(TambahDataGajiKaryawan.this,"Semua harus diisi data",Toast.LENGTH_SHORT).show();
        }else {
            strtglgj = tglgj.getText().toString();
            strnikgj = nikgj.getText().toString();

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url_insert_gaji, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Response: " + response.toString());
                    try {
                        JSONObject jObj = new JSONObject(response);
                        success = jObj.getInt(TAG_SUCCES);
                        if (success == 1) {
                            Toast.makeText(TambahDataGajiKaryawan.this, "Sukses menyimpan data", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Error: "+error.getMessage());
                    Toast.makeText(TambahDataGajiKaryawan.this,"Gagal menyimpan data",Toast.LENGTH_SHORT).show();
                }
            }){
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<>();
                    params.put("tanggal_gaji",strtglgj);
                    params.put("nik",strnikgj);

                    return params;
                }
            };
            requestQueue.add(stringRequest);
            callHome();
        }
    }

    public void callHome() {
        Intent intent = new Intent(TambahDataGajiKaryawan.this, HomeAdmin.class);
        startActivity(intent);
        finish();
    }
}