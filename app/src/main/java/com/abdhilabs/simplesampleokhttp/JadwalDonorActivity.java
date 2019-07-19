package com.abdhilabs.simplesampleokhttp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.abdhilabs.simplesampleokhttp.adapter.JadwalAdapter;
import com.abdhilabs.simplesampleokhttp.model.Jadwal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JadwalDonorActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private String url = "https://script.googleusercontent.com/macros/echo?user_content_key=4rXKXIAnP2u_Zd4JsTwcntpyxMQjKyeef_pD7Md6HR8XFKef0AtdKormq5lRQaule2GNCqGP6MIxNKZ9lQ5DITtfXeEC0zvROJmA1Yb3SEsKFZqtv3DaNYcMrmhZHmUMWojr9NvTBuB6lHT6qnqYcmFWggwoSVQQXTsQ1HqKa1CgDXQROm1OeNR5ibYVAaRxAeOtzLmbRZcVjrce7Uveb8iU1s-L39A_CLDTUaq6azCNVhRMhi1rsDKfemnxd2fexfjRk53QbyB9dvgI2LNPjdUqz9IQ1Q6_&lib=M-tpZm-Fm1QX9Yr80nZn_p-WXe3zpGnIr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_donor);

        recyclerView = findViewById(R.id.rv_list_jadwal);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //Digunakan untuk menseting layout dari recylerView. Nilai default dari
        //linearLayoutManager adalah vertical.
        recyclerView.setLayoutManager(linearLayoutManager);

        /**
         * Digunakan untuk memunculkan progress Loading di layar
         */
        final ProgressDialog progressDialog = new ProgressDialog(JadwalDonorActivity.this);
        progressDialog.setMessage("Tunggu rek...");
        progressDialog.show();

        //Proses mencetak objek OkHttp yang digunakan untuk melakukan proses reguest dan
        //Response.
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();

        //Proses meneruskan request dan menaruh proses ini dalam background thread .
        okHttpClient.newCall(request).enqueue(new Callback() {
            //Digunakan ketika response yang dihasilkan tidak sesuai dengan request atau terjadi
            //kesalahan di jaringan.
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(JadwalDonorActivity.this, "Hiyah we gak iso :v", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            //Digunakan ketika mendapat response dari server
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //Digunakan untuk mencetak objek ArrayList bernama jadwalDonor.
                final ArrayList<Jadwal> jadwalDonor = new ArrayList<>();
                //Menyimpan data response dari server ke dalam variable bertipe String bernama
                //jsonData.
                String jsonData = response.body().string();

                try {
                    //Memasukan variable string jsonData ke dalam jsonObject.
                    JSONObject jsonObject = new JSONObject(jsonData);
                    //Mendapatkan Array yang ada dalam jsonObjet yang mempunyai key bernama “data”.
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    //Mendapatkan nomor index Array untuk masing masing object.
                    for (int i = 0; i < jsonArray.length(); i++) {
                        //Mengurai Array yang didapatkan kedalam object object terpisah.
                        JSONObject objectJadi = jsonArray.getJSONObject(i);

                        //Mendapatkan nilai dari object dengan key masing masing kemudian merubahnya ke
                        //dalam variable bertipe String.
                        String instansi = objectJadi.get("instansi").toString();
                        String alamat = objectJadi.get("alamat").toString();
                        String jam = objectJadi.get("jam").toString();
                        String jmlDonor = objectJadi.get("rencana_donor").toString();

                        //Membuat objek Jadwal ke dalam jadwal ObjectDonor dan memasukan parameter yang
                        //telah kita dapatkan.
                        Jadwal jadwalObjectDonor = new Jadwal(instansi, alamat, jam, jmlDonor);
                        //Memasukan jadwalObjectDonor ke dalam Arraylist jadwalDonor.
                        jadwalDonor.add(jadwalObjectDonor);
                    }
                    //Menangkap error ketika ada keasalahan dalam hal pengelolaan JSON, sehingga ketika
                    //terjadi error tidak langsung force close.
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Menghubungkan main UI dengan Background thread, dimana hasil yang telah didapat
                //di background akan di run di main UI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Menghilangkan progress loading karena sudah mendapatkan data
                        progressDialog.dismiss();
                        //Membuat object JadwalAdapter ke dalam variable jadwalAdapter, dan menarima
                        //inputan berupa context dari activitynya dan variable jadwalDonor.
                        JadwalAdapter jadwalAdapter = new JadwalAdapter(getApplicationContext(), jadwalDonor);
                        //Setting adapter milik recylerview mengikuti setting dari jadwalAdapter.
                        recyclerView.setAdapter(jadwalAdapter);
                    }
                });
            }
        });
    }
}
