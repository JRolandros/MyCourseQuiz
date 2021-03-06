package com.akamba.roland.mycoursequiz;

import android.app.ActionBar;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.akamba.roland.mycoursequiz.Utilities.ExpandableListAdapter;
import com.akamba.roland.mycoursequiz.Utilities.MapsFrag;
import com.akamba.roland.mycoursequiz.Utilities.MyLocation;
import com.akamba.roland.mycoursequiz.Utilities.QuizConnectionRequest;
import com.akamba.roland.mycoursequiz.beans.Choix;
import com.akamba.roland.mycoursequiz.beans.Joueur;
import com.akamba.roland.mycoursequiz.beans.LibelleQuestion;
import com.akamba.roland.mycoursequiz.beans.Statistiques;
import com.akamba.roland.mycoursequiz.data.DataManager;
import com.akamba.roland.mycoursequiz.data.InitMyBDD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    //region variables
    //static Statistiques stat;
    Joueur joueur;
    DataManager dataManager;
    MyLocation myLocation;
    int noteMax=30;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    static int btnApro=46;
    int btnQuit=47;
    int btnStatis=48;
    double lo;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get intent
        Intent currentIntet=getIntent();

        //get login user sent by the login activity
        joueur= (Joueur) currentIntet.getExtras().getSerializable("loginUser");

        //get user's location
        myLocation=new MyLocation(this);
        getMyPosition();

        //display user info
        TextView userTv=(TextView)findViewById(R.id.userInfo);
        userTv.setText("User: "+joueur.getEmail());
        TextView connectionTv=(TextView)findViewById(R.id.connectionInfo);
        connectionTv.setText("Location :"+joueur.getCity());
        //set my app logo esig
        ImageView img=(ImageView)findViewById(R.id.esigLogo);
        img.setImageResource(R.drawable.esiglogo);

        //to delete database
        //this.deleteDatabase("MyCourseQuiz.db");

        //create database manager
        dataManager=new DataManager(this);

        //dataManager.open();

        //this is used to initialize my database if it's not done yet.
        InitMyBDD initMyBDD=new InitMyBDD(dataManager);

        //setting statistic object
        //stat=new Statistiques();

        //region populating view elements
        Button btnEvaluate= (Button)findViewById(R.id.btnEvaluer);
        Button btnApropos= (Button)findViewById(R.id.btnAPropos);
        Button btnStatistic= (Button)findViewById(R.id.btnStat);
        Button btnQuitter= (Button)findViewById(R.id.btnQuitter);
        TextView mapView= (TextView)findViewById(R.id.connectionInfo);

        btnEvaluate.setOnClickListener(this);
        btnApropos.setOnClickListener(this);
        btnQuitter.setOnClickListener(this);
        btnStatistic.setOnClickListener(this);
        mapView.setOnClickListener(this);
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.expList);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                //Toast.makeText(getApplicationContext(),listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition),Toast.LENGTH_LONG).show();
                if((listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))
                        =="JEE"){
                    Intent inten=new Intent(getApplicationContext(),TemplateQuestion.class);
                    joueur.getMyStat().nbTentativeJEE++;
                    inten.putExtra("loginUser",joueur);
                    inten.putExtra("sender","JEE");

                   startActivityForResult(inten, 11);
                }
                else
                {
                    Intent inten=new Intent(getApplicationContext(),TemplateQuestion.class);
                    joueur.getMyStat().nbTentativeDroid++;
                    inten.putExtra("loginUser",joueur);
                    inten.putExtra("sender","DROID");
                    startActivityForResult(inten, 12);
                }

                return false;
            }
        });

        //endregion
    }
