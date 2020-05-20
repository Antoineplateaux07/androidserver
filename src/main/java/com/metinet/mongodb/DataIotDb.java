/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metinet.mongodb;

import com.metinet.beans.Data;
import com.metinet.rest.MyTask;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 *
 * @author LP
 */

@Stateless
public class DataIotDb { 

    @EJB
    private MongoDbProvider mongoDbProvider;
    
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase; 
    private MongoCollection mongoCollection;
    
    public DataIotDb() {
        
    }
    
    
    @PostConstruct
    public void init() {
        this.mongoClient=mongoDbProvider.getMc();
        this.mongoDatabase = this.mongoClient.getDatabase("iot");
        this.mongoCollection = this.mongoDatabase.getCollection("DataIOT");
        //appelle une classe toutes les 10 minutes
      //récupérer  donner url json prof
        Timer t = new Timer();
        t.schedule(new MyTask(this),0, 60000 );
    }

    
    public void ajouter(Data d){
        Document doc = new Document();
        doc.put("temp", d.getTemp());
        doc.put("hygro", d.getHygro());
        doc.put("date", d.getDateData());

   
        mongoCollection.insertOne(doc);
    }
    
    public UpdateResult modifier(String id, Bson update){
        Bson filtre2 = eq("_id", new ObjectId(id));
        UpdateResult resup = mongoCollection.updateOne(filtre2, update);
        return resup;
    }
    
        
    public DeleteResult supprimer(String id){
        Bson filtre = eq("_id", new ObjectId(id));
        DeleteResult res = mongoCollection.deleteOne(filtre);
        return res;
    }
    
    public MongoCursor<Document> obtenir(){
        FindIterable<Document> iterable = mongoCollection.find();
        MongoCursor<Document> cursor = iterable.iterator();
        
        return cursor;
    }

    public List<Data> wrapper(MongoCursor<Document> cursor) {

        List<Data> listeDataSonde = new ArrayList<>();
        while (cursor.hasNext()) {
            Data data = new Data();
            Document obj = cursor.next();

            if (obj != null) {
                if (obj.get("_id") != null) {
                    data.setIdDataIOT(obj.get("_id").toString());

                    if (obj.get("temp") != null) {
                    data.setTemp(obj.get("temp").toString());
                    }
                    if (obj.get("hygro") != null) {
                        data.setHygro(obj.get("hygro").toString());
                    }
                     if (obj.get("date") != null) {
                        data.setDateData(obj.get("date").toString());
                    }
                    
                    listeDataSonde.add(data);
                }
            }

        }
        return listeDataSonde;
    }

    
}
