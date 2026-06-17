package org.openjfxroot.ui;

import org.openjfxroot.App;
import org.openjfxroot.UserCellFactory;
import org.openjfxroot.base.Client;
import org.openjfxroot.base.User;
import org.openjfxroot.base.UserModelDB;
import org.openjfxroot.base.AppModelVLook;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import javafx.scene.control.ListView;
import java.util.List;
import java.util.ArrayList;

public class SelectController {
	
	public static SelectController INSTANCE;
	
	@FXML
	private VBox root;
	
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
	
	// --- fred: this encapsulated way of programming shall replace in the future the static calls to App.setRoot(..)
	public static SelectController newInstance() {
		FXMLLoader loader = new FXMLLoader(
				SelectController.class.getResource("select_user.fxml"));
	if (INSTANCE == null) {
		try {
    		INSTANCE = new SelectController();
       		loader.setController(INSTANCE);
       		loader.load();
       		return INSTANCE;
		} catch (IOException ex) {
			System.out.println("severe: select_user fxml not loaded: "+ex.getMessage());
			return null;
		}
	} else {
		try {
			//INSTANCE.userModel = OrderModelDB.getInstance();
			loader.setController(INSTANCE);
			System.out.println("before loading select user view");
			loader.load();
			System.out.println("secondary view loaded");
			return INSTANCE;
		} catch (IOException ex) {
			System.out.println("severe: secondary fxml not loaded: "+ex.getMessage());
			ex.printStackTrace();
			return null;
		}	
	  }
	}
	
	// ---
	public VBox getRoot() {
		return root;
	}
	
    private static UserModelDB userModel;
    
    public static UserModelDB getUserModel() {
    // ca marche car le model est un singleton
    	return userModel;
    }
    public static void setUserModel() {
    // ca marche car le model est un singleton
    	if (userModel == null) {
    		userModel = UserModelDB.getInstance();
    	}
    }
    
    private static AppModelVLook modelVLook;
    public static void setModelVLook() {
    	if (modelVLook == null) {
    		modelVLook = AppModelVLook.getInstance();
    	}
    }
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
    	//App.setRoot("ui/primary");
       	PrimaryController primaryController = PrimaryController.newInstance();
       	System.out.println("primary controller new instanciated");
        primaryController.setClientModel();
       	System.out.println(" transmitted to thirdly view");
       	
       	App.setRootInstance(primaryController.getRoot());
    }
}
