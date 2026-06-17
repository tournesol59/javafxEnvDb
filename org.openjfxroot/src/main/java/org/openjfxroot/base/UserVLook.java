package org.openjfxroot.base;

import java.util.Arrays;

public class UserVLook {

	private char[] delivery;
	private char[] userName;
	
	public UserVLook(OEnum delivery_en, User user) {
		String deliveryCodePoints = delivery_en.getCode().codePoints()
			.limit(2)
			.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
			.toString();
		this.delivery = deliveryCodePoints.toCharArray();

		String userNameCodePoints = user.getLastName().codePoints()
			.limit(8)
			.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
			.toString();
		
		this.userName = userNameCodePoints.toCharArray();
	}
	
	public char[] getDelivery() {
		return this.delivery;
	}
	
	public void setDelivery(OEnum delivery_en) {
		String deliveryCodePoints = delivery_en.getCode().codePoints()
			.limit(2)
			.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
			.toString();
		this.delivery = deliveryCodePoints.toCharArray();	
	}
	
	public char[] getUserName() {
		return this.userName;
	}
	
	public void setUserName(User user) {
		String userNameCodePoints = user.getLastName().codePoints()
			.limit(8)
			.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
			.toString();
		this.userName = userNameCodePoints.toCharArray();	
	}	
	
	public boolean matchUser(User user) {
        boolean isEqual1 = Arrays.equals((this.userName), (user.getLastName().toCharArray()));
        System.out.println("result username match: "+isEqual1);
        return isEqual1;
	}
	
	public boolean matchDelivery(OEnum delivery) {
		boolean isEqual2 = Arrays.equals((this.delivery), (delivery.getCode().toCharArray()));
		System.out.println("result delivery match: "+isEqual2);
		return isEqual2;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		sb.append("[");
		sb.append(this.getDelivery().toString());
		sb.append("],[");
		sb.append(this.getUserName().toString());
		sb.append("]");
		return sb.toString();
	}
}
