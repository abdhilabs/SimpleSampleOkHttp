package com.abdhilabs.simplesampleokhttp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abdhilabs.simplesampleokhttp.R;
import com.abdhilabs.simplesampleokhttp.model.Jadwal;

import java.util.ArrayList;

public class JadwalAdapter extends RecyclerView.Adapter<JadwalAdapter.JadwalViewHolder> {
    private Context context;
    private ArrayList<Jadwal> jadwalDonor;

    public JadwalAdapter(Context context, ArrayList<Jadwal> jadwalDonor) {
        this.context = context;
        this.jadwalDonor = jadwalDonor;
    }

    @NonNull
    @Override
    public JadwalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_jadwal, viewGroup, false);
        return new JadwalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JadwalViewHolder holder, int i) {
        holder.tvInstansi.setText(jadwalDonor.get(i).getInstansi());
        holder.tvAlamat.setText(jadwalDonor.get(i).getAlamat());
        holder.tvJam.setText(jadwalDonor.get(i).getJam());
        holder.tvJmlDonor.setText(jadwalDonor.get(i).getJmlDonor());
    }

    @Override
    public int getItemCount() {
        return jadwalDonor.size();
    }

    class JadwalViewHolder extends RecyclerView.ViewHolder {
        TextView tvInstansi, tvAlamat, tvJam, tvJmlDonor;

        JadwalViewHolder(@NonNull View itemView) {
            super(itemView);
            tvInstansi = itemView.findViewById(R.id.tv_instansi_value);
            tvAlamat = itemView.findViewById(R.id.tv_alamat_value);
            tvJam = itemView.findViewById(R.id.tv_jam_value);
            tvJmlDonor = itemView.findViewById(R.id.tv_jumlah_donor_value);
        }
    }
}
