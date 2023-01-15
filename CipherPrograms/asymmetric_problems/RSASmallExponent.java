import java.math.BigInteger; 

public class RSASmallExponent{
    public static void main (String args[]){
        BigInteger i = new BigInteger("1121250253787742538124846216024729490593655211434899107089879509871620864301249243000");
       //This program computes the cubic root of number i and prints it.
        System.out.println(cbrt(i));
    }

    static final BigInteger TWO = new BigInteger("2");
    static final BigInteger THREE = new BigInteger("3");
    static BigInteger number;

    public static BigInteger cbrt(BigInteger n) {
        BigInteger guess = n.divide(BigInteger.valueOf((long) n.bitLength() / 3));
        boolean go = true;
        int c = 0;
        BigInteger test = guess;
        while (go) {
            BigInteger numOne = n.divide(guess.multiply(guess));
            BigInteger numTwo = guess.multiply(TWO);
            guess = numOne.add(numTwo).divide(THREE);
            if (numOne.equals(numTwo)) {
                go = false;
            }
            if (guess.mod(TWO).equals(BigInteger.ONE)) {
                guess = guess.add(BigInteger.ONE);
            }
            // System.out.println(guess.toString());
            c++;
            c %= 5;
            if (c == 4 && (test.equals(guess))) {
                return guess;
            }
            if (c == 2) {
                test = guess;
            }
        }

        if ((guess.multiply(guess)).equals(number)) {
            return guess;
        }
        return guess.add(BigInteger.ONE);
    }
}