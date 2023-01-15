import java.io.*; 
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.nio.*;
import javax.crypto.*;
import java.util.*;

public class HashCrackerProblem{

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

    public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
								 + Character.digit(s.charAt(i+1), 16));
		}
		return data;
	}

    public static LinkedList<String> readPasswordFile(String filename){
        LinkedList<String> passwords = new LinkedList<String>();
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              passwords.add(data);
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
          return passwords;
    }

    // This method generates random Hex numbers, 2 byte Hex values.
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

    public static void main(String args[])throws NoSuchAlgorithmException{
        String ciphertext = "fRHAGLHUnImSAPErVWKgra1tGEHZzTim1lWTxh+y44nW9yfbVy8V5QQXhYEdPZUmwsY5QyaJ6bG8Fk7abrkKZw==";
        LinkedList<String> passwords = readPasswordFile("21Pwd.txt");
        boolean notFound = true;
        while(notFound){
            String salt = getRandomHexNumber();
            for(String password:passwords){
                MessageDigest md = MessageDigest.getInstance("SHA-512");
                String hexPassword = bytesToHex(password.getBytes(StandardCharsets.UTF_8));
                String passwordPlusSaltHex = salt+hexPassword;
                byte[] hashBytes = md.digest(hexStringToByteArray(passwordPlusSaltHex));
                byte[] encodedBytes = Base64.getEncoder().encode(hashBytes);
                String b64output = new String(encodedBytes);
                System.out.println("Hash value obtained is: "+b64output);
                System.out.println("Good hash value is:     "+ciphertext);
                if(b64output.equals(ciphertext)){
                    notFound = false;
                    System.out.println("Password found! Password is: "+password+"\n");
                }
            }
            
        }
    }

}
