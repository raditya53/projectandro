package com.example.fooddelivery;

public class cart {
private String nama, deskripsi, quantity;

    public cart(String nama, String deskripsi, String quantity) {
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.quantity = quantity;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
