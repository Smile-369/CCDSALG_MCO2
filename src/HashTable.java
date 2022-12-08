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
	private final int hashCode (K key) {
		return Objects.hashCode(key);
	}

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
	
	
	
	// This implements hash function to find index
	// for a key
	private int getBucketIndex(K key)
	{
		int hashCode = hashCode(key);
		int index = hashCode % numBuckets;
		// key.hashCode() could be negative.
		index = index < 0 ? index * -1 : index;
		return index;
	}

	// Method to remove a given key
	public V remove(K key)
	{
		// Apply hash function to find index for given key
		int bucketIndex = getBucketIndex(key);
		int hashCode = hashCode(key);
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
	public V get(K key)
	{
		long startTime=System.nanoTime();
		// Find head of chain for given key
		int bucketIndex = getBucketIndex(key);
		int hashCode = hashCode(key);
	
		HashNode<K, V> head = bucketArray.get(bucketIndex);

		// Search key in chain
		while (head != null) {
			if (head.key.equals(key) && head.hashCode == hashCode) {
				long endTime=System.nanoTime();
				long elapsedTime=endTime-startTime;
				System.out.println(elapsedTime);
				return head.value;
			}
			head = head.next;
		}

		// If key not found
		return null;
	}

	// Adds a key value pair to hash
	public void add(K key, V value)
	{
		// Find head of chain for given key
		int bucketIndex = getBucketIndex(key);
		int hashCode = hashCode(key);
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
	// Driver method to test Map class
	public void create(){
		Map<String, Integer> map = new Map<>(0);
	}
	public static void create(String[] input,Map map){
		for(int i = 0 ; i < input.length;i++){
			map.add(input[i],i);
		}
	}
	public static void main(String[] args)
	{
		//================================================================HERE
		//search later on which java lib Map<>() is in
		Substring sns =new Substring();
		String thing=sns.createRandomString((int)Math.pow(10,4));
		Map<String, Integer> map = new Map<>(thing.length());
//		map.add("this", 1);
//		map.add("coder", 2);
//		map.add("this", 4);
//		map.add("hi", 5);
//		;
		String[] input = sns.kmerArrayCreation(thing,6);
		Map.create(input,map);
		System.out.println(map.size());
		System.out.println(map.remove("this"));
		System.out.println(map.remove(input[2]));
		System.out.println(map.remove("this"));
		System.out.println(map.size());
		System.out.println(map.isEmpty());
		System.out.println(map.get(input[23]));
	}
}
