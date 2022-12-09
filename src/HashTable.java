//Design an algorithm which, given an input DNA string of length n and size k of substrings, 
//uses a hash table of size n to compute its k-mer distribution.

//sources: https://www.geeksforgeeks.org/implementing-our-own-hash-table-with-separate-chaining-in-java/
//http://www.java2s.com/Code/Java/Development-Class/FNVHash.htm
//https://rosettacode.org/wiki/CRC-32#Java

//Java program to demonstrate implementation of our
//own hash table with chaining for collision detection
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;
import java.util.zip.CRC32;

//Class to represent entire hash table
class Map<K, V> {
	// bucketArray is used to store array of chains
	private ArrayList<HashNode<K, Integer> > bucketArray;

	// Current capacity of array list
	private int numBuckets;

	// Current size of array list
	private int size;

	// Constructor (Initializes capacity, size and
	// empty chains.
	public Map(int numBuckets)
	{
		bucketArray = new ArrayList<>();
		this.numBuckets = numBuckets;
		size = 0;

		// Create empty chains
		for (int i = 0; i < numBuckets; i++)
			bucketArray.add(null);
	}

	public int size() { return size; }
	public boolean isEmpty() { return size() == 0; }
	
	//HASH CODES
	private final int hashCode (K key, int choice) {
		long startTime=System.nanoTime();

		switch(choice) {
		
		case 1: //built-in hashCode
			
			long endTime=System.nanoTime();
			long elapsedTime=endTime-startTime;
			System.out.println(elapsedTime+"ns");
			
			return Objects.hashCode(key);
			
		case 2: //FNV1a32
			final int FNV_32_INIT = 0x811c9dc5;
		    final int FNV_32_PRIME = 0x01000193;
		    
		    byte[] keyInBytes = ((String) key).getBytes(StandardCharsets.UTF_8);
		    
		    int rv = FNV_32_INIT;
	        final int len = keyInBytes.length;
	        for(int i = 0; i < len; i++) {
	            rv ^= keyInBytes[i];
	            rv *= FNV_32_PRIME;
	        }
	        
	        endTime=System.nanoTime();
			elapsedTime=endTime-startTime;
			System.out.println(elapsedTime+"ns");
			
			return rv;
			
		case 3: //CRC32
			int crc;
		    CRC32 myCRC = new CRC32( ) ;
		    myCRC.update( ((String) key).getBytes( ) ) ;
		  	crc = (int) myCRC.getValue();
		  	
		  	endTime=System.nanoTime();
			elapsedTime=endTime-startTime;
			System.out.println(elapsedTime+"ns");
			
		  	return crc;
		default: 
			endTime=System.nanoTime();
			elapsedTime=endTime-startTime;
			System.out.println(elapsedTime+"ns");
			
			return 0;
		}
		
	}
	
	// This implements hash function to find index
	// for a key
	private int getBucketIndex(K key, int choice)
	{
		long startTime=System.nanoTime();
		
		int hashCode = hashCode(key, choice);
		int index = hashCode % numBuckets;
		// key.hashCode() could be negative.
		index = index < 0 ? index * -1 : index;
		
		long endTime=System.nanoTime();
		long elapsedTime=endTime-startTime;
		System.out.println(elapsedTime+"ns");
		
		return index;
	}

	// Returns value for a key
	public int get(K key, int choice)
	{
		long startTime=System.nanoTime();
		// Find head of chain for given key
		int bucketIndex = getBucketIndex(key, choice);
		int hashCode = hashCode(key, choice);
	
		HashNode<K, Integer> head = bucketArray.get(bucketIndex);

		// Search key in chain
		while (head != null) {
			if (head.key.equals(key) && head.hashCode == hashCode) {
				long endTime=System.nanoTime();
				long elapsedTime=endTime-startTime;
				System.out.println(elapsedTime+"ns");
				return head.value;
			}
				
			head = head.next;
		}

		// If key not found
		return -1;
	}

	// Adds a key value pair to hash
	public int add(K key, int value, int choice)
	{
		long startTime=System.nanoTime();
		int collision = 0;
		// Find head of chain for given key
		int bucketIndex = getBucketIndex(key, choice);
		int hashCode = hashCode(key, choice);
		HashNode<K, Integer> head = bucketArray.get(bucketIndex);

		// Check if key is already present
		while (head != null) {
			collision++;
			if (head.key.equals(key) && head.hashCode == hashCode) {
				head.value += value; 
				
				long endTime=System.nanoTime();
				long elapsedTime=endTime-startTime;
				System.out.println(elapsedTime+"ns");
				
				return 0;
			}
			head = head.next;
		}

		startTime=System.nanoTime();
		
		// Insert key in chain
		size++;
		head = bucketArray.get(bucketIndex);
		HashNode<K, Integer> newNode
			= new HashNode<K, Integer>(key, value, hashCode);
		newNode.next = head;
		bucketArray.set(bucketIndex, newNode);
		
		long endTime=System.nanoTime();
		long elapsedTime=endTime-startTime;
		System.out.println(elapsedTime+"ns");
		
		return collision;
			}
	public void create(){
		Map<String, Integer> map = new Map<>(0);
	}
	public int create(String[] input, Map<String, Integer> map, int choice){
		long startTime=System.nanoTime();
		int collision = 0;
		for(int i = 0 ; i < input.length;i++){
			collision += map.add(input[i],1,choice);
		}
		
		long endTime=System.nanoTime();
		long elapsedTime=endTime-startTime;
		System.out.println(elapsedTime+"ns");
		
		return collision;
	}

}
