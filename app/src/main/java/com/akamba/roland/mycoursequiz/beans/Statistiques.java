package com.akamba.roland.mycoursequiz.beans;

import java.io.Serializable;

/**
 * Created by Rolland on 06/11/2015.
 */
public class Statistiques implements Serializable{
    public float evalTotal;
    public int noteJEE;
    public int noteAndroid;
    public boolean isGoodResult=false;
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

    public String Appreciation(int maxResult){
        isGoodResult=false;
        int note=0;
        if(getNbTentatives()!=0)
        note=getNote()/getNbTentatives();
        if(note==0 && getNbTentatives()==0)
            return "vous n'avez pas encore passé de test!";
        if(note==0 && getNbTentatives()>0)
            return "Désolé, mais vous êtes vraiment null. Prenez vous au sérieux!";
        if(note<=((1.0/3)*maxResult) && note>0)
            return "Mediocre";
        if(note>=((1.0/3)*maxResult)&& note<((2.0/3)*maxResult))
            return "Bien, le résultat est satisfaisant";
        if(note>=((2.0/3)*maxResult)&&note<((3.0/4)*maxResult))
            return "Très bien, vous avez atteint un record impressionnant!";
        if(note>=((3.0/4)*maxResult) && note<maxResult){
            isGoodResult=true;
            return "Excelent, votre record est très impressionnant!\n Choisissez un contact à qui envoyer un billet de cinéma";
        }
        isGoodResult=true;
        return "Parfait! Rien à rajouter à vos performances.\nChoisissez un contact à qui envoyer un billet de cinéma";
    }
}
