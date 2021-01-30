package aoc.aoc2018;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Day5 {

  private static Stack<Character> stack;

  public static void main(String[] args) throws IOException {
    stack = new Stack<>();
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    int read;
    char chr, chr_top;
    while ((read = br.read()) != -1) {
      chr = (char) read;

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

    System.out.println("Stack size: " + stack.size());
  }

}
