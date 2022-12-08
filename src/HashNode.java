//source: https://www.geeksforgeeks.org/implementing-our-own-hash-table-with-separate-chaining-in-java/

//A node of chains

public class HashNode<K, V> {
	K key;
	int value;
	final int hashCode;

	// Reference to next node
	HashNode<K, V> next;

	// Constructor
	public HashNode(K key, int value, int hashCode)
	{
		this.key = key;
		this.value = value;
		this.hashCode = hashCode;
	}
}
