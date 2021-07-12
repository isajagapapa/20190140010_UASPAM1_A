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
    //Deklarasi variabel dengan jenis data array list
    private ArrayList<Pekerjaan> listData;

    //membuat arraylist
    public PekerjaanAdapter(ArrayList<Pekerjaan> listData){
        this.listData = listData;
    }

    @NonNull
    @Override
    public PekerjaanAdapter.PekerjaanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Mengatur layuot inflater dari context yang diberikan
        LayoutInflater layoutInf = LayoutInflater.from(parent.getContext());
        //mengatur layout untuk menampilkan item
        View view = layoutInf.inflate(R.layout.item_dt_pekerjaan,parent,false);
        return new PekerjaanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PekerjaanAdapter.PekerjaanViewHolder holder, int position) {
        //deklarasi variabel dengan tipedata String
        String jbt,gjpk,tk,tp,tt,total;

        //mengambil data dari array list ke String
        jbt = listData.get(position).getJabatan();
        gjpk = listData.get(position).getGaji_pokok();
        tk = listData.get(position).getTunjangan_kesehatan();
        tp = listData.get(position).getTunjangan_pendidikan();
        tt = listData.get(position).getTunjangan_transportasi();
        total = listData.get(position).getTotal_gaji();

        //mengeset nilai textview
        holder.namapkr.setText(jbt);

        //method untuk event long click dari cardview
        holder.cardku.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //membuat objek popup menu
                PopupMenu pm = new PopupMenu(v.getContext(), v);
                //menampilkan popup menu dari layout menu
                pm.inflate(R.menu.popup1);
                //membuat event untuk popup menu ketika dipilih
                pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        //switch untuk memilih case
                        switch (item.getItemId()){
                            case R.id.editkr:
                                //membuat objek bundle
                                Bundle bundle = new Bundle();
                                //mengambil nilai string dari list data
                                bundle.putString("jbt", jbt);
                                bundle.putString("gjpk", gjpk);
                                bundle.putString("tk", tk);
                                bundle.putString("tp", tp);
                                bundle.putString("tt", tt);
                                bundle.putString("total", total);
                                //intent untuk memanggil activity EditDataPekerjaan
                                Intent intent = new Intent(v.getContext(), EditDataPekerjaan.class);
                                //mengambil bundle kedalam intent untuk dikirim
                                intent.putExtras(bundle);
                                //berpindah dari karyawanadapter ke EditDataPekerjaan
                                v.getContext().startActivity(intent);
                                break;
                            case R.id.hapuskr:
                                try{
                                    //membuat variabel builder alert dialog
                                    AlertDialog.Builder alertdb = new AlertDialog.Builder(v.getContext());
                                    //mengeset judul/title dari alert dialog
                                    alertdb.setTitle("Yakin " +jbt+" akan dihapus?");
                                    //mengeset pesan dari alert dialog
                                    alertdb.setMessage("Tekan Ya untuk menghapus");
                                    //kalau sudah terhapus tidak bisa dicancel
                                    alertdb.setCancelable(false);
                                    //mengeset aksi dari positif button alert dialog
                                    alertdb.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //memanggil method HapusDataPk dengan parameter jbt
                                            HapusDataPk(jbt);
                                            //menampilkan pesan popup/toast jika data telah dihapus
                                            Toast.makeText(v.getContext(), "Data "+jbt+" telah dihapus", Toast.LENGTH_LONG).show();
                                            //intent untuk memanggil activity DtPekerjaan
                                            Intent intent1 = new Intent(v.getContext(), DtPekerjaan.class);
                                            //berpindah dari karyawanadpater ke DtKaryawan
                                            v.getContext().startActivity(intent1);
                                        }
                                    });
                                    //mengeset aksi button negatif dari alert dialog
                                    alertdb.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //menutup alert dialog
                                            dialog.cancel();
                                        }
                                    });
                                    //membuat variabel alert dialog
                                    AlertDialog adlg = alertdb.create();
                                    //menampilkan alert dialog
                                    adlg.show();
                                }catch (Exception e){
                                    //mengambil exception/eror
                                    e.printStackTrace();
                                }
                                break;
                        }
                        return true;
                    }
                });
                //menmapilkan popup menu
                pm.show();
                return true;
            }
        });

        holder.cardku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //membuat objek bundle
                Bundle bundle = new Bundle();
                //mengambil nilai string dari list data
                bundle.putString("jbt", jbt);
                bundle.putString("gjpk", gjpk);
                bundle.putString("tk", tk);
                bundle.putString("tp", tp);
                bundle.putString("tt", tt);
                bundle.putString("total", total);
                //intent untuk memanggil activity DetailPekerjaan
                Intent intent = new Intent(v.getContext(), DetailPekerjaan.class);
                //mengambil bundle kedalam intent untuk dikirim
                intent.putExtras(bundle);
                //berpindah dari karyawanadapter ke DetailPekerjaan
                v.getContext().startActivity(intent);
            }
        });
    }
    //method hapus dengan parameter
    public void HapusDataPk(final String jbtt){
        //variabel bertipe string untuk alamat server, local host android
        String url_delete_pekerjaan = "http://10.0.2.2/GarisStudio/deletepekerjaan.php";
        //String tag untuk nama pendek dari kelas EditDataPekerjaan
        final String TAG = EditDataPekerjaan.class.getSimpleName();
        //String tag untuk sukses
        final String TAG_SUCCES = "success";
        //Int untuk mengambil nilai sukses
        final int[] sukses = new int[1];

        //Request method yang akan kita posting untuk delete/hapus
        StringRequest stringReq = new StringRequest(Request.Method.POST, url_delete_pekerjaan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //membaca pesan dari response
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
                //membaca pesan eror
                Log.e(TAG, "Error: " + error.getMessage());
            }
        }){
            //mengirim data dalam bentuk array map
            protected Map<String,String> getParams(){
                //membuat objek hashmap
                Map<String,String> params = new HashMap<>();
                //memasukkan data sesuai nama kunci yaitu nama kolom pada tabel database dengan parameter
                params.put("jabatan",jbtt);
                return params;
            }
        };
        //menggunakan appcontroller
        AppController.getInstance().addToRequestQueue(stringReq);
    }

    @Override
    //mengembalikan nilai berupa objek data dari array data
    public int getItemCount() {
        return (listData != null)?listData.size() : 0;
    }

    //Class untuk mendeklarasikan tempat meletakkan isi kedalam RecyclerView
    public class PekerjaanViewHolder extends RecyclerView.ViewHolder {
        //mendeklarasikan variabel dengan jenis CardView
        private CardView cardku;
        //mendeklarasikan variabel dengan jenis TextView
        private TextView namapkr;

        public PekerjaanViewHolder(@NonNull View itemView) {
            super(itemView);
            //Set id untuk Cardview
            cardku = (CardView) itemView.findViewById(R.id.kartukuJbt);
            //Set id untuk textview
            namapkr = (TextView) itemView.findViewById(R.id.textNamaPkr);
        }
    }
}
