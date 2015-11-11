package com.akamba.roland.mycoursequiz.beans;

import java.io.Serializable;

/**
 * Created by Rolland on 06/11/2015.
 */
public class Statistiques implements Serializable{
    public float evalTotal;
    public int noteJEE;
    public int noteAndroid;

    public int nbTentativeJEE;
    public int nbTentativeDroid;
    public int getNbTentatives(){
        return nbTentativeDroid+nbTentativeJEE;
    }
    public int getNote(){
        return noteAndroid+noteJEE;
    }
    public Statistiques(){
        evalTotal=0;
        noteAndroid=0;

        nbTentativeDroid=0;
        nbTentativeJEE=0;
    }
}
