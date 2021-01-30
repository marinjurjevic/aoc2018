package aoc.aoc2018;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Day5b {

  private static Stack<Character> stack;

  public static void main(String[] args) throws IOException {
    stack = new Stack<>();
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String polymer = br.readLine();

    for (char l = 'a'; l < 'z'; l++) {
      String filtered = polymer.replaceAll(l + "|" + Character.toUpperCase(l), "");

      System.out.println("[" + l + "] Stack size: " + startReaction(filtered));
    }

  }

  /**
   * Performs polymer reaction and returns final polymer length
   * 
   * @param polymer polymer for test
   * @return length after reaction
   */
  private static int startReaction(String polymer) {
    stack = new Stack<>();

    char chr, chr_top;
    for (int i = 0; i < polymer.length(); i++) {
      chr = (char) polymer.charAt(i);

      if (!stack.isEmpty()) {
        chr_top = stack.peek();
        if (chr_top != chr && (Character.toUpperCase(chr_top) == Character.toUpperCase(chr))) {
          stack.pop();
        } else {
          stack.push(chr);
        }
      } else {
        stack.push(chr);
      }
    }

    return stack.size();
  }
}
