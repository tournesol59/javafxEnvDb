package com.openjfxroot;

import com.openjfxroot.base.Client;
import com.openjfxroot.base.DataClientModelDB;

public class ClientModel {

   private Client clientObj;

   private int clientCount; // pas un compteur mais un identificateur

// getters/setters
   public Client getClientObj() {
      return this.clientObj;
   }

   public void setClientObj(Client clientObj) {
      this.clientObj = clientObj;
   }

   public int getClientCount() {
      return this.clientCount;
   }

   public void setClientCount(int clientCount) {
      this.clientCount = clientCount;
   }

}

