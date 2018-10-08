/* Implements the Huffman coding
 algorithm. Computes the frequency of 
 each character in a given file, builds
 a tree of codes based on the frequency.
 Each character corresponds to a code
 By constructing and reconstructing
 the tree, a text file can
 be encoded to codes and decoded
back to text accordingly.
*/

import java.io.*;
import java.util.*;
public class HuffmanTree {
   private HuffmanNode overallRoot;
   
   // Takes an array of integers as parameter.
   // Each index of the array represents
   // a character, and the value stored at
   // each index is the frequency of the
   // character. This method constructs
   // the initial HuffmanTree.
   public HuffmanTree(int[]count) {
      overallRoot = null;
      Queue<HuffmanNode> treeQueue =
                  new PriorityQueue<HuffmanNode>();
      for(int i = 0; i < count.length; i++) {
         if(count[i] > 0) {
            HuffmanNode node = new HuffmanNode(i, count[i]);
            treeQueue.add(node);
         }
      }
      HuffmanNode node = new HuffmanNode(count.length, 1); //eof character
      treeQueue.add(node);
      // creating the tree using the frequencies: 
      while(treeQueue.size() > 1){
         HuffmanNode node1 = treeQueue.remove();
         HuffmanNode node2 = treeQueue.remove();
         int combinedFrequency = node1.countFrequency +
                                 node2.countFrequency;
         HuffmanNode combinedNode =
                new HuffmanNode(-1, combinedFrequency,"", node1, node2);
         treeQueue.add(combinedNode);
      }
      overallRoot = treeQueue.remove();
      makeCode(overallRoot, "");
   }
   
   // private method implemented by HuffmanTree(int[] count)
   // constructor. Takes a HuffmanNode which is the node
   // of the tree, and a String which is the code
   // of the node. Generates the code for each node.
   // The leaf node will be used to encode & decode.
   private void makeCode(HuffmanNode node, String nodeCode) {
      if(node != null) {
         node.code = nodeCode;
         makeCode(node.left, nodeCode + "" + 0);
         makeCode(node.right, nodeCode + "" + 1);
      }
   }
   
   // Takes a PrintStream object as the
   // parameter. Writes the tree to this
   // stream in standard format, which
   // means that the character is written
   // to the file first, and the code
   // is then written to the file. The
   // character and code are written to the
   // output stream in pairs and in the
   // same order.
   public void write(PrintStream output) {
      writeInternal(overallRoot, output);
   }
   
   // Private helper method for the write
   // method. Takes a HuffmanNode object and
   // a PrintStream output as the parameters.
   // Writes the tree to the PrintStream
   // in standard format.
   private void writeInternal(HuffmanNode root,
                              PrintStream output) {
      if(root != null) {
         if(root.left == null && root.right == null) { //leaf node
            output.println(root.asciiCharacter);
            output.println(root.code);
         } else {
            writeInternal(root.left, output);
            writeInternal(root.right, output);
         }
      }
   }
   
   // Takes a Scanner object as the parameter
   // which is the file from which the
   // binary tree is reconstructed from
   // the characters and the codes. 
   public HuffmanTree(Scanner input) {
      overallRoot = new HuffmanNode(-1,-1);
      while(input.hasNextLine()) {
         int asciiCharacter = Integer.parseInt(input.nextLine());
         String code = input.nextLine();
         overallRoot = createNodeRecur(overallRoot, code,
                                       asciiCharacter, 0);
      }
   }
   
   // private helper method for the HuffmanTree(Scanner input)
   // constructor. Returns a HuffmanNode object which
   // represents the node that the method is on in the tree.
   //  Takes a HuffmanNode object, a String
   // which is the code, an integer which is the character,
   // and an integer which is the index of the character of the code.
   // Examines each bit in the code, and appropriately
   // creates nodes and builds the tree.
   // When the bit is extracted from the code, it is
   // checked on whether it is 0 or 1. If it's not 0 or
   // 1, an IllegalArgumentException is thrown.
   private HuffmanNode createNodeRecur(HuffmanNode node,
   String code,
   int asciiChar, int index) {
      if (index == code.length()) { // leafNode
         node = new HuffmanNode(asciiChar, code); 
      } else {
         char currentBit = code.charAt(index);
         if(currentBit == '0') {
            if(node.left == null) {
               node.left = new HuffmanNode(-1, -1);
            }
            node.left = createNodeRecur(node.left, code,
                                        asciiChar, index + 1);
         } else if(currentBit == '1') {
            if(node.right == null) {
               node.right = new HuffmanNode(-1, -1);
            }
            node.right = createNodeRecur(node.right,
                         code, asciiChar, index + 1);
         } else {
            throw new IllegalArgumentException("Bit not 1 or 0.");
         }
      }
      return node;
   }
   
   // Takes a BitInputStream to read the
   // individual bits from, a PrintStream
   // to write the characters that the bits
   // from the input stream correspond to.
   public void decode(BitInputStream input,
                      PrintStream output, int eof) {
      boolean endOfFile = false;
      while(!endOfFile) {
         int asciiCharacter = decodeOneChar(input);
         if(asciiCharacter != eof) {
            output.write(asciiCharacter);
         } else {
            endOfFile = true;
         }
      }
   }
   
   // takes a BitInputStream object as the parameter.
   // Reads the bits from the input. Returns an
   // integer that is the character that the bits
   // represent.
   private int decodeOneChar(BitInputStream input) {
      HuffmanNode temp = overallRoot;
      while(temp.left != null && temp.right != null) {
         if(input.readBit() == 1) {
            temp = temp.right;
         } else {
            temp = temp.left;
         }
      }
      return temp.asciiCharacter;
   }
}
