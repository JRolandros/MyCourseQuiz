package com.akamba.roland.mycoursequiz.beans;

import java.io.Serializable;

/**
 * Created by Rolland on 16/11/2015.
 */
public class Joueur implements Serializable {
    private String email;
    private String pseudo;
    private String password;
    private double longitude;
    private double latitude;
    private String address;
    private Statistiques myStat;

    private String city;

    private int id;

    public Joueur(){
        myStat=new Statistiques();
    }

    public Joueur(String email, String password, String pseudo, int id) {
        this.email = email;
        myStat=new Statistiques();
        this.password=password;
        this.pseudo = pseudo;
        this.id = id;
    }

    public Statistiques getMyStat(){
        return myStat;
    }
    public void setMyStat(Statistiques myStat){
        this.myStat=myStat;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
