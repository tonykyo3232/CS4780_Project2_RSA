/****************************************************
* RSAGenKey.java
* Generates a private key

* one input or 3 input
* Either takes the key size as input or take input as p, q, and e.
* If only one argument k is given, the program randomly picks p and q in k bits and generates a key pair.
****************************************************/

/*
	Instruction:
	Your program, "RSAGenKey.java", either takes the key size as input or  take input as p, q, and e.
	If  only one argument k is given, the program randomly picks p and q in k bits and generates a key pair. 
	For example: c:\> java  RSAGenKey 12
	Two files will be created.: pub_key.txt and pri_key.txt. pub_key.txt contains a public key in the following format:
	e = 8311
	n = 31005883
	pri_key.txt contains the corresponding private key in the following format:
	d = 11296191
	n = 31005883
	
	If the program takes p, q, and e as the input (java RSAGenKey p q e), it should generate the corresponding private key. 
	The key pair should also be saved in two files as described above. For example:
	c:\> java RSAGenKey 6551 4733 8311
	The same files pub_key.txt and pri_key.txt should be created.
*/

import java.io.*; // file IO and IOException
import java.util.Random; // random nunmber 
import java.math.BigInteger;

public class RSAGenKey {

	private static void writeFile(String fileName, String content) throws IOException {
		
		try {
		      FileWriter myWriter = new FileWriter(fileName);
		      myWriter.write(content);
		      myWriter.close();
		    } catch (IOException e) {
		      e.printStackTrace();
		}	
	}

	/*
		- based on k, find p and q that are 2 random prime numbers in range [2, 2^k]
		- calculate n
		- calculate ø(n)
		- find e, where e needs to satisfy gcd(ø(n),e)=1; 1<e<ø(n)
		- find d, where d=e^-1 mod ø(n)
	*/
	private static void genKey_1(int k) throws IOException{

		// find p and q
		Random rand = new Random(); 		       
		BigInteger p = BigInteger.probablePrime(k, rand);
		BigInteger q = BigInteger.probablePrime(k, rand);

		// calculate n
		BigInteger n = p.multiply(q);

		// calculate ø(n)
		BigInteger one = new BigInteger("1");
		p = p.subtract(one);
		q = q.subtract(one);
		BigInteger phi_n = p.multiply(q);

		// calculate e
		BigInteger e = new BigInteger("2");
		while(e.compareTo(phi_n) == -1){
			if(one.equals(e.gcd(phi_n))){
				break;
			}
			else{
				e = e.add(one);
			}
		}

		// calculate d
		BigInteger d = e.modInverse(phi_n);

	    String output_public = "e = " + e.toString() + "\nn = " + n.toString();
	    String output_private = "d = " + d.toString() + "\nn = " + n.toString();

	    // write the output files
		writeFile("pub_key.txt", output_public);
		writeFile("pri_key.txt", output_private);
	}

	/*
		when user enter p, q, e:
		- set p
		- set q
		- set e
		- calculate  n
		- calculate  ø(n)
		- calculate d
	*/
	private static void genKey_2(String p_str,String q_str, String e_str) throws IOException{

		// set p and q
		BigInteger p = new BigInteger(p_str);
		BigInteger q = new BigInteger(q_str);

		// calculate n
		BigInteger n = p.multiply(q);

		// calculate ø(n)
		BigInteger one = new BigInteger("1");
		p = p.subtract(one);
		q = q.subtract(one);
		BigInteger phi_n = p.multiply(q);

		// set e
		BigInteger e = new BigInteger(e_str);

		// calculate d
		BigInteger d = e.modInverse(phi_n);

	    String output_public = "e = " + e_str + "\nn = " + n.toString();
	    String output_private = "d = " + d.toString() + "\nn = " + n.toString();

	    // write the output files
		writeFile("pub_key.txt", output_public);
		writeFile("pri_key.txt", output_private);
	}

	public static void main(String[] args) throws IOException {

		// when user enters k
		if(args.length == 1){
			genKey_1(Integer.parseInt(args[0]));
			System.out.println("Key generated...");
		}
		// when user enters p,q and e
		else if(args.length == 3){
			genKey_2(args[0], args[1], args[2]);
			System.out.println("Key generated...");
		}
		// invalid input
		else{
			// exception
			System.out.println("Invalid arguement for RSAGenKey!");	
			System.exit(0);
		}
	}
}