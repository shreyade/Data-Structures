/* This class creates the binary
tree that is used in the HuffmanTree
class. */

import java.util.*;
import java.io.*;

public class HuffmanNode implements Comparable<HuffmanNode> {
   public String code;
   public int asciiCharacter;
   public int countFrequency;
   public HuffmanNode left;
   public HuffmanNode right;
   
   // constructs HuffmanNode object.
   // Takes an int which is the
   // character and an int which
   // is the frequency of the
   // character as parameters.
   public HuffmanNode(int asciiCharacter, int countFrequency) {
      this(asciiCharacter, countFrequency,"", null, null);
   }
   
   // constructs the HuffmanNode object.
   // Takes an int which is the
   // character and a String which
   // is the code.
   public HuffmanNode(int asciiCharacter, String code) {
      this(asciiCharacter, -1, code, null, null);
   }
   
   // constructs the HuffmanNode 
   // for every data member 
   // of the tree. 
   // This is the constructor that
   // other constructors call.
   // Takes a int for the character,
   // an int for the frequency,
   // a string for the code, a HuffmanNode
   // object which
   // represents the left of the root,
   // and a HuffmanNode object which
   // the represents the right of the root.
   public HuffmanNode(int asciiCharacter, int countFrequency, String code,
   HuffmanNode left, HuffmanNode right) {
      this.code = code;
      this.asciiCharacter = asciiCharacter;
      this.countFrequency = countFrequency;
      this.left = left;
      this.right = right;
   }
   
   // compares the frequency of the
   // current HuffmanNode to the other
   // HuffmanNode object (which is passed
   // as the parameter).
   // Returns an integer which represents
   // the comparison: negative number if
   // the other HuffmanNode's frequency is larger,
   // positive number if the other's frequency
   // is smaller, and 0 if the frequencies are the same.
   public int compareTo(HuffmanNode other) {
      return this.countFrequency - other.countFrequency;
   }
}