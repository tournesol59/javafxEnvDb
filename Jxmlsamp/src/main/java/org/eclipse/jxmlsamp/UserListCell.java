/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.eclipse.jxmlsamp;

import org.eclipse.jxmlsamp.model.*;
//import javafx.application.Application;
//import javafx.collections.ObservableList;
import javafx.util.StringConverter;
//import javafx.beans.property.ObjectProperty;
import javafx.scene.control.ListCell;
/**
 *
 * @author frede
 */
public class UserListCell extends ListCell<User> {
    
	private StringConverter<User> converter;

        public UserListCell(StringConverter converter) {
		this.converter = converter;
	}
        
        @Override
	public void updateItem(User value, boolean empty) {
		super.updateItem(value, empty);
		String text = null;
		// graphic
		if (!empty) {
			text = value.getNom();
		}
		setText(text);
		//setGraphic(graphic);
	}
}
