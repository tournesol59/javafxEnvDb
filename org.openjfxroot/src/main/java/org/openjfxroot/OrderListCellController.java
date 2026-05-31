package org.openjfxroot;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;

public class OrderListCellController implements Initializable {
	// manual arrangement within an HBox
	private HBox container;
	private Label label;
	//private Button buttonModify;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		label = new Label("");
		//buttonModify = new Button("Modify");
		container = new HBox(label); //, buttonModify);
		
		/*
		 * buttonModify.setOnAction(event -> {
		 
	        final Application application = getApplication();
	        final String value = getValue();
			if (value != null) {
				//Order order = getItem();
				label.setText(value);
			}
		});
		*/
	}
	
	public Node getUI() {
		return (Node) this.container;
	}
	/**
	 * point dentree pour avoir la nouvelle valeur entree par l'utilisateur
	 */
	private final ChangeListener<String> valueChangeListener = (ObservableValue<? extends String>
			observableValue, String oldValue, String newValue) -> {
				updateUI(newValue);
			};
	
	// cette methode met a jour le champ du label
	public void updateUI(String newValue) {
		label.setText(newValue);
	}
    /**
    * Contient la valeur à afficher.
    */
    private final StringProperty value = new SimpleStringProperty(this, "value");

    public final String getValue() {
        return value.get();
    }

    public final void setValue(String value) {
        this.value.set(value);
    }

    public final StringProperty valueProperty() {
        return value;
    }
    /**
    * Contient une référence vers l'application parente.
    */
    private final ObjectProperty<Application> application = new SimpleObjectProperty<>(this, "application");

    public final Application getApplication() {
        return application.get();
    }

    public final void setApplication(Application value) {
        this.application.set(value);
    }

    public final ObjectProperty<Application> applicationProperty() {
        return application;
    }
}
