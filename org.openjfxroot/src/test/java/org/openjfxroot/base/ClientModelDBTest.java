package org.openjfxroot.base;

import org.openjfxroot.base.Client;
import java.util.List;
import java.util.ArrayList;
import java.sql.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;

public class ClientModelDBTest {

    public static Connection connect=null;
    public static ClientModelDB modelDB = null;
    public static List<Client> listClient = null;

    @BeforeClass
    public static void setup() {

       modelDB = ClientModelDB.getInstance();
       connect = ClientModelDB.getConnection();
    }
    
    @Test
    public void saveClientTest() {
        Client newClient = new Client(3, "Edgar", "Poe", "golden beetle");
        modelDB.saveClient(newClient);
        listClient = modelDB.getAllClients();
        // next: remember to decrease max Id by one for List indexing
        Client anotherClient = listClient.get(modelDB.getLastInsertedNr().intValue()-1);
        Assert.assertEquals( "Error match Edgar P.", "Poe", anotherClient.getLastName());     	
    }

    @AfterClass
    public static void tearDown() {
    	try {
    	    connect.close();
    	} catch (Exception e) {
    	    System.out.println("test teardown failed");
    	}
    }

}
