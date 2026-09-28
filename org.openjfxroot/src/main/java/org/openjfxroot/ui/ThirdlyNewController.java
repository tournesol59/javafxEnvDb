package org.openjfxroot.ui;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.beans.value.ObservableValue;

import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

public class ThirdlyNewController implements Initializable {

   @FXML
   private Label lblSelection;

   @FXML
   private TextField messageInput;

   @FXML
   private Label lblIndex;

   @FXML
   private Label lblClientName;

   @FXML
   private Label lblClientDisp;

   @FXML
   private Label lblUserName;

   @FXML
   private ComboBox cboxUserName;

   List<String> listOptionsUser = new ArrayList<String>();
   
   ObservableList<String> optionsUser;

   @Override
   public void initialize(URL url, ResourceBundle rb) {
      listOptionsUser.add("Blake");
      listOptionsUser.add("Mortimer");
      optionsUser = FXCollections.observableArrayList(listOptionsUser);

      cboxUserName = new ComboBox(optionsUser);
      // fred TBC
   }

}