//region override methods
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       SubMenu jouer= menu.addSubMenu(Menu.NONE,1,Menu.NONE,"Jouer");
        jouer.add(Menu.NONE, 11, Menu.NONE, "JEE");
        jouer.add(Menu.NONE, 12, Menu.NONE, "Android");
        menu.add(Menu.NONE, 2, Menu.NONE, "Evaluer");
        menu.add(Menu.NONE, 3, Menu.NONE, "Statistique");
        menu.add(Menu.NONE, 4, Menu.NONE, "A propos");
        menu.add(Menu.NONE, 6, Menu.NONE, "Mes contats");
        menu.add(Menu.NONE, 5, Menu.NONE, "Quitter");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case 11: //JEE quiz
                joueur.getMyStat().nbTentativeJEE++;
                Intent JEEIntent= new Intent(this,TemplateQuestion.class);
                JEEIntent.putExtra("loginUser",joueur);
                JEEIntent.putExtra("sender","JEE");
                startActivityForResult(JEEIntent,11);
                //Toast.makeText(this,"JEE"+item.getTitle(),Toast.LENGTH_LONG).show();
                break;
            case 12: //android quiz
                joueur.getMyStat().nbTentativeDroid++;
                Intent droidIntent= new Intent(this,TemplateQuestion.class);
                droidIntent.putExtra("loginUser",joueur);
                droidIntent.putExtra("sender","DROID");
                startActivityForResult(droidIntent,12);
                //Toast.makeText(this,"Android"+item.getTitle(),Toast.LENGTH_LONG).show();
                break;
            case 2: //Evaluer
                Intent evalInten=new Intent(this,EvaluateActivity.class);
                evalInten.putExtra("loginUser",joueur);
                startActivityForResult(evalInten, 45);
                break;
            case 3: //Statistique
                Toast.makeText(this,"Statistique\n==============\n"+joueur.getMyStat().Appreciation(noteMax)+"\n************\nNote: "+joueur.getMyStat().getNote()+"\n Note Android: "+joueur.getMyStat().noteAndroid+"\nNote JEE: "+joueur.getMyStat().noteJEE+"\n Tentative :" + joueur.getMyStat().getNbTentatives()+"\n Tentative Android :" + joueur.getMyStat().nbTentativeDroid+"\n Tentative Android :" + joueur.getMyStat().nbTentativeJEE,Toast.LENGTH_LONG).show();
                break;
            case 4: //A propos
                Intent about=new Intent(this,AboutActivity.class);
                about.putExtra("loginUser", joueur);
                startActivityForResult(about, 40);
                break;
            case 6: //contact
                Intent contact=new Intent(this,ContactActivity.class);
                contact.putExtra("loginUser", joueur);
                startActivityForResult(contact, 55);
                break;
            case 5: //quitter
                finish();
                System.exit(0);
                break;
            default:
                break;
        }
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("JOUER");
        //listDataHeader.add("Now Showing");
        //listDataHeader.add("Coming Soon..");

        // Adding child data
        List<String> jouer = new ArrayList<String>();
        jouer.add("JEE");
        jouer.add("Android");

        /*List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");*/

        listDataChild.put(listDataHeader.get(0), jouer); // Header, Child data
        //listDataChild.put(listDataHeader.get(1), nowShowing);
        //listDataChild.put(listDataHeader.get(2), comingSoon);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnEvaluer:
                Intent evalInten=new Intent(this,EvaluateActivity.class);
                evalInten.putExtra("loginUser",joueur);
                startActivityForResult(evalInten, 45);
                break;
            case R.id.btnStat:
                Toast.makeText(this,"Statistique\n==============\n"+joueur.getMyStat().Appreciation(noteMax)+"\n************\nNote: "+joueur.getMyStat().getNote()+"\n Note Android: "+joueur.getMyStat().noteAndroid+"\nNote JEE: "+joueur.getMyStat().noteJEE+"\n Tentative :" + joueur.getMyStat().getNbTentatives()+"\n Tentative Android :" + joueur.getMyStat().nbTentativeDroid+"\n Tentative Android :" + joueur.getMyStat().nbTentativeJEE,Toast.LENGTH_LONG).show();
                break;
            case R.id.btnAPropos:
                Intent about=new Intent(this,AboutActivity.class);
                about.putExtra("loginUser", joueur);
                startActivityForResult(about, 40);
               // Toast.makeText(this,"Author: Roland AKAMBA \nSociety: ESIGELEC",Toast.LENGTH_LONG).show();
                break;
            case R.id.btnQuitter:
                finish();
                System.exit(0);
                break;
            case R.id.connectionInfo:
                Intent mapInten=new Intent(this,MapsFrag.class);
                mapInten.putExtra("loginUser",joueur);
                startActivityForResult(mapInten, 100);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){

        switch (requestCode) {
            case 11: //JEE quiz
                if(resultCode==RESULT_OK) {
                    joueur= (Joueur) data.getExtras().getSerializable("loginUser");

                    Toast.makeText(this, joueur.getMyStat().Appreciation(noteMax)+"\n*************\nYour current mark: " + joueur.getMyStat().getNote()+"\n Note Android: "+joueur.getMyStat().noteAndroid+"\nNote JEE: "+joueur.getMyStat().noteJEE + "\n Tentative :" + joueur.getMyStat().getNbTentatives()+"\n Tentative Android :" + joueur.getMyStat().nbTentativeDroid+"\n Tentative Android :" + joueur.getMyStat().nbTentativeJEE, Toast.LENGTH_LONG).show();
                    if(joueur.getMyStat().isGoodResult){
                        Intent contact1=new Intent(this,ContactActivity.class);
                        contact1.putExtra("loginUser", joueur);
                        startActivityForResult(contact1, 55);
                    }

                }
                break;
            case 12: //Android quiz
                if(resultCode==RESULT_OK) {
                    joueur= (Joueur) data.getExtras().getSerializable("loginUser");
                    Toast.makeText(this, joueur.getMyStat().Appreciation(noteMax)+"\n***************\nYour current mark: " + joueur.getMyStat().getNote()+"\n Note Android: "+joueur.getMyStat().noteAndroid+"\nNote JEE: "+joueur.getMyStat().noteJEE + "\n Tentative :" + joueur.getMyStat().getNbTentatives()+"\n Tentative Android :" + joueur.getMyStat().nbTentativeDroid+"\n Tentative Android :" + joueur.getMyStat().nbTentativeJEE, Toast.LENGTH_LONG).show();
                    if(joueur.getMyStat().isGoodResult){
                        Intent contact1=new Intent(this,ContactActivity.class);
                        contact1.putExtra("loginUser", joueur);
                        startActivityForResult(contact1, 55);
                    }
                }
                break;
            case 45: //statistic
                if(resultCode==RESULT_OK){
                    joueur= (Joueur) data.getExtras().getSerializable("loginUser");
                    Toast.makeText(this, "values are.. "+joueur.getMyStat().evalTotal, Toast.LENGTH_LONG).show();
                }
                break;
            default:
                Toast.makeText(this, "Nothing detected....", Toast.LENGTH_LONG).show();
                break;
        }

    }

    //endregion

    // get th user location
    public void getMyPosition(){
        String address="";
        String city="";
        //checked connection availability
        QuizConnectionRequest quizConnection=QuizConnectionRequest.getInstance(this);

        if(myLocation.isGPSEnabled())
        {
            //this get the street number and its name such as 2 avenue du Roi as a string
            address=myLocation.getLocationAddressName().get(0).getAddressLine(0)+" ";
            //this get the locaty name (town) and the postale code such as 76000 Rouen as a string
            address+=myLocation.getLocationAddressName().get(0).getAddressLine(1)+" ";
            //this get the country name as a string
            address+= myLocation.getLocationAddressName().get(0).getAddressLine(2);

            //get user's city
            city=myLocation.getLocationAddressName().get(0).getAddressLine(1);

            //getting gps latitude and longitude
            double lat=myLocation.getMyLocation().getLatitude();
            double lon=myLocation.getMyLocation().getLongitude();
            joueur.setLatitude(lat);
            joueur.setLongitude(lon);
            joueur.setAddress(address);
            joueur.setCity(city);
            //TODO create an update method to do this properly
            //dataManager.deleteJoueur(joueur.getId());
            //dataManager.insertJoueur(joueur);

        }
        else
            myLocation.alertUserGPSNotAvailable(this);
    }

}
