package com.akamba.roland.mycoursequiz.beans;

/**
 * Created by Rolland on 06/11/2015.
 */
public class CalculNotes {
    public int totalPoint;
    public String nomEpreuve;

    public CalculNotes(){
        totalPoint=0;
        nomEpreuve="";
    }

    public void addPoint(int point){
        totalPoint+=point;
    }
}
