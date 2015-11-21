package com.akamba.roland.mycoursequiz.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.akamba.roland.mycoursequiz.beans.Choix;
import com.akamba.roland.mycoursequiz.beans.Jeu;
import com.akamba.roland.mycoursequiz.beans.Joueur;
import com.akamba.roland.mycoursequiz.beans.LibelleQuestion;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Rolland on 16/11/2015.
 */
public class DataManager {
    private static SQLiteDatabase bdd;
    private MyBDD my_bdd;

    public DataManager(Context context){
        //creation de la base avec ses tables
        my_bdd=new MyBDD(context,"MyCourseQuiz.db",null,1);

    }

        public void open(){
            if(bdd==null)
                bdd=my_bdd.getWritableDatabase();
                if(!bdd.isOpen())
                    bdd=my_bdd.getWritableDatabase();
        }
        public void close(){
            bdd.close();
        }
    //region Manage joueur
    public long addJoueur(Joueur joueur){
        open();
        ContentValues values=new ContentValues();
        values.put("nom",joueur.getNom());
        values.put("prenom",joueur.getPrenom());
        long result=bdd.insert("joueur", null, values);
        close();
        return result;
    }

    public int updateJoueur(int id,Joueur joueur){
        open();
        ContentValues values=new ContentValues();
        values.put("nom",joueur.getNom());
        values.put("prenom",joueur.getPrenom());
        int result=bdd.update("joueur", values, "id=" + id, null);
        close();
        return result;
    }

    public int deleteJoueur(int id){
        open();
        int result=bdd.delete("joueur", "id=" + id, null);
        close();
        return result;
    }

    public Joueur getJoueurByName(String nom){
        open();
        Cursor c=bdd.query("joueur",new String[]{"id","nom","prenom"}, "nom LIKE \""+nom+"\"",null,null,null,null);
        Joueur joueur=convertCursorToJoueur(c);
        close();
        return joueur;
    }
    private Joueur convertCursorToJoueur(Cursor c){
        //test si aucun joueur n'a été retourné
        if(c.getCount()==0)
            return null;
        else
            c.moveToFirst(); // on retourne le premier sinon
        Joueur joueur=new Joueur();
        joueur.setId(c.getInt(0));
        joueur.setNom(c.getString(1));
        joueur.setPrenom(c.getString(2));
        c.close();
        return joueur;
    }
    //endregion

    //region Manage jeu
    public long addJeu(Jeu jeu){
        ContentValues values=new ContentValues();
        values.put("themeJeu",jeu.themeJeu);
        values.put("dateJeu",jeu.strDateJeu);
        values.put("totalPoint",jeu.totalPoint);
        values.put("idJoueur",jeu.idJoueur);
        return bdd.insert("jeu", null, values);
    }

    public int updateJeu(int id,Jeu jeu){
        ContentValues values=new ContentValues();
        values.put("themeJeu", jeu.themeJeu);
        values.put("dateJeu", jeu.strDateJeu);
        values.put("totalPoint", jeu.totalPoint);
        values.put("idJoueur", jeu.idJoueur);
        return bdd.update("joueur", values, "id=" + id, null);
    }

    public int deleteJeu(int id){
        return bdd.delete("jeu", "id=" + id, null);
    }

    public Jeu getJeuByStrDateAndIdJoueur(String strDate,Integer idJoueur){
        String[] columns ={"id", "totalPoint","idJoueur","themeJeu","dateJeu"};
        Cursor c=bdd.query("joueur",columns, "dateJeu=? and idJoueur=?",new String[]{strDate,idJoueur.toString()},null,null, null,null);
        return convertCursorToJeu(c);
    }
    private Jeu convertCursorToJeu(Cursor c){
        //test si aucun jeu n'a été retourné
        if(c.getCount()==0)
            return null;
        else
            c.moveToFirst(); // on retourne le premier sinon
        Jeu jeu=new Jeu();
        jeu.id=c.getInt(0);
        jeu.totalPoint=c.getInt(1);
        jeu.themeJeu=c.getString(2);
        jeu.idJoueur=c.getInt(3);
        jeu.id=c.getInt(0);
        jeu.strDateJeu=c.getString(4);
        jeu.dateJeu= new Date(jeu.strDateJeu);
        c.close();
        return jeu;
    }
    //endregion

