package com.akamba.roland.mycoursequiz.beans;

/**
 * Created by Rolland on 16/11/2015.
 */
public class Choix {
    private boolean isResponse=false;
    private String libelle;
    private int id;
    private int idQuestion;
    public Choix(){}
    public Choix(int id,boolean isReponse, String libelle, int idQuestion) {
        this.isResponse = isReponse;
        this.libelle = libelle;
        this.id = id;
        this.idQuestion = idQuestion;
    }

    public boolean isResponse() {
        return isResponse;
    }

    public void setIsResponse(boolean isReponse) {
        this.isResponse = isReponse;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }
}
