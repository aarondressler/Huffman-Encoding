/**
 * Created by aarondressler on 12/4/15.
 * HUFFMAN TREE -- CS211 - Chapter 18, Assignment #10
 * HuffmanTree class, contains methods that read a file into characters, builds a Binary Tree depending on the most
 * frequent characters, prints the tree, encodes the tree into 0's and 1's, and decompresses encoded string back into the
 * original characters.
 */
import java.io.*;
import java.util.*;

public class HuffmanTree {
    private HuffmanNode overallRoot;
    Map<Character, Integer> counts; // holds the frequency of the characters
    Queue<HuffmanNode> pq;

    /**
     * Constructor
     * @param counts Map of Character Counts
     */
    public HuffmanTree(Map<Character, Integer> counts) {
        this.counts = counts;
        this.counts.put((char) 4, 1);       // put EOF character
        pq = storeNodes();
        createTree();
    }

    /**
     *  Helper function to build the Priority Queue of HuffmanNodes
     * @return PriorityQueue the queue of nodes
     */
    private PriorityQueue<HuffmanNode> storeNodes() {
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<HuffmanNode>();   // Init an empty priority queue
        for(Map.Entry<Character, Integer> entry : counts.entrySet()) {      // loop through the entries in the map
            pq.add(new HuffmanNode(entry.getKey(), entry.getValue()));      // add the entry to the priority queue
        }
        return pq;
    }

    /**
     * Takes nodes in PriorityQueue pq and builds a Binary Tree
     *
     *
     */
    private void createTree() {
        while(pq.size() > 1) {
            //  Get the first two nodes (2 minimum values)
            HuffmanNode left = pq.remove();
            HuffmanNode right = pq.remove();
            //  Make them siblings
            HuffmanNode parent = new HuffmanNode(left.frequency + right.frequency);
            parent.left = left;
            parent.right = right;

            //  Add the new parent back into the PriorityQueue (sorted by frequency)
            pq.add(parent);
        }
        overallRoot = pq.peek(); // point overallRoot to the root node
    }

    /**
     * Takes the string InputFile and compresses into 1's and 0's.
     * @param inputFile the InputStream file
     * @return StringBuilder
     * @throws IOException
     */
    public StringBuilder compress(InputStream inputFile) throws IOException {
        int code;
        StringBuilder result = new StringBuilder();
        while((code = inputFile.read()) != -1) {        // read through the inputFile
            result.append(findPath(code));
        }
        result.append(findPath(4));                     //  add the EOF character!
        return result;
    }

    /**
     * Decompresses the HuffmanTree, back into the original character sequence by
     * traversing the tree until a character is found.
     * @param inputString the string of 0's and 1's that leads to a character
     * @return StringBuilder the decompressed string of characters
     */
    public StringBuilder decompress(StringBuilder inputString) {
        StringBuilder output = new StringBuilder();
        HuffmanNode root = overallRoot;
        for(int i = 0; i < inputString.length(); i++) {
            char path = inputString.charAt(i);      // 0 or 1 = Left or Right
            root = findCharacterNode(root, path);   // Return the Node at the bit path (pointer)
            if (root.isLeaf()) {                    // this node has a character
                output.append(root.character);          // append the char
                root = overallRoot;                     // point back to overallRoot
            }
        }
        return output;
    }

    /**
     * Helper function to follow the path of 0's and 1's back to the proper character in the Tree.
     *
     * @param root HuffmanNode - the node we're working with
     * @param path Either 0 or 1, 0 goes left, 1 goes right
     * @return HuffmanNode - if leaf, we've reached our target, otherwise, return left or right child depending on path.
     */
    private HuffmanNode findCharacterNode(HuffmanNode root, char path) {
        if (root.isLeaf()) {            // if this is a leaf:
            return root;                // don't look any further
        } else {                        // otherwise:
            if (path == '0') {           // go left
                return root.left;
            } else {                    // go right
                return root.right;
            }
        }
    }

    /**
     * Finds the "path" of the character code we are looking for.  Calls the recursive method
     * with default parameters.
     * @param code the character code we are searching for
     * @return String - the path of the code through the HuffmanTree (0=left, 1=right)
     */
    private String findPath(int code) {
        return findPath(code, overallRoot, "");
    }

    /**
     * Recursive method to build a "path" of 0's and 1's starting at the root node
     * navigating to the leaf containing the character code we want.
     *
     * @param code the Integer value of the character we are looking for
     * @param root the current node we are working with
     * @param path the string path of the node we're in
     * @return String - a string of 0's and 1's indicating the path taken to reach the
     */
    private String findPath(int code, HuffmanNode root, String path) {
        String result;
        if (root.isLeaf()) {
            result = ((char) code == root.character) ? path : null;
        } else {
            if ((result = findPath(code, root.left, path + '0')) == null) {
                result = findPath(code, root.right, path + '1');
            }
        }
        return result;
    }

    /**
     * Prints the contents of the HuffmanTree in sideways orientation
     * @return  String
     */
    public String printSideways() {
        return printSideways(overallRoot, 0);
    }

    /**
     * Recursive method to traverse the tree and print the nodes
     * @param root HuffmanNode - The root node we are working with in this iteration
     * @param level Integer - the level of the node we are working with
     * @return String a string representation of the node contents, formatted with indentation
     */
    private String printSideways(HuffmanNode root, int level) {
        String string = "";
        if (root != null) {
            string += printSideways(root.right, level + 1);
            for (int i = 0; i < level; i++) {
                string += "    ";
            }
            if (root.character == '\0') {
                string += root.frequency+"=count";
            } else {
                string += root.frequency+"=char("+ (int) root.character+") " + root.character;
            }
            string += "\n\n";
            string += printSideways(root.left, level + 1);
        }
        return string;
    }
}
