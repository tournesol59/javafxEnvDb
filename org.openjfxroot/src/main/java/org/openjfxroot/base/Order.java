package org.openjfxroot.base;

import org.openjfxroot.base.OEnum;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Order {

   private int id;
   private int client_id;
   private int user_id;
   private final StringProperty message;
   private OEnum delivery;
	
   public Order(int id, String message) {
      this.id = id;
      this.message = new SimpleStringProperty(message);
   }
   
   public Order(int id, int client_id, String message) {
	      this.id = id;
	      this.client_id = client_id;
	      this.message = new SimpleStringProperty(message);
	}
   
	public Order(int id, int client_id, String orderMessage, int user_id, String delivery) {
		super();
		this.id = id;
		this.client_id = client_id;
		this.message = new SimpleStringProperty(orderMessage);
		this.user_id = user_id;
		this.delivery = OEnum.parse(delivery);
	}
	
	public int getId() {
		return this.id;
	}

   public void setId(int id) {
      this.id = id;
   }
   
   public final int getClient_id() {
	return client_id;
   }

   public final void setClient_id(int client_id) {
	this.client_id = client_id;
   }

   public StringProperty getMessageProperty() {
	      return this.message; 
   }

   public String getMessage() {
      return message.get();
   }

   public void setMessage(String msg) {
      this.message.set(msg);
   }
   
	//---new
   public final int getUser_id() {
	  return user_id;
   }

   public final void setUser_id(int user_id) {
	  this.user_id = user_id;
   }
   
	public final OEnum getDelivery() {
		return delivery;
	}

	public final void setDelivery(String delivery) {
		this.delivery = OEnum.parse(delivery);
	}
}
