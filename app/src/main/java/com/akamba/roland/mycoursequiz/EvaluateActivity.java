package com.akamba.roland.mycoursequiz;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.akamba.roland.mycoursequiz.beans.Statistiques;


public class EvaluateActivity extends ActionBarActivity implements View.OnClickListener{

    Statistiques stat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        Intent child= getIntent();
        stat= (Statistiques) child.getExtras().getSerializable("statData");

        RatingBar mBar = (RatingBar) findViewById(R.id.ratingBar);
        Button btnEValValider=(Button) findViewById(R.id.btnEvalValider);
        btnEValValider.setOnClickListener(this);
        mBar.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_evaluate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnEvalValider)
        {
            RatingBar mBar = (RatingBar) findViewById(R.id.ratingBar);

            //Toast.makeText(this, "values are.. " + mBar.getRating(), Toast.LENGTH_LONG).show();
            stat.evalTotal=mBar.getRating();
            Intent inten1=new Intent();
            inten1.putExtra("statData",stat);
            setResult(RESULT_OK, inten1);
            finish();
        }

    }
}
