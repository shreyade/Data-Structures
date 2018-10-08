// A binary tree which represents a game
// of 20 questions will be constructed
// using a text file. In this game,
// the computer will try to guess
// the object by asking a series of yes/no
// questions. Every time the computer
// loses a game, it becomes more intelligent,
// because it takes a new question from the user
// and adds it to it's knowledge.

import java.util.*;
import java.io.*;

public class QuestionTree {
    private Scanner console;
    private QuestionNode finalRoot;
   
   // constructs a question tree with
   // a leaf node. The leaf node stores
   // the object "computer".
   public QuestionTree() {
      console = new Scanner(System.in);
      finalRoot = new QuestionNode("computer");
   }
   
   // takes a Scanner as the parameter.
   // Replaces current tree with
   // the new tree. The new tree will
   // represent the information that is
   // provided in the file and the contents
   // of the file are accessed with the
   // Scanner.
   public void read(Scanner input) {
      finalRoot = readInternal(input);
   }
   
   // This is the helper method for the
   // public read method. It reads the lines
   // from the file as pairs, and appropriately
   // creates and returns a QuestionNode based
   // on whether it's a question or an
   // answer. If it's neither, an IllegalArgumentException
   // is thrown.
   private QuestionNode readInternal(Scanner input) {
      String type = input.nextLine();
      String data = input.nextLine();
      if(type.equals("Q:")) { //question node
         QuestionNode left = readInternal(input);
         QuestionNode right = readInternal(input);
         return new QuestionNode(data, left, right);
      } else if(type.equals("A:")) { // answer node
         QuestionNode root = new QuestionNode(data);
         return root;
      } else {
         throw new IllegalArgumentException
         ("Not a question or an answer!");
      }
   }
   
   // The parameter is a PrintStream output file.
   // Prints the current tree to this output file.
   public void write(PrintStream output) {
      writeInternal(finalRoot, output);
   }
   
   // Helper method for the public write method.
   // takes a QuestionNode which is the root of the tree
   // and an output file as the parameters.
   // It prints to the output file the data of
   // the tree either with an "A:" if it is an
   // answer node, or "Q:" if it is a
   // question node.
   private void writeInternal(QuestionNode root, PrintStream output) {
      if(root != null) {
         if(root.isLeafNode()) { // leaf node
            output.println("A:");
            output.println(root.data);
         } else {
            output.println("Q:");
            output.println(root.data);
         }
         writeInternal(root.left, output);
         writeInternal(root.right, output);
      }
   }
   
   // private method that returns
   // a boolean on whether the given node
   // in the parameter is a leaf node.
   // This function is called multiple
   // times throughout the program. 
   private boolean isLeafNode() {
      return (this.left == null && this.right == null);
   }
   
   // uses the current tree and asks
   // a series of yes/ no questions.
   // The purpose of these questions is to either
   // guess the object correctly or fail.
   // If fail, then the tree is expanded
   // and the new question is included in the
   // tree as well as the object which is
   // the answer.
   public void askQuestions() {
      finalRoot = askQuestionsInternal(finalRoot);
   }
   
   //This is the helper method for the public askQuestions method.
   // takes a QuestionNode as parameter which is the
   // root of the tree, and returns a QuestionNode object
   // which is either the root of the tree with
   // it's data changed,
   // or it's left / right changed, or unchanged.
   // It is assumed that the data is created
   // that either both left and right nodes are present
   // (question node), or neither left or right are
   // present (answer node).
   private QuestionNode askQuestionsInternal(QuestionNode root) {
      if(root.isLeafNode()) { // leaf node
         if(!(yesTo("Would your object happen to be " +
                     root.data + "?"))) {
            System.out.print("What is the name of your object? ");
            String answer = console.nextLine();
            System.out.println("Please give me a yes/no question that ");
            System.out.println("distinguishes between your object ");
            System.out.print("and mine--> ");
            String ques = console.nextLine();
            QuestionNode answerQuestionNode =
                        new QuestionNode(answer);
            QuestionNode originalAnsQuestionNode =
                        new QuestionNode(root.data);
            if(yesTo("And what is the answer for your object?")){
               root = new QuestionNode(ques, answerQuestionNode,
                                       originalAnsQuestionNode);
            } else {
               root = new QuestionNode(ques,
                                      originalAnsQuestionNode,
                                      answerQuestionNode);
            }
         } else {
            System.out.print("Great, I got it right!");
            System.out.println();
         }
         return root;
      } else { // recur down the tree based on user response.
         if(yesTo(root.data)) {
            root.left = askQuestionsInternal(root.left);
         } else {
            root.right = askQuestionsInternal(root.right);
         }
         return root;
      }
   }
   
   // post: asks the user a question, forcing an answer of "y" or "n";
   //       returns true if the answer was yes, returns false otherwise
   public boolean yesTo(String prompt) {
      System.out.print(prompt + " (y/n)? ");
      String response = console.nextLine().trim().toLowerCase();
      while (!response.equals("y") && !response.equals("n")) {
         System.out.println("Please answer y or n.");
         System.out.print(prompt + " (y/n)? ");
         response = console.nextLine().trim().toLowerCase();
      }
      return response.equals("y");
   }
}