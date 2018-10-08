// This class creates the binary search
// tree that is used in the QuestionTree
// class. This is based off the section
// handout on binary search trees.

public class QuestionNode {
   private String data;
   private QuestionNode left;
   private QuestionNode right;
   
   // constructs the leaf node
   // of the tree. Takes a string
   // as the parameter.
   public QuestionNode(String data) {
      this(data, null, null);
   }
   
   // constructs the branch node
   // of the tree. Takes a string,
   // a QuestionNode object which represents
   // the left of the root,
   // and a QuestionNode object which
   // the represents the right of the root.
   public QuestionNode(String data, QuestionNode left, QuestionNode right) {
      this.data = data;
      this.left = left;
      this.right = right;
   }
   
}