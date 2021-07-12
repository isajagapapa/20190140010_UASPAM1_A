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
    //Deklarasi variabel dengan jenis data array list
    private ArrayList<Gaji> listData;

    //membuat arraylist
    public GajiAdapter(ArrayList<Gaji> listData){
        this.listData = listData;
    }

    @Override
    public GajiAdapter.GajiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Mengatur layuot inflater dari context yang diberikan
        LayoutInflater layoutInf = LayoutInflater.from(parent.getContext());
        //mengatur layout untuk menampilkan item
        View view = layoutInf.inflate(R.layout.item_dt_gajikaryawan,parent,false);
        return new GajiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GajiAdapter.GajiViewHolder holder, int position) {
        //deklarasi variabel dengan tipedata String
        String no_gaji, tanggalgj,nikgaji;

        //mengambil data dari array list ke String
        no_gaji = listData.get(position).getNo_Gaji();
        tanggalgj = listData.get(position).getTanggal_gaji();
        nikgaji = listData.get(position).getNik();

        //mengeset nilai textview tanggal dari arraylist tanggal
        holder.tanggal.setText(tanggalgj);
        //mengeset nilai textview nik dari arraylist nikgaji
        holder.nik.setText(nikgaji);

        //method untuk event long click dari cardview
        holder.cardku.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //membuat objek popup menu
                PopupMenu pm1 = new PopupMenu(v.getContext(),v);
                //menampilkan popup menu dari layout menu
                pm1.inflate(R.menu.popup1);
                //membuat event untuk popup menu ketika dipilih
                pm1.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    //event yang terjadi ketika menu dipilih
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        //switch untuk memilih case
                        switch (item.getItemId()){
                            case R.id.editkr:
                                //membuat objek bundle
                                Bundle bundle = new Bundle();
                                //mengambil nilai string dari list data no_gaji dengan kunci No_Gaji
                                bundle.putString("No_Gaji", no_gaji);
                                //mengambil nilai string dari list data tanggalgj dengan kunci tglgaji
                                bundle.putString("tglgaji", tanggalgj);
                                //mengambil nilai string dari list data nikgaji dengan kunci nikgaji
                                bundle.putString("nikgaji", nikgaji);
                                //intent untuk memanggil activity EditDataGajiKaryawan
                                Intent intent = new Intent(v.getContext(), EditDataGajiKaryawan.class);
                                //mengambil bundle kedalam intent untuk dikirim
                                intent.putExtras(bundle);
                                //berpindah dari dajiadapter ke EditDataGajiKaryawan
                                v.getContext().startActivity(intent);
                                break;
                            case R.id.hapuskr:
                                try{
                                    //membuat variabel builder alert dialog
                                    AlertDialog.Builder alertdb = new AlertDialog.Builder(v.getContext());
                                    //mengeset judul/title dari alert dialog
                                    alertdb.setTitle("Yakin " +no_gaji+" akan dihapus?");
                                    //mengeset pesan dari alert dialog
                                    alertdb.setMessage("Tekan Ya untuk menghapus");
                                    //kalau sudah terhapus tidak bisa dicancel
                                    alertdb.setCancelable(false);
                                    //mengeset aksi dari positif button alert dialog
                                    alertdb.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //memanggil method HapusDataGj dengan parameter no_gaji
                                            HapusDataGj(no_gaji);
                                            //menampilkan pesan popup/toast jika data telah dihapus
                                            Toast.makeText(v.getContext(), "Data "+no_gaji+" telah dihapus", Toast.LENGTH_LONG).show();
                                            //intent untuk memanggil activity DtGajiKaryawan
                                            Intent intent1 = new Intent(v.getContext(), DtGajiKaryawan.class);
                                            //berpindah dari gajiadpater ke dtgajikaryawan
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

    }

    //method hapus dengan parameter
    public void HapusDataGj(final String ngj){
        //variabel bertipe string untuk alamat server, local host android
        String url_delete_gajikr = "http://10.0.2.2/GarisStudio/deletegaji.php";
        //String tag untuk nama pendek dari kelas editdatakaryawan
        final String TAG = EditDataGajiKaryawan.class.getSimpleName();
        //String tag untuk sukses
        final String TAG_SUCCES = "success";
        //Int untuk mengambil nilai sukses
        final int[] sukses = new int[1];

        //Request method yang akan kita posting untuk delete/hapus
        StringRequest stringReq = new StringRequest(Request.Method.POST, url_delete_gajikr, new Response.Listener<String>() {
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
                params.put("No_Gaji",ngj);
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
    public class GajiViewHolder extends RecyclerView.ViewHolder {
        //mendeklarasikan variabel dengan jenis CardView
        private CardView cardku;
        //mendeklarasikan variabel dengan jenis TextView
        private TextView tanggal, nik;

        public GajiViewHolder(@NonNull View itemView) {
            super(itemView);
            //Set id untuk Cardview
            cardku = (CardView) itemView.findViewById(R.id.kartukuGajiKry);
            //Set id untuk textview tanggal
            tanggal = (TextView) itemView.findViewById(R.id.textTglGaji);
            //Set id untuk textview nik
            nik = (TextView) itemView.findViewById(R.id.textNikGaji);
        }
    }
}
