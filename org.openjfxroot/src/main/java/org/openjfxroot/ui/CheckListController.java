package org.openjfxroot.ui;

import org.openjfxroot.App;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import javafx.util.Callback;
// my own factories
import org.openjfxroot.base.Order;
import org.openjfxroot.OrderCellFactory;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.StackPane;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

public class CheckListController implements Initializable {
	
	@FXML
	private Button changeButton;
	
	@FXML
	private ListView<String> checkLV;
	
    @FXML
    private void switchToSelect() throws IOException {
        App.setRoot("select_user");
    }
    // code pour afficher une liste de checkbox bindee a des ObservableValue
    final Preferences prefs = Preferences.userNodeForPackage(Order.class);

   // final ResourceBundle bundle = ResourceBundle.getBundle("resources/test.string");
    final String[] keys = {
    	"file.overwrite.confirm",
    	"file.save.auto",
    	"load.last.file.on.open"
    };
    
    public void initialize(URL url, ResourceBundle rb) {
    	Map<String, ObservableValue<Boolean>> properties = new HashMap<>();
    /*
     * 	for (final String key : keys) {
     
    		// Valeur initiale
    		final BooleanProperty property = new SimpleBooleanProperty();
    		final boolean value = prefs.getBoolean(key, false);
    		property.setValue(value);
    	// sauvegarder la valeur en cas de modification
    		property.addListener((ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) -> {
    				prefs.putBoolean(key, newValue);
    		});
    	}
    */
    	String key1="file.overwrite.confirm";
		final BooleanProperty property1 = new SimpleBooleanProperty();
		final Boolean value1 = prefs.getBoolean(key1, false);
		property1.setValue(value1);
		property1.addListener((ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) -> {
			prefs.putBoolean(key1, newValue);
		});
		//...
    	// Permet de recuperer la propriete JavaFX associée à chaque clé.
    	final Callback<String, ObservableValue<Boolean>> propertyAccessor = (String key) -> properties.get(key);
    	// Permet d'afficher un label plus parlant à l'utilisateur.
    	/*
    	final StringConverter<String> labelConverter = new StringConverter<String>() {
    		
    			@Override
    			public String toString(String t) {
    				return bundle.getString(t);
    			}
    			
    			//On n'utilise pas cette méthode
    			@Override
    			public String fromString(String string) {
    				throw new UnsupportedOperationException("Not supported yet.");
    			}
    	};
    	*/
    	// initialiser la liste
    	checkLV = new ListView<>();
    	// Fabrique à cellules
    	checkLV.setCellFactory((ListView<String> p) -> {
    		final CheckBoxListCell<String> result = new CheckBoxListCell<>();
    		//result.setConverter(labelConverter);
    		result.setSelectedStateCallback(propertyAccessor);
    		return result;
    	});
    	checkLV.getItems().setAll(keys);
    }
}
