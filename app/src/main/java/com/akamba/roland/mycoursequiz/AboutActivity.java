package com.akamba.roland.mycoursequiz;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.akamba.roland.mycoursequiz.Utilities.WebServices;


public class AboutActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        ImageView img=(ImageView)findViewById(R.id.myPhoto);
        img.setImageResource(R.drawable.vsprofiltn_);

        //show back button and active it
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //id of the view which will set the web content from a web service
        int id=R.id.w;
        String url="http://freegeoip.net/xml/173.194.40.184/";
        //get the web service content and display it
        WebServices webServices=new WebServices(this,id);
        webServices.execute(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about, menu);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
