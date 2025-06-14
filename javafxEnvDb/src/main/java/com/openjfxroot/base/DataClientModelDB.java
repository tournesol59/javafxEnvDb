package com.openjfxroot.base;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.lang.Iterable;
import java.util.Iterator;
import java.util.ListIterator;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class DataClientModelDB extends DataMemModelDB {
   
   private String JDBC_SOURCE="ouvragesdb";
   protected Connection connect;
   public static DataClientModelDB INSTANCE;
   private int lastnumber=0;
   private ObservableList<Client> obsvListClient = FXCollections.observableArrayList();

// make constructor protected to avoid more class instance than a singleton
   protected DataClientModelDB() {
      super();
   }

   public void createMemTable() {
      try {
        System.out.println("client : attempt to connect to H2 in memory database");
         connect = this.getInstance().getConnection();

         System.out.println("Connected in memory database.");
	 
         Statement stmt = connect.createStatement();

         stmt.execute("CREATE IF NOT EXIST TABLE tblclient(id INT PRIMARY KEY, last_name VARCHAR(40))");
	 System.out.println("Created table tblclient");

	 int rows = stmt.executeUpdate("INSERT INTO tblclient VALUES (1, 'Bradley') ");
         
	 if (rows > 0) {
            System.out.println("Inserted a new row into tblclient");
	 }

      } catch (Exception e) {
           System.out.println("An error occured when creating the tblclient at beginning");
      }

   }

   public static DataClientModelDB getInstance() {
      if (INSTANCE == null) {
         INSTANCE = new DataClientModelDB();
      }
      if ( INSTANCE.inMemoryDB == true ) {
         INSTANCE.createMemTable();
      }
      try {
         List<Client> listInit = INSTANCE.loadData(); // we dont need the list, it is just to actualize lastnumber at init from database
      } catch (Exception e) {
         System.out.println("could not load at the init");
      }
      return INSTANCE;
   }
 
   public int getLastNumber() {
      return this.lastnumber;
   }

   public ObservableList<Client> getObsvListClient() {
      return this.obsvListClient;
   }
  /*
   public Connection getConnection() throws Exception {
      if (connect == null) {
      // create a connection to a full in memory test db
         connect = DriverManager.getConnection("jdbc:h2:~/"+JDBC_SOURCE, "sa", "");
      }
      return connect;
   }
  */
   
   public List<Client> loadData() {
   // this method populates a list and read the max row id. It shall be called at instanciation
      ResultSet items;
      List<Client> listClient = new ArrayList<Client>();
      try {
         connect = getConnection();
	 System.out.println("loadData Clients, connected to db");
         PreparedStatement pstmt = connect.prepareStatement("SELECT id, last_name FROM tblclient");
         items = pstmt.executeQuery();
	 System.out.println("loadData Clients, query success");
         while (items.next() ) {
            Client aClient = new Client( items.getString(1) ); // getString(2) and not one ...
            listClient.add( aClient );
	    this.lastnumber = items.getInt(1); // overwritten at each iteration
         }
         return listClient;
      } catch (Exception e) {
         System.out.println("loadData Clients, error during connection or query");
	 e.printStackTrace();
         return null;
      }
   }

   public void loadDataObsv() {
      // a bit boilerplate and redundant code, but this is history.
      List<Client> listClient = null;
      Iterator<Client> clientIterator;
      try {
         listClient = this.loadData();
	 clientIterator = listClient.iterator();
	 while (clientIterator.hasNext() ) {
            Client aClient = clientIterator.next();
            obsvListClient.add( aClient );
	 }
	 
      } catch (Exception e) {
         System.out.println("load Clients into ObsvervableList, something went wrong");	 
      }
   }

   public void updateLast(Client aClient) {
      try {
         connect = getConnection();
	 System.out.println("update last client, connected");
	 PreparedStatement pstmt = connect.prepareStatement("UPDATE tblclient SET last_name = ? WHERE id=?");
         pstmt.setString(1, aClient.getLastName() );
	 //this.lastnumber++;  // NO this is required only for new entries
	 pstmt.setString(2, Integer.toString(aClient.getId()) ); // getInt(1, aClient.getId()) nok
	 pstmt.executeUpdate();
	 System.out.println("update last client, operation success");
	 //return lastnumber;// no! preview a getter
      } catch (Exception e) {
	 System.out.println("update last Client, something went wrong");
      }
   }
}

