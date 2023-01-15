import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;
import java.math.BigInteger;

public class RSASignature{
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
    public static BigInteger computeSignature(BigInteger hash, BigInteger d, BigInteger n){
        BigInteger S = hash.modPow(d, n);
        System.out.println("Signature S is: "+S);
        return S;
    }
    public static final BigInteger ONE = new BigInteger("1");
    public static BigInteger computeSignatureWithGarnerCRT(BigInteger hash, BigInteger Sp, BigInteger Sq, BigInteger p, BigInteger q){
        //BigInteger Sp = hash.modPow(d.mod(p.subtract(ONE)), p);
        //BigInteger Sq = hash.modPow(d.mod(q.subtract(ONE)), q);
        BigInteger T = p.modInverse(q);
        BigInteger SqminusSp = Sq.subtract(Sp);
        BigInteger u = (SqminusSp.multiply(T)).mod(q);
        BigInteger S = Sp.add(u.multiply(p));
        System.out.println("Signature S with Garner CRT is: "+S);
        return S;
    }
    public static boolean verifySigntaure(BigInteger S, BigInteger e, BigInteger n,  BigInteger correctH){
        BigInteger h = S.modPow(e, n);
        System.out.println("Computed h after verification is: "+h);
        System.out.println("Correct value of h is: "+correctH);
        return correctH.compareTo(h) == 0;
    }

    public static void main(String args[]){
        BigInteger hash = new BigInteger("11");
        BigInteger Sp = new BigInteger("27");
        BigInteger Sq = new BigInteger("8102");
        BigInteger p = new BigInteger("37");
        BigInteger q = new BigInteger("10007");
        BigInteger e = new BigInteger("265097");
        BigInteger n = new BigInteger("370259");
        BigInteger signature = computeSignatureWithGarnerCRT(hash, Sp, Sq,p, q);
        boolean verify = verifySigntaure(signature,e, n, hash);

    }
}