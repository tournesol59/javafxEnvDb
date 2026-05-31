package org.openjfxroot.ui;

import org.openjfxroot.App;
import org.openjfxroot.UserCellFactory;
import org.openjfxroot.base.Client;
import org.openjfxroot.base.User;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import javafx.scene.control.ListView;
import java.util.List;
import java.util.ArrayList;

public class SelectController {

	@FXML
	private Button button_add;

	@FXML
	private Button button_change;
	
	@FXML
	private TextField lastNameInput;
	
	@FXML
	private Label lastNameLabel;

	@FXML
	private TextField emailInput;
	
	@FXML
	private Label emailLabel;
	
	@FXML
	private TextField postalInput;
	
	@FXML
	private Label postalLabel;
	
	@FXML
	private ListView<User> userLV;
	
	private ObservableList<User> userObsvList;
	
	public void initialize() {
	    List<User> userList = new ArrayList<User>();
		userList.add(new User("E", "Blake", "francis.blake@gmail.com", "London City"));
		userList.add(new User("E", "Mortimer", "philip.mortimer@gmail.com", "Edinburgh"));
		
		userLV.setCellFactory(new UserCellFactory());
		userObsvList = FXCollections.observableArrayList(userList);
		userLV.setItems(userObsvList);
	}
	
	@FXML
	public void addNewUser() throws IOException {
		if (emailInput.getText() != "") {
	    	User auser = new User("E", lastNameInput.getText(),
				emailInput.getText(), postalInput.getText());
		   	System.out.println("values to prompt: "+auser.getContactEmail()+", "+auser.getContactPostal());
			userObsvList.add(auser);
		
		}
	}
	
    @FXML
    private void switchToPrimary() throws IOException {
    	App.setRoot("primary");
    }
}
