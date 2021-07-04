package com.example.garisstudio.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.garisstudio.App.AppController;
import com.example.garisstudio.DetailKaryawan;
import com.example.garisstudio.DtKaryawan;
import com.example.garisstudio.EditDataKaryawan;
import com.example.garisstudio.R;
import com.example.garisstudio.database.Karyawan;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KaryawanAdapter extends RecyclerView.Adapter<KaryawanAdapter.KaryawanViewHolder> {
    private ArrayList<Karyawan> listData;

    public KaryawanAdapter(ArrayList<Karyawan> listData) {
        this.listData = listData;
    }

    @Override
    public KaryawanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_dt_karyawan,parent,false);
        return new KaryawanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KaryawanAdapter.KaryawanViewHolder holder, int position) {
        String nika, nik, pwkr, namakr, alamatkr, jkkr, agamakr, ptkr, jabatankry;

        nika = listData.get(position).getNik();
        nik = listData.get(position).getNik();
        pwkr = listData.get(position).getPassword();
        namakr = listData.get(position).getNama();
        alamatkr = listData.get(position).getAlamat();
        jkkr = listData.get(position).getJenis_kelamin();
        agamakr = listData.get(position).getAgama();
        ptkr = listData.get(position).getPendidikan_terakhir();
        jabatankry = listData.get(position).getJabatan();


        holder.tvnama.setText(namakr);
        holder.tvjabatan.setText(jabatankry);

        holder.kartukry.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu pm1 = new PopupMenu(v.getContext(),v);
                pm1.inflate(R.menu.popup1);
                pm1.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.editkr:
                                Bundle bundle = new Bundle();
                                bundle.putString("kunci1", nik);
                                bundle.putString("kunci2", pwkr);
                                bundle.putString("kunci3", namakr);
                                bundle.putString("kunci4", alamatkr);
                                bundle.putString("kunci5", jkkr);
                                bundle.putString("kunci6", agamakr);
                                bundle.putString("kunci7", ptkr);
                                bundle.putString("kunci8", jabatankry);
                                Intent intent = new Intent(v.getContext(), EditDataKaryawan.class);
                                intent.putExtras(bundle);
                                v.getContext().startActivity(intent);
                                break;
                            case R.id.hapuskr:
                                try{
                                    AlertDialog.Builder alertdb = new AlertDialog.Builder(v.getContext());
                                    alertdb.setTitle("Yakin " +namakr+" akan dihapus?");
                                    alertdb.setMessage("Tekan Ya untuk menghapus");
                                    alertdb.setCancelable(false);
                                    alertdb.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            HapusDataKr(nika);
                                            Toast.makeText(v.getContext(), "Data "+namakr+" telah dihapus", Toast.LENGTH_LONG).show();
                                            Intent intent1 = new Intent(v.getContext(), DtKaryawan.class);
                                            v.getContext().startActivity(intent1);
                                        }
                                    });
                                    alertdb.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    AlertDialog adlg = alertdb.create();
                                    adlg.show();
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                break;
                        }
                        return true;
                    }
                });
                pm1.show();
                return true;
            }
        });
        holder.kartukry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("kunci1", nik);
                bundle.putString("kunci2", pwkr);
                bundle.putString("kunci3", namakr);
                bundle.putString("kunci4", alamatkr);
                bundle.putString("kunci5", jkkr);
                bundle.putString("kunci6", agamakr);
                bundle.putString("kunci7", ptkr);
                bundle.putString("kunci8", jabatankry);
                Intent intent = new Intent(v.getContext(), DetailKaryawan.class);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });
    }
    public void HapusDataKr(final String nikx){
        String url_delete_karyawan = "http://10.0.2.2/GarisStudio/deletekaryawan.php";
        final String TAG = EditDataKaryawan.class.getSimpleName();
        final String TAG_SUCCES = "success";
        final int[] sukses = new int[1];

        StringRequest stringReq = new StringRequest(Request.Method.POST, url_delete_karyawan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    sukses[0] = jObj.getInt(TAG_SUCCES);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
            }
        }){
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("nik",nikx);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringReq);
    }

    @Override
    public int getItemCount() {
        return (listData != null)?listData.size() : 0;
    }

    public class KaryawanViewHolder extends RecyclerView.ViewHolder {
        private CardView kartukry;
        private TextView tvnama, tvjabatan;
        public KaryawanViewHolder(View view) {
            super(view);
            kartukry = (CardView) view.findViewById(R.id.kartukuKry);
            tvnama = (TextView) view.findViewById(R.id.textNamaKry);
            tvjabatan = (TextView) view.findViewById(R.id.textJabatanKry);
        }
    }
}
