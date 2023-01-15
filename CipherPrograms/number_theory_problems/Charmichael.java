
import java.math.BigInteger;
import java.util.*;

public class Charmichael{
	
	public static boolean isCarmicheal (BigInteger n) {
		
		if (n.isProbablePrime(10)) return false;
		if (n.compareTo(BigInteger.ZERO) == 0 || n.compareTo(BigInteger.ONE) == 0) return false;
		
		boolean flag = true;
		for (BigInteger i = BigInteger.valueOf(2); ((i.compareTo(n)) == -1  && flag == true); i = i.add(BigInteger.ONE)) 
			if (i.gcd(n).compareTo(BigInteger.ONE) == 0) flag &= (i.modPow(n.subtract(BigInteger.ONE), n)).compareTo(BigInteger.ONE) == 0;
		return flag;
	}
	
	public static void main(String[] args){
		Scanner input = new  Scanner(System.in);
		System.out.print("Please Enter a number for n: ");
		
		BigInteger n = input.nextBigInteger();
		int count = 0;
		for(BigInteger i = BigInteger.valueOf(0); i.compareTo(n) == -1; i = i.add(BigInteger.ONE)){
			if(isCarmicheal(i) == true){
				System.out.println(i + " is a Carmicheal number");
				count++;
			}
		}	
		System.out.println("There are a total of " + count + " Carmichael ranging numbers from 1 to " + n);
		input.close();
	}
}