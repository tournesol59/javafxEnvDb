package org.eclipse.jxmlsamp;

import org.eclipse.jxmlsamp.model.*;
import java.io.IOException;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
//import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;

//import javafx.beans.property.IntegerProperty;
//import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.ListCell;

import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//import javafx.collections.ListChangeListener; // NO!
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.beans.value.ObservableValue;



public class PrimaryChoiceController implements Initializable {

    @FXML
    private Label lblId;

    @FXML
    private Label lblFirstName;
    
    @FXML
    private Label lblLastName;
    
    @FXML
    private Label lblIdDisp;
       
    @FXML
    private TextField inputFirstName;

    @FXML
    private TextField inputLastName;
    
    @FXML
    private Button btnSubmitUser;
    
    @FXML
    private ListView<User> usersLV;
    
    private List<User> listUsers = new ArrayList<>(); 

    private ObservableList<User> obsvListUsers = FXCollections.observableArrayList();
    
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    /**
     * Initialisation du contrôleur.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // until now the values of the list that should be imported from xml
        // are set from fixed hard-coded values
        listUsers.add(new User("Michel", "Benoit", "ingenieur"));
        listUsers.add(new User("Labrosse", "Fabrice", "ingenieur"));
        lblIdDisp.setText("2");
        // set the Observable list fixed, but in the future it shall be populated from a xml parser module
        obsvListUsers.setAll(listUsers);
        // then do the binding to the ListView Object for display
        usersLV.setItems(obsvListUsers);
        
        usersLV.setEditable(true);
        StringConverter<String> converter = new DefaultStringConverter();     
        usersLV.setCellFactory(param -> new UserListCell(converter));

        // last but not least: bind notesLV display to ObservableList
        usersLV.setItems(obsvListUsers); 
        
        usersLV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
           @Override
           public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
               //info
               System.out.println("current selection, old value="+oldValue.getNom()+", new value"+newValue.getNom());
               String arg0 = Integer.toString(usersLV.getSelectionModel().getSelectedIndex());
               lblIdDisp.setText(arg0);
               inputLastName.setText(newValue.getNom());
               inputFirstName.setText(newValue.getPrenom());
           }
        });
    }

    // routine to add a new User from Form inputs to be displayed in the usersLV ListView
    public void addorUpdateUser() throws IOException {
        int index = Integer.parseInt(lblIdDisp.getText());
        if (index >= obsvListUsers.size()) {
            User addedUser = new User(inputLastName.getText(), inputFirstName.getText(), "ingenieur");
            obsvListUsers.add(addedUser);
            index = index+1;
            lblIdDisp.setText(Integer.toString(index));
        } else {
            User updUser = obsvListUsers.get(index);
            updUser.setNom(inputLastName.getText());
            updUser.setPrenom(inputFirstName.getText());
            //obsvListUsers.set(index, updUser);
            //obsvListUsers.remove(index+1); // pushed with-old-values item
            usersLV.getItems().add(index, updUser);
            usersLV.getItems().remove(index+1);
            index = obsvListUsers.size();
            lblIdDisp.setText(Integer.toString(index));
        }
    }
}
