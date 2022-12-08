//Design an algorithm which, given an input DNA string of length n and size k of substrings, 
//uses a hash table of size n to compute its k-mer distribution.

//source: https://www.geeksforgeeks.org/implementing-our-own-hash-table-with-separate-chaining-in-java/

//Java program to demonstrate implementation of our
//own hash table with chaining for collision detection
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;
import java.util.zip.CRC32;

//Class to represent entire hash table
class Map<K, V> {
	// bucketArray is used to store array of chains
	private ArrayList<HashNode<K, V> > bucketArray;

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
	
	
	//=========================================================HERE=============
	
	
	//THIS IS THE PART WHERE THE OTHER HASH CODE COMES IN
	//probs put it in as another method
	private final int hashCode (K key, int choice) {
		
		switch(choice) {
		
		case 1: //built-in hashCode
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
			return rv;
			
		case 3: //CRC32
			int crc;
		    CRC32 myCRC = new CRC32( ) ;
		    myCRC.update( ((String) key).getBytes( ) ) ;
		    //System.out.println( "The CRC-32 value is : " + Long.toHexString( myCRC.getValue( ) ) + " !" ) ;
		  	crc = (int) myCRC.getValue();
		  	return crc;
		default: return 0;
		}
		
	}
/*
	//FNV1A HASH FUNCTION
	private final int hashFNV(String key) {
		final int FNV_32_INIT = 0x811c9dc5;
	    final int FNV_32_PRIME = 0x01000193;
	    
	    byte[] keyInBytes = key.getBytes(StandardCharsets.UTF_8);
	    
	    int rv = FNV_32_INIT;
        final int len = keyInBytes.length;
        for(int i = 0; i < len; i++) {
            rv ^= keyInBytes[i];
            rv *= FNV_32_PRIME;
        }
		return rv;
	}
	
	//CRC32 HASH FUNCTION
	private final int hashCRC32(String key) {
		int crc;
	    CRC32 myCRC = new CRC32( ) ;
	    myCRC.update( key.getBytes( ) ) ;
	    //System.out.println( "The CRC-32 value is : " + Long.toHexString( myCRC.getValue( ) ) + " !" ) ;
	  	crc = (int) myCRC.getValue();
	  	return crc;
	}
	
	
	//============================================================================
	
	*/
	
	// This implements hash function to find index
	// for a key
	private int getBucketIndex(K key, int choice)
	{
		int hashCode = hashCode(key, choice);
		int index = hashCode % numBuckets;
		// key.hashCode() could be negative.
		index = index < 0 ? index * -1 : index;
		return index;
	}

	// Method to remove a given key
	public V remove(K key, int choice)
	{
		// Apply hash function to find index for given key
		int bucketIndex = getBucketIndex(key, choice);
		int hashCode = hashCode(key, choice);
		// Get head of chain
		HashNode<K, V> head = bucketArray.get(bucketIndex);

		// Search for key in its chain
		HashNode<K, V> prev = null;
		while (head != null) {
			// If Key found
			if (head.key.equals(key) && hashCode == head.hashCode)
				break;

			// Else keep moving in chain
			prev = head;
			head = head.next;
		}

		// If key was not there
		if (head == null)
			return null;

		// Reduce size
		size--;

		// Remove key
		if (prev != null)
			prev.next = head.next;
		else
			bucketArray.set(bucketIndex, head.next);

		return head.value;
	}

	// Returns value for a key
	public V get(K key, int choice)
	{
		// Find head of chain for given key
		int bucketIndex = getBucketIndex(key, choice);
		int hashCode = hashCode(key, choice);
	
		HashNode<K, V> head = bucketArray.get(bucketIndex);

		// Search key in chain
		while (head != null) {
			if (head.key.equals(key) && head.hashCode == hashCode)
				return head.value;
			head = head.next;
		}

		// If key not found
		return null;
	}

	// Adds a key value pair to hash
	public void add(K key, V value, int choice)
	{
		// Find head of chain for given key
		int bucketIndex = getBucketIndex(key, choice);
		int hashCode = hashCode(key, choice);
		HashNode<K, V> head = bucketArray.get(bucketIndex);

		// Check if key is already present
		while (head != null) {
			if (head.key.equals(key) && head.hashCode == hashCode) {
				head.value = value; //change to head.value += value;?=============================here=====
				return;
			}
			head = head.next;
		}

		// Insert key in chain
		size++;
		head = bucketArray.get(bucketIndex);
		HashNode<K, V> newNode
			= new HashNode<K, V>(key, value, hashCode);
		newNode.next = head;
		bucketArray.set(bucketIndex, newNode);

			}
/*
	// Driver method to test Map class
	public static void main(String[] args)
	{
		//================================================================HERE
		//search later on which java lib Map<>() is in
        Map<String, Integer> map = new Map<>();
		map.add("this", 1,1);
		map.add("coder", 2,1);
		map.add("this", 4,1);
		map.add("hi", 5,1);
		System.out.println(map.size());
		System.out.println(map.remove("this",1));
		System.out.println(map.remove("this",1));
		System.out.println(map.size());
		System.out.println(map.isEmpty());
	}*/
}
