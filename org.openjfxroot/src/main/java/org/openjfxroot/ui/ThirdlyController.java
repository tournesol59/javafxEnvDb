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
public class ThirdlyController implements Initializable {
	
	// singleton
	public static ThirdlyController INSTANCE;
	
	@FXML
	private VBox root;
	
	@FXML
	private ListView<Order> notesLV;
	
	private ObservableList<Order> obsvNotesList;
	
	@FXML
	private Label lblSelection;
	
	@FXML
	Label lblIndex;
	
	@FXML
	TextField messageInput;

    public static ObservableList<Order> messagesList = FXCollections.observableArrayList();
	
    // --- fred: this encapsulated way of programming shall replace in the future the static calls to App.setRoot(..)
    public static ThirdlyController newInstance(Client client) {
    	FXMLLoader loader = new FXMLLoader(
    			ThirdlyController.class.getResource("thirdly.fxml"));
      if (INSTANCE == null) {
    	try {
    		loader.load();
    		System.out.println("thirdly view loaded");
    		INSTANCE = loader.getController();
    		INSTANCE.orderModel = OrderModelDB.getInstance();
    		INSTANCE.orderModel.setSelectClient(client);
    		return INSTANCE;
    	} catch (IOException ex) {
    		System.out.println("severe: thirdly fxml not loaded or controller is null: "+ex.getMessage());
    		return null;
    	}		
      }
      else return INSTANCE;
     }
    // ---    
	public VBox getRoot() {
		return root;
	}
	
	// fred: classe statique car utilisee seulement a linterieur de cette classe
	// a faire: rendre editable
	static class OrderRectCell extends ListCell<Order> {
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
    
	// fred: une autre classe statique pour le converter
	   //public class UserStringConverter extends 
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		//List<Order> notesList = new ArrayList<Order>();
		
		// data for ComboBox for editing the notes
		messagesList.add(new Order(1, "reserve a play field"));
		messagesList.add(new Order(2, "check in your reservation"));
		messagesList.add(new Order(3, "select your payment mode"));
		
		// data in initialization
		//obsvNotesList = FXCollections.observableArrayList(notesList);
		orderModel = OrderModelDB.getInstance();
		///obsvNotesList = orderModel.loaddataByClient();  // fred debug
		List<Order> notesList = orderModel.getAllOrders();
    	obsvNotesList = FXCollections.observableArrayList();
    	for (Order order : notesList) {
    		obsvNotesList.add(order);
    	}
		
		//Callback<ListView<String>, ListCell<String>> {
		/*** fred: the following below works but we want a dedicated factory
		notesLV.setCellFactory(new Callback<ListView<Order>, ListCell<Order>>() {
			@Override
			public ListCell<Order> call(ListView<Order> param) {
				return new TextFieldListCell<>(new StringConverter<Order>() {
					@Override
					public String toString(Order object) {
						return object.getMessage();
					}
					// we do not need the following function
					@Override
					public Order fromString(String name) {
						return new Order(11, name);
					}
				}) {
					@Override
					public void updateItem(Order order, boolean empty) {
						super.updateItem(order, empty);
						if (empty || order==null) {
							setText(null);
						} else {
							setText(order.getMessage());
						}
					}
				};
			}
		});
		*/
		// we use a ComboBoxListCell as for now, see OrderListCell implementation
		/*
		notesLV.setCellFactory(ComboBoxListCell.forListView(
				new StringConverter<Order>() {
					@Override
					public String toString(Order object) {
						return object.getMessage();
					}
					// we do not need the following function
					@Override
					public Order fromString(String name) {
						return new Order(11, name);
					}
				}
				, messagesList));
		
		*/
		//notesLV.setEditable(true); // ne produit pas l'effet escompté car on n'a pas indiqué de Converter
		/*
		 * fred: last, this code differs by the previous to use a predefined textboxlistcell extension
		 * and an override of the updateItem method
		 */
		notesLV.setCellFactory(new Callback<ListView<Order>, 
	            ListCell<Order>>() {
            @Override 
            public ListCell<Order> call(ListView<Order> list) {
                return new OrderRectCell();
            }
        });
		// last but not least: bind notesLV displau to ObservableList
		notesLV.setItems(obsvNotesList);

		notesLV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Order>() {
			@Override
			public void changed(ObservableValue<? extends Order> observable, Order oldValue, Order newValue)
			{
				System.out.println("current order selection: old message="+oldValue.getMessage()+
						", new message="+newValue.getMessage());
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
	    	// fred: by-pass here: not checklist but select_user since checklist is under debug
	        App.setRoot("select_user");
	    }	   
}
