package org.openjfxroot.base;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class AppModelVLook {

	private static AppModelVLook INSTANCE=null;
	
	private static Map<String, UserVLook> userVLookMap;
	// the selected client in the PrimaryController -<associated view>
	private static Client selectClient;
	// the selected Order in the ThirdlyController -<associated view>
	private static Order selectOrder;
	// the selected User in the SelectController -<associated view>
	private static User selectUser;
	
	protected AppModelVLook() {};
	
	public static AppModelVLook getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new AppModelVLook();
			INSTANCE.loadUserVLook();
		}
		return INSTANCE;
	}
	
	public Map<String, UserVLook> loadUserVLook() {
		
		userVLookMap = new HashMap<String, UserVLook>();
		
		String currentdelivery = "BEGIN";
		String lastdelivery = "END";
		int count = 1;
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
						// we have to find a way to "arrange" the content of the created map 
						//(even if it is a map thus not sorted), at least a numbering of keys for the same 'deliveries'
						currentdelivery = tokens[0];
						//if (count == 1) {
						//	lastdelivery = currentdelivery;
						//}
						if ((count > 1) && (currentdelivery != lastdelivery)) {
							System.out.println("remise a un: "+count); //DEBUG
							count = 1;
						}
						// create a user(type, lastname, email, postal="")
						User auser = new User("E", tokens[1], tokens[2], "");
						
						OEnum delivery = OEnum.parse(currentdelivery);
						UserVLook userVL = new UserVLook(delivery, auser);

						// the trick to generate a key that is close to the delivery code: eg MA1 => value
						StringBuilder stringBuilder = new StringBuilder(4);
						stringBuilder.append(tokens[0]);
						stringBuilder.append(Integer.toString(count));
						String key = stringBuilder.toString();
						System.out.println("DEBUG: UserVLook("+count+"), key="+key);
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

	// function to retains only the Users that match one delivery type
	public List<User> filterUsersByOEnum(List<User> listUserToFilter, OEnum delivery) {
		List<User> results = new ArrayList<>();
		String deliverykey = delivery.getCode().toString();
		boolean foundUserVLook=false;
		int count=0;
		UserVLook userVLook = null;
		// OEnum delivery = OEnum.parse(currentdelivery); // if we would better have input delivery as a String
		for (User user : listUserToFilter) {
			foundUserVLook=false;
			count=1;
			StringBuilder stringBuilder = new StringBuilder(4);
			stringBuilder.append(deliverykey);
			stringBuilder.append(Integer.toString(count));
			String key = stringBuilder.toString();
			//while ((!foundUserVLook) && (userVLookMap.get(key)!=null)) {
			while (userVLookMap.get(key)!=null) {
				userVLook = userVLookMap.get(key);
				// the first match(delivery) test is redundant
				if (userVLook.matchDelivery(delivery) && (userVLook.matchUser(user))) {
					//foundUserVLook = true;
					results.add(user);
				}
				//preview next while iteration
				count++;
				stringBuilder = new StringBuilder(4);
				stringBuilder.append(deliverykey);
				stringBuilder.append(Integer.toString(count));
				key = stringBuilder.toString();
			}
		}
		return results;
	}

	//getters setters
	public static final Client getSelectClient() {
		return selectClient;
	}

	public static final void setSelectClient(Client selectClient) {
		AppModelVLook.selectClient = selectClient;
	}

	public static final Order getSelectOrder() {
		return selectOrder;
	}

	public static final void setSelectOrder(Order selectOrder) {
		AppModelVLook.selectOrder = selectOrder;
	}

	public static final User getSelectUser() {
		return selectUser;
	}

	public static final void setSelectUser(User selectUser) {
		AppModelVLook.selectUser = selectUser;
	}	

}
