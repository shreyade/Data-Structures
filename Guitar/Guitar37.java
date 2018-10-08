
public class Guitar37 implements Guitar { 
    public static final String KEYBOARD =
        "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' "; 
    
    private GuitarString [] arrayOfGuitarStrings; 
    private int numTicTimes; 

    // Constructor which initializes all the guitar strings.
    // Also initializes the number of tics.s
    public Guitar37() {
         arrayOfGuitarStrings = new GuitarString[KEYBOARD.length()];
         for(int i = 0; i < arrayOfGuitarStrings.length; i++) {
            double frequency = 440 * Math.pow(2, ((i - 24) / 12.0));
            arrayOfGuitarStrings[i] = new GuitarString(frequency);
         }
         numTicTimes = 0;                                      
   }
    
    // takes an int as the parameter,
    // which is the pitch. The given
    // pitch finds the corresponding guitar string and plucks it. 
    public void playNote(int pitch) {
        int indexString = pitch + 24;
        if(!(indexString < 0 || indexString >= KEYBOARD.length())) {    
            arrayOfGuitarStrings[indexString].pluck();
        }
    }
    
    // takes a char as a parameter.
    // checks if the parameter is a legal
    // string for the guitar (one of 
    // the 37 keys it can play).
    public boolean hasString(char string) {
        return (KEYBOARD.indexOf(string) != -1);
    }
    
    // takes a char as parameter which is the
    // guitar string. If the parameter, is
    // not in the range of the 37 keys it
    // can play, it will throw an IllegalArgumentException.
    // Otherwise, it will call the pluck() method of
    // the corresponding guitar string.  
    public void pluck(char string) {
      int index = KEYBOARD.indexOf(string);
      if(index != -1) {
         arrayOfGuitarStrings[index].pluck();   
      } else {
         throw new IllegalArgumentException("The key provided is not one of the 37 keys it can play!");
      }           
    }

    // returns a double which is the sum of all 
    // the guitar strings. 
    public double sample() {
        double sum = 0.0; 
        for(int i = 0; i < arrayOfGuitarStrings.length; i++) {
         sum = sum + arrayOfGuitarStrings[i].sample(); 
        }
        return sum;
     }
   
   // calls the tic method on all the guitar strings
   // of this guitar. 
    public void tic() {
      numTicTimes++;
      for(int i = 0; i < arrayOfGuitarStrings.length; i++) {
         arrayOfGuitarStrings[i].tic();
      }  
    }
   
   // returns the number of the times the tic() method 
   // is called 
    public int time() {
        return numTicTimes;  
    }
}
