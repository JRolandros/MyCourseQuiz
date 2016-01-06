package com.akamba.roland.mycoursequiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;

import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.telephony.gsm.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.akamba.roland.mycoursequiz.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity{
    String phoneNumber;
    ListView lv;
    TextView running;
    ProgressBar progress;
    ArrayList <String> aa= new ArrayList<String>();
    SimpleCursorAdapter mAdapter;
    MatrixCursor mMatrixCursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //The contacts from the contacts content provider is stored in this cursor
                mMatrixCursor = new MatrixCursor(new String[] {"_id","name","number"} );

        // Adapter to set data in the listview
        mAdapter = new SimpleCursorAdapter(getBaseContext(),
                R.layout.contact_list_item,
                null,
                new String[] { "name","number"},
                new int[] { R.id.tv_name,R.id.tv_details}, 0);

        // Getting reference to listview
        ListView lstContacts = (ListView) findViewById(R.id.lst_contacts);

        // Setting the adapter to listview
        lstContacts.setAdapter(mAdapter);

        //set progressbar
        progress=(ProgressBar)findViewById(R.id.progressBar);
        progress.setVisibility(View.VISIBLE);
        progress.setProgress(0);
        //adding the item click listener
        lstContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //to get the entire cursor
               // MatrixCursor cursor = (MatrixCursor) parent.getAdapter().getItem(position);

                //get needed info from the view.
                String owner= (String)((TextView)view.findViewById(R.id.tv_name)).getText();
                String number= (String)((TextView)view.findViewById(R.id.tv_details)).getText();
                String message="Vous avez gagné une place de cinéma UGC grâce à votre expéditeur!\n Pouvez vous le remercier?";

                //ask the user to confirm to send a message to the right user
                confirmToSendSMS(number,message,owner);

            }
        });
        // Creating an AsyncTask object to retrieve and load listview with contacts
       ListViewContactsLoader listViewContactsLoader = new ListViewContactsLoader(this.getContentResolver());

        // Starting the AsyncTask process to retrieve and load listview with contacts
        listViewContactsLoader.execute();

    }

    /** An AsyncTask class to retrieve and load listview with contacts */
    private class ListViewContactsLoader extends AsyncTask<Void, Integer, Cursor> {
        ContentResolver cr;
        public ListViewContactsLoader(ContentResolver cr){
            this.cr=cr;
        }
        @Override
        protected Cursor doInBackground(Void... params) {
            publishProgress(5);
           Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
            int i=15;
            publishProgress(i);
        while (phones.moveToNext())
        {
            i++;
            publishProgress(i);
            long contactId = phones.getLong(phones.getColumnIndex("_ID"));
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            //System.out.println(".................."+phoneNumber);
            //aa.add(phoneNumber);
            // Adding id, display name, path to photo and other details to cursor
            mMatrixCursor.addRow(new Object[]{Long.toString(contactId),name,phoneNumber});
        }
        phones.close();//close cursor
            return mMatrixCursor;
        }

        @Override
        protected void onPostExecute(Cursor result) {
            //remove progressBar and running view
            progress=(ProgressBar)findViewById(R.id.progressBar);
            running=(TextView)findViewById(R.id.running);
            progress.setVisibility(View.GONE);
            running.setVisibility(View.GONE);

            // Setting the cursor containing contacts to listview
            mAdapter.swapCursor(result);
        }

        @Override
        protected void onPreExecute() {
            //running=(TextView)findViewById(R.id.running);
            //running.setText("Starting getting contacts...");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            running=(TextView)findViewById(R.id.running);
            running.setText("Getting Contacts please wait..." + values[0]);
            progress=(ProgressBar)findViewById(R.id.progressBar);
            progress.setProgress(values[0]);
        }
    }

    private void sendSMS(String phoneNumber, String message)
    {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
                finish();
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
                finish();
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }

    public void confirmToSendSMS(final String phone, final String message,String owner){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to send to "+owner+ "("+phone+") the following\n\nMessage:\n"+message)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        sendSMS(phone, message);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void getNumber(ContentResolver cr)
    {
        Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while (phones.moveToNext())
        {
            long contactId = phones.getLong(phones.getColumnIndex("_ID"));
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            //System.out.println(".................."+phoneNumber);
            //aa.add(phoneNumber);
            // Adding id, display name, path to photo and other details to cursor
            mMatrixCursor.addRow(new Object[]{Long.toString(contactId),name,phoneNumber});
        }
        phones.close();//close cursor
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
             //   android.R.layout.simple_list_item_1,aa);
        //lv.setAdapter(adapter);
        //display contact numbers in the list
        mAdapter.swapCursor(mMatrixCursor);
    }


}
