package com.akamba.roland.mycoursequiz.data;

import com.akamba.roland.mycoursequiz.beans.Choix;
import com.akamba.roland.mycoursequiz.beans.Joueur;
import com.akamba.roland.mycoursequiz.beans.LibelleQuestion;

/**
 * Created by Rolland on 22/11/2015.
 */
public class InitMyBDD {
    private static DataManager dataManager;

    public InitMyBDD(DataManager dataManager){

        this.dataManager=dataManager;
        if(dataManager.getQuestionsByTheme("JEE")==null){
            populateTableJoueur();
            populateTableQuestion();
            populateTableChoix();
        }


    }

    private void populateTableJoueur(){
        Joueur joueur=new Joueur("roland@yahoo.fr","roland","ros", 0);
        dataManager.insertJoueur(joueur);
        //TODO add other joueur here
    }
    private  void populateTableQuestion(){
        LibelleQuestion libelleQ;

        //q1
        libelleQ=new LibelleQuestion(1,"Pourquoi le framework Spring est t-il qualifie comme conteneur leger?","JEE",2,null);
        dataManager.addQuestion(libelleQ);

        //q2
        libelleQ=new LibelleQuestion(2,"Pour qu'un attribut soit instancie avec un bean on doit utiliser l'annotation?","JEE",8,null);
        dataManager.addQuestion(libelleQ);

        //q3
        libelleQ=new LibelleQuestion(3,"Pour grader l'ordre d'insertion des cles, on doit utiliser la classe","JEE",11,null);
        dataManager.addQuestion(libelleQ);

        //q4
        libelleQ=new LibelleQuestion(4,"Which is the latest mobile version of android?","Android",14,null);
        dataManager.addQuestion(libelleQ);

        //q5
        libelleQ=new LibelleQuestion(5,"Web browser available in android is based on?","Android",19,null);
        dataManager.addQuestion(libelleQ);

        //q6
        libelleQ=new LibelleQuestion(5,"Android doesn't support which format","Android",23,null);
        dataManager.addQuestion(libelleQ);
    }

    private  void populateTableChoix(){
        Choix choix;
        //q1
        choix=new Choix(1,false,"La taille des jars",1);
        dataManager.addChoix(choix);
        choix=new Choix(2,true,"La faible charge de developpement",1);
        dataManager.addChoix(choix);
        choix=new Choix(3,false,"La possibilite de deployer une application sur le conteneur de servlet",1);
        dataManager.addChoix(choix);
        choix=new Choix(4,false,"En oppisition avec la EJB",1);
        dataManager.addChoix(choix);

        //q2
        choix=new Choix(5,false,"@Autowired",2);
        dataManager.addChoix(choix);
        choix=new Choix(6,false,"@Value",2);
        dataManager.addChoix(choix);
        choix=new Choix(7,false,"@Controller",2);
        dataManager.addChoix(choix);
        choix=new Choix(8,true,"@Resource",2);
        dataManager.addChoix(choix);

        //q3
        choix=new Choix(9,false,"TreeTable",3);
        dataManager.addChoix(choix);
        choix=new Choix(10,false,"HashTable",3);
        dataManager.addChoix(choix);
        choix=new Choix(11,true,"LinkedHashMap",3);
        dataManager.addChoix(choix);
        choix=new Choix(12,false,"LinkedHashSet",3);
        dataManager.addChoix(choix);

        //q4
        choix=new Choix(13,false,"3.0 (Honeycomb)",4);
        dataManager.addChoix(choix);
        choix=new Choix(14,true,"2.3 (Gingerbread)",4);
        dataManager.addChoix(choix);
        choix=new Choix(15,false,"2.6",4);
        dataManager.addChoix(choix);
        choix=new Choix(16,false,"2.2 (Froyo)",4);
        dataManager.addChoix(choix);

        //q5
        choix=new Choix(17,false,"Chrome",5);
        dataManager.addChoix(choix);
        choix=new Choix(18,false,"Firefox",5);
        dataManager.addChoix(choix);
        choix=new Choix(19,true,"Open-source webkit",5);
        dataManager.addChoix(choix);
        choix=new Choix(20,false,"Opera",5);
        dataManager.addChoix(choix);

        //q6
        choix=new Choix(21,false, "MP4", 6);
        dataManager.addChoix(choix);
        choix=new Choix(22,false,"MPEG", 6);
        dataManager.addChoix(choix);
        choix=new Choix(23,true, "AVI", 6);
        dataManager.addChoix(choix);
        choix=new Choix(24,false,"MIDI",6);
        dataManager.addChoix(choix);
    }
}
