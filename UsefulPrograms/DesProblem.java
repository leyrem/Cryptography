import javax.crypto.*;

import java.util.Base64;

import java.util.*;
import java.io.*;
import java.security.*;


import sun.misc.*;



public class DesProblem {
	
    public static void main (String[] args) throws Exception
	{

		System.out.println("Program to decrypt ciphertext using Symmetric DES algorithm given the key in HEX format \n\n");

		System.out.println("__________________________________________\n");

		
		byte[] encodedKey = new byte[]{};
		
		String ciphertext = "ToFzH93tGJAnrGeChuvgUrijIMtkJTdO";

		int option = 1;

		if (option == 1){
			String keyInHexFormat = "5B154C02AED0E6D0";
			System.out.println("KEY in HEX format is : "+keyInHexFormat);
        	encodedKey = hexStringToByteArray(keyInHexFormat);
        	SecretKey nKey = new javax.crypto.spec.SecretKeySpec(encodedKey, 0, encodedKey.length, "DES");
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, nKey);
		byte[] raw = Base64.getDecoder().decode(ciphertext);
		byte[] stringBytes = cipher.doFinal(raw);
		String result = new String( stringBytes, "UTF8");
		System.out.println("The recovered Plaintext is: "+result);
        	
		} else if (option == 2){
			String keyInBase64Format = "cK1XAPDv/8Y=";
			System.out.println(" KEY in BASE64 format is: "+keyInBase64Format);
			encodedKey = Base64.getDecoder().decode(keyInBase64Format);
			SecretKey nKey = new javax.crypto.spec.SecretKeySpec(encodedKey, 0, encodedKey.length, "DES");
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, nKey);
		byte[] raw = Base64.getDecoder().decode(ciphertext);
		byte[] stringBytes = cipher.doFinal(raw);
		String result = new String( stringBytes, "UTF8");
		System.out.println("The recovered Plaintext is: "+result);
		} else if( option == 3){
			Key key;
			String keyFileName = "key.ser";
			System.out.println("filename of KEY object is: "+keyFileName);
        	ObjectInputStream inObj = new ObjectInputStream(new FileInputStream(keyFileName));
			key= (Key)inObj.readObject();
			inObj.close();


			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] raw2 = Base64.getEncoder().encode(ciphertext.getBytes());
			byte[] raw = Base64.getDecoder().decode(ciphertext);
			byte[] stringBytes = cipher.doFinal(raw2);
			String result = new String( stringBytes, "UTF8");
			System.out.println("The recovered Plaintext is: "+result);
		}
        
        

		/*SecretKey nKey = new javax.crypto.spec.SecretKeySpec(encodedKey, 0, encodedKey.length, "DES");
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, nKey);
		byte[] raw = Base64.getDecoder().decode(ciphertext);
		byte[] stringBytes = cipher.doFinal(raw);
		String result = new String( stringBytes, "UTF8");
		System.out.println("The recovered Plaintext is: "+result);*/

	

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
