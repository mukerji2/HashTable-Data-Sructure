
//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: P3
// Files: HashTable.java, HashTableTest
//
// Author: Shefali Mukerji
// Email: mukerji2@wisc.edu
// Lecturer's Name: Deb Deppeler
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates,
// strangers, and others do. If you received no outside help from either type
// of source, then please explicitly indicate NONE.
//
// Persons: none
// Online Sources: Piazza, StackOverflow.com
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
import java.util.Arrays;

// I am using open addressing linear probing to construct a HashTable implemented with an array of
// HashNodes.
// the hash index is found by using the hashCode of the key modulo table size.
public class HashTable<K extends Comparable<K>, V> implements HashTableADT<K, V> {

  /**
   * inner class that stores a key value pair in a node.
   * 
   * @author Shefali Mukerji
   *
   */
  protected class HashNode {
    K key;
    V value;
    boolean removed;

    protected HashNode(K key, V value) {
      this.key = key;
      this.value = value;
      removed = false;
    }

    /**
     * @return the key in the node
     */
    protected K getKey() {
      return this.key;
    }

    /**
     * @return true if the node has been removed, false otherwise
     */
    private boolean isRemoved() {
      if (this.removed = true) {
        return false;
      }
      return true;
    }

    /**
     * sets the removed field of the node
     * 
     * @param bool - the new value to set removed to
     */
    private void setRemoved(boolean bool) {
      this.removed = bool;
    }

    /**
     * @return the value of the node
     */
    private V getValue() {
      return this.value;
    }
  }

  private int initialCapacity; // the initial capacity of the hashTable array
  private double loadFactorThreshold; // maximum load factor array can reach before rehashing
  private int tableSize; // capacity of the array - gets updated with rehashing
  private int numKeys = 0; // number of keys in the hashtable
  HashTable<K, V>.HashNode[] hashArray; // the array that stores key value pairs.

  public HashTable() {
    this.initialCapacity = 11; // default initial capacity is 11
    this.loadFactorThreshold = 0.75; // default load factor threshold is 0.75
    setTableSize(initialCapacity);
    hashArray = new HashTable.HashNode[initialCapacity];
  }

  public HashTable(int initialCapacity, double loadFactorThreshold) {
    this.initialCapacity = initialCapacity;
    this.loadFactorThreshold = loadFactorThreshold;
    setTableSize(initialCapacity);
    hashArray = new HashTable.HashNode[initialCapacity];
  }

  /**
   * @return the load factor threshold of the HashTable
   */
  @Override
  public double getLoadFactorThreshold() {
    return this.loadFactorThreshold;
  }

  /**
   * @return the current load factor of the HashTable
   */
  @Override
  public double getLoadFactor() {
    return (1.0 * numKeys() / getCapacity());
  }

  /**
   * @return the capacity (table size) of the HashTable
   */
  @Override
  public int getCapacity() {
    return this.tableSize;
  }

  /**
   * indicates which collision resolution strategy is being used (open addressing linear probe)
   */
  @Override
  public int getCollisionResolution() {
    return 1;
  }

  /**
   * inserts a new key-value pair into the HashTable
   * 
   * @param key   - the key to insert
   * @param value - the value to insert
   * @throws IllegalNullKeyException, DuplicateKeyException
   */
  @Override
  public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {
    if (key == null) {
      throw (new IllegalNullKeyException());
    } else if (this.contains(key)) {
      throw (new DuplicateKeyException());
    } else {
      if (this.getLoadFactor() >= this.getLoadFactorThreshold()) {
        rehash();
      }
      int index = hash(key, hashArray);
      if (index >= 0) {
        hashArray[index] = new HashTable.HashNode(key, value);
        ++numKeys;
      }
    }
  }

  /**
   * indicates whether or not a given key is in the HashTable
   * 
   * @param key - the key to search for
   * @return true if key is in HashTable, false otherwise
   * @throws IllegalNullKeyException
   */
  protected boolean contains(K key) throws IllegalNullKeyException {
    if (key == null) {
      throw (new IllegalNullKeyException());
    } else {
      for (int i = 0; i < this.getCapacity(); ++i) {
        if (hashArray[i] != null) {
          if (hashArray[i].getKey().compareTo(key) == 0) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * hash function that uses hashCode % tableSize to find hash index
   * 
   * @param key - the key to find an index for
   * @return the index that the key will be placed in
   */
  private int hash(K key, HashTable<K, V>.HashNode[] hashArray) {
    if (key == null) {
      return -1;
    }
    int index = key.hashCode() % getCapacity();
    if (hashArray[index] == null) {
      return index;
    } else {
      for (int i = index + 1; i < getCapacity(); ++i) {
        if (hashArray[i] == null) {
          return i;
        }
      }
    }
    return -1;
  }

  /**
   * resizes and rehashes the HashTable when the load factor threshold has been reached
   */
  protected void rehash() {
    HashTable<K, V>.HashNode[] temp = new HashTable.HashNode[2 * hashArray.length];
    setTableSize(temp.length);
    int index;
    for (int i = 0; i < hashArray.length; ++i) {
      if (hashArray[i] != null) {
        index = hash(hashArray[i].getKey(), temp);
        temp[index] = hashArray[i];
      }
    }
    this.hashArray = temp;
  }

  /**
   * removes the given key from the HashTable
   * 
   * @param key - the key to remove
   * @return true if key has been removed, false otherwise
   * @throws IllegalNullKeyException
   */
  @Override
  public boolean remove(K key) throws IllegalNullKeyException {
    if (key == null) {
      throw (new IllegalNullKeyException());
    }
    if (!this.contains(key)) {
      return false;
    }
    for (int i = 0; i < hashArray.length; ++i) {
      if (hashArray[i] != null) {
        if (hashArray[i].getKey().compareTo(key) == 0) {
          if (hashArray[i].isRemoved()) {
            return false;
          }
          hashArray[i].setRemoved(true);
          numKeys--;
          return true;
        }
      }
    }
    return false;
  }

  /**
   * returns the value associated with the given key
   * 
   * @param key - the key whose value is to be found
   * @return the value associated with the key
   * @throws IllegalNullKeyException, KeyNotFoundException
   */
  @Override
  public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
    if (key == null) {
      throw (new IllegalNullKeyException());
    }
    if (!this.contains(key)) {
      throw (new KeyNotFoundException());
    }
    for (int i = 0; i < hashArray.length; ++i) {
      if (hashArray[i] != null) {
        if (hashArray[i].getKey().compareTo(key) == 0) {
          return hashArray[i].getValue();
        }
      }
    }
    return null;
  }

  /**
   * @return the number of keys in the HashTable
   */
  @Override
  public int numKeys() {
    return this.numKeys;
  }

  /**
   * sets the tableSize to the new value
   * 
   * @param tableSize - the new tableSize
   */
  private void setTableSize(int tableSize) {
    this.tableSize = tableSize;
  }

}
