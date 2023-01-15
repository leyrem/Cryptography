import java.nio.charset.StandardCharsets;
import java.security.*;
import javax.crypto.*;
import java.lang.Object;
import java.util.*;
import java.io.*;



public class Mac{
    public static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
    public static void main(String args[]) throws NoSuchAlgorithmException, InvalidKeyException{
        
        //KeyGenerator keygen = KeyGenerator.getInstance("HmacSHA512"); // Use a secure underlying hash for HMAC algorithm.  
        //keygen.init(256); // Explicitly initializing keyGenerator. Specify key size, and trust the provider supplied randomness. 
        //SecretKey hmacKey = keygen.generateKey(); // SecretKey holds Symmetric Key(K)

        //Creating a KeyGenerator object
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");

        //Creating a SecureRandom object
        SecureRandom secRandom = new SecureRandom();

        //Initializing the KeyGenerator
        keyGen.init(secRandom);
        //Creating/Generating a key
        Key key = keyGen.generateKey();

        //Creating a Mac object
        javax.crypto.Mac mac =  javax.crypto.Mac.getInstance("HmacSHA256");
        mac.init(key);

        //Computing the Mac
        String msg = new String("Hi how are you");
        byte[] bytes = msg.getBytes();      
        byte[] macResult = mac.doFinal(bytes);

        System.out.println("Mac result:");
        System.out.println(new String(macResult)); 
    }
}