package org.openjfxroot.base;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.openjfxroot.dbname.*;

public class UserModelDBTest {

    static Connection connect = null;
    static UserModelDB userDB = null;
    static AppModelVLook mdlVLook = null;
    static List<User> listUser = null;
	static Map<String, UserVLook> userVLookMap = null;

    @BeforeClass
    public static void setup() {
       userDB = UserModelDB.getInstance();
       connect = UserModelDB.getConnection();
       mdlVLook = AppModelVLook.getInstance();
    }
    
    @Test
    public void getAllClientsTest() {
    	// insert your test code here
    	listUser = userDB.getAllUsers(); 
    	Assert.assertEquals("the size of the list of users does not match", 2, listUser.size());

    	//User auser = listUser.get(userDB.getLastInsertedNr().intValue()-1);
    	User auser = listUser.get(0);
    	Assert.assertEquals("the email of the first user does not match", "simono4@cti.ecp.fr", auser.getContactEmail());    	
    }
    
    @Test
    public void loadUserVLookTest() {
    	userVLookMap = mdlVLook.loadUserVLook();
    	System.out.println("VLook Map has size="+userVLookMap.size());
    	UserVLook userVLU = userVLookMap.get("MA1");
		System.out.println("first object in map: "+userVLU.toString());
    	// solution chosen: convert the string into char array to test directly the map
		
    	String testUserName = "thomas";
		String userNameCodePoints = testUserName.codePoints()
				.limit(8)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.toString();
		char[] username = userNameCodePoints.toCharArray();
/*
		System.out.println("username[0] "+username[0]);  // inverse operation to check
		System.out.println("username[1] "+username[1]);  // inverse operation to check
		System.out.println("username[2] "+username[2]);  // inverse operation to check
		System.out.println("username[3] "+username[3]);  // inverse operation to check
		System.out.println("username[4] "+username[4]);  // inverse operation to check
		System.out.println("username[5] "+username[5]);  // inverse operation to check
*/
		boolean matched = Arrays.equals(username, userVLU.getUserName());
    	Assert.assertTrue("the name of the first user to rule MA delivery is not the expected", matched);
    }
    
    @Test
    public void filterUsersByOEnum() {
    	OEnum delivery = OEnum.parse("MA");
    	List<User> allUsers = userDB.getAllUsers();
    	List<User> speciUsers = mdlVLook.filterUsersByOEnum(allUsers, delivery);
    	Assert.assertTrue("the number of Users delivering MA projects is not good", !speciUsers.isEmpty());
    	User oneUser = speciUsers.get(0);
    	Assert.assertEquals("the name of the first user matching MA is not good: "+oneUser.getLastName(), "leautet4@cti.ecp.fr", oneUser.getContactEmail());
    }
    
    @AfterClass
    public static void tearDown() {
    	try {
    	    connect.close();
    	} catch (Exception e) {
    	    System.out.println("test teardown failed");
    	}
    }
    
}
