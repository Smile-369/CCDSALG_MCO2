import java.util.Scanner;

public class MainHash {
	public static void main(String[] args) {
		System.out.println("Enter length: ");
	  	Scanner sc = new Scanner(System.in);
	  	int n = sc.nextInt();
	  	System.out.println("Enter k: ");
	  	int k = sc.nextInt();
	  	System.out.println("Enter hash function: ");
	  	System.out.println("1 - Built-in\n2 - FNV1a32\n3 - CRC32");
	  	int choice = sc.nextInt();
	  	sc.close();
	  	
	  	String input = Substring.createRandomString((int)Math.pow(10,n));
	  	String[] kmerArray = Substring.kmerArrayCreation(input, k);
	  	Map<String, Integer> map = new Map<>(input.length());
	  	map.create(kmerArray,map,choice);
		
//		System.out.println("HELLO"+map.size());
		
		for (int i=0; i<map.size(); i++) {
			System.out.println(kmerArray[i]+ " = " + map.get(kmerArray[i], choice));
		}
		
		System.out.println(map.isEmpty());
		
	}
}
