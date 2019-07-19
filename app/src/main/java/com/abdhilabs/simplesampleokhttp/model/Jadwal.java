package com.abdhilabs.simplesampleokhttp.model;

public class Jadwal {
    private String instansi, alamat, jam, jmlDonor;

    public Jadwal(String instansi, String alamat, String jam, String jmlDonor) {
        this.instansi = instansi;
        this.alamat = alamat;
        this.jam = jam;
        this.jmlDonor = jmlDonor;
    }

    public String getInstansi() {
        return instansi;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getJam() {
        return jam;
    }

    public String getJmlDonor() {
        return jmlDonor;
    }
}
