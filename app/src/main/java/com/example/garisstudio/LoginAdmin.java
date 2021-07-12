package com.example.garisstudio;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginAdmin extends AppCompatActivity {
    //variabel untuk edittext
    private EditText idAdm, pwAdm;
    //variabel untuk button
    private Button adm;
    //variabel untuk string
    private String stridadm, strpwadm;
    //variabel untuk checkbox
    private CheckBox cbPass;

    //String untuk alamat server, local host android
    private static String url_loginadm = "http://10.0.2.2/GarisStudio/loginadmin.php";
    //String tag untuk nama pendek dari kelas LoginAdmin
    private static final String TAG = LoginAdmin.class.getSimpleName();
    //String tag untuk sukses
    private static final String TAG_SUCCES = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        //set id untuk edittext
        idAdm = findViewById(R.id.EtIDAdm);
        pwAdm = findViewById(R.id.EtPwAdm);
        //set id untuk checkbox
        cbPass = findViewById(R.id.cbxShowAdm);
        //set id untuk button
        adm = findViewById(R.id.btnLoginAdm);

        //set password agar menjadi titik titik
        pwAdm.setTransformationMethod(PasswordTransformationMethod.getInstance());

        //aksi ketika klik cbPass
        cbPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbPass.isChecked()){
                    //jika cbPass centang maka password akan terlihat
                    pwAdm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    pwAdm.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        //aksi ketika klik adm
        adm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //memanggil method loginadmin
                loginadmin();
            }
        });
    }
    
    public void loginadmin(){
        //pesan ketika edittext kosong
        if(idAdm.getText().equals("") || pwAdm.getText().toString().equals("")){
            Toast.makeText(LoginAdmin.this,"Semua harus diisi!!!",Toast.LENGTH_SHORT).show();
        } else {
            //membaca data dari edittext
            stridadm = idAdm.getText().toString();
            strpwadm = pwAdm.getText().toString();

            //mengirim data ke server
            StringRequest request = new StringRequest(Request.Method.POST, url_loginadm, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.contains("1")){
                        //ketika response dari server benar maka berpindah dari activity logi ke HomeAdmin
                        startActivity(new Intent(getApplicationContext(), HomeAdmin.class));
                    }else {
                        //ketika response dari server salah makan pesan eror akan muncul
                        Toast.makeText(getApplicationContext(),
                                "ID Admin atau Password Salah",Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                //mengirim data dalam bentuk array map
                protected Map<String, String> getParams() throws AuthFailureError {
                    //membuat objek hashmap
                    Map<String,String> params = new HashMap<>();
                    //memasukkan data sesuai nama kunci yaitu nama kolom pada tabel database dengan parameter
                    params.put("id_admin", stridadm);
                    params.put("password", strpwadm);
                    return params;
                }
            };
            //array dimasukkan ke antrian request
            Volley.newRequestQueue(this).add(request);
        }
    }
}