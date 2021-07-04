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
import android.widget.TextView;
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

public class EditDataGajiKaryawan extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private ImageView imgDatePicker;

    private EditText tgl,nik;
    private TextView no;
    String strTgl, strNik, strNo, edTgl,edNik,edNo;
    Button btnEdit;
    int sukses;

    private static String url_update_gaji = "http://10.0.2.2/GarisStudio/updategaji.php";
    private static final String TAG = EditDataPekerjaan.class.getSimpleName();
    private static final String TAG_SUCCES = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data_gaji_karyawan);
        setTitle("Edit Tanggal Gaji Karyawan");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.biru)));

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        imgDatePicker = findViewById(R.id.dateImg);
        no = findViewById(R.id.tvNikGaji1);
        tgl = findViewById(R.id.EtTanggalGajiEdit);
        nik = findViewById(R.id.EtNikGajiEdit);
        btnEdit = findViewById(R.id.btnSimpanEditGaji);

        Bundle bundle = getIntent().getExtras();
        strNo = bundle.getString("No_Gaji");
        strTgl = bundle.getString("tglgaji");
        strNik = bundle.getString("nikgaji");

        no.setText(strNo);
        tgl.setText(strTgl);
        nik.setText(strNik);

        imgDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditDataGaji();
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

                tgl.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalender.get(Calendar.YEAR), newCalender.get(Calendar.MONTH), newCalender.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void EditDataGaji(){
        edNo = no.getText().toString();
        edTgl = tgl.getText().toString();
        edNik = nik.getText().toString();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringReq = new StringRequest(Request.Method.POST, url_update_gaji, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    sukses = jObj.getInt(TAG_SUCCES);
                    if (sukses == 1) {
                        Toast.makeText(EditDataGajiKaryawan.this, "Sukses mengupdate data", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(EditDataGajiKaryawan.this, "Gagal", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: "+error.getMessage());
                Toast.makeText(EditDataGajiKaryawan.this,"Gagal menupdate data",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("No_Gaji",edNo);
                params.put("tanggal_gaji", edTgl);
                params.put("nik",edNik);

                return params;
            }
        };
        requestQueue.add(stringReq);
        callHome();
    }
    public void callHome(){
        Intent intent = new Intent(EditDataGajiKaryawan.this,HomeAdmin.class);
        startActivity(intent);
        finish();
    }
}