import java.math.BigInteger;
import java.security.PrivateKey;


import javax.crypto.*;

import java.io.*;
import java.security.*;

public class Rsa{
    public static BigInteger encrypt(BigInteger p, BigInteger N, BigInteger e){
        BigInteger C = p.modPow(e, N);
        return C;
    }
    public static BigInteger decrypt(BigInteger N, BigInteger C, BigInteger d){
        BigInteger P = C.modPow(d, N);
        return P;
    }
    public static BigInteger findD(BigInteger totientN, BigInteger e ){
        return e.modInverse(totientN);

    }
    //WAY USING CIPHER OBJ
        /*String secretMessage = "Baeldung secret message";
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] secretMessageBytes = secretMessage.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);
        String encodedMessage = Base64.getEncoder().encodeToString(encryptedMessageBytes);*/
    public static BigInteger computeApowBmodN(BigInteger A, BigInteger B, BigInteger N){
        BigInteger t = new BigInteger("1");
        while(B != new BigInteger("0")){
            //If B is odd
            if(B.mod(new BigInteger("2")) != new BigInteger("0")){
                t = (t.multiply(A)).mod(N);
            }
            B = B.divide(new BigInteger("2"));
            A = (A.multiply(A)).mod(N);

        }
        return t;
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

    public static void main(String args[]) throws Exception{


       KeyPair key;
       ObjectInputStream in = new ObjectInputStream(new FileInputStream("DefaultRSA_key.ser"));
	    key= (KeyPair)in.readObject();
        in.close();
        PrivateKey privk = key.getPrivate();

        String ciphertext = "24:61:E6:5C:00:8F:47:7C:A9:55:C5:C4:FC:DC:14:44:79:AD:26:28:84:82:A5:D3:27:43:F7:BA:CD:53:A3:F5:B5:AF:8F:BF:7B:47:2A:01:E6:73:30:80:30:0C:47:91:7A:5D:8E:A4:43:37:81:42:C4:59:B3:AA:A7:38:C7:6D:66:F8:AD:84:59:9A:4F:69:2B:4B:7A:CE:5D:5D:C7:4B:6D:92:E6:D2D4:1C:D4:FC:3C:CD:21:15:7A:B5:DF:1D:88:22:F9:55:D7:D2:93:8A:E4:4F:8A:45:15:D0:83:89:93:7C:5B:F7:D5:0F:73:A2:2D:DF:DF:75:C1:2D:26:83:86:6C:D5:FC:42:F1:40:27:2A:47:88:1D:2E:A4:AB:61:A5:5B:76:49:B8:EF:3E:FA:11:AB30:75:EE:F2:F2:7C:EA:71:E5:A1:83:0D:7F:44:83:61:E6:FD:41:88:BA:D0:0C:C6:AF:A9:B5:55:DC:2C:41:01:E3:0A:DE:00:D9:11:00:6C:52:C9:33:64:F0:6A:B2:0D:F1:2B:77:83:BB:A8:80:F46A:D6:19:16:01:02:79:01:04:6D:CB:A6:6B:2517:87:E8:19:D5:85:DB:B0:90:57:50:9E:87:14:07:CD:94:C9:88:B8:71:94:A6:99:31:72:E3:7D:44:60:10:07";
		String c2 = ciphertext.replaceAll("\\p{Punct}", "");
        byte[] ciphertextBytes = hexStringToByteArray(c2);

        Cipher decryptCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        decryptCipher.init(Cipher.DECRYPT_MODE, privk);
        byte[] decryptedMessageBytes = decryptCipher.doFinal(ciphertextBytes);

        MessageDigest md = MessageDigest.getInstance("SHA3-256");
        byte[] hashBytes = md.digest(decryptedMessageBytes);
        //byte[] encodedBytes = Base64.getEncoder().encode(hashBytes);
        //String b64output = new String(encodedBytes);
        //System.out.println("Hash value obtained is: "+b64output);
        String hexOutput = bytesToHex(hashBytes);
        System.out.println("Key in hex obatined is: "+hexOutput);

    }

}