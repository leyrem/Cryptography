import java.util.LinkedList;
import java.lang.Math;

import java.security.MessageDigest;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Scanner; // Import the Scanner class to read text files
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import javax.crypto.*;

import java.util.Base64;

import java.util.*;
import java.io.ByteArrayOutputStream;


public class MerkleRoot{
    // Encrypts string and returns in HEX format
    public static String encryptThisString(String input)
    {
        try {
            
            MessageDigest md = MessageDigest.getInstance("SHA-256");
  
            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
           
            byte[] messageDigest = md.digest(input.getBytes());
  
            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);
  
            // Convert message digest into hex value
            String hashtext = no.toString(16);
  
            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
  
            // return the HashText
            return hashtext;
        }
  
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
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


    public static byte[] concatenateByteArrays(byte[]a, byte[]b) throws IOException {
        byte[] ab = Arrays.copyOf(a, a.length + b.length);
        System.arraycopy(b, 0, ab, a.length, b.length);
        return ab;
    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }


    public static byte[] computeMerkleRoot(int index, String value, LinkedList<byte[]> branch) throws NoSuchAlgorithmException, IOException{
        byte[] ph = hexStringToByteArray(value);

        //String ph = value;
        
        for(int i = 0; i < branch.size(); i++){
            int val = (int)(index/Math.pow(2,i));
            if( val % 2 != 0 ){
                byte[] c = concatenateByteArrays(branch.get(i) , ph);
                ph = SHA256(c);
               
                
            } else if (val % 2 == 0){
                byte[] c = concatenateByteArrays(ph, branch.get(i));
                ph = SHA256(c);
                
            }
            System.out.println("Hashed value of branch: "+i+" is :"+convertByteToHexadecimal(ph));
        }
        return ph;

    }
    public static byte[] SHA256(byte[] obytes) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(obytes);
    }

    public static byte[] swapEndianness(byte[] hash) {
        byte[] result = new byte[hash.length];
        for (int i = 0; i < hash.length; i++) {
            result[i] = hash[hash.length-i-1];
        }
        return result;
    }

    public static String convertByteToHexadecimal(byte[] byteArray)
    {
        String hex = "";
  
        // Iterating through each byte in the array
        for (byte i : byteArray) {
            hex += String.format("%02X", i);
        }
        return hex;
    }

    public static void main(String args[]) throws NoSuchAlgorithmException, IOException{
        String str = "t";
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] messageDigest = md.digest(str.getBytes());
        //String value = bytesToHex(messageDigest);
        String value = toHexString(messageDigest);
        System.out.println("Str t hashed in hex is: "+value);

        LinkedList<byte[]> branch = new LinkedList<byte[]>();
        branch.add(hexStringToByteArray("043a718774c572bd8a25adbeb1bfcd5c0256ae11cecf9f9c3f925d0e52beaf89"));
        branch.add(hexStringToByteArray("aaf76092023d7001b92495856e34fbec93e104bc8c456736a0e6f8ff0375337d"));
        branch.add(hexStringToByteArray("5445fc8564d0fb96584bc803f2aa07c64efe50cc06bf7eeee486e984e09ac73d"));
        branch.add(hexStringToByteArray("f9a54bb160d635090f357e0a0e341f52a08bdb835c443d064c211b398cbca080"));
        branch.add(hexStringToByteArray("99c17b71d66b6307ab76148bc3de0c7daec3ee00806c5c30cb21405fec275da6"));

        
        byte[] merkleRoot = computeMerkleRoot(19, value, branch);
        

        System.out.println("Merkle root is:"+convertByteToHexadecimal(merkleRoot));
   
        System.out.println("Merkle root in hex little endian:"+toHexString(swapEndianness(hexStringToByteArray("00f611b41c37ad50aec99c33c65a50a9217346bbece2df9261d0143cdb246b8d"))));


    }
    public static String toHexString(byte[] bytes) {

        StringBuilder sb = new StringBuilder();
    
        if (bytes != null) 
            for (byte b:bytes) {
    
                final String hexString = Integer.toHexString(b & 0xff);
    
                if(hexString.length()==1)
                    sb.append('0');
    
                sb.append(hexString);//.append(' ');
            }
    
          return sb.toString();//.toUpperCase();
    }
}

