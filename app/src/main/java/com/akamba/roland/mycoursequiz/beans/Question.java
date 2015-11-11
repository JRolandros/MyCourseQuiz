package com.akamba.roland.mycoursequiz.beans;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rolland on 06/11/2015.
 */
public class Question implements Serializable{


    private String libelle;
    private Map<Integer,String> listChoix;
    private int idReponse;


    private int point;

    public Question(Map<Integer,String> listChoix, int idReponse,String libelle,int point){
        this.libelle=libelle;
        this.listChoix=listChoix;
        this.idReponse=idReponse;
        this.point=point;
    }

    public Question(){
        listChoix=new LinkedHashMap<>();
        libelle="";
        idReponse=-1;
    }

    //getters and setters

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Map<Integer, String> getListChoix() {
        return listChoix;
    }

    public void setListChoix(Map<Integer, String> listChoix) {
        this.listChoix = listChoix;
    }

    public int getIdReponse() {
        return idReponse;
    }

    public void setIdReponse(int idReponse) {
        this.idReponse = idReponse;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }


}
