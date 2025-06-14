package com.openjfxroot.contract;

import com.openjfxroot.contract.IDatabaseAccess;

import java.lang.StringBuilder;

//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.beans.factory.annotation.Autowired;

//@Configuration
public class DatabaseHSQLAccess implements IDatabaseAccess{ // extends interface in the future
 
   private String jdbcdriver;
   private String server;
   private int port;
   private String databasename;
   private String dbusername;
   private String dbpassword;

   //@Autowired     
   public DatabaseHSQLAccess() {
	   // fred: there are two possibilities: file or server (commented even lines) access
	   // for server mode, you also have to start the server with one (default) database with files named "mydb.*" and the public name of ouvragesdb
      this.jdbcdriver = "jdbc:hsqldb:file:";
      // this.jdbcdriver = "jdbc:hsqldb:hsql:";
      this.server = "";
      //this.server = "//localhost
      this.port = 8082; // not used
      //this.port = 8082;
      this.databasename = "/Users/frede/hsqldb/ouvragesdb";
      //this.databasename = "/ouvragesdb";
      this.dbusername = "SA";
      this.dbpassword = "";
   }

   public String getClassDriver() {
       return "com.h2.jdbc.Driver";
   }

   public String getJdbcDriver() {
//       return "jdbc:h2://";
       return jdbcdriver;
   }

   public void setJdbcDriver(String jdbcdriver) {
       this.jdbcdriver = jdbcdriver;
   }

   public String getDatabaseName() {
//   return "ouvragesdb"
       return databasename;
   }

   public void setDatabaseName(String databasename) {
       this.databasename = databasename;
   }

   public String getServer() {  
       return server;
   }

   public void setServer(String server) {  
       this.server = server;
   }

   public int getPort() {
       return port;
   }

   public void setPort(int port) {
       this.port = port;
   }

   public String getDBusername() {
	return dbusername;
   }

   public void setDBusername(String dbusername) {
	this.dbusername = dbusername;
   }

   public String getDBpassword() {
        return dbpassword;
   }  

   public void setDBpassword(String dbpassword) {
        this.dbpassword = dbpassword;
   }  

   public String getUrl() {
       StringBuilder sb = new StringBuilder("localhost");       
       sb.append(":");
       sb.append("3306");
       sb.append("/");
       sb.append(this.getDatabaseName());
       return sb.toString();

   }

   public String getJdbcUrl() {
// jdbc:h2:~/instr_collection
//or if use in memory db: jdbc:h2:mem:instr_collection"
       StringBuilder sb = new StringBuilder("");
       sb.append(this.getJdbcDriver());
//       sb.append(":~/");
//       sb.append(":");
//       sb.append("3306");
//       sb.append("/");
       sb.append(this.getDatabaseName());
       return sb.toString();
   }

}
