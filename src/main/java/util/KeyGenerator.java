package util;

import java.security.MessageDigest;
import java.util.UUID;

public class KeyGenerator {

	public static String generateUserKey() {
		try {
			String uuid = UUID.randomUUID().toString();
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(uuid.getBytes("utf-8"));
			
			StringBuilder sb = new StringBuilder();
			for(byte b : hash) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
