/* Class LetterInventory is used to keep a
count of letters of the alphabet of a given String */

public class LetterInventory {
   public static final int LETTER_COUNT = 26;
   
   private int[] counters; // array of counters (1 for each letter)
   private int allCharCount; // total number of each of the letters in the inventory
   
   // takes a String as the parameter and counts each alphabetic
   // letter in the given string. When counting letters, ignores
   // case and does not count non alphabetic characters.
   public LetterInventory(String data) {
      counters = new int[LETTER_COUNT];
      allCharCount = 0;
      data = data.toLowerCase();
      for(int i = 0; i < data.length(); i++) {
         char charAtPosition = data.charAt(i);
         if(Character.isLetter(charAtPosition)){
            int charIndex = charAtPosition - 'a';
            counters[charIndex]++;
            allCharCount++;
         }
      }
   }
   
   // returns sum of all alphabetic characters
   //  in inventory.
   public int size() {
      return allCharCount;
   }
   
   // checks if all counts are zero & inventory is empty
   // returns a boolean value true if inventory is empty,
   // returns a boolean value false if inventory not empty.
   public boolean isEmpty() {
      return (allCharCount == 0);
   }
   
   // takes a char value as parameter
   // throws IllegalArgumentException if the char parameter
   // is not an alphabetic letter.
   // If char parameter is an alphabetic letter,
   // returns count for given letter.
   public int get(char letter) {
      if (!(Character.isLetter(letter))) {
         throw new IllegalArgumentException("The given value is nonalphabetic!");
      }
      letter = Character.toLowerCase(letter);
      int charIndex = letter - 'a';
      return counters[charIndex];
   }
   
   // returns String of inventory with the letters as per
   // the number of each alphabetic letter
   // and all in sorted ordered. This returned String
   // will be enclosed in square brackets []
   public String toString() {
      String representation = "[";
      for(int i = 0; i < LETTER_COUNT; i++) {
         char letter = (char)('a' + i);
         for(int j = 0; j < counters[i]; j++) {
            representation = representation + letter;
         }
      }
      representation = representation + "]";
      return representation;
   }
   
   // takes a LetterInventory object as parameter, and
   // returns a new LetterInventory object.
   // the new LetterInventory is a sum of this LetterInventory
   // and the other LetterInventory.
   public LetterInventory add(LetterInventory other) {
      LetterInventory result = new LetterInventory("");
      result.allCharCount = this.allCharCount + other.allCharCount;
      for(int i = 0; i < result.LETTER_COUNT; i++) {
         result.counters[i] = this.counters[i] + other.counters[i];
      }
      return result;
   }
   
   // takes a LetterInventory object as parameter, and
   // returns a new LetterInventory object.
   // the new LetterInventory is the result of subtracting
   // the other inventory from this inventory. If the
   // difference of any counter is less than zero/negative,
   // the method returns null.
   public LetterInventory subtract(LetterInventory other) {
      LetterInventory result = new LetterInventory("");
      result.allCharCount = this.allCharCount - other.allCharCount;
      for(int i = 0; i < result.LETTER_COUNT; i++) {
         result.counters[i] = this.counters[i] - other.counters[i];
         if(result.counters[i] < 0) {
            return null;
         }
      }
      return result;
   }
   
   // takes char and int values as parameters.
   // assigns the given int value to the count
   // of the letter in the counters[] array data field.
   public void set(char letter, int value) {
      letter = Character.toLowerCase(letter);
      int charIndex = letter - 'a';
      allCharCount = allCharCount - counters[charIndex] + value;
      counters[charIndex] = value;
   }
}