    //region Manage choix
    public long addChoix(Choix choix){
        open();
        ContentValues values=new ContentValues();
        values.put("libelle",choix.getLibelle());
        values.put("idQuestion",choix.getIdQuestion());
        values.put("isResponse",choix.isResponse());
        long result=bdd.insert("choix", null, values);
        close();
        return result;
    }

    public int updateChoix(int id,Choix choix){
        open();
        ContentValues values=new ContentValues();
        values.put("libelle",choix.getLibelle());
        values.put("idQuestion",choix.getIdQuestion());
        values.put("isResponse",choix.isResponse());
        int result=bdd.update("choix", values, "id=" + id, null);
        close();
        return result;
    }

    public int deleteChoix(int id){
        open();
        int result=bdd.delete("choix", "id=" + id, null);
        close();
        return result;
    }

    public Choix getChoixBy2Id(Integer idQuestion,Integer idChoix){
        open();
        String[] columns ={"id", "idQuestion","isResponse","libelle"};
        Cursor c=bdd.query("choix",columns, "idQuestion=? and id=?",new String[]{idQuestion.toString(),idChoix.toString()},null,null, null,null);
        return convertCursorToChoix(c);
    }
    private Choix convertCursorToChoix(Cursor c){
        //test si aucun jeu n'a été retourné
        if(c.getCount()==0)
            return null;
        else
            c.moveToFirst(); // on retourne le premier sinon
        boolean b=false;
        if(c.getInt(2)!=0)
            b=true;
        Choix choix=new Choix(c.getInt(0),b,c.getString(3),c.getInt(1));
        c.close();
        return choix;
    }
    //endregion

    //region Manage question
    public long addQuestion(LibelleQuestion question){
        open();
        ContentValues values=new ContentValues();
        values.put("libelle",question.getLibelle());
        values.put("idResponse",question.getIdResponse());
        values.put("themeJeu",question.getThemeJeu());
        long result=bdd.insert("question", null, values);
        close();
        return result;
    }

    public int updateQuestion(int id,LibelleQuestion question){
        ContentValues values=new ContentValues();
        values.put("libelle",question.getLibelle());
        values.put("idResponse",question.getIdResponse());
        values.put("themeJeu", question.getThemeJeu());
        return bdd.update("question", values, "id=" + id, null);
    }

    public int deleteQuestion(int id){
        return bdd.delete("question", "id=" + id, null);
    }

    public LibelleQuestion getQuestionById(String theme){
        open();
        String[] columns ={"id", "libelle","themeJeu","idResponse"};
        Cursor c=bdd.query("question",columns, "themeJeu LIKE \""+theme+"\"",null,null, null,null);
        //if ((c!= null && c.getCount() > 0 && c.moveToFirst()))
            return convertCursorToQuestion(c);
    }
    private LibelleQuestion convertCursorToQuestion(Cursor c){
        //test si aucun jeu n'a été retourné
        if(c.getCount()==0)
            return null;
        else
            c.moveToFirst(); // on retourne le premier sinon
        LibelleQuestion question=new LibelleQuestion();
        question.setId(c.getInt(0));
        question.setLibelle(c.getString(1));
        question.setThemeJeu(c.getString(2));
        question.setIdResponse(c.getInt(3));
        question.setListChoix(getChoixQuestionById(question.getId()));
        c.close();
        return question;
    }

    public List<Choix> getChoixQuestionById(Integer idQuestion){
        List<Choix> listChoix= new LinkedList<Choix>();
        String[] columns ={"id", "idQuestion","isResponse","libelle"};
        Cursor c=bdd.query("choix",columns, "idQuestion="+idQuestion,null,null,null, null,null);
        if(c.getCount()==0)
            return null;

        else
        while(c.moveToNext())
         listChoix.add(iteratorConvertCursorToChoix(c));
        c.close();
        return listChoix;
    }
    private Choix iteratorConvertCursorToChoix(Cursor c){
        boolean b=false;
        if(c.getInt(1)!=0)
            b=true;
        Choix choix=new Choix(c.getInt(0),b,c.getString(2),c.getInt(3));
        return choix;
    }
    //endregion
}
