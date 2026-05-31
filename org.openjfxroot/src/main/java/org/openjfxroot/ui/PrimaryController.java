package org.openjfxroot.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import org.openjfxroot.App;
import org.openjfxroot.ClientCellFactory;
import org.openjfxroot.base.Client;
import org.openjfxroot.base.ClientModelDB;
import org.openjfxroot.base.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class PrimaryController implements Initializable {

	@FXML
	private VBox root;
	
	@FXML
	private Label firstNameLabel;
	
	@FXML
	private TextField firstNameInput;
	
	@FXML
	private Label lastNameLabel;
	
	@FXML
	private TextField lastNameInput;
	
	@FXML
	private Label titleLabel;
	
	@FXML
	private ComboBox<String> titleComboBox;
	
	@FXML
	private Button addCliButton;
	
	@FXML
	private ListView<Client> clientLV;
	
	private ObservableList<Client> clientObsvList;
	
	private ClientModelDB clientModel;
	
	// --- fred: this encapsulated way of programming shall replace in the future the static calls to App.setRoot(..)
	public static PrimaryController newInstance() {
		FXMLLoader loader = new FXMLLoader(
				PrimaryController.class.getResource("primary.fxml"));
		try {
			loader.load();
			return loader.getController();
		} catch (IOException ex) {
			System.out.println("severe: primary fxml not loaded: "+ex.getMessage());
			return null;
		}
	}
	// ---
	public VBox getRoot() {
		return root;
	}
	
	public void initialize(URL url, ResourceBundle rb) {
		clientModel = ClientModelDB.getInstance();
   	
        //final ComboBox titleComboBox = new ComboBox();
        titleComboBox.getItems().addAll(
            "Mr.",
            "Miss.",
            "Sir",
            "Doctor",
            "Professor"
        );   

        titleComboBox.setValue("Mr.");
        /*
	    List<Client> clientList = new ArrayList<Client>();
	    clientList.add(new Client(1, "Georges", "Eddington", "test1"));
	    clientList.add(new Client(2, "Georges", "Muir", "test2"));
	    
	    // fred a tester
	    clientLV.setOnEditCommit( e -> {});
		clientObsvList = FXCollections.observableArrayList(clientList);
    	// for the moment just a test of a call to a method of ClientModelDB
        */
	    clientObsvList = clientModel.loaddata();

	    clientLV.setCellFactory(new ClientCellFactory());
		clientLV.setItems(clientObsvList);
	}
	
	@FXML
	public void addNewClient() throws IOException {
		if ((firstNameInput.getText() != "") && (lastNameInput.getText() != "")) {
	    	Client aclient = new Client(1, firstNameInput.getText(),
	    			lastNameInput.getText(), "test");
		   	System.out.println("values to prompt: "+aclient.getFirstName()+", "+aclient.getLastName());
		   	clientObsvList.add(aclient);
		   	clientModel.saveClient(aclient);
		//clientModel.compareAndSaveStub( clientObsvList );
		}
	}
	
    @FXML
    private void switchToSecondary() throws IOException {
// fred: cette partie doit etre bien revue avant de tester: cas de dépendance entre controller et modeles de vue differente
    	Client selectClient = clientObsvList.get(clientLV.getSelectionModel().selectedIndexProperty().get());

    	SecondaryController secondaryCtlr = SecondaryController.newInstance(selectClient);
    	System.out.println("secondary controller new instanciated");
    ///secondaryCtlr.getOrderModel().setSelectClient(selectClient);  // on essaie de passer selectClient dans le premier constructor	
    	App.setRootInstance(secondaryCtlr.getRoot());
    	
    	// fred: 27.05.2026 try to set
        //System.out.println("before switching to thirdly: clientid="+selectClient.getId()+", client last name="+selectClient.getLastName());
    	//ThirdlyController thirdlyCtlr = ThirdlyController.newInstance(selectClient);
    	//System.out.println("thirdly controller new instanciated");
    	//App.setRootInstance(thirdlyCtlr.getRoot());
    	
    }
    
}
