import java.io.*;
import java.nio.*;
import java.security.*;
import java.security.spec.*;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAReadKeyFile{

  

  public static PrivateKey getPrivate(String filename)throws Exception {

    byte[] keyBytes = Files.readAllBytes(Paths.get(filename));

    PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
    KeyFactory kf = KeyFactory.getInstance("RSA");
    return kf.generatePrivate(spec);
  }
  public static PublicKey getPublic(String filename) throws Exception {

    byte[] keyBytes = Files.readAllBytes(Paths.get(filename));

    X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
    KeyFactory kf = KeyFactory.getInstance("RSA");
    return kf.generatePublic(spec);
  }


  public static final String PRIVATE_KEY="DefaultRSA_key.ser";
  public static final String PUBLIC_KEY="DefaultRSA_key.ser";

  public static void main(String args[]) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        /*PrivateKey privateKey = getPrivate("FILENAME");
        byte[] pretty_key = privateKey.getEncoded();

        String hex_output="";

        String eye;

        for(int i=0;i<pretty_key.length;i++) {
            eye= byteToHex(pretty_key[i]);
            hex_output+=eye;
            if(i<pretty_key.length-1){
                hex_output+=":";
            }
        }
        System.out.println("key is "+hex_output+"\n\n");*/


        //ALTERNATIVE WAY TO GET KEYS

         //get the private key

         File file = new File(PRIVATE_KEY); // Replace private key declaration with correct path.
         FileInputStream fis = new FileInputStream(file);
         DataInputStream dis = new DataInputStream(fis);
 
         byte[] keyBytes = new byte[(int) file.length()];
         dis.readFully(keyBytes);
         dis.close();
 
         PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
         KeyFactory kf = KeyFactory.getInstance("RSA");
         RSAPrivateKey privKey = (RSAPrivateKey) kf.generatePrivate(spec);
         System.out.println("Exponent :" + privKey.getPrivateExponent());
         System.out.println("Modulus" + privKey.getModulus());
 
         //get the public key
         File file1 = new File(PUBLIC_KEY);
         FileInputStream fis1 = new FileInputStream(file1);
         DataInputStream dis1 = new DataInputStream(fis1);
         byte[] keyBytes1 = new byte[(int) file1.length()];
         dis1.readFully(keyBytes1);
         dis1.close();
 
         X509EncodedKeySpec spec1 = new X509EncodedKeySpec(keyBytes1);
         KeyFactory kf1 = KeyFactory.getInstance("RSA");
         RSAPublicKey pubKey = (RSAPublicKey) kf1.generatePublic(spec1);
 
         System.out.println("Exponent :" + pubKey.getPublicExponent());
         System.out.println("Modulus" + pubKey.getModulus());



  }

}





  
