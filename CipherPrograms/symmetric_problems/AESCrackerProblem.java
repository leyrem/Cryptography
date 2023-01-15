import javax.crypto.*;

import java.util.Base64;

import java.util.*;


public class AESCrackerProblem {
    public static void main (String[] args) throws Exception
	{

		System.out.println("Program to find full Key given plaintext and ciphertext using Symmetric AES algorithm \n\n");

		System.out.println("__________________________________________\n");


		String plaintext = "codebreaker";
		byte[] stringBytes = plaintext.getBytes("UTF8");

		String ciphertext = "5XWvgqrISJZ+DCXEryChjQ==";
		Boolean notEqualKey = true;

		String keyFragment = "C0C1C7CA60317BD827FB1BEE0130";

		// This loop will iterate until the correct key is found.
		while (notEqualKey) {
			// Generate a 4 digit random Hex number.
			String randomKeyFragment = getRandomHexNumber();

			// Create a key appending the generated number.
			byte[] encodedNewKey = hexStringToByteArray(keyFragment + randomKeyFragment);
			System.out.println("\n key is "+keyFragment+randomKeyFragment+"\n"); 
			SecretKey nKey = new javax.crypto.spec.SecretKeySpec(encodedNewKey, 0, encodedNewKey.length, "AES");
			
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, nKey);

			// Pass the plaintext.
			byte[] raw = cipher.doFinal(stringBytes);
			byte[] encodedBytes = Base64.getEncoder().encode(raw);
			String b64output = new String(encodedBytes);

			if(b64output.equals(ciphertext)){
				notEqualKey = false;
				System.out.println("Key found! key is: "+keyFragment+randomKeyFragment+"\n");
			}
			System.out.println("ciphertext (in Base64) is  " +b64output+"\n\n");
		}

		System.out.println("__________________________________________\n");
	}

	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
								 + Character.digit(s.charAt(i+1), 16));
		}
		return data;
	}

	// This method generates random Hex numbers. It only returns the first 4 digits.
	public static String getRandomHexNumber(){
		  // Random instance
		  Random r = new Random();
		  int n = r.nextInt();
		  
		  // n stores the random integer in decimal form
		  String Hexadecimal = Integer.toHexString(n);
		  if(Hexadecimal.length() < 4){
			  return "0000";
		  }
		  String str = "";
		  for(int i = 0; i < 4; i ++){
			  if(Character.isLowerCase(Hexadecimal.charAt(i))){
				str += java.lang.Character.toUpperCase(Hexadecimal.charAt(i));
			  } else{
				str += Hexadecimal.charAt(i);
			  }
		  }
		  return str;
	}

}
