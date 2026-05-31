package org.openjfxroot.base;

import org.openjfxroot.dbname.*;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.lang.Iterable;
import java.util.Iterator;
import java.util.ListIterator;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

//import org.apache.log4j.Logger; // dont compile => find out why

public class ClientModelDB {
	private static IDataSource dataSrc;
	private static ClientModelDB INSTANCE;
//	private static final Logger logger = Logger.getLogger(ClientModelDB.class);
	private static Long lastnumber;
	private static Long lastInsertedNr;
	private static Connection connect;

	// for dev-test only:
	private List<Client> listClientsStub = new ArrayList<Client>();
	
	// initialization of a h2 in-memory database
	public static void initClientPartDB() {
		try {
			//connect = DriverManager.getConnection("jdbc:h2:mem:clientdb", "SA", "");
			/*!!!!  Here the selection of the database !!!! */
			dataSrc = new DataSourceH2();
			connect = dataSrc.getConnection();
			System.out.println("Connected manually to the H2 database");
			Statement stmt = connect.createStatement();

			stmt.execute("CREATE TABLE IF NOT EXISTS tblclient (id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, "+
					"first_name VARCHAR(40), last_name VARCHAR(40), order_msg VARCHAR(40));");
			System.out.println("Created table tblclient");
			stmt.close();
		} catch (Exception e) {
			System.out.println("Creation of tblclient failed");
		}
		try {
			Statement stmt = connect.createStatement();
			stmt.executeUpdate("INSERT INTO tblclient(first_name, last_name, order_msg) VALUES ('Bill', 'Bradley', 'You have 3 books ordered');");
			stmt.executeUpdate("INSERT INTO tblclient(first_name, last_name, order_msg) VALUES ('John', 'Glenn', 'it is a big step');");
			stmt.close();
			lastInsertedNr=Long.valueOf(2L);
		} catch (Exception e) {
			System.out.println("Populating tblclient failed");
		}
	}
	
	// the constructor designed to be singleton
	private ClientModelDB() {
		lastnumber=0L;
		lastInsertedNr=0L;
		initClientPartDB();
		// test part only, shall be removed
		//listClientsStub.add(new Client(1, "Georges", "Eddington", "test1s"));
		//listClientsStub.add(new Client(2, "Georges", "Muir", "test2"));
		for (Client aclient : listClientsStub) {
			System.out.println("client ID="+aclient.getId()+", FN="+aclient.getFirstName()+
	    			", LN="+aclient.getLastName()+", ORD="+aclient.getOrderMessage());
	    }
	}

	// singleton implementation
	public static ClientModelDB getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ClientModelDB();
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
	
	// the operation that populates the items of a ListView from the first-level retrived rows
	public ObservableList<Client> loaddata() {
//		logger.info("This is a message from the logger");
		/*
		System.out.println("test");
	    List<Client> clientList = new ArrayList<Client>();
	    clientList.add(new Client(1, "Georges", "Eddington", "test1s"));
	    clientList.add(new Client(2, "Georges", "Muir", "test2"));
	    */
		List<Client> clientList = getAllClients();
		ObservableList<Client> obsvListCli = FXCollections.observableArrayList(clientList);

		return obsvListCli;
	}
	
	// first level save as a new row operation
	public void saveClient(Client client) {
		List<Long> insertIds = new ArrayList<>();
		int count=0;
		try {
			String sql = "INSERT INTO tblclient (first_name, last_name, order_msg) VALUES(?,?,?);";
			PreparedStatement pstmt = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, client.getFirstName());
			pstmt.setString(2, client.getLastName());
			pstmt.setString(3, client.getOrderMessage());
			//System.out.println("we are after parameterizing the execute query");
			int numRows = pstmt.executeUpdate();
		
			ResultSet generatedKeys = pstmt.getGeneratedKeys();
			System.out.println("saved Client "+client.getFirstName()+", lastInsertedNr: "+lastInsertedNr);
						
			while (generatedKeys.next()) {
				insertIds.add(generatedKeys.getLong(1));
				count++;
			}
			generatedKeys.close();
			pstmt.close();
			// note: the following line looks complicated but works
			lastInsertedNr = Long.valueOf(insertIds.get(count-1).toString());
			System.out.println("lastInsertedNr: "+lastInsertedNr);
			
			pstmt.close();
		
		} catch (Exception e) {
			System.out.println("error in ClientModelDB:saveClient");
		}
	}
	// a stubbed version for very first try ofc ompareAndSave test (then we should remove it
	public void saveClientStub(Client client) {
		listClientsStub.add(client);
		System.out.println("saved client with FN="+client.getFirstName()+", LN="+client.getLastName());
		
	}
	
	public void updateClientStub(int id, Client client) {
		Client upclient = new Client(id, client.getFirstName(), client.getLastName(),
								client.getOrderMessage());
		listClientsStub.remove(id);
		listClientsStub.add(upclient);
		System.out.println("updated client with FN="+client.getFirstName()+", LN="+client.getLastName());
		
	}
	
	// the standard java.util.List : we need it either to initialize the ObservableList or compare with an existing ObservableList
	public List<Client> getAllClients() {
		List<Client> listClients = new ArrayList<Client>();
		try {
			String sql = "SELECT * FROM tblclient;";
			PreparedStatement pstmt = connect.prepareStatement(sql);
			ResultSet result = pstmt.executeQuery();
			while (result.next()) {
				Client client = new Client(result.getInt(1), result.getString(2), result.getString(3), result.getString(4));
				listClients.add(client);
			}
			result.close();
			pstmt.close();
			return listClients;
		} catch (Exception e) {
			System.out.println("error in ClientModelDB:getAllClients");
			return null;
		}
	}
	
	// a stubbed version for very first try ofc ompareAndSave test (then we should remove it
	public List<Client> getAllClientsStub() {
		return listClientsStub;
	}
		

	// the save all entities that are new in an ObservableList, for this we need a double loop over observed items and db items
	// fitsly a useful function
	public boolean clientEquals(Client dbClient, Client memClient) {
		if (dbClient.getId() != memClient.getId()) {
			return false;
		}
		else if ( (dbClient.getFirstName() != memClient.getFirstName()) ||
				 (dbClient.getLastName() != memClient.getLastName()) ||
				 (dbClient.getOrderMessage() != memClient.getOrderMessage()) )
		{
			return false;
		} else {
			return true;
		}
	}
	
	
	public void compareAndSaveStub(ObservableList<Client> obsvClients) {
		// firstly we query the database and create an ietrator over the select result
		List<Client> listClients = getAllClientsStub(); //DEBUG
		// then we create a second iterator over the obsv list
		ListIterator<Client> listmemIterator = obsvClients.listIterator();
		
		while (listmemIterator.hasNext()) {
			Client memClient = listmemIterator.next();
			ListIterator<Client> listdbIterator = listClients.listIterator();
			boolean matchItem = false;
			while (listdbIterator.hasNext()) {
				Client dbClient = listdbIterator.next();
				if (dbClient.getId() != memClient.getId()) {
					continue;
				}
				else if (!clientEquals(dbClient, memClient)) {
					updateClientStub(dbClient.getId(), memClient);  // DEBUG and not implemented yet
					matchItem = true;
					break;
				}	
			}
			// if no matching record from the db was found, create a new row
			if (matchItem == false) {
					saveClientStub(memClient); // DEBUG
			}
		}
	
	}
}// end class
