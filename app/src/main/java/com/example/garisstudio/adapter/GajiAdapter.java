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
import com.example.garisstudio.DtGajiKaryawan;
import com.example.garisstudio.EditDataGajiKaryawan;
import com.example.garisstudio.R;
import com.example.garisstudio.database.Gaji;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GajiAdapter extends RecyclerView.Adapter<GajiAdapter.GajiViewHolder> {
    private ArrayList<Gaji> listData;

    public GajiAdapter(ArrayList<Gaji> listData){
        this.listData = listData;
    }

    @Override
    public GajiAdapter.GajiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInf = LayoutInflater.from(parent.getContext());
        View view = layoutInf.inflate(R.layout.item_dt_gajikaryawan,parent,false);
        return new GajiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GajiAdapter.GajiViewHolder holder, int position) {
        String no_gaji, tanggalgj,nikgaji;

        no_gaji = listData.get(position).getNo_Gaji();
        tanggalgj = listData.get(position).getTanggal_gaji();
        nikgaji = listData.get(position).getNik();

        holder.tanggal.setText(tanggalgj);
        holder.nik.setText(nikgaji);

        holder.cardku.setOnLongClickListener(new View.OnLongClickListener() {
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
                                bundle.putString("No_Gaji", no_gaji);
                                bundle.putString("tglgaji", tanggalgj);
                                bundle.putString("nikgaji", nikgaji);
                                Intent intent = new Intent(v.getContext(), EditDataGajiKaryawan.class);
                                intent.putExtras(bundle);
                                v.getContext().startActivity(intent);
                                break;
                            case R.id.hapuskr:
                                try{
                                    AlertDialog.Builder alertdb = new AlertDialog.Builder(v.getContext());
                                    alertdb.setTitle("Yakin " +no_gaji+" akan dihapus?");
                                    alertdb.setMessage("Tekan Ya untuk menghapus");
                                    alertdb.setCancelable(false);
                                    alertdb.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            HapusDataGj(no_gaji);
                                            Toast.makeText(v.getContext(), "Data "+no_gaji+" telah dihapus", Toast.LENGTH_LONG).show();
                                            Intent intent1 = new Intent(v.getContext(), DtGajiKaryawan.class);
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

    }

    public void HapusDataGj(final String ngj){
        String url_delete_gajikr = "http://10.0.2.2/GarisStudio/deletegaji.php";
        final String TAG = EditDataGajiKaryawan.class.getSimpleName();
        final String TAG_SUCCES = "success";
        final int[] sukses = new int[1];

        StringRequest stringReq = new StringRequest(Request.Method.POST, url_delete_gajikr, new Response.Listener<String>() {
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
                params.put("No_Gaji",ngj);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringReq);
    }

    @Override
    public int getItemCount() {
        return (listData != null)?listData.size() : 0;
    }

    public class GajiViewHolder extends RecyclerView.ViewHolder {
        private CardView cardku;
        private TextView tanggal, nik;

        public GajiViewHolder(@NonNull View itemView) {
            super(itemView);
            cardku = (CardView) itemView.findViewById(R.id.kartukuGajiKry);
            tanggal = (TextView) itemView.findViewById(R.id.textTglGaji);
            nik = (TextView) itemView.findViewById(R.id.textNikGaji);
        }
    }
}
