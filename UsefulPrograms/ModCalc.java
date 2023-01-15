import java.lang.Number;
import java.math.BigInteger;

public class ModCalc {
    public static BigInteger aBmodn(BigInteger a, BigInteger b, BigInteger n ){
        //  BigInteger f = new BigInteger("1");
        BigInteger t = new BigInteger("1");
        BigInteger zero = new BigInteger("0");
        BigInteger two = new BigInteger("2");
        while(b.compareTo(zero) == 1){
            if( b.mod(two) != zero){
                t = (t.multiply(a)).mod(n);
            }
            b = b.divide(two);
            a = (a.multiply(a)).mod(n);
        }
        return t;
    }
    public static BigInteger modulo(BigInteger a, int b, BigInteger n){
        BigInteger x = new BigInteger("1");
        while (b > 0) {
            if (b%2 == 1) {
                x = (x.multiply(a)).mod(n); // multiplying with base
            }
            a = (a.multiply(a)).mod(n); // squaring the base
            b /= 2;
        }
        return x.mod(n);
    }
  
    public static void main(String args[]){
        BigInteger a = new BigInteger("19");
        BigInteger b = new BigInteger("5");
        BigInteger n = new BigInteger("119");
        System.out.println(aBmodn(a, b, n));
    }
}

