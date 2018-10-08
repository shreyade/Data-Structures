// Class AnagramSolver uses a dictionary of words to
// find all anagrams as a given phrase. Anagrams are
// combinations of words that have the same letters
// as a word or phrase.

import java.util.*;

public class AnagramSolver {
   private Map<String, LetterInventory> wordToInventory;
   private List<String> dictionary;
   
   // Takes a list of Strings as the parameter.
   // Constructs a new AnagramSolver object that
   // uses this list as the dictionary. Since the
   // spec said it's a nonempty list passed,
   // no exception check is needed.
   public AnagramSolver(List<String> list) {
      wordToInventory = new HashMap<String, LetterInventory>();
      dictionary = list;
      for(String word : list) {
         LetterInventory inventoryForEachWord = new LetterInventory(word);
         wordToInventory.put(word, inventoryForEachWord);
      }
   }
   
   // takes a String which is the word to create
   // anagrams of, and takes an int which is the
   // max words that the anagrams of this string
   // can contain. The combinations of words are
   // printed to System.out. If the max is less
   // than 0, at the very beginning,
   // an IllegalArgumentException is thrown. If max
   // is equal to 0, then the solver will find all possible
   // solutions with no restriction of number of words.
   public void print(String s, int max) {
      if(max < 0) {
         throw new IllegalArgumentException("The max is less than 0!");
      }
      Stack<String> matchedWords = new Stack<String>();
      List<String> dictionaryReduced = new ArrayList<String>();
      LetterInventory currentInventory = new LetterInventory(s);
      for(String word : dictionary) {
         LetterInventory subtractedInventory =
         currentInventory.subtract(wordToInventory.get(word));
         if(subtractedInventory != null) {
            dictionaryReduced.add(word);
         }
      }
      exploreRecur(currentInventory, max, 
                   matchedWords, dictionaryReduced);
   }
   
   // Takes a LetterInventory object which
   // is the current Inventory being worked with,
   // an int which is the max number of words that
   // can be in the combination, a stack of strings
   // which are the words that have been matched so far,
   // and a list of strings which is the reduced dictionary
   // of relevant words, which means currentInventory can be
   // subtracted from these words
   // This method explores and prints the combinations
   // of words that are anagrams of the passed word represented
   // as a LetterInventory
   // and includes as most max words,
   // unless max is 0 which means that unlimited number of words will
   // be printed that are anagrams of the passed word
   // represented as a LetterInventory.
   private void exploreRecur(LetterInventory currentInventory, int max,
           Stack <String> matchedWords, List<String> dictionaryReduced) {
      if(currentInventory.isEmpty()) {
         // Found an anagram solution
         System.out.println(matchedWords.toString());
      } if(matchedWords.size() < max || max == 0) {
         for(String s: dictionaryReduced) {
            LetterInventory sInventory =
            currentInventory.subtract(wordToInventory.get(s));
            if(sInventory != null) {
               matchedWords.push(s);
               exploreRecur(sInventory, max, matchedWords,
                            dictionaryReduced);
               matchedWords.pop(); // backtracking
            }
         }
      }
   }
}
