/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metinet.rest;

import com.metinet.beans.Data;
import com.metinet.mongodb.DataIotDb;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.bson.conversions.Bson;

/**
 * REST Web Service
 *
 * @author LP
 */
@Path("dataIoT")
@RequestScoped
public class DataIoTResource {

    @EJB
    private DataIotDb dataIotDb;



    /**
     * Creates a new instance of DataIotWs
     */
    public DataIoTResource() {
    }

    /**
     * Retrieves representation of an instance of com.metinet.ws.DataIotWs
     * @return an instance of java.lang.String
     */

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Data> getAll() {
        return dataIotDb.wrapper(dataIotDb.obtenir());
    }

    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(Data obj) {
        
        dataIotDb.ajouter(obj);
        
        return Response.status(200).build();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response put(Data obj) {
        Bson updateHydro = set("hydro", obj.getHydro());
        Bson updateTemp = set("temp", obj.getTemp());
        Bson updateMany = combine(updateHydro, updateTemp);
        dataIotDb.modifier(obj.getIdDataIOT(), updateMany);
        
        return Response.status(200).build();
    }
    
    @GET
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") String id) {
        dataIotDb.supprimer(id);
        return Response.status(200).build();
    }
    
    /*@DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(Data obj) {
        dataIotDb.supprimer(obj.getIdDataIOT());
        return Response.status(200).build();
    }*/
}
