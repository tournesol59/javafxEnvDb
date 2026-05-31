package org.openjfxroot;

import org.openjfxroot.base.User;

import javafx.util.Callback;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class UserCellFactory implements Callback<ListView<User>, ListCell<User>> {
  @Override
  public ListCell<User> call(ListView<User> param)
	  { return new ListCell<User>() {
		@Override
		public void updateItem(User user, boolean empty) {
			super.updateItem(user, empty);
			if (empty || user==null) {
				setText(null);
			} else {
				setText(user.getLastName()+", "+user.getContactEmail()+", "+user.getContactPostal());
			}
		}
	 };
  }
}
