package org.openjfxroot;

import org.openjfxroot.base.Client;
import org.openjfxroot.base.User;

import javafx.util.Callback;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ClientCellFactory implements Callback<ListView<Client>, ListCell<Client>> {
  @Override
  public ListCell<Client> call(ListView<Client> param)
	  { return new ListCell<Client>() {

		@Override
		public void startEdit() {
			super.startEdit();
			Client client = param.getSelectionModel().getSelectedItem();
			if (client != null) {
			   setText(null);
			   setGraphic(new TextField(client.getFirstName()+", "+client.getLastName()));
			} else {
				setText("null");
				setGraphic(null);
			}
		}
		
		@Override
		public void cancelEdit() {
			super.cancelEdit();
		}
		
		@Override
		public void updateItem(Client client, boolean empty) {
			super.updateItem(client, empty);
			if (empty || client==null) {
				setText(null);
			} else {
				setText(client.getFirstName()+", "+client.getLastName());
			}
		}
	 };
  }
}