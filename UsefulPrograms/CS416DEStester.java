
import java.io.*;
import java.security.*;

import javax.crypto.*;

import sun.misc.*;
import java.util.Base64;


public class CS416DEStester
{
	public static void main (String[] args) throws Exception
	{

		System.out.println("Program to encrypt and decrypt Text using the Symmetric DES algorithm \n\n");

		// Check arguments
		if (args.length<2)
		{
			System.out.println("Usage: DEStester -e|-d text");
			return;
		}

		// Get or generate key

		Key key;
		try
		{
			ObjectInputStream in = new ObjectInputStream(
					new FileInputStream("DESSecretKey.ser"));

			key= (Key)in.readObject();

			in.close();
		}
		catch( FileNotFoundException fnfe)
		{

			System.out.println("KEy file not found, rolling my own now \n\n");
			KeyGenerator generator = KeyGenerator.getInstance("DES");
			generator.init(new SecureRandom());
			key=generator.generateKey();
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream("key.ser"));
			out.writeObject(key);
			out.close();
		}



		byte[] pretty_key = key.getEncoded();

		String hex_output="";

		String eye;

		for(int i=0;i<pretty_key.length;i++)
		{

			eye= byteToHex(pretty_key[i]);

			hex_output+=eye;
			if(i<pretty_key.length-1)
			{
				hex_output+=":";
			}
		}

		System.out.println("__________________________________________\n");

		System.out.println("key is "+hex_output+"\n\n");



		System.out.println("length of the key is "+pretty_key.length+" bytes \n");

		System.out.println("__________________________________________\n");

		// Get a cipher object
		Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");

		// Encrypt/Decrypt the input string

		if(args[0].indexOf("e") !=-1)
		{
			cipher.init(Cipher.ENCRYPT_MODE, key);
			String amalgam=args[1];
			for(int i=2;i<args.length;i++)
			{
				amalgam+= " "+args[i];
			}
			byte[] stringBytes = amalgam.getBytes("UTF8");
			byte[] raw = cipher.doFinal(stringBytes);
			byte[] encodedBytes = Base64.getEncoder().encode(raw);
			String b64output =new String(encodedBytes);
		    System.out.println("ciphertext (in Base64) is  " +b64output);



		}
		else if(args[0].indexOf("d") !=-1)
		{
			cipher.init(Cipher.DECRYPT_MODE, key);
						byte[] raw = Base64.getDecoder().decode(args[1]);
						byte[] stringBytes = cipher.doFinal(raw);
						String result = new String( stringBytes, "UTF8");
			System.out.println("The recovered Plaintext is "+result);


		}
		System.out.println("__________________________________________\n");
	}
	/**
	* Convenience method to convert a byte to a hex string.
	*
	* @param data the byte to convert
	* @return String the converted byte
	*/
	public static String byteToHex(byte data)
	{
	StringBuffer buf = new StringBuffer();
	buf.append(toHexChar((data>>>4)&0x0F));
	buf.append(toHexChar(data&0x0F));
	return buf.toString();
	}

	/**
	* Convenience method to convert an int to a hex char.
	*
	* @param i the int to convert
	* @return char the converted char
	*/
	public static char toHexChar(int i)
	{
	if ((0 <= i) && (i <= 9 ))
	return (char)('0' + i);
	else
	return (char)('A' + (i-10));
	}

}