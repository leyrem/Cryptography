public class ElGamalFindExponent{
    public static void main(String args[]){
        // Solves the problem of: gen^x mod n = a, finds x
        int gen = 6;
        int n = 229;
        int a = 213;
        int i = 1;
        int b = 1;
        while(i < a){
            b = (b*gen) % n;
            if(b == a){
                System.out.println("found exponent: "+i);
                break;
            }
            i++;
        }
    }
}