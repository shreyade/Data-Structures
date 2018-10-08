// Class Hangman Manager that plays a cheating
// game of Hangman. The computer cheats by
// keeping a set of words instead of one which
// matches the guessed letters so far. It maintains
// a maximal set of words that can best match the current
// set of words.

import java.util.*;

public class HangmanManager {
   private Set<String> currentWords;
   private int maxGuesses;
   private int numWrongGuesses;
   private SortedSet<Character> lettersGuessed;
   private String currentPattern;
   
   // The parameters are a string collection,
   // which is the dictionary of words,
   // an integer which is the
   // target word length,
   // and another integer which is the max number of wrong guesses
   // the player is allowed to make.
   public HangmanManager(Collection <String> dictionary, int length, int max) {
      maxGuesses = max;
      numWrongGuesses = 0;
      currentWords = new TreeSet<String>();
      currentPattern = "";
      lettersGuessed = new TreeSet<Character>();
      for(int i = 0; i < length; i++) {
         currentPattern = currentPattern + "-";
      }
      for(String word : dictionary) {
         if (word.length() == length) {
            currentWords.add(word);
         }
      }
   }
   
   // Returns a set of strings.
   // Gives the client access
   // to the set of words which is the current
   // set of words that the class HangmanManager is using.
   public Set<String> words() {
      return currentWords;
   }
   
   // Returns an integer which is the number of guesses
   // that the player has till the game ends.
   public int guessesLeft() {
      return maxGuesses - numWrongGuesses;
   }
   
   // Returns a sorted set which is the
   // current set of letters that have
   // been guessed by the user.
   public SortedSet<Character> guesses() {
      return lettersGuessed;
   }
   
   // Returns a string which is the current pattern displayed
   // for the hangman game. If the set of words is empty,
   // an IllegalStateException is thrown.
   public String pattern() {
      if(currentWords.isEmpty()) {
         throw new IllegalStateException("The set of words is empty");
      }
      String patternResult = "";
      for(int i = 0; i < currentPattern.length(); i++) {
         patternResult = patternResult +
         currentPattern.charAt(i) + " ";
      }
      return patternResult;
   }
   
   // takes a character which is the guess as the parameter.
   // returns an integer which is the number of occurences of
   // the guessed letter in the new pattern.
   // Updates the number of guesses left.
   // If number of guesses left is less than 1 or the set of
   // words is empty, an IllegalStateException is thrown.
   // If the set of words is not empty and the character
   // being guessed was already guessed.
   public int record(char guess) {
      if((maxGuesses - numWrongGuesses) < 1 ||
          currentWords.isEmpty()) {
         throw new IllegalStateException("The guesses are less than 1 or the set of words is empty!");
      }
      if(lettersGuessed.contains(guess)) {
         throw new IllegalArgumentException("This letter has already been guessed!");
      }
      lettersGuessed.add(guess);
      Map<String, Set<String>> wordsWithPattern = new TreeMap<String, Set<String>>();
      createPatternAndWordSet(guess, wordsWithPattern);
      chooseBestPattern(wordsWithPattern);
      int numOccurrences = numOccurrencesofGuessedLetter(guess);
      if(numOccurrences == 0) {
         numWrongGuesses++;
      }
      return numOccurrences;
   }
   
   // Takes a character which is the guess and map as parameters.
   // Creates the possible pattern and creates word sets matching the pattern,
   // with the words guessed so far.
   private void createPatternAndWordSet(char guess, Map<String, Set<String>> wordsWithPattern) {
      for(String word : currentWords) {
         String pattern = "";
         for(int i = 0; i < word.length(); i++) {
            if (currentPattern.charAt(i) != '-') {
               pattern = pattern + currentPattern.charAt(i);
            }  else if(guess == word.charAt(i)) {
               pattern = pattern + guess;
            } else {
               pattern = pattern + "-";
            }
         }
         addPatternWordToMap(wordsWithPattern, word, pattern);
      }
   }
   
   // Takes the map, string which is the word, and pattern
   // as parameters.
   // Saves the pattern and word set into the map.
   private void addPatternWordToMap(Map<String, Set<String>> wordsWithPattern,
   String word, String pattern) {
      if (!(wordsWithPattern.containsKey(pattern))) {
         Set<String> wordsWithSpecificPattern = new TreeSet<String>();
         wordsWithSpecificPattern.add(word);
         wordsWithPattern.put(pattern,
         wordsWithSpecificPattern);
      } else {
         Set<String> wordsWithSpecificPattern =
                 wordsWithPattern.get(pattern);
         wordsWithSpecificPattern.add(word);
      }
   }
   
   // Takes the map as parameter.
   // Chooses the pattern that has the set of words associated with it that
   // has the most words. If there is a tie, chooses the set that occurs
   // earlier in the map.
   private void chooseBestPattern(Map<String, Set<String>> wordsWithPattern) {
      int max = 0;
      Set <String> keySetOfPatterns = wordsWithPattern.keySet();
      String patternTemp = "";
      for(String patternKey : keySetOfPatterns) {
         if( wordsWithPattern.get(patternKey).size() > max) {
            max = wordsWithPattern.get(patternKey).size();
            currentWords = wordsWithPattern.get(patternKey);
            patternTemp = patternKey;
         }
      }
      currentPattern = patternTemp;
   }
   
   // Takes the letter guess as parameter and returns
   // an integer which is the number of occurences
   // of the guessed character in the current pattern.
   private int numOccurrencesofGuessedLetter(char guess) {
      int numOccurrences = 0;
      for(int i = 0; i < currentPattern.length(); i++) {
         if(currentPattern.charAt(i) == guess) {
            numOccurrences++;
         }
      }
      return numOccurrences;
   }
}
