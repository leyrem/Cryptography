import java.math.BigInteger;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;


public class DSASignature{
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
    public static String hashMessageString(String message){
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] byteMessage = message.getBytes(StandardCharsets.UTF_8);
        byte[] hashBytes = md.digest(byteMessage);
        byte[] encodedBytes = Base64.getEncoder().encode(hashBytes);
        String b64output = new String(encodedBytes);
        String hexHashOutput = bytesToHex(hashBytes);
        System.out.println("Hashed message in hex is: "+hexHashOutput);
        return hexHashOutput;
    }
    public static BigInteger[] computeSignature(BigInteger hash, BigInteger g, BigInteger k, BigInteger p, BigInteger q, BigInteger x){
        BigInteger r = g.modPow(k, p).mod(q);
        BigInteger s = k.modInverse(q).multiply(hash.add(x.multiply(r))).mod(q);
        System.out.println("Signature (r,s) is: ("+r+","+s+")");
        return new BigInteger[]{r,s};
    }
    public static boolean verifySignature(BigInteger hash, BigInteger s, BigInteger r, BigInteger q, BigInteger p, BigInteger g, BigInteger y){
        BigInteger a = s.modInverse(q).multiply(hash).mod(q);
        BigInteger b = s.modInverse(q).multiply(r).mod(q);
        BigInteger v = (g.modPow(a, p).multiply(y.modPow(b, p)).mod(p)).mod(q);
        System.out.println("Computed verification value v is: "+v);
        System.out.println("Correct value r is: "+r);
        return v.compareTo(r) == 0;
    }
    public static void main(String args[]){
        BigInteger p = new BigInteger("53");
        BigInteger q = new BigInteger("13");
        BigInteger g = new BigInteger("16");
        BigInteger x = new BigInteger("3");
        BigInteger hash = new BigInteger("5");
        BigInteger k = new BigInteger("2");

        BigInteger y = g.modPow(x, p);
        BigInteger[] signature = computeSignature(hash, g, k, p, q, x);
        boolean verify = verifySignature(hash, signature[1], signature[0], q, p, g, y);
    }
}