/****************************************************
* RSAGenKey.java
* Generates a private key

* one input or 3 input
* Either takes the key size as input or take input as p, q, and e.
* If only one argument k is given, the program randomly picks p and q in k bits and generates a key pair.
****************************************************/


/*
* user enter k:
1. based on k, find p and q that are 2 random prime numbers in range [2, 2^k]
2. calculate n
3. calculate ø(n)
4. find e , where e needs to satisfy gcd(ø(n),e)=1; 1<e<ø(n)
5. find d, where d=e^-1 mod ø(n)

* user enter p, q, e:
1. set p
2. set q
3. set e
4. calculate  n
5. calculate  ø(n)
6. calculate d

* write (e,n) and (e,d) into 2 .txt files

*/


import java.io.*; // file IO and IOException
import java.util.Random; // random nunmber 
import java.lang.Math;

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

        Random rand = new Random(); 
  		
  		int max = (int) Math.pow(2,k);
  		int min = 2;
  		
		// Generate random integers in range 2 to 2^k
        int p = rand.nextInt(max - min) + min; 
        int q = rand.nextInt(max - min) + min;

  		while(isPrime(p) == false || isPrime(q) == false){
	        p = rand.nextInt(max - min) + min; 
	        q = rand.nextInt(max - min) + min;

	        // debug
	        // System.out.println("p is: " + p);
	        // System.out.println("q is: " + q);
  		}

	   	int n = p * q;
	   	int phi_n = (p-1) * (q-1);
	   	int e = 2; // default value for smallest prime
	   	while (e < phi_n) { 
	        if (gcd(e, phi_n) == 1){
	        	break;
	        } 
	        else{
	            e++; 
	        }
	    }

	    int d = modular_inverse(e,phi_n);
	    if(d == -1){
	    	System.out.println("weird...");
	    }

	    // debug
	    System.out.println("--------------------------");
	    System.out.println("k is:" + k);
	    System.out.println("p is:" + p);
	    System.out.println("q is:" + q);
	    System.out.println("n is:" + n);
	    System.out.println("ø(n) is:" + phi_n);
	    System.out.println("e is:" + e);
	    System.out.println("d is:" + d);
	    System.out.println("--------------------------");

	    String output_public = "e = " + e + "\nn = " + n;
	    String output_private = "e = " + e + "\nd = " + d;

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
	private static void genKey_2(int p,int q, int e) throws IOException{

		int n = p * q;
	   	int phi_n = (p-1) * (q-1);
		int d = modular_inverse(e,phi_n);
	    if(d == -1){
	    	System.out.println("weird...");
	    }

	    // debug
	    System.out.println("--------------------------");
	    System.out.println("p is:" + p);
	    System.out.println("q is:" + q);
	    System.out.println("n is:" + n);
	    System.out.println("ø(n) is:" + phi_n);
	    System.out.println("e is:" + e);
	    System.out.println("d is:" + d);
	    System.out.println("--------------------------");

	    String output_public = "e = " + e + "\nn = " + n;
	    String output_private = "e = " + e + "\nd = " + d;

	    // write the output files
		writeFile("pub_key.txt", output_public);
		writeFile("pri_key.txt", output_private);
	}

	// helper function - Euclidean Algorithm
	private static int gcd(int num1, int num2){
		int r1 = num1,
			r2 = num2,
			q = 0,
			r = 0;

		while(r2 > 0){
			q = r1/r2;
			r = r1-q*r2;
			r1 = r2;
			r2 = r;
		}
		return r1;
	}

	// helper function - isPrime
	private static boolean isPrime(int num){
	    
	    if (num <= 1)
	        return false;
	 
	    for (int i = 2; i < num; i++)
	        if (num % i == 0)
	            return false;
	 
	    return true;
	}

	// helper function - Modular Inverses
	// num1 should always greater than num2
	private static int modular_inverse(int num1, int num2){

		// first check gcd
		if(gcd(num1, num2) != 1){
			return -1;
		}

		int mod_num = 1;
		while((num1 * mod_num) % num2 != 1){
			mod_num++;
		}
		return mod_num;
	}

	public static void main(String[] args) throws IOException {
		
		int option = 0;

		// when user enters k
		if(args.length == 1){
			genKey_1(Integer.parseInt(args[0]));
		}
		// when user enters p,q and e
		else if(args.length == 3){
			genKey_2(Integer.parseInt(args[0]),Integer.parseInt(args[1]),Integer.parseInt(args[2]));
		}
		// invalid input
		else{
			// exception
		}
	}
}