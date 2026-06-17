package org.openjfxroot.ui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.openjfxroot.base.*;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OrderCellController implements Initializable  {
	
	// singleton
	public static OrderCellController INSTANCE;
	
	@FXML
	private VBox rootPane;
	
	@FXML
	private Label lblIndex;
	
	@FXML
	private Label indexDisp;
	
	@FXML
	private Label lblMessage;
	
	@FXML
	private TextField messageDisp;	

	@FXML
	private Label lblUser;

	@FXML	
	private ComboBox userComboBox;

	@FXML
	private Label lblDelivery;
	
	@FXML
	private Label deliveryDisp;
	
	public Parent getRoot() {
		return this.rootPane;
	}
	
    public static OrderCellController newInstance() {
    	FXMLLoader loader = new FXMLLoader(
    			ThirdlyController.class.getResource("thirdlyuser.fxml"));
      if (INSTANCE == null) {
    	try {
    		loader.load();
    		System.out.println("thirdlyuser view loaded");
    		INSTANCE = loader.getController();
    	} catch (IOException ex) {
    		System.out.println("severe: thirdlyuser fxml not loaded or controller is null: "+ex.getMessage());
    		return null;
    	}	
      }
	  return INSTANCE;
    }
      
	// for the moment set the content of the ComboBox with fixed value:
	private List<User> listUsers = null;
	
    private final ChangeListener<User> userChangeListener = new ChangeListener<User>() {
        @Override
        public void changed(ObservableValue<? extends User> observableValue, User oldValue, User newValue) {
            updateContent(newValue);
        }
    };
    
    protected void updateContent(User user) {
    	lblUser.setText((user == null)? "not selected" : user.getLastName());
    }
    
	@Override
	public void initialize(URL aurl, ResourceBundle rb) {
		this.lblIndex.setText("Index");	
		this.lblMessage.setText("Message");
		this.lblUser.setText("User");
		this.lblDelivery.setText("Delivery");
	
		User user1 = new User("E", "simon", "simono4@cti.ecp.fr", "");
		User user2 = new User("E", "thomas", "leautet4@cti.ecp.fr", "");
		listUsers = new ArrayList<>();
		listUsers.add(user1);
		listUsers.add(user2);
		
		userComboBox.getItems().addAll(listUsers);
	}
	
}