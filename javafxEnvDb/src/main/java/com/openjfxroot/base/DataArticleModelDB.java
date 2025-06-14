package com.openjfxroot.base;

import java.io.IOException;
import java.sql.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import java.util.List;
import java.lang.Iterable;
import java.util.Iterator;
import java.util.ListIterator;

public class DataArticleModelDB extends DataMemModelDB {
   private String JDBC_SOURCE="ouvragesdb";
   private Connection connect;
   public static DataArticleModelDB INSTANCE;

   protected DataArticleModelDB() {
     super();
   }

   public void createMemTable() {
      try {
	 System.out.println("article : attempt to connect to H2 in memory database");
	 connect = this.getInstance().getConnection();
         System.out.println("Connected to H2 in memory database.");
	 
         Statement stmt = connect.createStatement();

         stmt.execute("CREATE IF NOT EXIST TABLE tblarticle(id INT PRIMARY KEY, title VARCHAR(40))");
	System.out.println("Created table tblarticle");

	 int rows = stmt.executeUpdate("INSERT INTO tblarticle VALUES (1, 'The Lords of the Ring T1') ");

	 if (rows > 0) {
            System.out.println("Inserted a new row into tblclient");
	 }

      } catch (Exception e) {
           System.out.println("An error occured when creating the tblclient at beginning");
      }
   }
   
   public static DataArticleModelDB getInstance() {
      if (INSTANCE == null) {
         INSTANCE = new DataArticleModelDB();
      }
      if ( INSTANCE.inMemoryDB == true ) {
         INSTANCE.createMemTable();
      }
      return INSTANCE;
   }

   protected int number=0;

   public ObservableList<Article> listArticles = FXCollections.observableArrayList();
    
   public ObjectProperty<Article> currentArticle = new SimpleObjectProperty<>(null);
   /*
   public Connection getConnection() throws Exception { 
      if (connect == null) {
         connect = DriverManager.getConnection("jdbc:h2:~/"+JDBC_SOURCE, "sa", "");
      }
      return connect;
   }
   */

    // initialize with populating the list from a data sourceextern H2database 
    public void loadData() {
       ResultSet items; 
       try {
          connect = getConnection(); 
          PreparedStatement pstmt = connect.prepareStatement("SELECT * FROM tblarticle");
          items = pstmt.executeQuery();
          while (items.next()) { 
             listArticles.add(new Article(items.getString(1)) );
             // here modify your code as long as new props are inserted
          } 
       } catch (Exception e) {
          System.out.println("error reading from table tblarticle of database ");
       }
    }

    public Article find(String title)  {
      // this function do a query for an Article by title in db 
       ResultSet items;
       Article article=null;
       String foundtitle_db="";
       int rowcount=0;
       ListIterator<Article> listIterator = this.getListArticles().listIterator(); 
       try {
          connect = getConnection();
          PreparedStatement pstmt = connect.prepareStatement("SELECT * FROM tblarticle");
          items = pstmt.executeQuery();
          while (items.next()) {
            //article_iter = listIterator.next();
            foundtitle_db = items.getString(1);
	    rowcount++;
            if (foundtitle_db != title) { // 
               //continue;
            }
            else {
	       // the id of the object article is based on the order of appearance in the query, and hence in the obsvlist, not on the primary key 
               article = new Article(rowcount, foundtitle_db);
               break;
            }
          } 
          return article;
       } catch (Exception e) {
          System.out.println("error reading from table tblarticle of database ");
          return null;
       }
   }

    public void updatearticle(String title_old, Article article_new) {
       int rowid=0;
       ResultSet result;
       String foundtitle=null;
       try {
          connect = getConnection();
	  PreparedStatement pstmt = connect.prepareStatement("SELECT * FROM tblarticle");
	  result = pstmt.executeQuery();
	  while (result.next()) {
	     if (foundtitle != result.getString(1)) { // not found, increase id
                rowid++; // not to be used since primary key can be different
	     }
	     else { // found !
		rowid = result.getInt(1); // yes
		result.close();
                break;
	     }
	  }
       } catch (Exception e) {
          System.out.println("Search operation for article id in oder to update has failed");
       }
       // now that we have an id we can update
       try {
	   connect = getConnection();
           PreparedStatement pstmt=connect.prepareStatement("SET (id, title) INTO tblarticle VALUES (?,?) WHERE id=?");
	   pstmt.setInt(1,rowid);
	   pstmt.setString(1,article_new.getTitle());
	   pstmt.setInt(2,rowid);
	   pstmt.executeUpdate();

       } catch (Exception e) {
          System.out.println("Update operation of article, id="+rowid+" has failed");
       }
    }

    public int comparelist()  {
    // this function checks if the content of the ObservableList was not changed since import from the database but does not persist for the moment
       ResultSet items;
       Article article_iter;
       Article article;
       String foundtitle_iter="";
       String foundtitle_db="";
       int rowcount=0;
       int rowcount_iter=0;
       ListIterator<Article> listIterator = this.getListArticles().listIterator(); 
       try {
          connect = getConnection(); 
          PreparedStatement pstmt = connect.prepareStatement("SELECT * FROM tblarticle");
          items = pstmt.executeQuery();
          if (items.last()) {
             rowcount = items.getRow();
             items.beforeFirst(); // we need to replace the cursor before first, not at items.first();
          }
          while (items.next() && listIterator.hasNext()) {
             foundtitle_db = items.getString(1); 
             // check if the observable list does not correspond to database
             article_iter = listIterator.next();
             foundtitle_iter = article_iter.getTitle();
             if (foundtitle_iter == foundtitle_db) {
                listIterator.next(); 
                rowcount_iter++;
                continue;
             } else {
		     // update operation from modified data in observablelist
		updatearticle(foundtitle_db, article_iter); // see above
                //break;
             } 
          }
       } catch (Exception e) {
          System.out.println("error reading from table tblarticle of database ");
       }
 
       if (rowcount != rowcount_iter) {
          System.out.println("error observable list does not correspound to database");
          return -1;
          // we do nothing for the moment
       } else {
          return 0;
       }
    } 

    public ObservableList<Article> getListArticles() {
        return listArticles ;
    }
 
 
}

