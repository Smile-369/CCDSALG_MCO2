//Design an algorithm which, given an input DNA string of length n and size k of substrings, 
//uses a hash table of size n to compute its k-mer distribution.

//source: https://www.geeksforgeeks.org/implementing-our-own-hash-table-with-separate-chaining-in-java/

//Java program to demonstrate implementation of our
//own hash table with chaining for collision detection
import java.util.ArrayList;
import java.util.Objects;


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
	public Map()
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
	
	
	//======================================
	
	
	//THIS IS THE PART WHERE THE HASH CODE COMES IN
	private final int hashCode (K key) {
		return Objects.hashCode(key);
	}

	
	private final int hashFNV(K key) {
		
		/* DIS FROM THE VID
		uint32_t fnv1a32(const char *data, size_t len) {
			uint32_t hash = 2166136261;
			for (char *byte = data; byte < (data+len); byte++) {
				hash ^= *byte;
				hash *= 16777619;
			}
			return hash;
		}
		*/
		return -1;
	}
		
	private final int hashCRC32(K key) {
			
		/* THIS FROM THE VIDEO
		static const uint32_t crc32tab[256] = {/*....**};
		
		uint32_t crc32(const char *data, size_t len) {
			uint32_t crc = 0xffffffff;
			const char *ptr;
			
			for (ptr = data; ptr < (data + len); ptr++) {
				crc = (crc >> 8) ^ crc32tab[(crc ^ (*ptr)) & 0xff];
			}
			return crc;
		}
		*/
		return -1;
	}
	
	//========================================
	
	
	
	// This implements hash function to find index
	// for a key
	private int getBucketIndex(K key)
	{
		int hashCode = hashCode(key); //hashFNV(key) or hashCRC32(key)
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
		int hashCode = hashCode(key); //hashFNV(key) or hashCRC32(key)
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
		// Find head of chain for given key
		int bucketIndex = getBucketIndex(key);
		int hashCode = hashCode(key); //hashFNV(key) or hashCRC32(key)
	
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
	public void add(K key, V value)
	{
		// Find head of chain for given key
		int bucketIndex = getBucketIndex(key);
		int hashCode = hashCode(key); //hashFNV(key) or hashCRC32(key)
		HashNode<K, V> head = bucketArray.get(bucketIndex);

		// Check if key is already present
		while (head != null) {
			if (head.key.equals(key) && head.hashCode == hashCode) {
				head.value = value;
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

		// If load factor goes beyond threshold, then
		// double hash table size
		if ((1.0 * size) / numBuckets >= 0.7) {
			ArrayList<HashNode<K, V> > temp = bucketArray;
			bucketArray = new ArrayList<>();
			numBuckets = 2 * numBuckets;
			size = 0;
			for (int i = 0; i < numBuckets; i++)
				bucketArray.add(null);

			for (HashNode<K, V> headNode : temp) {
				while (headNode != null) {
					add(headNode.key, headNode.value);
					headNode = headNode.next;
				}
			}
		}
	}

	// Driver method to test Map class
	public static void main(String[] args)
	{
		Map<String, Integer> map = new Map<>();
		map.add("this", 1);
		map.add("coder", 2);
		map.add("this", 4);
		map.add("hi", 5);
		System.out.println(map.size());
		System.out.println(map.remove("this"));
		System.out.println(map.remove("this"));
		System.out.println(map.size());
		System.out.println(map.isEmpty());
	}
}
