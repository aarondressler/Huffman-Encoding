/**
 * Created by aarondressler on 12/4/15.
 * Huffman Node Class, used for creating a Binary Tree of Node objects.
 */
import java.io.*;
import java.util.*;

// Stores each character, its number of occurrences, and connects to other nodes
public class HuffmanNode implements Comparable<HuffmanNode>{
    public int frequency;
    public char character;
    public HuffmanNode left;
    public HuffmanNode right;


    /**
     * Leaf constructor
     * @param frequency the frequency of the character
     * @param character the character
     */
    public HuffmanNode(char character, int frequency) {
        this.frequency = frequency;
        this.character = character;
        left = null;
        right = null;
    }

    /**
     * Simple constructor for empty parent node
     * @param frequency the frequency
     */
    public HuffmanNode(int frequency) {
        this.frequency = frequency;
        left = null;
        right = null;
    }

    /**
     * Returns a Map of Character keys and integer Frequency values
     * @param input the FileInputStream
     * @return Map a frequency map of Key: character, Value: integer frequency
     * @throws IOException
     */
    public static Map<Character, Integer> getCounts(FileInputStream input) throws IOException {
        Map<Character, Integer> map = new TreeMap<Character, Integer>();

        int code;
        while((code = input.read()) != -1) { // read through the file
            if (!map.containsKey((char) code)) {
                //  If this is a new key, add it
                map.put((char) code, 1);
            } else {
                //  Otherwise increment the count of this key
                map.put((char) code, map.get((char) code) + 1);
            }
        }
        return map;
    }

    /**
     * Is this node a leaf?
     * @return boolean
     */
    public boolean isLeaf() {
        if (left == null && right == null) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param that the other node
     * @return Integer negative if this < that, positive if this > that, 0 if equal
     */
    @Override
    public int compareTo(HuffmanNode that) {
        return this.frequency - that.frequency;
    }
}
