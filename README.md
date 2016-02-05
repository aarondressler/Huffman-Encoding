# Huffman-Encoding
A simple JAVA program that illustrates the Huffman Encoding Algorithm.

This program reads a text file into a Queue of most frequent characters.  This map is then read into a Binary Tree of sorted by the frequency of each charater in the file.  This tree is then decoded into 0s and 1s, 0 indicates the left branch, and 1 indicates the right branch.  Using this logic, the tree is then decoded back into it's original characters.  The compressed data is roughly 25% of the original data.
