package com.akamba.roland.mycoursequiz.beans;

import java.util.List;

/**
 * Created by Rolland on 16/11/2015.
 */
public class LibelleQuestion {
    private int id;
    private String libelle;
    private String themeJeu;
    private List<Choix> listChoix;
    private int idResponse;

//TODO penser à changer cette classe en Question quand j'aurai supprimé celle actuelle.
    public LibelleQuestion(){}
    public LibelleQuestion(int id, String libelle, String themeJeu, int idResponse, List<Choix> listChoix) {
        this.id = id;
        this.libelle = libelle;
        this.listChoix = listChoix;
        this.idResponse=idResponse;
        this.themeJeu=themeJeu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public List<Choix> getListChoix() {
        return listChoix;
    }

    public void setListChoix(List<Choix> listChoix) {
        this.listChoix = listChoix;
    }

    public int getIdResponse() {
        return idResponse;
    }

    public void setIdResponse(int idResponse) {
        this.id = idResponse;
    }

    public String getThemeJeu() {
        return themeJeu;
    }

    public void setThemeJeu(String themeJeu) {
        this.themeJeu = themeJeu;
    }
}
