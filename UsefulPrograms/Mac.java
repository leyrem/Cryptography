import java.security.MessageDigest;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.LinkedList;
import java.util.Scanner; // Import the Scanner class to read text files
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

import javax.crypto.KeyGenerator;
import java.lang.Object;
import org.bouncycastle.jcajce.provider.digest.Keccak;
//import org.bouncycastle.*;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.*;
import java.io.*;


import sun.misc.*;

public class Mac{
    private static String bytesToHex(byte[] hash) {
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
    public static void main(){
        String message = "";
        KeyGenerator keygen = KeyGenerator.getInstance("HmacSHA512"); // Use a secure underlying hash for HMAC algorithm.  
        keygen.init(256); // Explicitly initializing keyGenerator. Specify key size, and trust the provider supplied randomness. 
        SecretKey hmacKey = keygen.generateKey(); // SecretKey holds Symmetric Key(K)

        Mac mac = Mac.getInstance("HmacSHA512"); // get access to Mac object which implements HmacSHA512 algorithm. 
        mac.init(new SecretKeySpec(hmacKey.getEncoded(), "HmacSHA512")); // Initialize Mac object with symmetric key(K), same as with sender
        mac.update(message.getBytes()); // add message data (M) to Mac object to compute Mac. 
        String senderMac = mac.doFinal(); // Compute MAC


        Mac macRecv = Mac.getInstance("HmacSHA512"); // get access to Mac object which implements same algorithm used on sender side 
        macRecv.init(new SecretKeySpec(hmacKey.getEncoded(), "HmacSHA512")); // Initialize Mac object with symmetric key(K), same as with sender
        macRecv.update(message.getBytes()); // add message data (M) to Mac object to compute Mac. 
        String receivedMac = macRecv.doFinal(); // Compute MAC

        if (macComputationAPI.computeMac(hmacKey, data).equals(macRecv)){
           System.out.println("Authentication and Integrity checked cleared on Received message " + message);
        } else {
            System.out.println("Message " + message + " received on receiver side is tampered with, or doesn't come from the expected sender");
        }


        String originalString = "";
        // HASHING
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));

        //SHA-3 256
        //MessageDigest digest = MessageDigest.getInstance("SHA3-256");
        //byte[] hashbytes = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
        //String sha3Hex = bytesToHex(hashbytes);

        // Keccak-256
        //Security.addProvider(new BouncyCastleProvider());
        //MessageDigest digest = MessageDigest.getInstance("Keccak-256");
        //byte[] encodedhash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
        //String sha3Hex = bytesToHex(encodedhash);
    }
}