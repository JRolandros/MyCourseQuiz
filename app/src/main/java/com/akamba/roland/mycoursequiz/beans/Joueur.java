package com.akamba.roland.mycoursequiz.beans;

/**
 * Created by Rolland on 16/11/2015.
 */
public class Joueur {
    private String email;
    private String pseudo;
    private String password;
    private int id;

    public Joueur(){}

    public Joueur(String email, String password, String pseudo, int id) {
        this.email = email;

        this.password=password;
        this.pseudo = pseudo;
        this.id = id;
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
