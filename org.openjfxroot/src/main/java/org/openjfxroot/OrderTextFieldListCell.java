package org.openjfxroot;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;

public class OrderTextFieldListCell extends TextFieldListCell<String> {

	private Label label;
	private Button buttonModify;
	private HBox container;
	//private StringConverter<String> converter;
	
	public OrderTextFieldListCell(StringConverter<String> converter) {
		//this.converter = converter;
		this.setConverter(converter);
		this.label = new Label("");
		this.buttonModify = new Button("Modify");
		this.container = new HBox(label, buttonModify);
	}
	
	@Override
	public void updateItem(String value, boolean empty) {
		super.updateItem(value, empty);
		String text = null;
		if (!empty) {
			text = value;
		}
		setText(text);
		label.setText(text);
		//setGraphic(graphic);
	}
}
