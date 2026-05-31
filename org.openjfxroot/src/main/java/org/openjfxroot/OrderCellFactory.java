package org.openjfxroot;

import org.openjfxroot.base.Order;

import javafx.util.Callback;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;

public class OrderCellFactory implements Callback<ListView<String>, ListCell<String>> {
	@Override
	public ListCell<String> call(ListView<String> param) {
		return new TextFieldListCell<>(new StringConverter<String>() {
			/* fred: non present dans cette interface */

			@Override
			public String toString(String object) {
				return object;
			}
			@Override
			public String fromString(String string) {
				return string;
			}
			
		});
	}
	
}
