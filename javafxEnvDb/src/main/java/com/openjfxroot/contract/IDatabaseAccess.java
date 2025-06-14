package com.openjfxroot.contract;

public interface IDatabaseAccess { // extends interface in the future
	/**
         * 
         * @return URL
         */
	String getUrl();

	/**
         * 
         * @return URL JDBC
         */
        String getJdbcUrl();

     	/**
         * 
         * @return User
         */
        String getDBusername();
 
	/**
         * 
         * @return Password
         */
	String getDBpassword();
 
	/**
         * 
         * @return port
         */
	int getPort();
 
	/**
         * 
         * @return Le Driver JDBC
         */
	String getClassDriver();
 

}


