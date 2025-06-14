package com.openjfxroot.base;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {

   private int id;
   //private String lastName;
   private int numOfArticles;  // 0 or 1

   private final StringProperty firstName;
   private final StringProperty lastName;

   public User(String firstName, String lastName) {
      this.firstName = new SimpleStringProperty(firstName);
      this.lastName = new SimpleStringProperty(lastName);
   }

   public StringProperty getLastNameProperty() {
      return this.lastName; 
   }

   public String getLastName() {
      return lastName.get();
   }

   public void setLastName(String lastName) {
      this.lastName.set(lastName);
   }

   public StringProperty getFirstNameProperty() {
      return this.firstName; 
   }

   public String getFirstName() {
      return firstName.get();
   }

   public void setFirstName(String firstName) {
      this.firstName.set(firstName);
   }

   public int getNumOfArticles() {
      return numOfArticles;
   }

   public void setNumOfArticles(int numOfArticles) {
      this.numOfArticles = numOfArticles;
   }

   public void incNumOfArticles() {
      this.numOfArticles = this.numOfArticles+1;
   }
}
