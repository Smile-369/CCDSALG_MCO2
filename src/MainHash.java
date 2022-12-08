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
	  	
	  	String input = Substring.createRandomString(n);
	  	String[] kmerArray = Substring.kmerArrayCreation(input, k);
	  	
	
	 // Driver method to test Map class
		
			//================================================================HERE
			//search later on which java lib Map<>() is in
        Map<String, Integer> map = new Map<String, Integer>(n);
		for (int i=0; i < n; i++) {
			//key= dna string; value= occurences; hashcode= key->function->hashcode
			map.add(kmerArray[i], 1, choice);
		}
		
		System.out.println(map.size());
		
		for (int i=0; i<map.size(); i++) {
			System.out.println(kmerArray[i]+ " = " + map.get(kmerArray[i], choice));
		}
		
//		map.add("this", 1,1);
//		map.add("coder", 2,1);
//		map.add("this", 4,1);
//		map.add("hi", 5,1);
		
//		System.out.println(map.remove("this",1));
//		System.out.println(map.remove("this",1));
//		System.out.println(map.size());
		System.out.println(map.isEmpty());
		
	}
}
