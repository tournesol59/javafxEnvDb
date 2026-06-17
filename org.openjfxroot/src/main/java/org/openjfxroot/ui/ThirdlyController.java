package org.openjfxroot.ui;

import org.openjfxroot.App;
import org.openjfxroot.ClientCellFactory;
import org.openjfxroot.OrderCellFactory;
import org.openjfxroot.base.Order;
import org.openjfxroot.base.User;
import org.openjfxroot.base.Client;
import org.openjfxroot.base.OrderModelDB;
import org.openjfxroot.base.User;
import org.openjfxroot.base.AppModelVLook;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import javafx.beans.value.ObservableValue;
import javafx.util.Callback;
import javafx.scene.control.ListCell;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.VBox;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.util.StringConverter;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;

// cette classe controleur affiche grosso modo la meme vue que Secondary, basee sur l entite "Order"
// mais avec une listView utilisant une fabrique pre construite
// !!! TO DO NEXT implementer la ComboBox utilisateur dans la partie edition et traiter nouvelle value, old value
// puis updateItem de la list view affichant aussi l'utilisateur maj.
public class ThirdlyController implements Initializable {
	
	// singleton
	public static ThirdlyController INSTANCE;
	
	@FXML
	private VBox root;

	public VBox getRoot() {
		return root;
	}

	@FXML
	private ListView<Order> notesLV;
	
	private ObservableList<Order> obsvNotesList;
	
	@FXML
	private Label lblSelection;
	
	@FXML
	private Label lblIndex;
	
	@FXML
	private Label lblClientName;
	
	@FXML
	private Label lblClientDisp;
	
	@FXML
	private TextField messageInput;

    public static ObservableList<Order> messagesList = FXCollections.observableArrayList();
	
    // --- fred: this encapsulated way of programming shall replace in the future the static calls to App.setRoot(..)
    public static ThirdlyController newInstance() {
    	FXMLLoader loader = new FXMLLoader(
    			ThirdlyController.class.getResource("thirdly.fxml"));
      if (INSTANCE == null) {
    	try {
    		loader.load();
    		System.out.println("thirdly view loaded");
    		INSTANCE = loader.getController();
    		// fred: between the command above and the command below, UI are created, controls are injected here and
    		// a call to initialize() is done: problem when INSTANCE global vars are used inside initialize
    		INSTANCE.orderModel = OrderModelDB.getInstance();
    		//INSTANCE.orderModel.setSelectClient(client); // to replaced with follows
    		INSTANCE.modelVLook = AppModelVLook.getInstance();
    		// the static way of calling was imposed below due to the fact that this class is self used as a singleton
    		OrderModelDB.setSelectClient(AppModelVLook.getSelectClient());
    		return INSTANCE;
    	} catch (IOException ex) {
    		System.out.println("severe: thirdly fxml not loaded or controller is null: "+ex.getMessage());
    		ex.printStackTrace();
    		return null;
    	}		
      }
      else return INSTANCE;
     }
    // ---    
	
	// fred: classe statique car utilisee seulement a linterieur de cette classe
	// a faire: rendre editable
	static class OrderRectCell extends ListCell<Order> {
		StringConverter<Order> converter=new StringConverter<Order>() {
			@Override
			public String toString(Order object) {
				return object.getMessage();
			}
			@Override
			public Order fromString(String name) {
				return new Order(12, name);
			}
		};
		@Override
		public void updateItem(Order item, boolean empty) {
			super.updateItem(item, empty);
			String style = null;
			if (item != null) {
				if (item.getClient_id() != 0) {
					// condition the style to be attached to a particular client
					style = "-fx-font-weight: bold; -fx-text-fill: skyblue; -fx-underline: true;";
				}
				setStyle(style);
				setText(item.getMessage());
			}
		}
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
    
    private static AppModelVLook modelVLook;
    public static void setModelVLook() {
    	if (modelVLook == null) {
    		modelVLook = AppModelVLook.getInstance();
    	}
    }

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// data for ListView in initialization
		if (orderModel == null) {
			System.out.println("Thirdly controller instanciation mistake in getting orderModel singleton");
			orderModel = OrderModelDB.getInstance(); // fred: this has caused a very bad to find bug	
		}
	///obsvNotesList = orderModel.loaddataByClient();  // fred  debug
		List<Order> notesList = orderModel.getAllOrdersByClient(); //modif of this func=> no args
		/**
    	Order order1 = new Order(1, 1, "try it out");
    	Order order2 = new Order(2, 1, "you have got a mail");
    	List<Order> notesList = new ArrayList<Order>();
    	notesList.add(order1);
    	notesList.add(order2);
    	*/
    	obsvNotesList = FXCollections.observableArrayList();
    	for (Order order : notesList) {
    		obsvNotesList.add(order);
    	}
    	
    	lblClientName.setText("Client ID/Name");
    	
    	lblClientDisp.setText(orderModel.getSelectClient().getLastName());
    	
	
		notesLV.setEditable(true); // ne produit pas l'effet escompté car on n'a pas indiqué de Converter
		/*
		 * fred: last, this code follows the same pattern as in PrimaryController
		 */
		notesLV.setCellFactory(new OrderCellFactory());
		
		// last but not least: bind notesLV display to ObservableList
		notesLV.setItems(obsvNotesList);

		notesLV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Order>() {
			@Override
			public void changed(ObservableValue<? extends Order> observable, Order oldValue, Order newValue)
			{
				if (oldValue != null) {
					System.out.println("current order selection: old message="+oldValue.getMessage()+
						", new message="+newValue.getMessage());
				}
				String arg0 = Integer.toString(notesLV.getSelectionModel().getSelectedIndex());
				lblIndex.setText(arg0);
				lblSelection.setText(newValue.getMessage());
				messageInput.setText(newValue.getMessage());
			}
		});
		
	}// end initialize method
	
	//set the submit event
	   @FXML
	   public void submitNoteToList() throws IOException
	   {
		   int index = Integer.parseInt(lblIndex.getText());
		   notesLV.getItems().add(index, new Order(12, messageInput.getText()));
		   // fred: remove after because if it was done before, the focus would have replaced the +1 index to erase the content
		   // of the form and created a duplicate of record(index+1)
		   notesLV.getItems().remove(index+1);
	   }
	   
	    @FXML
	    public void switchToChecklist() throws IOException {
	        // App.setRoot("select_user");
	        SelectController selectCtlr = SelectController.newInstance(); //(this.getOrderModel().getSelectClient());
	       	System.out.println("thirdly controller new instanciated");
	        selectCtlr.setUserModel();
	        //NO good idea to store selectOrder in UserModel, prefer this
	        AppModelVLook.setSelectOrder(this.getOrderModel().getSelectOrder());
	    	// the static way to call setSelectOrder was forced due to the fact that this present instance is self used statically
	        // pas besoin car OrderModel est une unique instance
	       	System.out.println("selectOrder transmitted to select view");
	       	App.setRootInstance(selectCtlr.getRoot());}	   
}
