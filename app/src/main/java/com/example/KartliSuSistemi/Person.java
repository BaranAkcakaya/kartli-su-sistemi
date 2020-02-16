package com.example.KartliSuSistemi;

public class Person {
    private String tarih;
    private String tutar;
    private int photoId;
    private String durum="ODENDI";

    public Person(String name,String address,  int photoId,String durum) {
        this.tarih = name;
        this.tutar = address;
        this.photoId = photoId;
        this.durum=durum;
    }

    public int getPhotoId() {
        return photoId;
    }

    public String getName() {
        return tarih;
    }

    public String getAddress() {return tutar;}
    public String getDurum() {return durum;}

}