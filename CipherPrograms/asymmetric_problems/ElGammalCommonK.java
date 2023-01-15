import java.math.*;

import javax.security.auth.Subject;
public class ElGammalCommonK{
    public static void main(String args[]){
        BigInteger h1 = new BigInteger("1001");
        BigInteger h2 = new BigInteger("1122");
        BigInteger b1 = new BigInteger("4143");
        BigInteger b2 = new BigInteger("3392");
        BigInteger a = new BigInteger("6745");
        BigInteger pminus1 = new BigInteger("11806");
        BigInteger k = (b1.subtract(b2)).modInverse(pminus1).multiply(h1.subtract(h2)).mod(pminus1);
        System.out.println("k is:"+k);
        BigInteger x = a.modInverse(pminus1).multiply(h1.subtract(k.multiply(b1))).mod(pminus1);
        System.out.println("x is:"+x);
    }
}