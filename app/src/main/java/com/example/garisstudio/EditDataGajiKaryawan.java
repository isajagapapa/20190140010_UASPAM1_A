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
    //variabel untuk datepicker
    private DatePickerDialog datePickerDialog;
    //variabel untuk format tanggal
    private SimpleDateFormat dateFormatter;
    //variabel untuk image view
    private ImageView imgDatePicker;

    //variabel untuk edittext
    private EditText tgl,nik;
    //variabel untuk textview
    private TextView no;
    //variabel untuk string
    String strTgl, strNik, strNo, edTgl,edNik,edNo;
    //variabel untuk button
    Button btnEdit;
    //variabel untuk integer
    int sukses;

    //String untuk alamat server, local host android
    private static String url_update_gaji = "http://10.0.2.2/GarisStudio/updategaji.php";
    //String tag untuk nama pendek dari kelas EditDataPekerjaan
    private static final String TAG = EditDataPekerjaan.class.getSimpleName();
    //String tag untuk sukses
    private static final String TAG_SUCCES = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data_gaji_karyawan);
        //mengeeset judul/title dari activity EditDataGajiKAryawan
        setTitle("Edit Tanggal Gaji Karyawan");
        //mengganti background pada action bar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.biru)));

        //memnaggil format tanggal
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        //set id untuk imageview
        imgDatePicker = findViewById(R.id.dateImg);
        //set id untuk edittext
        no = findViewById(R.id.tvNikGaji1);
        tgl = findViewById(R.id.EtTanggalGajiEdit);
        nik = findViewById(R.id.EtNikGajiEdit);
        //set id untuk button
        btnEdit = findViewById(R.id.btnSimpanEditGaji);

        //menyimpan data yang dikirim dari activity sebelumnya dengan kunci
        Bundle bundle = getIntent().getExtras();
        strNo = bundle.getString("No_Gaji");
        strTgl = bundle.getString("tglgaji");
        strNik = bundle.getString("nikgaji");

        //menamilkan value dari variabel string kedalam textview
        no.setText(strNo);
        tgl.setText(strTgl);
        nik.setText(strNik);

        //aksi ketika klik imageview
        imgDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //memanggil method showDateDialog
                showDateDialog();
            }
        });

        //aksi ketika klik btn edit
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //memanggil method EditDataGaji
                EditDataGaji();
            }
        });
    }

    //method untuk menampilkan tanggal
    private void showDateDialog(){
        //memanggil calender
        Calendar newCalender = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year,month,dayOfMonth);

                tgl.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalender.get(Calendar.YEAR), newCalender.get(Calendar.MONTH), newCalender.get(Calendar.DAY_OF_MONTH));
        //menampilkan tanggal
        datePickerDialog.show();
    }

    public void EditDataGaji(){
        //membaca data dari edittext
        edNo = no.getText().toString();
        edTgl = tgl.getText().toString();
        edNik = nik.getText().toString();

        //antrian request menggunakan library volley
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        //mengirim data ke server
        StringRequest stringReq = new StringRequest(Request.Method.POST, url_update_gaji, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //membaca pesan dari response
                Log.d(TAG, "Response: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    sukses = jObj.getInt(TAG_SUCCES);
                    //pesan sukses
                    if (sukses == 1) {
                        Toast.makeText(EditDataGajiKaryawan.this, "Sukses mengupdate data", Toast.LENGTH_SHORT).show();
                    }else {
                        //pesan gagal
                        Toast.makeText(EditDataGajiKaryawan.this, "Gagal", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    //memunculkan pesan kesalahan
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //membaca pesan eror jika jaringan bermasalah
                Log.e(TAG, "Error: "+error.getMessage());
                Toast.makeText(EditDataGajiKaryawan.this,"Gagal menupdate data",Toast.LENGTH_SHORT).show();
            }
        }){
            //mengirim data dalam bentuk array map
            @Override
            protected Map<String,String> getParams(){
                //membuat objek hashmap
                Map<String,String> params = new HashMap<>();
                //memasukkan data sesuai nama kunci yaitu nama kolom pada tabel database dengan parameter
                params.put("No_Gaji",edNo);
                params.put("tanggal_gaji", edTgl);
                params.put("nik",edNik);

                return params;
            }
        };
        //array dimasukkan ke antrian request
        requestQueue.add(stringReq);
        //memanggil method callHome
        callHome();
    }
    public void callHome(){
        //intent untuk memanggil activity HomeAdmin
        Intent intent = new Intent(EditDataGajiKaryawan.this,HomeAdmin.class);
        //berpindah dari EditDataGajiKaryawan ke HomeAdmin
        startActivity(intent);
        finish();
    }
}