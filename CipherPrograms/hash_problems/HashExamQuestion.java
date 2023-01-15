
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
//mport org.bouncycastle.jcajce.provider.digest.Keccak;
//import org.bouncycastle.*;

public class HashExamQuestion{

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


    public static String encryptThisString(String input)
    {
        try {
            // getInstance() method is called with algorithm SHA-1
            MessageDigest md = MessageDigest.getInstance("SHA3-384");
  
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
    public static String readHashTextFile(String fileName) throws IOException {
        Path fileNameP = Path.of(fileName);
        String actual = Files.readString(fileNameP);
	    System.out.println("Read text in file is: "+actual);
        return actual;
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
    public static String getRandomPinFragment(String ciphertext, String text) throws UnsupportedEncodingException, NoSuchAlgorithmException{
        String pinFragment = "";
        String knownPinPart = "!";

        for(int i = 0; i <= 50; i++){
            for(int j = 0; j <= 50; j++){
                for(int z = 0; z <= 50; z++){
                    pinFragment = String.valueOf((char)i)+ String.valueOf((char)j) + String.valueOf((char)z);
                    System.out.println("Pin fragemnt is: "+pinFragment);

                    MessageDigest md = MessageDigest.getInstance("SHA-1");
                    String hexText = bytesToHex(text.getBytes(StandardCharsets.UTF_8));
                    String hexKnownPinPart = bytesToHex(knownPinPart.getBytes(StandardCharsets.UTF_8));
                    String hexPinFragment = bytesToHex(pinFragment.getBytes(StandardCharsets.UTF_8));
                    String all = hexText+hexKnownPinPart+hexPinFragment;
                    byte[] hashBytes = md.digest(hexStringToByteArray(all));
                    byte[] encodedBytes = Base64.getEncoder().encode(hashBytes);
                    String b64output = new String(encodedBytes);
                    System.out.println("Hash obtained is: "+b64output);
                    System.out.println("Ciphertext    is: "+ciphertext);
                    if(b64output.equals(ciphertext)){
                        System.out.println("PIN found! pin is: !"+pinFragment+"\n");
                        return "yay";
                    }

                }
            }
        }
        return "nope";
    }
   /* public void findRemainingPin(String ciphertext, String text){

        String knownPinPart = "!";

        Boolean notFound = true;
        while(notFound){
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] hashBytes = md.digest(text.getBytes("UTF8")+knownPinPart.getBytes("UTF8")+randomPinFragment.getBytes("UTF8"));
            byte[] encodedBytes = Base64.getEncoder().encode(hashBytes);
            String b64output = new String(encodedBytes);
            System.out.println("Hash obtained is: "+b64output);
            if(b64output.equals(ciphertext)){
                notFound = false;
                System.out.println("PIN found! pin is: !"+randomPinFragment+"\n");
            }
        }
    }*/
    public static void decrypt(){
        // How do i decrypt a hashed value?
    }
    public static void main(String args[]) throws NoSuchAlgorithmException, IOException {

        String text = readHashTextFile("hashTester.txt");
        String ciphertext = "lvlb8MIRyeq5sxEC9Ot+NTdUaC4=";
        //String sol = encryptThisString(text);
        System.out.println(getRandomPinFragment(ciphertext, text));
        //System.out.println(sol);

        //System.out.println(encryptThisString(text));
  
    
    }

}