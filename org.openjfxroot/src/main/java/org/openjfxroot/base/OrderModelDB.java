package org.openjfxroot.base;

import org.openjfxroot.dbname.*;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.lang.Iterable;
import java.util.Iterator;
import java.util.ListIterator;

import org.openjfxroot.dbname.DataSourceH2;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class OrderModelDB {
	private static IDataSource dataSrc;
	private static OrderModelDB INSTANCE;
	//	private static final Logger logger = Logger.getLogger(OrderModelDB.class);
	private static Long lastnumber;
	private static Long lastInsertedNr;
	private static Connection connect;
	private static Client selectClient;
	
	public static void initOrderPartDB() {
		try {
			//connect = DriverManager.getConnection("jdbc:h2:mem:clientdb", "SA", "");
			/*!!!!  Here the selection of the database !!!! */
			dataSrc = new DataSourceH2();
			connect = dataSrc.getConnection();
			System.out.println("Connected manually to the H2 database");
			Statement stmt = connect.createStatement();

			stmt.execute("CREATE TABLE IF NOT EXISTS tblorder (id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, "+
					"client_id INT, order_msg VARCHAR(40));");
			System.out.println("Created table tblorder");
			stmt.close();
		} catch (Exception e) {
			System.out.println("Creation of tblorder failed");
		}
		try {
			Statement stmt = connect.createStatement();
			stmt.executeUpdate("INSERT INTO tblorder(client_id, order_msg) VALUES (1, 'You have 3 books ordered');");
			stmt.executeUpdate("INSERT INTO tblorder(client_id, order_msg) VALUES (2, 'it is a big step');");
			stmt.close();
			lastInsertedNr=Long.valueOf(2L);
		} catch (Exception e) {
			System.out.println("Populating tblorder failed");
		}
	}
	
	/*
	 * Fred: il y a encore bcp de code a recopier mais le principe est le suivant,
	 * par defaut la base de donnees ne doit pas etre initialisee (mem) dans cette classe-ci
	 * mais dans ClientModelDB.java
	 */
	// the constructor designed to be a singleton
	private OrderModelDB() {
		lastnumber=0L;
		lastInsertedNr=0L;
		initOrderPartDB();
	}
	
	public static OrderModelDB getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new OrderModelDB();
		}
		return INSTANCE;
	}
	
	public static Connection getConnection() {
		if (INSTANCE != null) {
			return INSTANCE.connect;
		} else {
			return null;
		}
	}

	public static Long getLastInsertedNr() {
		return lastInsertedNr;
	}

	public static final Client getSelectClient() {
		return selectClient;
	}

	public static final void setSelectClient(Client selectClient) {
		OrderModelDB.selectClient = selectClient;
	}

	public List<Order> getAllOrders() {
		List<Order> listOrders = new ArrayList<Order>();
		try {
			String sql = "SELECT * FROM tblorder;";
			PreparedStatement pstmt = connect.prepareStatement(sql);
			ResultSet result = pstmt.executeQuery();
			while (result.next()) {
				Order order = new Order(result.getInt(1), result.getString(3)); //id, message
				order.setClient_id(result.getInt(2));
				order.setUser_id(result.getInt(4));
				order.setDelivery(result.getString(5));
				listOrders.add(order);
			}
			result.close();
			pstmt.close();
			return listOrders;
		} catch (Exception e) {
			System.out.println("error in OrderModelDB:getAllOrders");
			return null;
		}
	}
	
	public List<Order> getAllOrdersByClient(Client client) {
		List<Order> listOrders = new ArrayList<Order>();
		if (client == null) {
			System.out.println("error in OrderModel: client is null");
		}
		try {
			String sql = "SELECT (id,client_id,order_message) FROM tblorder WHERE client_id=?";
			PreparedStatement pstmt = connect.prepareStatement(sql);
			pstmt.setInt(1, client.getId());
			ResultSet result = pstmt.executeQuery();
			while (result.next()) {
				Order order = new Order(result.getInt(1), result.getString(3)); //id, message
				order.setClient_id(result.getInt(2));
				order.setUser_id(result.getInt(4));
				order.setDelivery(result.getString(5));
				listOrders.add(order);
			}
			result.close();
			pstmt.close();
			return listOrders;
		} catch (Exception e) {
			System.out.println("error in OrderModel:getAllOrdersByClient");
			return null;
		}
	}
	
	public ObservableList<Order> loaddata() {
		List<Order> orderList = getAllOrders();
		for (Order order : orderList) {
			System.out.println("order: "+order.getId()+", client: "+order.getClient_id() +", msg: "+order.getMessage());
		}
		ObservableList<Order> obsvListOrder = FXCollections.observableArrayList(orderList);
		return obsvListOrder;
	}
	
	public ObservableList<Order> loaddataByClient() {
		List<Order> orderList = getAllOrdersByClient(selectClient);
		for (Order order : orderList) {
			System.out.println("order: "+order.getId()+", client: "+order.getClient_id() +", msg: "+order.getMessage());
		}
		ObservableList<Order> obsvListOrder = FXCollections.observableArrayList(orderList);
		return obsvListOrder;
	}

}