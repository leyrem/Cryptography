import java.math.BigInteger; 

public class ElGamal{
    public static BigInteger[] encrypt(BigInteger y, BigInteger k, BigInteger m, BigInteger p, BigInteger g){
        BigInteger a = g.modPow(k, p);
        BigInteger b = y.modPow(k, p).multiply(m).mod(p);
        return new BigInteger[]{a,b};
    }
    public static BigInteger decrypt(BigInteger a, BigInteger b, BigInteger x, BigInteger p){
        BigInteger axmodp = a.modPow(x, p);
        BigInteger axmodpInverse = axmodp.modInverse(p);
        BigInteger m = b.multiply(axmodpInverse).mod(p);
        return m;
    }
    public static void main(String args[]){
        BigInteger p = new BigInteger("1009");
        BigInteger x = new BigInteger("237");
        BigInteger g = new BigInteger("101");

        BigInteger y = g.modPow(x, p);
        BigInteger k = new BigInteger("291");
        BigInteger m = new BigInteger("559");
       
        BigInteger[] ab = encrypt(y, k, m, p, g);
        
        System.out.println("a,b encrypted ciphertext is: "+ab[0]+","+ab[1]);
        System.out.println("m decrypted plaintext is: "+decrypt(ab[0], ab[1], x, p));
        
    }
}