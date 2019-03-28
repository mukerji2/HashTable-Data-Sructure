/**
 * Filename: MyProfiler.java Project: p3b-201901 Authors: Shefali Mukerji lecture
 *
 * Semester: Spring 2019 Course: CS400
 * 
 * Due Date: 3/28/19 10 pm Version: 1.0
 * 
 * Credits: TODO: name individuals and sources outside of course staff
 * 
 * Bugs: TODO: add any known bugs, or unsolved problems here
 */

// Used as the data structure to test our hash table against
import java.util.TreeMap;

public class MyProfiler<K extends Comparable<K>, V> {

  HashTableADT<K, V> hashtable;
  TreeMap<K, V> treemap;

  public MyProfiler() {
    // TODO: complete the Profile constructor
    // Instantiate your HashTable and Java's TreeMap
    hashtable = new HashTable<>();
    treemap = new TreeMap<>();
  }

  public void insert(K key, V value) throws DuplicateKeyException, IllegalNullKeyException {
    // TODO: complete insert method
    treemap.put(key, value);
    hashtable.insert(key, value);
  }

  public void retrieve(K key) throws KeyNotFoundException, IllegalNullKeyException {
    // TODO: complete the retrieve method
    treemap.get(key);
    hashtable.get(key);
  }

  public static void main(String[] args) {
    try {
      int numElements = Integer.parseInt(args[0]);


      // TODO: complete the main method.
      // Create a profile object.
      // For example, Profile<Integer, Integer> profile = new Profile<Integer, Integer>();
      // execute the insert method of profile as many times as numElements
      // execute the retrieve method of profile as many times as numElements
      // See, ProfileSample.java for example.
      MyProfiler<Integer, Integer> profile = new MyProfiler<Integer, Integer>();
      for (Integer i = 0; i <= numElements; i++) {
        profile.insert(i, i);
        profile.retrieve(i);
      }
      String msg = String.format("Inserted and retreived %d (key,value) pairs", numElements);
      System.out.println(msg);
    } catch (Exception e) {
      System.out.println("Usage: java MyProfiler <number_of_elements>");
      System.exit(1);
    }
  }
}
