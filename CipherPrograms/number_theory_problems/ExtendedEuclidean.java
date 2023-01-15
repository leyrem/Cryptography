import java.math.BigInteger; 

public class ExtendedEuclidean{

   public static void main(final String[] args) {
      BigInteger e1 = new BigInteger("18");
      BigInteger e2 = new BigInteger("27");
      System.out.println("eea(  ,  ) = " + apply(e1, e2));
      Triple sol = apply(e1,e2);
      
      System.out.println(sol);
   }

   /*
    * extended_euclid(d,s)
         if s = 0
             than return (d,1,0)
         (d',s',t') <-- extended_euclid(s, d mod s)
         return (d',t',s' - (d div s)t')
    */
   public static Triple apply(final BigInteger a, final BigInteger b) {
       if (b.equals(BigInteger.ZERO)) {
           return new Triple(a, BigInteger.ONE, BigInteger.ZERO);
       } else {
           final Triple extension = apply(b, a.mod(b));
           return new Triple(extension.gcd, extension.t, extension.s.subtract(a.divide(b).multiply(extension.t)));
       }
   }


   private static class Triple {
       public final BigInteger gcd;
       public final BigInteger s;
       public final BigInteger t;
       private Triple(final BigInteger gcd, final BigInteger s, final BigInteger t) {
           this.gcd = gcd;
           this.s = s;
           this.t = t;
       }
       @Override
       public String toString() {
           return "Triple{" +
                   "gcd=" + gcd +
                   ", s=" + s +
                   ", t=" + t +
                   '}';
       }
   }
}