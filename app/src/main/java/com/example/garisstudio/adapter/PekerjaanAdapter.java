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
import com.example.garisstudio.DetailPekerjaan;
import com.example.garisstudio.DtKaryawan;
import com.example.garisstudio.DtPekerjaan;
import com.example.garisstudio.EditDataKaryawan;
import com.example.garisstudio.EditDataPekerjaan;
import com.example.garisstudio.R;
import com.example.garisstudio.database.Karyawan;
import com.example.garisstudio.database.Pekerjaan;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PekerjaanAdapter extends RecyclerView.Adapter<PekerjaanAdapter.PekerjaanViewHolder> {
    private ArrayList<Pekerjaan> listData;

    public PekerjaanAdapter(ArrayList<Pekerjaan> listData){
        this.listData = listData;
    }

    @NonNull
    @Override
    public PekerjaanAdapter.PekerjaanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInf = LayoutInflater.from(parent.getContext());
        View view = layoutInf.inflate(R.layout.item_dt_pekerjaan,parent,false);
        return new PekerjaanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PekerjaanAdapter.PekerjaanViewHolder holder, int position) {
        String jbt,gjpk,tk,tp,tt,total;

        jbt = listData.get(position).getJabatan();
        gjpk = listData.get(position).getGaji_pokok();
        tk = listData.get(position).getTunjangan_kesehatan();
        tp = listData.get(position).getTunjangan_pendidikan();
        tt = listData.get(position).getTunjangan_transportasi();
        total = listData.get(position).getTotal_gaji();

        holder.namapkr.setText(jbt);

        holder.cardku.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu pm = new PopupMenu(v.getContext(), v);
                pm.inflate(R.menu.popup1);
                pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.editkr:
                                Bundle bundle = new Bundle();
                                bundle.putString("jbt", jbt);
                                bundle.putString("gjpk", gjpk);
                                bundle.putString("tk", tk);
                                bundle.putString("tp", tp);
                                bundle.putString("tt", tt);
                                bundle.putString("total", total);
                                Intent intent = new Intent(v.getContext(), EditDataPekerjaan.class);
                                intent.putExtras(bundle);
                                v.getContext().startActivity(intent);
                                break;
                            case R.id.hapuskr:
                                try{
                                    AlertDialog.Builder alertdb = new AlertDialog.Builder(v.getContext());
                                    alertdb.setTitle("Yakin " +jbt+" akan dihapus?");
                                    alertdb.setMessage("Tekan Ya untuk menghapus");
                                    alertdb.setCancelable(false);
                                    alertdb.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            HapusDataPk(jbt);
                                            Toast.makeText(v.getContext(), "Data "+jbt+" telah dihapus", Toast.LENGTH_LONG).show();
                                            Intent intent1 = new Intent(v.getContext(), DtPekerjaan.class);
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
                pm.show();
                return true;
            }
        });

        holder.cardku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("jbt", jbt);
                bundle.putString("gjpk", gjpk);
                bundle.putString("tk", tk);
                bundle.putString("tp", tp);
                bundle.putString("tt", tt);
                bundle.putString("total", total);
                Intent intent = new Intent(v.getContext(), DetailPekerjaan.class);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });
    }
    public void HapusDataPk(final String jbtt){
        String url_delete_pekerjaan = "http://10.0.2.2/GarisStudio/deletepekerjaan.php";
        final String TAG = EditDataPekerjaan.class.getSimpleName();
        final String TAG_SUCCES = "success";
        final int[] sukses = new int[1];

        StringRequest stringReq = new StringRequest(Request.Method.POST, url_delete_pekerjaan, new Response.Listener<String>() {
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
                params.put("jabatan",jbtt);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringReq);
    }

    @Override
    public int getItemCount() {
        return (listData != null)?listData.size() : 0;
    }

    public class PekerjaanViewHolder extends RecyclerView.ViewHolder {
        private CardView cardku;
        private TextView namapkr;

        public PekerjaanViewHolder(@NonNull View itemView) {
            super(itemView);
            cardku = (CardView) itemView.findViewById(R.id.kartukuJbt);
            namapkr = (TextView) itemView.findViewById(R.id.textNamaPkr);
        }
    }
}
