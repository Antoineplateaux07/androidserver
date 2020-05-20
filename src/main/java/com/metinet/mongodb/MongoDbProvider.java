/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metinet.mongodb;

import com.mongodb.MongoClient;
import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class MongoDbProvider {
    private MongoClient mc=null;

    @Lock(LockType.READ)
    public MongoClient getMc() {
        return mc;
    }

    @PostConstruct
    public void init(){
        String mongoIp = "localhost";
        int mongoPort = 27017;
        mc = new MongoClient(mongoIp,mongoPort);
    }

}