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
    private EditText idAdm, pwAdm;
    private Button adm;
    private String stridadm, strpwadm;
    private CheckBox cbPass;

    private static String url_loginadm = "http://10.0.2.2/GarisStudio/loginadmin.php";
    private static final String TAG = LoginAdmin.class.getSimpleName();
    private static final String TAG_SUCCES = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        idAdm = findViewById(R.id.EtIDAdm);
        pwAdm = findViewById(R.id.EtPwAdm);
        cbPass = findViewById(R.id.cbxShowAdm);
        adm = findViewById(R.id.btnLoginAdm);

        pwAdm.setTransformationMethod(PasswordTransformationMethod.getInstance());

        cbPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbPass.isChecked()){
                    pwAdm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    pwAdm.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        adm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginadmin();
            }
        });
    }
    
    public void loginadmin(){
        if(idAdm.getText().equals("") || pwAdm.getText().toString().equals("")){
            Toast.makeText(LoginAdmin.this,"Semua harus diisi!!!",Toast.LENGTH_SHORT).show();
        } else {
            stridadm = idAdm.getText().toString();
            strpwadm = pwAdm.getText().toString();

            StringRequest request = new StringRequest(Request.Method.POST, url_loginadm, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.contains("1")){
                        startActivity(new Intent(getApplicationContext(), HomeAdmin.class));
                    }else {
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
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("id_admin", stridadm);
                    params.put("password", strpwadm);
                    return params;
                }
            };
            Volley.newRequestQueue(this).add(request);
        }
    }
}