package org.openjfxroot.dbname;

import java.sql.*;

public class DataSourceH2 implements IDataSource {
	
    public Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:mem:clientdb", "SA", "");
           return connection;
      } catch (Exception ex) {
            System.out.println("error");
           return null;
      }
   }
}
