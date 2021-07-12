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
    //Deklarasi variabel dengan jenis data array list
    private ArrayList<Karyawan> listData;

    //membuat arraylist
    public KaryawanAdapter(ArrayList<Karyawan> listData) {
        this.listData = listData;
    }

    @Override
    public KaryawanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Mengatur layuot inflater dari context yang diberikan
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        //mengatur layout untuk menampilkan item
        View view = layoutInflater.inflate(R.layout.item_dt_karyawan,parent,false);
        return new KaryawanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KaryawanAdapter.KaryawanViewHolder holder, int position) {
        //deklarasi variabel dengan tipedata String
        String nika, nik, pwkr, namakr, alamatkr, jkkr, agamakr, ptkr, jabatankry;

        //mengambil data dari array list ke String
        nika = listData.get(position).getNik();
        nik = listData.get(position).getNik();
        pwkr = listData.get(position).getPassword();
        namakr = listData.get(position).getNama();
        alamatkr = listData.get(position).getAlamat();
        jkkr = listData.get(position).getJenis_kelamin();
        agamakr = listData.get(position).getAgama();
        ptkr = listData.get(position).getPendidikan_terakhir();
        jabatankry = listData.get(position).getJabatan();

        //mengeset nilai textview
        holder.tvnama.setText(namakr);
        holder.tvjabatan.setText(jabatankry);

        //method untuk event long click dari cardview
        holder.kartukry.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //membuat objek popup menu
                PopupMenu pm1 = new PopupMenu(v.getContext(),v);
                //menampilkan popup menu dari layout menu
                pm1.inflate(R.menu.popup1);
                //membuat event untuk popup menu ketika dipilih
                pm1.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        //switch untuk memilih case
                        switch (item.getItemId()){
                            case R.id.editkr:
                                //membuat objek bundle
                                Bundle bundle = new Bundle();
                                //mengambil nilai string dari list data
                                bundle.putString("kunci1", nik);
                                bundle.putString("kunci2", pwkr);
                                bundle.putString("kunci3", namakr);
                                bundle.putString("kunci4", alamatkr);
                                bundle.putString("kunci5", jkkr);
                                bundle.putString("kunci6", agamakr);
                                bundle.putString("kunci7", ptkr);
                                bundle.putString("kunci8", jabatankry);
                                //intent untuk memanggil activity EditDataKaryawan
                                Intent intent = new Intent(v.getContext(), EditDataKaryawan.class);
                                //mengambil bundle kedalam intent untuk dikirim
                                intent.putExtras(bundle);
                                //berpindah dari karyawanadapter ke EditDataKaryawan
                                v.getContext().startActivity(intent);
                                break;
                            case R.id.hapuskr:
                                try{
                                    //membuat variabel builder alert dialog
                                    AlertDialog.Builder alertdb = new AlertDialog.Builder(v.getContext());
                                    //mengeset judul/title dari alert dialog
                                    alertdb.setTitle("Yakin " +namakr+" akan dihapus?");
                                    //mengeset pesan dari alert dialog
                                    alertdb.setMessage("Tekan Ya untuk menghapus");
                                    //kalau sudah terhapus tidak bisa dicancel
                                    alertdb.setCancelable(false);
                                    //mengeset aksi dari positif button alert dialog
                                    alertdb.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //memanggil method HapusDataKr dengan parameter nika
                                            HapusDataKr(nika);
                                            //menampilkan pesan popup/toast jika data telah dihapus
                                            Toast.makeText(v.getContext(), "Data "+namakr+" telah dihapus", Toast.LENGTH_LONG).show();
                                            //intent untuk memanggil activity DtKaryawan
                                            Intent intent1 = new Intent(v.getContext(), DtKaryawan.class);
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
                pm1.show();
                return true;
            }
        });
        //method untuk event click dari cardview
        holder.kartukry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //membuat objek bundle
                Bundle bundle = new Bundle();
                //mengambil nilai string dari list data
                bundle.putString("kunci1", nik);
                bundle.putString("kunci2", pwkr);
                bundle.putString("kunci3", namakr);
                bundle.putString("kunci4", alamatkr);
                bundle.putString("kunci5", jkkr);
                bundle.putString("kunci6", agamakr);
                bundle.putString("kunci7", ptkr);
                bundle.putString("kunci8", jabatankry);
                //intent untuk memanggil activity DetailKaryawan
                Intent intent = new Intent(v.getContext(), DetailKaryawan.class);
                //mengambil bundle kedalam intent untuk dikirim
                intent.putExtras(bundle);
                //berpindah dari karyawanadapter ke DetailKaryawan
                v.getContext().startActivity(intent);
            }
        });
    }
    //method hapus dengan parameter
    public void HapusDataKr(final String nikx){
        //variabel bertipe string untuk alamat server, local host android
        String url_delete_karyawan = "http://10.0.2.2/GarisStudio/deletekaryawan.php";
        //String tag untuk nama pendek dari kelas EditDataKaryawan
        final String TAG = EditDataKaryawan.class.getSimpleName();
        //String tag untuk sukses
        final String TAG_SUCCES = "success";
        //Int untuk mengambil nilai sukses
        final int[] sukses = new int[1];

        //Request method yang akan kita posting untuk delete/hapus
        StringRequest stringReq = new StringRequest(Request.Method.POST, url_delete_karyawan, new Response.Listener<String>() {
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
                params.put("nik",nikx);
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
    public class KaryawanViewHolder extends RecyclerView.ViewHolder {
        //mendeklarasikan variabel dengan jenis CardView
        private CardView kartukry;
        //mendeklarasikan variabel dengan jenis TextView
        private TextView tvnama, tvjabatan;
        public KaryawanViewHolder(View view) {
            super(view);
            //Set id untuk Cardview
            kartukry = (CardView) view.findViewById(R.id.kartukuKry);
            //Set id untuk textview
            tvnama = (TextView) view.findViewById(R.id.textNamaKry);
            tvjabatan = (TextView) view.findViewById(R.id.textJabatanKry);
        }
    }
}
