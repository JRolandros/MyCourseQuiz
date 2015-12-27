package com.akamba.roland.mycoursequiz.Utilities;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import com.akamba.roland.mycoursequiz.R;
import com.akamba.roland.mycoursequiz.beans.Response;

import java.io.BufferedInputStream;
import java.io.BufferedReader;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by roland on 08/12/2015.
 */
public class WebServices extends AsyncTask<String, Void, String> {

    //get main activity instance
    private Activity myActivity;

    //view element id
    int webContentId;

    //constructor
    public WebServices(Activity activity,int id){
        this.myActivity=activity;
        webContentId=id;
    }

    //method executed at the end of the doInBackground thread
    @Override
    protected void onPostExecute(String stream_url)
    {
        TextView tv= (TextView)myActivity.findViewById(webContentId);
        tv.setText(stream_url);
    }

    // run in background to execute the web service. this method is called when calling the execute() method in the instance of this class
    @Override
    protected String doInBackground(String... params) {
        String url=params[0];
        return executerQuery(url);
    }

    public String executerQuery(String url){
        String webContent=null;
        int code=0;
        URL _url= null;
        try {
            _url = new URL(url);
        } catch (MalformedURLException e) {
            System.out.println("========>>>>>>>>>>>> url mal formed");
            e.printStackTrace();
        }
        HttpURLConnection httpClient= null;
        try{
            httpClient = (HttpURLConnection)_url.openConnection();
            code=httpClient.getResponseCode();
            InputStream in= new BufferedInputStream(httpClient.getInputStream());
            webContent=generateString(in);
        }
        catch (IOException ex){
            System.out.println("=========>>>>> Error code :"+code);
            ex.printStackTrace();
        }finally {
            if(httpClient!=null)
            httpClient.disconnect();
        }
        return webContent;
    }
    public String generateString(InputStream in){
        InputStreamReader reader = new InputStreamReader(in);
        BufferedReader buffer=new BufferedReader(reader);
        StringBuilder sb =new StringBuilder();
        try {
            String cur;
            while((cur=buffer.readLine())!=null){
                sb.append(cur+System.getProperty("line.separator"));
            }
            reader.close();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return sb.toString();
    }

    public void convertXMLToJava(Response resp){
        //JAXBContext jc = JAXBContext.newInstance(Foo.class);
    }
}

