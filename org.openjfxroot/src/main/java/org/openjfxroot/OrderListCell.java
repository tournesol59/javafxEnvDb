package org.openjfxroot;

import org.openjfxroot.base.Order;

import org.openjfxroot.OrderListCellController;

import javafx.scene.layout.HBox;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.ListCell;

public class OrderListCell extends ComboBoxListCell<Order> {
	
	//private Node renderer;
	//private OrderListCellController rendererController;
	//private Order value;
	private StringConverter<Order> converter;
	private ObservableList<String> messages;
	// constructor
	public OrderListCell(StringConverter converter, ObservableList<String> messages) {
		this.converter = converter;
		this.messages = messages;
	}
	
	@Override
	public void updateItem(Order value, boolean empty) {
		super.updateItem(value, empty);
		String text = null;
		// graphic
		if (!empty) {
			text = value.getMessage();
		}
		setText(text);
		//setGraphic(graphic);
	}
}
