import java.security.*;
import javax.crypto.*;
import java.util.*;

public class RSAOAEP {
    public static void main(String args[]){
        // Get a cipher object

        // RSA/ECB/OAEPWithSHA-1AndMGF1Padding (1024, 2048)
        // RSA/ECB/OAEPWithSHA-256AndMGF1Padding (1024, 2048)
		//Cipher cipher = Cipher.getInstance("RSA/ECB/OAEP");

        /*RSAPublicKeySpec rsaPubKS = new RSAPublicKeySpec(new BigInteger(big1, 16), new BigInteger(big2, 16));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        RSAPublicKey publicKey = (RSAPublicKey) kf.generatePublic(rsaPubKS);
        String modulus = publicKey.getModulus().toString();
        String public_exponent = publicKey.getPublicExponent().toString();*/


        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024); // speedy generation, but not secure anymore
        KeyPair kp = kpg.generateKeyPair();
        RSAPublicKey pubkey = (RSAPublicKey) kp.getPublic();
        RSAPrivateKey privkey = (RSAPrivateKey) kp.getPrivate();

        // --- encrypt given algorithm string
        Cipher oaepFromAlgo = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING");
        oaepFromAlgo.init(Cipher.ENCRYPT_MODE, pubkey);
        byte[] ct = oaepFromAlgo.doFinal("owlstead".getBytes(StandardCharsets.UTF_8));

        // --- decrypt given OAEPParameterSpec
        Cipher oaepFromInit = Cipher.getInstance("RSA/ECB/OAEPPadding");
        OAEPParameterSpec oaepParams = new OAEPParameterSpec("SHA-256", "MGF1", new MGF1ParameterSpec("SHA-1"), PSpecified.DEFAULT);
        oaepFromInit.init(Cipher.DECRYPT_MODE, privkey, oaepParams);
        byte[] pt = oaepFromInit.doFinal(ct);
        System.out.println(new String(pt, StandardCharsets.UTF_8));


    }

}