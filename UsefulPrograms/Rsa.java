import java.math.BigInteger; 

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

    public static void main(String args[]){
        BigInteger e = new BigInteger("8597143");
        BigInteger N = new BigInteger("13039013");
       //BigInteger d = 
       BigInteger totientN = new BigInteger("12036000");
       System.out.println(findD(totientN, e));

       BigInteger N2 = new BigInteger("119");
       BigInteger e2 = new BigInteger("5");
       BigInteger P = new BigInteger("19");
       System.out.println(encrypt(P, N2, e2));

       System.out.println(computeApowBmodN(P,e, N));
       System.out.println(P.modPow(e, N));

    }
    // If we have n = pq, if we know p and q  and also e then we can obtain euler totient of n and get d 
}