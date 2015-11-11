package com.akamba.roland.mycoursequiz;

import android.content.Intent;
import android.support.annotation.NonNull;
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

import com.akamba.roland.mycoursequiz.beans.CalculNotes;
import com.akamba.roland.mycoursequiz.beans.Question;
import com.akamba.roland.mycoursequiz.beans.Statistiques;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;


public class TemplateQuestion extends ActionBarActivity implements View.OnClickListener{
    Intent currentIntet;
    Statistiques stat;
    String sender;
    Question query;
    CalculNotes calculNotes;
    Map<Integer,String> listQ;
    List<Question> myQuizQuestions;
    static int idRep;
    static int nbQuestion;
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
        calculNotes=new CalculNotes();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myQuizQuestions=new LinkedList<Question>();
        Toast.makeText(this, sender, Toast.LENGTH_LONG).show();
        nbQuestion=0;
        //creating data to populate questions
        if(sender.toString().equals("JEE")) {
            for (int i = 0; i < 3; i++) {
                listQ = new LinkedHashMap<Integer, String>();
                if (i == 0) {
                    listQ.put(1, "La taille des jars");
                    listQ.put(2, "La faible charge de developpement");
                    listQ.put(3, "La possibilite de deployer une application sur le conteneur de servlet");
                    listQ.put(4, "En oppisition avec la EJB");
                    String libelle = "Pourquoi le framework Spring est t-il qualifie comme conteneur leger";
                    myQuizQuestions.add(new Question(listQ, 2, libelle, 10));
                }
                if (i == 1) {
                    listQ.put(1, "@Autowired");
                    listQ.put(2, "@Value");
                    listQ.put(3, "@Controller");
                    listQ.put(4, "@Resource");
                    String libelle = "Pour qu'un attribut soit instancie avec un bean on doit utiliser l'annotation?";
                    myQuizQuestions.add(new Question(listQ, 4, libelle, 20));
                }
                if (i == 2) {
                    listQ.put(1, "TreeTable");
                    listQ.put(2, "HashTable");
                    listQ.put(3, "LinkedHashMap");
                    listQ.put(4, "LinkedHashSet");
                    String libelle = "Pour grader l'ordre d'insertion des cles, on doit utiliser la classe";
                    myQuizQuestions.add(new Question(listQ, 3, libelle, 5));
                }
            }
        }
        else //sender==Droid
        {

            for (int i = 0; i < 3; i++) {
                listQ = new LinkedHashMap<Integer, String>();
                if (i == 0) {
                    listQ.put(1, "3.0 (Honeycomb)");
                    listQ.put(2, "2.3 (Gingerbread)");
                    listQ.put(3, "2.6");
                    listQ.put(4, "2.2 (Froyo)");
                    String libelle = "Which is the latest mobile version of android?";
                    myQuizQuestions.add(new Question(listQ, 2, libelle, 10));
                }
                if (i == 1) {
                    listQ.put(1, "Chrome");
                    listQ.put(2, "Firefox");
                    listQ.put(3, "Open-source webkit");
                    listQ.put(4, "Opera");
                    String libelle = "Web browser available in android is based on?";
                    myQuizQuestions.add(new Question(listQ, 3, libelle, 20));
                }
                if (i == 2) {
                    listQ.put(1, "MP4");
                    listQ.put(2, "MPEG");
                    listQ.put(3, "AVI");
                    listQ.put(4, "MIDI");
                    String libelle = "Android doesn't support which format";
                    myQuizQuestions.add(new Question(listQ, 2, libelle, 5));
                }
            }
        }

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
        rb1.setText(myQuizQuestions.get(0).getListChoix().get(1));
        rb2.setText(myQuizQuestions.get(0).getListChoix().get(2));
        idRep=rb2.getId();
        rb3.setText(myQuizQuestions.get(0).getListChoix().get(4));
        rb4.setText(myQuizQuestions.get(0).getListChoix().get(3));
        lib.setText(myQuizQuestions.get(0).getLibelle());
        nbQuestion++;

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

                if(nbQuestion>=3)
                {
                    if(idChecked==idRep)
                        calculNotes.addPoint(myQuizQuestions.get(nbQuestion-1).getPoint());
                    //End of party
                    if(sender.toString().equals("JEE"))
                    stat.noteJEE+=calculNotes.totalPoint;
                    else
                    stat.noteAndroid+=calculNotes.totalPoint;
                    Intent inten1 = new Intent();
                    inten1.putExtra("statData", stat);
                    setResult(RESULT_OK, inten1);
                    finish();
                }
                else
                {
                    if(idChecked==idRep)
                        calculNotes.addPoint(myQuizQuestions.get(nbQuestion).getPoint());
                    TextView t=(TextView)findViewById(R.id.info);
                    if(nbQuestion==2)
                        t.setText("Derniere question!");
                    else
                        t.setText("");

                    rb1 = (RadioButton)findViewById(R.id.rb1);
                    rb2 = (RadioButton)findViewById(R.id.rb2);
                    rb3 = (RadioButton)findViewById(R.id.rb3);
                    rb4 = (RadioButton)findViewById(R.id.rb4);
                    lib=(TextView)findViewById(R.id.libelleQ);

                    //charging next question and populating view elements
                    rb1.setText(myQuizQuestions.get(nbQuestion).getListChoix().get(1));
                    rb2.setText(myQuizQuestions.get(nbQuestion).getListChoix().get(2));
                    idRep=myQuizQuestions.get(nbQuestion).getIdReponse();
                    rb3.setText(myQuizQuestions.get(nbQuestion).getListChoix().get(3));
                    rb4.setText(myQuizQuestions.get(nbQuestion).getListChoix().get(4));
                    lib.setText(myQuizQuestions.get(nbQuestion).getLibelle());
                    nbQuestion++;

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
