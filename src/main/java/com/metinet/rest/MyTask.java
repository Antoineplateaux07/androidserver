/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metinet.rest;

import com.metinet.beans.Data;
import com.metinet.mongodb.DataIotDb;
import com.metinet.mongodb.MongoDbProvider;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;
import org.primefaces.json.JSONObject;

/**
 *
 */

//Recup donnée iot prof
public class MyTask extends TimerTask{
    private DataIotDb dataIotDb;
 private Data data;
    
    private String hygro;
    private String temp;

    public MyTask(DataIotDb aThis) {
  this.dataIotDb = aThis;
    
    }
    
    @Override
    public void run() {
              
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL("https://api-project-1026902595480.firebaseio.com/.json");
            urlConnection = (HttpURLConnection) url.openConnection();
            
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            //readStream(in);//            
        StringBuilder stringBuilder = new StringBuilder();  
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in),1000);  
        for (String line = bufferedReader.readLine(); line != null; line =bufferedReader.readLine()){
        System.out.println(line);                      
        JSONObject jsonObject = new JSONObject(line);

            String humidity2 =jsonObject.getJSONObject("TEMP_INT_DHT22").get("humidity").toString();
            String temperautre2 =jsonObject.getJSONObject("TEMP_INT_DHT22").get("temperature").toString();
            Date actuelle=new Date();
           
                     
        data = new Data();
        data.setHygro(humidity2);
        data.setTemp(temperautre2);
  DateFormat DateFormat=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
             String date=DateFormat.format(actuelle);
             System.out.println(date);
         data.setDateData(date);
        dataIotDb.ajouter(data);
        
        }
        
         urlConnection.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
 
}
