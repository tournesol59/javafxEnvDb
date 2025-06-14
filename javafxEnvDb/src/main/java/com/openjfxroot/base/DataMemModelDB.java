package com.openjfxroot.base;

import com.openjfxroot.contract.*;
import java.io.IOException;
import java.sql.*;

public class DataMemModelDB {

      
   private String JDBC_SOURCE="testdb"; // to be replaced by an objet - implementation of contract methods getJdbcURL()
   private IDatabaseAccess JDBC_ACCESS;
   protected boolean inMemoryDB;
   protected Connection connect;

   public static DataMemModelDB INSTANCE;

   // make constructor protected to avoid more class instance than a singleton
   protected DataMemModelDB() {
      super();
      // !!! Here the single place of code where the selection is done !!!
      this.JDBC_ACCESS = new DatabaseMySQLAccess();
      //this.JDBC_ACCESS = new DatabaseH2Access();
      this.inMemoryDB = false;
   }

   public static DataMemModelDB getInstance() {
      if (INSTANCE == null) {
         INSTANCE = new DataMemModelDB();
      }
      return INSTANCE;
   }

   public Connection getConnection() throws Exception {
     if (connect == null) {
	// System.out.println("data in-mem model, before creating the connection at init");
      // create a connection to a full in memory test db
      //         connect = DriverManager.getConnection("jdbc:h2:mem:test");
         //System.out.println("url: "+JDBC_ACCESS.getJdbcUrl() + ", user: "+JDBC_ACCESS.getDBusername()+", passwd: "+JDBC_ACCESS.getDBpassword());

         connect = DriverManager.getConnection( JDBC_ACCESS.getJdbcUrl(), JDBC_ACCESS.getDBusername(), JDBC_ACCESS.getDBpassword() );
	 System.out.println("data in-mem model: connection success");
     }
     return connect;
   }

   public void closeConnection() throws Exception {
       if (connect != null) {
           connect.close();
       }
   }

}
