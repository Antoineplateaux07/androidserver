/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metinet.controller;

import com.metinet.beans.Data;
import com.metinet.mongodb.DataIotDb;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Moi
 */
@Named(value = "iot")
@RequestScoped
public class Iot {
   
    @EJB
    private DataIotDb dataIotDb;
    
    private List<Data> listeData = new ArrayList<Data>();
    
    private Data data;
    
    private String hygro;
    private String temp;
    private Date dateData;

    
    public Iot(){
       
    }
    
    public void valider(){
        System.out.println(this.data);

            ajouter();
    }
    
    public void ajouter(){
        data = new Data();
        data.setHygro(hygro);
        data.setTemp(temp);
        dataIotDb.ajouter(data);
    }
    
    public void modifier(Data data){
        this.data.setIdDataIOT(data.getIdDataIOT());
        this.data.setHygro(data.getHygro());
        this.data.setTemp(data.getTemp());

    }
     
    public void supprimer(String id){
       DeleteResult res = dataIotDb.supprimer(id);
       System.out.println(res);
    }

    public List<Data> getListeData() {
        listeData=dataIotDb.wrapper(dataIotDb.obtenir());
        return listeData;
    }

    public void setListeData(List<Data> listeData) {
        this.listeData = listeData;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }



    public String getHygro() {
        return hygro;
    }

    public void setHygro(String hygro) {
        this.hygro = hygro;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public Date getDateData() {
        return dateData;
    }

    public void setDateData(Date dateData) {
        this.dateData = dateData;
    }

   
   
}
