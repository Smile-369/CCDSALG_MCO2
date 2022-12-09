import java.util.Scanner;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;

public class MainHash {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter length: ");
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
	  	//int collision =0;
	  	int collision = input.length() - map.create(kmerArray,map,choice) - map.size();
	  	//int collision = map.values().stream().map(Set::size).reduce(0, Integer::sum) - map.size();


	  	
	  	try {
	        FileWriter fileWriter = new FileWriter("HashTables.txt");
	        for (int i=0; i<map.size(); i++) {
				if(map.get(kmerArray[i], choice) != -1)
					fileWriter.write(kmerArray[i]+ " = " + map.get(kmerArray[i], choice)+"\n");
			}
	        fileWriter.close();
	        
	        System.out.println("Successfully wrote to the file.");
	      } catch (IOException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	      }
		
		System.out.println("The number of collisions is " + collision);
		
	}
}
