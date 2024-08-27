package strings;

import java.util.*;

/**
 * Implement the class WordCounter that counts the number of occurrences
 * of each word in a given piece of text.
 * Feel free to use existing java classes.
 */
public class WordCounter implements Iterable<String> {

    private HashMap<String, Integer> mapofCount;
    private TreeSet<String> wordTree;

    public WordCounter() {
        this.mapofCount = new HashMap<>();
        this.wordTree = new TreeSet<>();
        //could also use only mapOfCount but implement it as a TreeMap, so you get the lexical ordering and for iterator() return mapOfCount.keySet().iterator()
    }

    /**
     * Add the word so that the counter of the word is increased by 1
     */
    public void addWord(String word) {
        this.wordTree.add(word);
        if (!this.mapofCount.containsKey(word)) {
            this.mapofCount.put(word, 1);
        } else {
            this.mapofCount.computeIfPresent(word, (key, value) -> value +1);
        }
    }

    /**
     * Return the number of times the word has been added so far
     */
    public int getCount(String word) {
         if (!this.mapofCount.containsKey(word)) return 0;
         return this.mapofCount.get(word);
    }

    // iterate over the words in ascending lexicographical order
    @Override
    public Iterator<String> iterator() {
         return wordTree.iterator();
    }
}
