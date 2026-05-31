package org.openjfxroot.dbname;

import java.sql.Connection;

public interface IDataSource {
    public Connection getConnection();
}
