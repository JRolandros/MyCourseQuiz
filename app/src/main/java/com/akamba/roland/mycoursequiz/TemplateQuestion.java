package com.akamba.roland.mycoursequiz;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.akamba.roland.mycoursequiz.beans.Choix;
import com.akamba.roland.mycoursequiz.beans.Jeu;
import com.akamba.roland.mycoursequiz.beans.LibelleQuestion;
import com.akamba.roland.mycoursequiz.beans.Question;
import com.akamba.roland.mycoursequiz.beans.Statistiques;
import com.akamba.roland.mycoursequiz.data.DataManager;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class TemplateQuestion extends ActionBarActivity implements View.OnClickListener{
    Intent currentIntet;
    Statistiques stat;
    String sender;
    //Question query;
    DataManager dataManager;
    Jeu jeu;
    //Map<Integer,String> listQ;
    static List<LibelleQuestion> myQuizQuestions;
    static int idRep;
    static int nbQuestions;
    int idChecked;
    RadioButton rb1;
    RadioButton rb2 ;
    RadioButton rb3;
    RadioButton rb4;
    TextView lib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_question);

        //getting the context from the sender
        currentIntet=getIntent();
        stat= (Statistiques) currentIntet.getExtras().getSerializable("statData");
        sender= (String) currentIntet.getStringExtra("sender");
        TextView t=(TextView)findViewById(R.id.info);
            t.setText("yo!");
        jeu =new Jeu();
        //permet d'activer le bouton retour
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //myQuizQuestions=new LinkedList<LibelleQuestion>();
        Toast.makeText(this, sender, Toast.LENGTH_LONG).show();
        nbQuestions=0;

        //creating data to populate questions
        dataManager=new DataManager(this);


        if(sender.toString().equals("JEE")) {
            myQuizQuestions=dataManager.getQuestionsByTheme("JEE");
        }
        else //sender==Droid
        {
           myQuizQuestions=dataManager.getQuestionsByTheme("Android");
        }
        nbQuestions=myQuizQuestions.size()-1;

        //managing radio button events
        RadioGroup rg = (RadioGroup) findViewById(R.id.rGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                idChecked=checkedId;
            }
        });

        //retrieving view elements
         rb1 = (RadioButton)findViewById(R.id.rb1);
         rb2 = (RadioButton)findViewById(R.id.rb2);
         rb3 = (RadioButton)findViewById(R.id.rb3);
         rb4 = (RadioButton)findViewById(R.id.rb4);
        Button v =(Button)findViewById(R.id.btnValider);
         lib=(TextView)findViewById(R.id.libelleQ);

        //populating view elements for the beginning party
        int d=nbQuestions;
        String g=myQuizQuestions.get(0).getListChoix().get(0).getLibelle();
        rb1.setText(myQuizQuestions.get(nbQuestions).getListChoix().get(0).getLibelle());
        if(myQuizQuestions.get(nbQuestions).getListChoix().get(0).isResponse())
            idRep=rb1.getId();

        rb2.setText(myQuizQuestions.get(nbQuestions).getListChoix().get(1).getLibelle());
            if(myQuizQuestions.get(nbQuestions).getListChoix().get(1).isResponse())
                idRep=rb2.getId();

        rb3.setText(myQuizQuestions.get(nbQuestions).getListChoix().get(2).getLibelle());
            if(myQuizQuestions.get(nbQuestions).getListChoix().get(2).isResponse())
                idRep=rb3.getId();

        rb4.setText(myQuizQuestions.get(nbQuestions).getListChoix().get(3).getLibelle());
            if(myQuizQuestions.get(nbQuestions).getListChoix().get(3).isResponse())
                idRep=rb4.getId();

        lib.setText(myQuizQuestions.get(nbQuestions).getLibelle());
        nbQuestions--;

        //registing event listner
        v.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        SubMenu jouer= menu.addSubMenu(Menu.NONE,1,Menu.NONE,"Jouer");
        jouer.add(Menu.NONE, 11, Menu.NONE, "JEE");
        jouer.add(Menu.NONE, 12, Menu.NONE, "Android");
        menu.add(Menu.NONE, 2, Menu.NONE, "Evaluer");
        menu.add(Menu.NONE, 3, Menu.NONE, "Statistique");
        menu.add(Menu.NONE, 4, Menu.NONE, "A propos");
        menu.add(Menu.NONE, 5, Menu.NONE, "Quitter");
        getMenuInflater().inflate(R.menu.menu_template_question, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==android.R.id.home)
        {
            //Intent intent=new Intent();
            super. onBackPressed();
            //setResult(RESULT_OK,intent);
            finish();
            return true;
        }

        //noinspection SimplifiableIfStatement
        switch (id){
            case 11:
                Intent JEEIntent= new Intent(this,TemplateQuestion.class);
                JEEIntent.putExtra("statData",stat);
                JEEIntent.putExtra("sender","JEE");
                startActivityForResult(JEEIntent,11);
                //Toast.makeText(this,"JEE"+item.getTitle(),Toast.LENGTH_LONG).show();
                break;
            case 12:
                Intent droidIntent= new Intent(this,TemplateQuestion.class);
                droidIntent.putExtra("statData",stat);
                droidIntent.putExtra("sender","DROID");
                startActivityForResult(droidIntent,12);
                //Toast.makeText(this,"Android"+item.getTitle(),Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText(this,"Evaluer"+item.getTitle(),Toast.LENGTH_LONG).show();
                break;
            case 3:
                Toast.makeText(this,"Stat"+item.getTitle(),Toast.LENGTH_LONG).show();
                break;
            case 4:
                Toast.makeText(this,"JEE"+item.getTitle(),Toast.LENGTH_LONG).show();
                break;
            case 5:
                Toast.makeText(this,"JEE"+item.getTitle(),Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnValider:

                if(nbQuestions<0)
                {
                    if(idChecked==idRep)
                        jeu.addPoint(10);
                    //End of party
                    if(sender.toString().equals("JEE"))
                    stat.noteJEE+= jeu.totalPoint;
                    else
                    stat.noteAndroid+= jeu.totalPoint;
                    Intent inten1 = new Intent();
                    inten1.putExtra("statData", stat);
                    setResult(RESULT_OK, inten1);
                    finish();
                }
                else
                {
                    if(idChecked==idRep)
                        jeu.addPoint(10);
                    TextView t=(TextView)findViewById(R.id.info);
                    if(nbQuestions==1)
                        t.setText("Derniere question!");
                    else
                        t.setText("");

                    rb1 = (RadioButton)findViewById(R.id.rb1);
                    rb2 = (RadioButton)findViewById(R.id.rb2);
                    rb3 = (RadioButton)findViewById(R.id.rb3);
                    rb4 = (RadioButton)findViewById(R.id.rb4);
                    lib=(TextView)findViewById(R.id.libelleQ);

                    //charging next question and populating view elements
                    rb1.setText(myQuizQuestions.get(nbQuestions).getListChoix().get(0).getLibelle());
                    if(myQuizQuestions.get(nbQuestions).getListChoix().get(0).isResponse())
                        idRep=rb1.getId();

                    rb2.setText(myQuizQuestions.get(nbQuestions).getListChoix().get(1).getLibelle());
                    if(myQuizQuestions.get(nbQuestions).getListChoix().get(1).isResponse())
                        idRep=rb2.getId();

                    rb3.setText(myQuizQuestions.get(nbQuestions).getListChoix().get(2).getLibelle());
                    if(myQuizQuestions.get(nbQuestions).getListChoix().get(2).isResponse())
                        idRep=rb3.getId();

                    rb4.setText(myQuizQuestions.get(nbQuestions).getListChoix().get(3).getLibelle());
                    if(myQuizQuestions.get(nbQuestions).getListChoix().get(3).isResponse())
                        idRep=rb4.getId();

                    lib.setText(myQuizQuestions.get(nbQuestions).getLibelle());
                    nbQuestions--;

                    switch (idRep){
                        case 1:
                            idRep=rb1.getId();
                            break;
                        case 2:
                            idRep=rb2.getId();
                        case 3:
                            idRep=rb3.getId();
                            break;
                        case 4:
                            idRep=rb4.getId();
                            break;
                        default:
                            break;
                    }
                }

                break;
            default:
                break;
        }

    }


}
