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
 * @author LP
 */
@Named(value = "iot")
@RequestScoped
public class Iot {
   
    @EJB
    private DataIotDb dataIotDb;
    
    private List<Data> listeData = new ArrayList<Data>();
    
    private Data data;
    private boolean modif = false;;
    
    private String hydro;
    private String temp;
    
    public Iot(){
       
    }
    
    public void valider(){
        System.out.println(this.data);
        if(modif == true){
            Bson updateHydro = set("hydro", hydro);
            Bson updateTemp = set("temp", temp);
            Bson updateMany = combine(updateHydro, updateTemp);
            UpdateResult resup = dataIotDb.modifier(data.getIdDataIOT(), updateMany);
            System.out.println(resup);
            modif = false;
            data = new Data();
        } else {
            ajouter();
        }
    }
    
    public void ajouter(){
        data = new Data();
        data.setHydro(hydro);
        data.setTemp(temp);
        dataIotDb.ajouter(data);
    }
    
    public void modifier(Data data){
        this.data.setHydro(data.getHydro());
        this.data.setIdDataIOT(data.getIdDataIOT());
        this.data.setTemp(data.getTemp());
        this.modif = true;
        PrimeFaces current = PrimeFaces.current();
//        current.executeScript("PF('myDialogID').show();");
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

    public boolean isModif() {
        return modif;
    }

    public void setModif(boolean modif) {
        this.modif = modif;
    }

    public String getHydro() {
        return hydro;
    }

    public void setHydro(String hydro) {
        this.hydro = hydro;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

   
   
}
