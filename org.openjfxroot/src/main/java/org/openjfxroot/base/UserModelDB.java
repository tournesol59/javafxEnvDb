package org.openjfxroot.base;

import org.openjfxroot.dbname.*;
import org.openjfxroot.base.User;
import org.openjfxroot.base.OEnum;
import org.openjfxroot.base.UserVLook;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.lang.Iterable;
import java.util.Iterator;
import java.util.ListIterator;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class UserModelDB {
	private static IDataSource dataSrc;
	private final static int DATABASE_CHOICE=2;  // 1=h2, 2=postgresql
	private static UserModelDB INSTANCE;

	private static Long lastnumber; // par rapport à la taille de la listView
	private static Long lastInsertedNr; // recupéré lors des inserts jdbc
	private static Connection connect;
	private static User selectUser;
	
	private List<User> listUsersStub = new ArrayList<User>();

	/* init method (non testee) */
	public static void initUserPartDB() {
		try {
			/*!!!!  Here the selection of the database !!!! */
			dataSrc = new DataSourcePSQL();
			//connect = DriverManager.getConnection("jdbc:h2:mem:clientdb", "SA", "");
			connect = dataSrc.getConnection();
			System.out.println("Connected manually to the H2 database");
			Statement stmt = connect.createStatement();
			
			stmt.execute("CREATE TABLE IF NOT EXISTS tbluser (id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, "+
						"ustype VARCHAR(2), lastname VARCHAR(40), contactemail VARCHAR(40), contactpostal VARCHAR(40));");
			System.out.println("Created table tbluser");
			stmt.close();
		} catch (Exception e) {
			System.out.println("creation of tbluser failed");
		}
		try {
			Statement stmt = connect.createStatement();
			stmt.executeUpdate("INSERT INTO tbluser(ustype, lastname, contactemail, contactpostal) VALUES ('E', 'Simon', 'simono4@cti.ecp.fr', 'Paris');");
			stmt.executeUpdate("INSERT INTO tbluser(ustype, lastname, contactemail, contactpostal) VALUES ('E', 'Leaute', 'leautet4@cti.ecp.fr', 'Paris');");
			stmt.close();
			lastInsertedNr = Long.valueOf(2L);
		} catch (Exception e) {
			System.out.println("Populating tbluser failed");
		}
	}
	
	// the constructor designed to be a singleton
	private UserModelDB() {
		lastnumber=0L;
		lastInsertedNr=0L;
		initUserPartDB();
		selectUser = new User(2, "E", "Leaute", "leautet4@cti.ecp.fr", "Paris");
	}
	
	public static UserModelDB getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new UserModelDB();
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
	
	//--------------
	public List<User> getAllUsers() {
		//database operation
		List<User> listUsers = new ArrayList<User>();
		try {
			String sql = "SELECT * FROM tbluser;";
			PreparedStatement pstmt = connect.prepareStatement(sql);
			ResultSet result = pstmt.executeQuery();
			while (result.next()) {
				User user = new User(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5));
				listUsers.add(user);
			}
			result.close();
			pstmt.close();
			return listUsers;
		} catch (Exception e) {
			System.out.println("error in UserModelDB:getAllUsers");
			return null;
		}
	}
	
	public Map<String, UserVLook> loadUserVLook() {
		Map<String, UserVLook> userVLookMap = new HashMap<String, UserVLook>();
		String currentdelivery = "BEGIN";
		String lastdelivery = "END";
		int count=1;
		try {
			try (final InputStream input = this.getClass().getResourceAsStream("deliveryuser.txt"))
			{
				try (final LineNumberReader in = new LineNumberReader(new InputStreamReader(input)))
				{	for (String line=in.readLine(); line != null; line = in.readLine()) {
						line = line.trim();
						if (line.isEmpty()) {
							continue;
						}
						final String[] tokens = line.split("\\|");
						currentdelivery = tokens[0];
						if (currentdelivery != lastdelivery) {
							count = 1;
						}
						User auser = new User("E", tokens[1], tokens[2], "");
						OEnum delivery = OEnum.parse(tokens[0]);
						UserVLook userVL = new UserVLook(delivery, auser);
						StringBuilder stringBuilder = new StringBuilder(4);
						stringBuilder.append(tokens[0]);
						// the trick to generate a key that is close to the delivery code: eg MA1 => value
						stringBuilder.append(Integer.toString(count));
						String key = stringBuilder.toString();
						// save in the map
						userVLookMap.put(key, userVL);
						lastdelivery = currentdelivery;
						count++;
					}
				}
			}
		} catch (IOException | IllegalArgumentException | IndexOutOfBoundsException e) {
			System.out.println("Error");
		}
		return userVLookMap;
	}

//	public List<User> getUsersByEnumType() {
	//	List<User> userEnList=new ArrayList<User>();
	//}
}


