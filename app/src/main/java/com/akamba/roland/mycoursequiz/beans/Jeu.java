package com.akamba.roland.mycoursequiz.beans;

import android.text.format.Time;

import java.util.Date;

/**
 * Created by Rolland on 06/11/2015.
 */
public class Jeu {
    public int id;
    public int totalPoint;
    public int idJoueur;
    public String themeJeu;
    public Date dateJeu;
    public String strDateJeu;

    public Jeu(){
        totalPoint=0;
        dateJeu=new Date(Time.getCurrentTimezone());
        idJoueur=0;
        themeJeu="";
        strDateJeu=dateJeu.toString();
    }

    public void addPoint(int point){
        totalPoint+=point;
    }
}
