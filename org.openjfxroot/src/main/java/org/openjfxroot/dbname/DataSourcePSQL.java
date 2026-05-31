package org.openjfxroot.dbname;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataSourcePSQL implements IDataSource {
    public Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost/clientdb", "postgres", "postgres");
           return connection;
      } catch (Exception ex) {
            System.out.println("error");
           return null;
      }
   }
}
