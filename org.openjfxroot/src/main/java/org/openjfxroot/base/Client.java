package org.openjfxroot.base;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Client {

   private int id;

   private final StringProperty firstName;
   private final StringProperty lastName;
   private final StringProperty orderMessage;
   
   public Client(int id, String firstName, String lastName, String orderMessage) {
      this.id = id;
      this.firstName = new SimpleStringProperty(firstName);
      this.lastName = new SimpleStringProperty(lastName);
      this.orderMessage = new SimpleStringProperty(orderMessage);
   }

   public int getId() {
      return this.id;
   }

   public void setId(int id) {
      this.id = id;
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

   public final StringProperty getOrderMessageProperty() {
       return orderMessage;
   }

   public final String getOrderMessage() {
       return orderMessage.get();
   }

   public void setOrderMessage(String orderMessage) {
      this.orderMessage.set(orderMessage);
   }
}
