// Class AssassinManager can be used for a
// client to play a game of assassin and
// manage the different aspects of the game.
// In the game of Assassin, each person is targeting
// someone to kill. Each player only knows who they
// are trying to kill, but don't know who's trying
// to kill them.

import java.util.*;

public class AssassinManager {
   private AssassinNode frontKillRing;
   private AssassinNode frontGraveyardList;
   
   // Constructor for the AssassinManager class.
   // Takes a list as parameter and adds the names
   // in the list in the kill ring in the same
   // order as it's in in the list. If the list passed
   // is empty, an IllegalArgumentException is thrown.
   public AssassinManager(List<String> names) {
      frontKillRing = null;
      frontGraveyardList = null;
      AssassinNode node;
      if(names.size() == 0) {
         throw new IllegalArgumentException("The list is empty!");
      }
      for(int i = names.size() - 1; i >= 0; i--) {
         if (frontKillRing == null) {
            node = new AssassinNode(names.get(i));
         } else {
            node = new AssassinNode(names.get(i), frontKillRing);
         }
         frontKillRing = node;
      }
   }
   
   // Prints the name of the peopole in the kill ring
   // If the kill ring is empty, nothing is printed
   // (there is no output).
   public void printKillRing() {
      AssassinNode current = frontKillRing;
      while(current.next != null) {
         System.out.println("    " + current.name + " is stalking " + current.next.name);
         current = current.next;
      }
      System.out.println("    " + current.name + " is stalking " + frontKillRing.name);
   }
   
   // Prints the name of the people in the graveyard.
   // The person who was most recently killed is printed first,
   // and the person who was killed first is printed last.
   // If the graveyard list is empty, nothing is printed (no output).
   public void printGraveyard() {
      AssassinNode current = frontGraveyardList;
      while(current != null) {
         System.out.println("    " + current.name + " was killed by " + current.killer);
         current = current.next;
      }
   }
   
   // Takes a string that is a name to check for in
   // the kill ring. Returns a boolean value (true/ false).
   // If the kill ring contains the passed string name while
   // ignoring case, then the method returns true. If not,
   // it returns false.
   public boolean killRingContains(String name) {
      AssassinNode current = frontKillRing;
      while(current != null) {
         if(current.name.equalsIgnoreCase(name)) {
            return true;
         }
         current = current.next;
      }
      return false;
   }
   
   // Takes a string that is a name to check for in
   // the graveyard. Returns a boolean value (true/ false).
   // If the graveyard contains the passed string name while
   // ignoring case, then the method returns true. If not,
   // it returns false.
   public boolean graveyardContains(String name) {
      AssassinNode current = frontGraveyardList;
      while(current != null) {
         if(current.name.equalsIgnoreCase(name)) {
            return true;
         }
         current = current.next;
      }
      return false;
   }
   
   // Method returns a boolean value depending
   // on whether the game is over or not. A game
   // is over if there is only one person in the kill
   // ring, and it is not over if there are more than
   // one persons in the kill ring. Returns true if
   // game is over, and returns false if game is not over.
   public boolean gameOver() {
      AssassinNode current = frontKillRing;
      if(current.next == null) {
         return true;
      }
      return false;
   }
   
   // This method returns a string which is the winner
   // of the game. The winner is the one person left in
   // the kill ring.
   public String winner() {
      AssassinNode current = frontKillRing;
      if(current.next == null) {
         return current.name;
      } else {
         return null;
      }
   }
   
   // Takes a string as parameter which is
   // the name of the person being killed.
   // Once the person is killed, they are moved from the kill ring
   // to the graveyard. If the name passed
   //  is not in the kill ring, an IllegalArgumentException is thrown.
   // If the game is over (only one person in kill ring), then an
   // IllegalArgumentException is also thrown. For checking if
   // the name exists in the kill ring, cases are ignored.
   public void kill(String name) {
      if(gameOver()){
         throw new IllegalArgumentException("The game is over");
      }
      boolean nameExists = false;
      AssassinNode current = frontKillRing;
      AssassinNode previous = null;
      if(current.name.equalsIgnoreCase(name)) {
         nameExists = true;
         AssassinNode temp = current.next;
         while(temp.next != null) {
            temp = temp.next;
         }
         current.killer = temp.name;
         frontKillRing = current.next;
         current.next = frontGraveyardList;
         frontGraveyardList = current;
      } else {
         while(current != null && !nameExists) {
            if(current.name.equalsIgnoreCase(name)) {
               nameExists = true;
               current.killer = previous.name;
               previous.next = current.next;
               current.next = frontGraveyardList;
               frontGraveyardList = current;
            }
            previous = current;
            current = current.next;
         }
      }
      if(!nameExists) {
         throw new IllegalArgumentException("The string passed does not exist in the kill ring!");
      }
   }
}