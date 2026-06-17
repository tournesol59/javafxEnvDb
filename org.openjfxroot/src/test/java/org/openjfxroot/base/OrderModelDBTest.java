package org.openjfxroot.base;

import java.sql.Connection;
import java.util.List;
import java.sql.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;

public class OrderModelDBTest {

    public static Connection connect = null;
    public static OrderModelDB modelDB = null;
    public static List<Order> listOrder = null;

    @BeforeClass
    public static void setup() {

       modelDB = OrderModelDB.getInstance();
       connect = OrderModelDB.getConnection();
    }
    
    @Test
    public void getAllOrdersTest() {
    	listOrder = modelDB.getAllOrders();
    	Order anorder = listOrder.get(0);
    	Assert.assertEquals("not the expected name: ", "You have 3 books ordered", anorder.getMessage());
    }
    
    @Test
    public void getAllOrdersByClientTest() {
    	modelDB.setSelectClient(new Client(2,"John", "Glenn", "space in"));
    	listOrder = modelDB.getAllOrdersByClient();
    	Assert.assertEquals("list is empty", 1, listOrder.size());
    	//Order anorder = listOrder.get(0);
    	//Assert.assertEquals("not the expected name: ", "it is a big step", anorder.getMessage());
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
