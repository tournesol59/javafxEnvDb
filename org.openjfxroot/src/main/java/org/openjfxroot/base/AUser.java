package org.openjfxroot.base;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class AUser {

   protected String usertype;
   private int id;
 
   public abstract String getUserType();

   public abstract void setUserType(String usertype);
}
