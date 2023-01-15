import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.math.BigInteger;
import java.security.*;
import java.nio.*;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.X509EncodedKeySpec;

public class RSAKEM{
    public static PrivateKey loadPrivateKeyFromFile(String keyFile){
        /* Read all bytes from the private key file */
        Path path = Paths.get(keyFile);
        byte[] bytes = Files.readAllBytes(path);
 
        /* Generate private key. */
        PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(bytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey pvt = kf.generatePrivate(ks);
        return pvt;
    }
    public static PublicKey loadPublicKeyFromFile(String keyFile){
        /* Read all the public key bytes */
        Path path = Paths.get(keyFile);
        byte[] bytes = Files.readAllBytes(path);
 
        /* Generate public key. */
        X509EncodedKeySpec ks = new X509EncodedKeySpec(bytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey pub = kf.generatePublic(ks);
        return pub;
    }
    

    
    public static void main(String args[]) throws NoSuchAlgorithmException{
       PrivateKey priv = loadPrivateKeyFromFile("DefaultRSA_key.ser");
        
       /*//GENERATE RSA KEY
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        // usually use size of 1024 or 2048
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        Key pub = kp.getPublic();
        Key pvt = kp.getPrivate();    */
         
    }
}