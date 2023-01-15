import java.util.HashMap;
import java.util.*;

public class ShiftCipher{
    // Encrypts text using shift
   public static StringBuffer encrypt(String text, int shift) {
      StringBuffer result = new StringBuffer();

      for (int i = 0; i < text.length(); i++) {
         if (Character.isUpperCase(text.charAt(i))) {
            char ch = (char)(((int) text.charAt(i) +
               shift - 65) % 26 + 65);
            result.append(ch);
         } else {
            char ch = (char)(((int) text.charAt(i) +
               shift - 97) % 26 + 97);
            result.append(ch);
         }
      }
      return result;
   }

    // Decrypts cipher using shift
   public static StringBuffer decrypt(String cipher, int shift) {
      StringBuffer result = new StringBuffer();

      for (int i = 0; i < cipher.length(); i++) {
         if (Character.isUpperCase(cipher.charAt(i))) {
            char ch = (char)(((int) cipher.charAt(i) +
               shift - 65) % 26 + 65);
            result.append(ch);
         } else {
            char ch = (char)(((int) cipher.charAt(i) +
               shift - 97) % 26 + 97);
            result.append(ch);
         }
      }
      return result;
   }


    public static void breakCipher(String encryptedMesasge){
        int key = 1;
        while (key < 27){
            System.out.println("----------------------------------");

            String decrypted = decrypt(encryptedMesasge, key).toString();
            System.out.println("Decrypted is: "+decrypted);
            System.out.println("Key is: "+key);

            System.out.println("----------------------------------");
            key++;


        }
    }
    public static void main (String args[]){
        String message = "TRYWITHTHISMESSAGE";
        System.out.println(encrypt(message, 2).toString());
        System.out.println(decrypt(encrypt(message, 2).toString(), 24).toString());
        breakCipher(encrypt(message, 2).toString());

    }


    
}
