package com.abdhilabs.simplesampleokhttp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    TextView tvTampil;
    private final String url = "http://www.gookkis.com/hello.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTampil = findViewById(R.id.tv_tampil);
    }

    public void ambilData(View view) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Connection Failed", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responData = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvTampil.setText(responData);
                    }
                });
            }
        });
    }

    public void pindahJadwalDonor(View view) {
        Intent intent = new Intent(this, JadwalDonorActivity.class);
        startActivity(intent);
    }
}
