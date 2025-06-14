package com.openjfxroot;

import com.openjfxroot.base.Client;
import com.openjfxroot.base.Article;
import com.openjfxroot.ClientModel;
import com.openjfxroot.ArticleModel;
import com.openjfxroot.base.DataClientModelDB;
import com.openjfxroot.base.DataArticleModelDB;

import java.io.IOException;

import javafx.util.Callback;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory; 
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.control.ListCell;
import javafx.collections.ListChangeListener;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

public class CreateSchemeArtView extends VBox {
        // controller dependency
	final CreateSchemeArtController createSchemeArtController;
        final ClientModel clientModel;
        // DataModel has only static methods but is public
        public DataClientModelDB dataClientModel;

	//**** change listview location inside a model class 
        //private ListView<Person> list = new ListView<>();
        // rows, obsv list is created in the datasource/datamodel class, static
        //protected ObservableList<Person> rows = FXCollections.observableArrayList();
        //****
	// fred: after first test change String into <Person>

        HBox hboxtitle = new HBox();
        Button alertbtn = new Button("info last value");
	Label info = new Label("List read of Persons ");

	HBox hboxlabelname = new HBox();
	Label firstNameLabel = new Label("First Name");
        Label lastNameLabel  = new Label("Last Name");

	HBox hboxname = new HBox();
        Button savebtn = new Button("update new value");
        TextArea inputFirstName = new TextArea();
        TextArea inputLastName = new TextArea();

	public CreateSchemeArtView(CreateSchemeArtController createSchemeArtController, ClientModel clientModel) {
// append another argument to this constructor, PersonModel, when it will be created
	    this.createSchemeArtController = createSchemeArtController;
            this.clientModel = clientModel;
	    initCreateScheme();
	}

        public CreateSchemeArtController getController() {
            return this.createSchemeArtController;
        }
/*
       public void setController(CreateSchemeArtController createSchemeArtController) 
	{
            this.createSchemeArtController = createSchemeArtController;
        }
*/
        public ClientModel getModel() {
            return this.clientModel;
        }
/*
        public void setModel(ClientModel clientModel) {
            this.clientModel = clientModel;
        }
*/	
	public void initCreateScheme() {
		/*
           hboxtitle.setLayoutX(0);
           hboxtitle.setLayoutY(0);

	   hboxtitle.setMaxWidth(640);
	   hboxtitle.setMaxHeight(480);
	
	   hboxlabelname.setLayoutX(0);
           hboxlabelname.setLayoutY(0);

	   hboxlabelname.setMaxWidth(640);
	   hboxlabelname.setMaxHeight(480);
           
	   hboxname.setLayoutX(0);
           hboxname.setLayoutY(0);

	   hboxname.setMaxWidth(640);
	   hboxname.setMaxHeight(480);
	   */
/******
	   rows.add( new Person("Mark", "Shaw"));
	   rows.add( new Person("Elisabeth", "Blackman"));
	   rows.add( new Person("Georges", "Bellingham"));
	
           list.setEditable(true); 
           list.setItems(rows);
****** */

/*****
// setCellFactory, static class version (verbious) and error at compilation:
// does not accept something after TextFieldListCell (illegal identifier) 
*****/    

/*
        // Parent pane groups definition 
        hboxtitle.getChildren().addAll(alertbtn, info);
	hboxlabelname.getChildren().addAll(firstNameLabel, lastNameLabel);
	hboxname.getChildren().addAll(savebtn, inputFirstName, inputLastName);
        
        this.getChildren().addAll(hboxtitle, list, hboxlabelname, hboxname); 
// error with the following
	final Stage primaryStage = createSchemeController.getPrimaryStage();
	final Scene scene = new Scene(new StackPane(this), 640, 480);
	primaryStage.setScene(scene);
	*/

    }
/*
    private void showPersonDetails(Person person) {
       if (person !=null) {
          firstNameLabel.setText(person.getFirstName());
	  lastNameLabel.setText(person.getLastName());
       } else {
          // Person is null, remove all the text
	  firstNameLabel.setText("");
	  lastNameLabel.setText("");
       }
    }
*/
}
