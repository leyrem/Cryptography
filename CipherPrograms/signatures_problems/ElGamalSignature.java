import java.math.BigInteger;

public class ElGamalSignature{
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
    public static final BigInteger ONE = new BigInteger("1");
    public static BigInteger[] computeSignature(BigInteger hash, BigInteger g, BigInteger k, BigInteger p, BigInteger x){
        BigInteger a = g.modPow(k, p);
        BigInteger b = (k.modInverse(p.subtract(ONE))).multiply(hash.subtract(x.multiply(a))).mod(p.subtract(ONE));
        System.out.println("Signature (a,b) is: ("+a+","+b+")");
        return new BigInteger[]{a,b};
    }
    public static boolean verifySignature(BigInteger a, BigInteger b, BigInteger hash, BigInteger y, BigInteger g, BigInteger p){
        int h = hash.intValue();
        BigInteger gPowH = g.pow(h);
        BigInteger other = y.modPow(a, p).multiply(a.modPow(b,p)).mod(p);
        System.out.println("Computed g^h after verification is: "+gPowH);
        System.out.println("Computed value y^aa^bmodp is: "+other);
        return gPowH.compareTo(other) == 0;

    }
    public static void main(String args[]){
        BigInteger y = new BigInteger("884");
        BigInteger g = new BigInteger("3");
        BigInteger p = new BigInteger("1009");
        BigInteger hash = new BigInteger("38");
        BigInteger[] signature = new BigInteger[]{new BigInteger("829"),new BigInteger("155") };
        System.out.println(verifySignature(signature[0], signature[1],hash, y, g,p));

    }
}