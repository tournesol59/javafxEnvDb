package org.openjfxroot.base;

import java.sql.Connection;
import java.util.List;
import java.util.ArrayList;
import java.sql.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;


public class UserModelDBTest {

    public static Connection connect=null;
    public static UserModelDB userDB = null;
    public static List<User> listUser = null;


    @BeforeClass
    public static void setup() {
       userDB = UserModelDB.getInstance();
       connect = UserModelDB.getConnection();
    }
    
    @Test
    public void getAllClientsTest() {
    	// insert your test code here
    	listUser = userDB.getAllUsers(); 
    	Assert.assertEquals("the size of the list of users does not match", 2, listUser.size());

    	//User auser = listUser.get(userDB.getLastInsertedNr().intValue()-1);
    	User auser = listUser.get(0);
    	Assert.assertEquals("the email of the first user does not match", "simono4@cti.ecp.fr", auser.getContactEmail());
    	
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
