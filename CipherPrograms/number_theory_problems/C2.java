import java.math.BigInteger;


public class C2 {
    public static void main(String args[]){

        BigInteger eulerTotient = new BigInteger("9863457479343348");
        BigInteger e = new BigInteger("519129341018071");
        System.out.println(e.modInverse(eulerTotient));
    }
}