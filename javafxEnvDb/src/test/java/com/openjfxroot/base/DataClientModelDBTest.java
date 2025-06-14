package com.openjfxroot.base;

import com.openjfxroot.base.DataClientModelDBTest;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.lang.Iterable;
import java.util.Iterator;
import java.util.ListIterator;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;

import org.junit.Assert;

public class DataClientModelDBTest {

   private static Client aclient;
   private static DataClientModelDB clientmod;
   private static List<Client> listClient;
  // public DataClientModelDBTest {
  // }

   @BeforeClass
   public static void setup() {

	   // fred: inserted a first try catch bloc to quick test if the error comes from the h2 db or from my mutiple code
      try {
	//Class.forName("com.mysql.cj.jdbc.Driver");
	Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/ouvragesdb", "root", "");

        //Class.forName ("org.h2.Driver");    
        //Connection connect = DriverManager.getConnection("jdbc:h2:mem:ouvragedb", "SA", "");
	//Connection connect = DriverManager.getConnection("jdbc:h2:~/testdb", "SA", "");
	
	//Class.forName("org.hsqldb.jdbc.JDBCDriver");
        //Connection connect = DriverManager.getConnection("jdbc:hsqldb:file:/Users/frede/hsqldb/ouvragesdb", "SA", "");
	System.out.println("Connected manually to the H2 database");
	
        Statement stmt = connect.createStatement();
	ResultSet result = stmt.executeQuery("SELECT last_name FROM tblclient");
	//ResultSet result = stmt.executeQuery("CREATE TABLE tblclient( id int PRIMARY KEY, name VARCHAR(40) )");
	
	while (result.next()) {
           System.out.println("found client with name: " + result.getString(1));
	}
	result.close();
	//connect.close();

	System.out.println("Connection the database normally closed");
      } catch (Exception e) {
          System.out.println("Test case manually connection failed");
	  e.printStackTrace();
      }
      try {
        clientmod = DataClientModelDB.getInstance();
	System.out.println("Re-connected to the H2 database");
        listClient = clientmod.loadData();
        Assert.assertNotNull(listClient);
        Assert.assertEquals(1, listClient.size());
      }
      catch (Exception e) {
          System.out.println("Test case clientmodel (@beforeClass) failed");
      }
    }

   @Test
   public void testUpdateLast() {
      try {
	 aclient = new Client( clientmod.getLastNumber(), "Philipp");
	 clientmod.updateLast(aclient); 
         Assert.assertEquals("Philipp", clientmod.loadData().get(0).getLastName());
      } catch (Exception e) {
          System.out.println("Test case client update failed");
      }
   } 

   @AfterClass
   public static void tearDown() {
      try {
         clientmod.closeConnection();
      } catch (Exception e) {
         System.out.println("connection not closed properly");
      }
   }
}

