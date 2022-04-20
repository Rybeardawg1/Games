import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Wordle {

   public static HashMap<String, String> wordMap = new HashMap<String, String>();
   public static HashMap<Integer, String> wordMap2 = new HashMap<Integer, String>();
   public static HashMap<String, String> wordExists = new HashMap<String, String>();

   public static void main(String[] args) throws InterruptedException {
      Wordle wordle = new Wordle();
      wordle.playGame();
   }

   public void playGame() throws InterruptedException {
      boolean done = false;
      int guesses = 1;
      Wordle wordle = new Wordle();
      String word = wordle.wordSelection();
      System.out.print("WORDLE\nFive Letter Words Only");
      String[] guess = new String[] { "", "First", "Second", "Third", "Fourth", "Fifth" };
      while (!done) {
         if (guesses == 6) {
            losingSequence(word);
         }
         System.out.print("\n" + guess[guesses] + " Guess: ");
         String input = getInput().toUpperCase();
         while (input.length() != 5 || !wordExists.containsValue(input)) {
            System.out.println("Must be a real 5 letter word!");
            System.out.print(guess[guesses] + " Guess: ");
            input = getInput().toUpperCase();
         }
         guesses++;
         if (input.equals(word)) {
            done = true;
            break;
         }
         similarities(word, input);
      }
      winningSequence();
   }

   public static String getInput() {
      Scanner scanner = new Scanner(System.in);
      return scanner.nextLine();
   }

   public static void similarities(String word, String input) throws InterruptedException {
      boolean check = false;

      HashMap<Character, Character> charMapInput = new HashMap<Character, Character>();
      HashMap<Character, Character> charMapWord = new HashMap<Character, Character>();

      for (int i = 0; i < 5; i++) {
         charMapInput.put(input.charAt(i), input.charAt(i));
         charMapWord.put(word.charAt(i), word.charAt(i));
      }

      String message = "Correct letters and correct position: ";
      char[] message1 = message.toCharArray();
      for (int i = 0; i < message1.length; i++) {
         System.out.print(message1[i]);
         Thread.sleep(40);
      }

      ArrayList ArrayListFormat = new ArrayList();
      for (int i = 0; i < 5; i++) {
         if (word.charAt(i) == input.charAt(i)) {
            check = true;
            ArrayListFormat.add(input.charAt(i));
         }
      }

      if (!check) {
         System.out.println("None");
      } else {
         System.out.println(ArrayListFormat);
      }

      check = false;
      message = "Correct letters but wrong position: ";
      message1 = message.toCharArray();
      for (int i = 0; i < message1.length; i++) {
         System.out.print(message1[i]);
         Thread.sleep(40);
      }

      ArrayList ArrayListFormat1 = new ArrayList();
      for (char i : charMapInput.keySet()) {
         if (charMapWord.containsKey(i)) {
            check = true;
            ArrayListFormat1.add(i);
         }
      }
      
      int[] duplicates = new int[177];
      for (int i = 0; i < word.length(); i++) {
         for (int z = 0; z < word.length(); z++) {
            if(word.charAt(i) == word.charAt(z)) {
               duplicates[(int)word.charAt(i)]++;
            }
         }
      }

      if (!check) {
         System.out.print("None");
      } else {
         for (int i = 0; i < ArrayListFormat.size(); i++) {
            for (int z = 0; z < ArrayListFormat1.size(); z++) {
               if (ArrayListFormat.get(i) == ArrayListFormat1.get(z)) {
                  ArrayListFormat1.remove(z);
               }
            }
         }
         for(int i = 0; i < duplicates.length; i++) {
            if(duplicates[i] > 1) {
               ArrayListFormat1.add((char)i);
            }
         }
         System.out.print(ArrayListFormat1);
      }
   }

   public static void losingSequence(String word) throws InterruptedException {
      System.out.print("\nYOU LOSE");
      for (int i = 0; i < 60; i++) {
         Thread.sleep(25);
         System.out.print("E");
      }
      System.out.println("\nAnswer: " + word);
      System.exit(0);
   }

   public static void winningSequence() throws InterruptedException {
      System.out.print("A");
      for (int i = 0; i < 20; i++) {
         Thread.sleep(25);
         System.out.print("Y");
      }
      for (int i = 0; i < 40; i++) {
         Thread.sleep(25);
         System.out.print("O");
      }
      System.out.print("\nYOU WON");
   }

   public String wordSelection() {
      try {

         File file2 = new File("UnReasonableWordList.txt");
         Scanner scanner2 = new Scanner(file2);
         for (int i = 0; i < 12947; i++) {
            String data = scanner2.nextLine().toUpperCase();
            wordExists.put(data, data);
         }
         
         File file = new File("ReasonableWordList.txt");
         Scanner scanner = new Scanner(file);
         for (int i = 0; i < 2499; i++) {
            String data = scanner.nextLine().toUpperCase();
            wordMap.put(data, data);
            wordMap2.put(i, data);
         }
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      }
      return wordMap2.get((int) (Math.random() * 2499));
   }
}