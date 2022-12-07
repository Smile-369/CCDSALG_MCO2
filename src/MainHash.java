import java.util.Scanner;

public class MainHash {
	public static void main(String[] args) {
		System.out.println("Enter length: ");
	  	Scanner sc = new Scanner(System.in);
	  	int n = sc.nextInt();
	  	System.out.println("Enter k: ");
	  	int k = sc.nextInt();
	  	sc.close();
	  	
	  	String input = Substring.createRandomString(n);
	  	String[] kmerArray = Substring.kmerArrayCreation(input, k);
	  	
	  	
	}
}
