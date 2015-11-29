package com.akamba.roland.mycoursequiz.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.akamba.roland.mycoursequiz.beans.Choix;
import com.akamba.roland.mycoursequiz.beans.Jeu;
import com.akamba.roland.mycoursequiz.beans.Joueur;
import com.akamba.roland.mycoursequiz.beans.LibelleQuestion;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Rolland on 16/11/2015.
 */
public class DataManager {
    private static SQLiteDatabase bdd;
    private MyBDD my_bdd;

    public DataManager(Context context){
        //context.deleteDatabase("MyCourseQuiz.db");
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
        Cursor c=bdd.query("joueur", columns, "dateJeu=? and idJoueur=?", new String[]{strDate, idJoueur.toString()}, null, null, null, null);
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
        jeu.setDateJeu(jeu.strDateJeu);
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
        values.put("isResponse", choix.isResponse());
        long result=bdd.insert("choix", null, values);
        close();
        return result;
    }

    public int updateChoix(int id,Choix choix){
        open();
        ContentValues values=new ContentValues();
        values.put("libelle",choix.getLibelle());
        values.put("idQuestion",choix.getIdQuestion());
        values.put("isResponse", choix.isResponse());
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
        Cursor c=bdd.query("choix", columns, "idQuestion=? and id=?", new String[]{idQuestion.toString(), idChoix.toString()}, null, null, null, null);
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
        open();
        ContentValues values=new ContentValues();
        values.put("libelle",question.getLibelle());
        values.put("idResponse",question.getIdResponse());
        values.put("themeJeu", question.getThemeJeu());
        int result=bdd.update("question", values, "id=" + id, null);
        close();
        return result;
    }

    public int deleteQuestion(int id){
        open();
        int result=bdd.delete("question", "id=" + id, null);
        close();
        return result;
    }

    public List<LibelleQuestion> getQuestionsByTheme(String theme){
        open();
        String[] columns ={"id", "libelle","themeJeu","idResponse"};
        Cursor c=bdd.query("question",columns, "themeJeu LIKE \""+theme+"\"",null,null, null,null);
        List<LibelleQuestion> result=convertCursorToQuestion(c);
        close();
        return result;
    }
    private List<LibelleQuestion> convertCursorToQuestion(Cursor c){
        List<LibelleQuestion> result=new LinkedList<LibelleQuestion>();
        LibelleQuestion question;
        //test si aucun jeu n'a été retourné
        if(c.getCount()==0)
            return null;
        else
            while(c.moveToNext())
            {
                question=new LibelleQuestion();
                question.setId(c.getInt(0));
                question.setLibelle(c.getString(1));
                question.setThemeJeu(c.getString(2));
                question.setIdResponse(c.getInt(3));
                question.setListChoix(getChoixQuestionById(c.getInt(0)));
                result.add(question);
            }

        c.close();
        return result;
    }

    public List<Choix> getChoixQuestionById(int idQuestion){
        List<Choix> listChoix= new LinkedList<Choix>();
        String[] columns ={"id", "idQuestion","isResponse","libelle"};
        Cursor c=bdd.query("choix",columns, "idQuestion="+idQuestion,null,null,null, null,null);

        if(c.getCount()==0)
            return null;
        else
        while(c.moveToNext())
        {
            boolean b=false;
            if(c.getInt(2)!=0)
                b=true;
            listChoix.add(new Choix(c.getInt(0),b,c.getString(3),c.getInt(1)));
        }
        c.close();
        return listChoix;
    }
    //endregion

    //region Manage users
    public Joueur insertJoueur (Joueur queryValues){
        open();
        ContentValues values = new ContentValues();
        values.put("email", queryValues.getEmail());
        values.put("password", queryValues.getPassword());
        values.put("pseudo",queryValues.getPseudo());

        queryValues.setId((int)bdd.insert("joueur", null, values));
        bdd.close();
        return queryValues;
    }


    public int updateJoueurPassword (Joueur queryValues){
        open();
        ContentValues values = new ContentValues();
        values.put("email", queryValues.getEmail());
        values.put("password", queryValues.getPassword());
        values.put("pseudo", queryValues.getPseudo());

        queryValues.setId((int) bdd.insert("joueur", null, values));
        bdd.close();
        return bdd.update("joueur", values, "id = ?", new String[] {String.valueOf(queryValues.getId())});
    }

    public Joueur getJoueur (String email){
        String query = "Select id, password, pseudo from joueur where email ='"+email+"'";
        Joueur myUser = new Joueur(email,"","",0);
        open();
        Cursor cursor = bdd.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                myUser.setId((int)cursor.getLong(0));
                myUser.setPassword(cursor.getString(1));
                myUser.setPseudo(cursor.getString(2));
            } while (cursor.moveToNext());
        }
        return myUser;
    }


    public int deleteJoueur(int id){
        open();
        int result=bdd.delete("joueur", "id=" + id, null);
        close();
        return result;
    }
    //endregion
}
