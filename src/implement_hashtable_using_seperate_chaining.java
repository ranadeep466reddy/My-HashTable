import java.util.ArrayList;

class HashNode<K, V>
{
	K key;
	V value;
	HashNode<K,V> next;
	public HashNode(K key, V value)
	{
		this.key = key;
		this.value = value;
	}
}

public class implement_hashtable_using_seperate_chaining<K, V>
{
	private ArrayList<HashNode<K,V>> bucketArray;
	private int numBuckets;
	private int size;
	
	public implement_hashtable_using_seperate_chaining()
	{
		bucketArray = new ArrayList<>();
		numBuckets = 10;
		size = 0;
		
		for(int i = 0; i < numBuckets; i++)
			bucketArray.add(null);
	}
	public int getBucketIndex(K key)
	{
		int hashCode = key.hashCode();
		int index = hashCode%numBuckets;
		return index;
	}
	public void add(K key, V value)
	{
		int bucketIndex = getBucketIndex(key);
		HashNode<K,V> head = bucketArray.get(bucketIndex);
		
		while(head != null)
		{
			if(head.key.equals(key))
			{
				head.value = value;
				return;
			}
			head = head.next;
		}
		size++;
		head = bucketArray.get(bucketIndex);
		HashNode<K,V> newNode = new HashNode<K,V>(key,value);
		newNode.next = head;
		bucketArray.set(bucketIndex, newNode);
		
		if((0.1*size)/numBuckets >= 0.7)
		{
			ArrayList<HashNode<K,V>> temp = bucketArray;
			bucketArray = new ArrayList<>();
			numBuckets = 2 * numBuckets;
			size = 0;
			for(int i = 0; i < numBuckets; i++)
				bucketArray.add(null);
			
			for(HashNode<K,V> headNode : temp)
			{
				while(headNode != null)
				{
					add(headNode.key, headNode.value);
					headNode = headNode.next;
				}
			}
		}
	}
	public int size()
	{
		return size;
	}
	public boolean isEmpty()
	{
		return size == 0;
	}
	public V remove(K key)
	{
		int bucketIndex = getBucketIndex(key);
		HashNode<K,V> head = bucketArray.get(bucketIndex);
		HashNode<K,V> prev = null;
		
		while(head != null)
		{
			if(head.key.equals(key))
				break;
			prev = head;
			head = head.next;
		}
		if(head == null)
			return null;
		size--;
		
		if(prev != null)
			prev.next = head.next;
		else
			bucketArray.set(bucketIndex, head.next);
		return head.value;
	}
	
    public static void main(String[] args)
    {
        implement_hashtable_using_seperate_chaining<String, Integer>map = new implement_hashtable_using_seperate_chaining<>();
        map.add("this",1 );
        map.add("coder",2 );
        map.add("this",4 );
        map.add("hi",5 );
        System.out.println(map.size());
        System.out.println(map.remove("this"));
        System.out.println(map.remove("this"));
        System.out.println(map.size());
        System.out.println(map.isEmpty());
    }
}