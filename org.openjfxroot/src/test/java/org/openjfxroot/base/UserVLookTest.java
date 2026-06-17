package org.openjfxroot.base;

import org.openjfxroot.base.User;
import org.openjfxroot.base.OEnum;
import org.openjfxroot.base.UserVLook;

import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;

import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;

public class UserVLookTest {
	
	private static OEnum typeen;
	private static User auser;
	
    @BeforeClass
    public static void setup() {
    	typeen = OEnum.parse("MA");
    	auser = new User("E", "simon", "simono4@cti.ecp.fr", "Paris");
    }
    
    @Test
    public void getUserVLookTest() {
    	UserVLook auserVLook = new UserVLook(typeen, auser);
    	/*
    	System.out.println("type: "+typeen.getCode());
		String deliveryCodePoints = typeen.getCode().codePoints()
				.limit(2)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.toString();
		System.out.println("type2: "+deliveryCodePoints);
		char[] delivery = deliveryCodePoints.toCharArray();
		System.out.println("type3: "+(delivery[0])+(delivery[1]));
		*/
		char[] deliverytest = {'M', 'A'};
    	Assert.assertTrue("no good matching type", Arrays.equals(auserVLook.getDelivery(), deliverytest) );
		char[] usernametest = {'s', 'i', 'm', 'o', 'n'};
    	Assert.assertTrue("no good matching name", Arrays.equals(auserVLook.getUserName(), usernametest) );
    }
    
    @Test
    public void matchUser() {
    	UserVLook auserVLook = new UserVLook(typeen, auser);
    	
    	Assert.assertEquals("not good matching name by func", true, auserVLook.matchUser(auser));
    }
    
    @AfterClass
    public static void tearDown() {
    }
}
