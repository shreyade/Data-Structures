// Class GrammarSolver reads an input file with
// grammar in Backnus-Naur form and manipulates the
// grammar, and randomly generates elements
// of the grammar.

import java.util.*;

public class GrammarSolver {
   private Random rand; // use same random object for all randomization
   private SortedMap<String, String[]> grammarMap;
   
   // Takes a String List as the parameter and stores it.
   // If the list passed is empty, it throws an IllegalArgumentException.
   // If there are two or more entries in the grammar for the same
   // non terminal, an IllegalArgumentException is again
   // thrown.
   public GrammarSolver(List<String> grammar) {
      if(grammar.size() == 0) {
         throw new IllegalArgumentException("The grammar list is empty!");
      }
      grammarMap = new TreeMap<String, String[]>();
      rand = new Random();
      for(String s: grammar) {
         String[] split = s.split("::=");
         String nonTerminal = split[0];
         if(grammarMap.containsKey(nonTerminal)) {
            throw new IllegalArgumentException("two or more "
            + "entries in same non terminal: "
            + nonTerminal);
         }
         String[] terminals = split[1].split("[|]");
         grammarMap.put(nonTerminal, terminals);
      }
   }
   
   // Takes a string which is the symbol as the parameter.
   // returns a boolean value. If the symbol is a nonterminal,
   // it returns true. If the symbol is not a nonterminal,
   // it returns false.
   public boolean grammarContains(String symbol) {
      return (grammarMap.containsKey(symbol));
   }
   
   // The parameters are a
   // string which is the symbol and an integer
   // which is the number of times to randomly generate
   // the number of occurrences.
   // returns an array of randomly generated results.
   // Throws an IllegalArgumentException if the grammar
   // does not have the nonterminal symbol passed or if
   // the times passed is less than 0.
   public String[] generate(String symbol, int times) {
      if((!(grammarMap.containsKey(symbol))) || times < 0) {
         throw new IllegalArgumentException("The grammar does" +
         " not contain the" +
         " passed symbol" +
         " or the times is less" +
         " than zero.");
      }
      String[] generatedArray = new String[times];
      for(int i = 0; i < times; i++) {
         generatedArray[i] = generate_recur(symbol);
      }
      return generatedArray;
   }
   
   // Returns a string which is the nonterminal symbols
   // which are sorted, in a comma separated list and
   // has square brackets on each side.
   public String getSymbols() {
      return grammarMap.keySet().toString();
   }
   
   // Takes a string symbol as parameter which is the
   // non terminal of the grammar and solves
   // the grammar to generate results. Returns the
   // generated results as a string.
   private String generate_recur(String symbol) {
      if(!(grammarMap.containsKey(symbol))) { // terminal symbol
         return symbol + " ";
      } else { // non terminal symbol
         String[] rules = grammarMap.get(symbol);
         String result = "";
         int randomIndex = rand.nextInt(rules.length); // generate a random number between
                                                      // 0 and last index in the array
         String grammarRule = rules[randomIndex];
         String[] individualStringParts = grammarRule.split("[ \t]+");
         for(String ruleSymbol : individualStringParts) {
            ruleSymbol = ruleSymbol.trim(); // removes leading & trailing spaces
            if(!(ruleSymbol.equals(""))) { // don't generate for empty string
               String resultOfRecursion = generate_recur(ruleSymbol);
               result = result + resultOfRecursion;
            }
         }
         return result;
      }
   }
}