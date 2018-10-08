/* Class GuitarString is used to model a
vibrating guitar string by keeping track of a ring buffer. */

import java.util.*;

public class GuitarString {
   public static final double ENERGY_DECAY_FACTOR = 0.996;
   
   private int N;
   private Queue<Double> ringBuffer;
   
   // Takes a double for the frequency as the parameter.
   // Creates a ring buffer which is a guitar string at rest.
   // The size of the ring buffer is N which is StdAudio.SAMPLE_RATE divided by frequency.
   // The ring buffer is filled with N number of 0s.
   // If the frequency passed is less than 0 or the size of the ring buffer
   // is less than 2, then an illegal argument exception is thrown.
   public GuitarString(double frequency) {
      N = (int)Math.round(StdAudio.SAMPLE_RATE / frequency);
      if(frequency <= 0 || N < 2) {
         throw new IllegalArgumentException("The frequency is less than or equal to 0" +
         " or the size of the ring buffer is less than" +
         " or equal to 2.");
      }
      ringBuffer = new LinkedList<Double>();
      for(int i = 0; i < N; i++) {
         ringBuffer.add(0.0);
      }
   }
   
   // Takes a double as array as a parameter.
   // If the array parameter has less than 2 elements,
   // an IllegalArgumentException is thrown.
   // If the exception is not thrown,
   // the contents of the ring buffer are the contents of
   // the array parameter.
   public GuitarString(double[] init) {
      ringBuffer = new LinkedList<Double>();
      if(init.length < 2) {
         throw new IllegalArgumentException("There are fewer than two elements in the given array!");
      }
      for(int i = 0; i < init.length; i++) {
         ringBuffer.add(init[i]);
      }
   }
   
   // Replaces all the elements in the ring buffer
   // with N number of random elements between - 0.5
   // and 0.5
   public void pluck() {
      while(!ringBuffer.isEmpty()) {
         ringBuffer.remove();
      }
      Random r = new Random();
      for(int i = 0; i < N; i++) {
         ringBuffer.add(-0.5 + (r.nextDouble()* (0.5 - (-0.5))));
      }
   }
   
   // Removes the sample at the beginning of the ring buffer.
   // At the end of the ring buffer, an element is added
   // which is the average of the two samples at the beginning
   // of the ring buffer multiplied by the energy decay factor.
   public void tic() {
      ringBuffer.add((ringBuffer.remove()+ ringBuffer.peek())/2 * ENERGY_DECAY_FACTOR);
   }
   
   // Returns a double value which is the sample at the
   // front of the ring buffer.
   public double sample() {
      return ringBuffer.peek();
   }
}