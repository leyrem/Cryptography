import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.ChaCha20ParameterSpec;
import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.*;

import java.util.Base64;

import java.util.*;
import java.util.Base64;

/*
The inputs to ChaCha20 encryption, specified by RFC 7539, are:
 - A 256-bit secret key (32 bytes)
 - A 96-bit nonce (12 bytes)
 - A 32-bit initial count (4 bytes)
*/
public class ChaCha20 {

    private static final String ENCRYPT_ALGO = "ChaCha20";

    public static byte[] encrypt(byte[] pText, SecretKey key, byte[] nonce, int counter) throws Exception {

        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);

        ChaCha20ParameterSpec param = new ChaCha20ParameterSpec(nonce, counter);

        cipher.init(Cipher.ENCRYPT_MODE, key, param);

        byte[] encryptedText = cipher.doFinal(pText);

        return encryptedText;
    }

    public static byte[] decrypt(byte[] cText, SecretKey key, byte[] nonce, int counter) throws Exception {

        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);

        ChaCha20ParameterSpec param = new ChaCha20ParameterSpec(nonce, counter);

        cipher.init(Cipher.DECRYPT_MODE, key, param);

        byte[] decryptedText = cipher.doFinal(cText);

        return decryptedText;

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
    public static void main(String[] args) throws Exception {

  
        String ciphertext = "EQ1n0sTsXtbg5cUo+zG1VlE0ig==";
        byte[] byteCiphertext = Base64.getDecoder().decode(ciphertext.getBytes("UTF-8"));
 

        String myKey = "CAB456AF43C9370EACB54594A508956D54E257F9CCC5A2C02141D3A6F3309408";
  
        // Create a key appending the generated number.
        byte[] encodedNewKey = hexStringToByteArray(myKey);
        SecretKey nKey = new javax.crypto.spec.SecretKeySpec(encodedNewKey, 0, encodedNewKey.length, "ChaCha20");


        String nonceFragment = "00010203040506070809"; // 2 bytes missing
        int counter = 17;                    // 32-bit initial count (8 bytes)

 
        boolean notEqualKey = true;
        while(notEqualKey){
            String fullNonce = nonceFragment+getRandomHexNumber();
            byte[] pText = decrypt(byteCiphertext, nKey, hexStringToByteArray(fullNonce), counter);
            String plaintext = new String(pText, "UTF8");
            System.out.println("Decrypted      : " + new String(pText, "UTF8"));

            byte[] cText = encrypt(plaintext.getBytes(), nKey, hexStringToByteArray(fullNonce), counter);   // encrypt
            byte[] encodedBytes = Base64.getEncoder().encode(cText);
			String b64output = new String(encodedBytes);
            if(b64output.equals(ciphertext)){
				notEqualKey = false;
                System.out.println("Nonce value is: "+fullNonce);
				System.out.println("Plaintext found: "+plaintext+"\n");
                
			}

        }


    }

    // A 256-bit secret key (32 bytes)
    public static SecretKey getKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("ChaCha20");
        keyGen.init(256, SecureRandom.getInstanceStrong());
        return keyGen.generateKey();
    }

    // 96-bit nonce (12 bytes)
    public static byte[] getNonce() {
        byte[] newNonce = new byte[12];
        new SecureRandom().nextBytes(newNonce);
        return newNonce;
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

    public static String convertBytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte temp : bytes) {
            result.append(String.format("%02x", temp));
        }
        return result.toString();
    }


}