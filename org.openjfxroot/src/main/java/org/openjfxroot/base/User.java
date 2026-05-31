package org.openjfxroot.base;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User extends AUser {

   private int id;
   //private String lastName;
   //private int numOfArticles;  // 0 or 1

   private String usertype;
   private final StringProperty lastName;
   private final StringProperty contactEmail;

   private final StringProperty contactPostal;

   //private IUserService userConcreteService;

   public User(int id, String usertype, String lastName, String contactEmail, String contactPostal) {
      this.id = id;
      this.usertype = usertype;
      this.lastName = new SimpleStringProperty(lastName);
      this.contactEmail = new SimpleStringProperty(contactEmail);
      this.contactPostal = new SimpleStringProperty(contactPostal);
   }

   public User(String usertype, String lastName, String contactEmail, String contactPostal) {
      this.usertype = usertype;
      this.lastName = new SimpleStringProperty(lastName);
      this.contactEmail = new SimpleStringProperty(contactEmail);
      this.contactPostal = new SimpleStringProperty(contactPostal);
   }
   /*
   public User(int id, String usertype, String lastName, IUserService userConcreteService, String contactEmail, String contactPostal) {
      this.id=id;
      this.usertype = usertype;
      this.lastName = new SimpleStringProperty(lastName);
      this.userConcreteService = userConcreteService;
      this.contactEmail = new SimpleStringProperty(contactEmail);
      this.contactPostal = new SimpleStringProperty(contactPostal);
   }
   */
   public int getId() {
      return this.id;
   }

   public String getUserType() {
      return this.usertype;
   }

   public void setUserType(String usertype) {
     this.usertype = usertype;
   } 

   /*
   public void setUserConcreteService(IUserService userConcreteService) {
     this.userConcreteService = userConcreteService;
   } 
*/
   
   public final String getLastName() {
	  return this.lastName.get();
   }
   
   public String getContactEmail() {
      return this.contactEmail.get();
   }

   public StringProperty getContactEmailProperty() {
      return this.contactEmail; 
   }

   public void setContactEmail(String stringEmail) {
      this.contactEmail.set(stringEmail);
   }

   public String getContactPostal() {
      return this.contactPostal.get();
   }

   public StringProperty getContactPostalProperty() {
      return this.contactPostal; 
   }

   public void setContactPostal(String stringPostal) {
      this.contactPostal.set(stringPostal);
   }

}
