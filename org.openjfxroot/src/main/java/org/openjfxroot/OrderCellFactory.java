package org.openjfxroot;

import org.openjfxroot.base.Order;

import javafx.util.Callback;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;

public class OrderCellFactory implements Callback<ListView<Order>, ListCell<Order>> {
	@Override
	public ListCell<Order> call(ListView<Order> param) {
		return new TextFieldListCell<Order>(new StringConverter<Order>() {
			/* fred: non present dans cette interface */
			@Override
			public String toString(Order object) {
				return object.getMessage();
			}
			@Override
			public Order fromString(String name) {
				return new Order(12, name);
			}
		}) 
		{
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
		}};
	}
	
}
