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
import android.widget.Toast;

import com.akamba.roland.mycoursequiz.Utilities.ExpandableListAdapter;
import com.akamba.roland.mycoursequiz.beans.Statistiques;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    static Statistiques stat;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    static int btnApro=46;
    int btnQuit=47;
    int btnStatis=48;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //setting statistic object
        stat=new Statistiques();

        Button btnEvaluate= (Button)findViewById(R.id.btnEvaluer);
        Button btnApropos= (Button)findViewById(R.id.btnAPropos);
        Button btnStatistic= (Button)findViewById(R.id.btnStat);
        Button btnQuitter= (Button)findViewById(R.id.btnQuitter);

        btnEvaluate.setOnClickListener(this);
        btnApropos.setOnClickListener(this);
        btnQuitter.setOnClickListener(this);
        btnStatistic.setOnClickListener(this);
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
                // TODO Auto-generated method stub
                //Toast.makeText(getApplicationContext(),listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition),Toast.LENGTH_LONG).show();
                if((listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))
                        =="JEE"){
                    Intent inten=new Intent(getApplicationContext(),TemplateQuestion.class);
                    stat.nbTentativeJEE++;
                    inten.putExtra("statData",stat);
                    inten.putExtra("sender","JEE");
                    startActivityForResult(inten, 11);
                }
                else
                {
                    Intent inten=new Intent(getApplicationContext(),TemplateQuestion.class);
                    stat.nbTentativeDroid++;
                    inten.putExtra("statData",stat);
                    inten.putExtra("sender","DROID");
                    startActivityForResult(inten, 12);
                }

                return false;
            }
        });
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
                evalInten.putExtra("statData",stat);
                startActivityForResult(evalInten, 45);
                break;
            case R.id.btnStat:
                Toast.makeText(this,"Statistique\n Note: "+stat.getNote()+"\n Note Android: "+stat.noteAndroid+"\nNote JEE: "+stat.noteJEE+"\n Tentative :" + stat.getNbTentatives()+"\n Tentative Android :" + stat.nbTentativeDroid+"\n Tentative Android :" + stat.nbTentativeJEE,Toast.LENGTH_LONG).show();
                break;
            case R.id.btnAPropos:
                Intent about=new Intent(this,AboutActivity.class);
                about.putExtra("statData", stat);
                startActivityForResult(about, 40);
               // Toast.makeText(this,"Author: Roland AKAMBA \nSociety: ESIGELEC",Toast.LENGTH_LONG).show();
                break;
            case R.id.btnQuitter:
                finish();
                System.exit(0);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        switch (requestCode) {
            case 11:
                if(resultCode==RESULT_OK) {
                    stat = (Statistiques) data.getExtras().getSerializable("statData");
                    Toast.makeText(this, "Your current mark: " + stat.getNote()+"\n Note Android: "+stat.noteAndroid+"\nNote JEE: "+stat.noteJEE + "\n Tentative :" + stat.getNbTentatives()+"\n Tentative Android :" + stat.nbTentativeDroid+"\n Tentative Android :" + stat.nbTentativeJEE, Toast.LENGTH_LONG).show();
                }
                break;
            case 12:
                if(resultCode==RESULT_OK) {
                    stat = (Statistiques) data.getExtras().getSerializable("statData");
                    Toast.makeText(this, "Your current mark: " + stat.getNote()+"\n Note Android: "+stat.noteAndroid+"\nNote JEE: "+stat.noteJEE + "\n Tentative :" + stat.getNbTentatives()+"\n Tentative Android :" + stat.nbTentativeDroid+"\n Tentative Android :" + stat.nbTentativeJEE, Toast.LENGTH_LONG).show();
                }
                break;
            case 45:
                if(resultCode==RESULT_OK){
                    stat = (Statistiques) data.getExtras().getSerializable("statData");
                    Toast.makeText(this, "values are.. "+stat.evalTotal, Toast.LENGTH_LONG).show();
                }
                break;
            default:
                Toast.makeText(this, "Nothing detected....", Toast.LENGTH_LONG).show();
                break;
        }

    }
}
