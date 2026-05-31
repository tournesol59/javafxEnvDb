package org.openjfxroot.ui;

import org.openjfxroot.App;
import org.openjfxroot.base.Order;
import org.openjfxroot.base.User;
import org.openjfxroot.base.Client;
import org.openjfxroot.base.OrderModelDB;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
// this is the most important interface for feature we are demonstrating in this view
//import javafx.collections.ListChangeListener; // NO!
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.scene.control.cell.TextFieldListCell;
//import org.openjfxroot.OrderTextFieldListCell;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

public class SecondaryController implements Initializable {
	// this controller shall contains the initialisation for the Secondary.fxml view
	// that is used only for demo purpose of a ListView item access by selection

	// singleton
	public static SecondaryController INSTANCE;
	
	@FXML
	private VBox root;

	@FXML
	private ListView<String> notesLV;
	
	private ObservableList<String> obsvNotesList;
	
	@FXML
	private Label lblIndex;
	
	@FXML
	private Label lblSelection;
	
	@FXML
	private TextField messageInput;
    
 // --- fred: this encapsulated way of programming shall replace in the future the static calls to App.setRoot(..)
    public static SecondaryController newInstance(Client client) {
    	FXMLLoader loader = new FXMLLoader(
    			SecondaryController.class.getResource("secondary.fxml"));
       if (INSTANCE == null) {
    	try {
    		loader.load();
    		System.out.println("secondary view loaded");
    		INSTANCE = loader.getController();
    		INSTANCE.orderModel = OrderModelDB.getInstance();
    		INSTANCE.orderModel.setSelectClient(client);
    		return INSTANCE;
    	} catch (IOException ex) {
    		System.out.println("severe: secondary fxml not loaded: "+ex.getMessage());
    		return null;
    	}		
       }
       else return INSTANCE;
    }
    // ---
	public VBox getRoot() {
		return root;
	}
	
    protected static OrderModelDB orderModel;
    
    public static OrderModelDB getOrderModel() {
    // ca marche car le model est un singleton
    	return orderModel;
    }
    public static void setOrderModel() {
    // ca marche car le model est un singleton
    	if (orderModel == null) {
    		orderModel = OrderModelDB.getInstance();
    	}
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    // fred: here implement TBC the custom ListView
	    //
	    //
    	orderModel = OrderModelDB.getInstance();
    	List<Order> notesList = orderModel.getAllOrders();
    	obsvNotesList = FXCollections.observableArrayList();
    	for (Order order : notesList) {
    		obsvNotesList.add(order.getMessage());
    	}
	// for the moment just a String-type List()
    	
    	StringConverter<String> converter = new DefaultStringConverter();
        notesLV.setEditable(true);
        notesLV.setCellFactory(param -> new TextFieldListCell(converter));
   	//  notesLV.setCellFactory(new OrderCellFactory());

    	notesLV.setItems(obsvNotesList);
    	
    	notesLV.getSelectionModel().selectedItemProperty().addListener( new ChangeListener<String>() {
    		  @Override
    		  public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) 
    		  {
    			  System.out.println("current selection, old value="+oldValue+", new value="+newValue);   
    			  // copy the selected list content in the form view:
    			  String arg0=Integer.toString(notesLV.getSelectionModel().getSelectedIndex());
    			  lblIndex.setText(arg0);
    			  lblSelection.setText(newValue);
    			  messageInput.setText(newValue);
    		  }
    		  //or if not a redefinition, simply:
    		  //System.out.println("current selection, old value="+oldValue+", new value="+newValue);   
    	});
    }
    	

   @FXML
   public void submitNoteToList() throws IOException
   {
	  //notesLV.getSelectionModel().getSelectedIndex();
	   int index = Integer.parseInt(lblIndex.getText());

	   notesLV.getItems().add(index, messageInput.getText());
	   // fred: remove after because if it was done before, the focus would have replaced the +1 index to erase the content
	   // of the form and created a duplicate of record(index+1)
	   notesLV.getItems().remove(index+1);
   }
    
   @FXML
   private void switchToThirdly() throws IOException {
       // App.setRoot("thirdly");
   	ThirdlyController thirdlyCtlr = ThirdlyController.newInstance(this.getOrderModel().getSelectClient());
   	System.out.println("thirdly controller new instanciated");
   	thirdlyCtlr.setOrderModel();
   //	thirdlyCtlr.getOrderModel().setSelectClient(this.getOrderModel().getSelectClient());
   	// pas besoin car OrderModel est une unique instance
   	System.out.println("selectClient transmitted to thirdly view");
   	App.setRootInstance(thirdlyCtlr.getRoot());
   	
   }
   
}
