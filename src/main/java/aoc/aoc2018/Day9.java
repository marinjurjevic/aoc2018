package aoc.aoc2018;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Day9 {

    public static final int PLAYERS_NUM = 403;
    
    public static final int MARBLES_NUM = (71920*100 + 1);
    
    public static long[] scores;
    
    private static List<Integer> list;

    private static CircleDeque<Integer> circle;

    public static void main(String[] args) throws IOException {
        list = new LinkedList<>();
        circle = new Day9.CircleDeque<>();

        scores = new long[PLAYERS_NUM];

        // 0 is initially in the circle
        list.add(0);
        circle.add(0);
        
        for(int i = 1; i < MARBLES_NUM; i++)
        {
            if(i % 23 == 0)
            {
                circle.rotate(-7);
                scores[i % PLAYERS_NUM] += (i + circle.pop());

            }
            else
            {
                circle.rotate(2);
                circle.addLast(i);
            }
        }
        
        long max_score = 0;
        for(long s : scores)
        {
            if(s > max_score)
            {
                max_score = s;
            }
        }

        System.out.println("Max score: " + max_score);
    }

    static class CircleDeque<T> extends ArrayDeque<T> {
        void rotate(int num) {
            if (num == 0) return;
            if (num > 0) {
                for (int i = 0; i < num; i++) {
                    T t = this.removeLast();
                    this.addFirst(t);
                }
            } else {
                for (int i = 0; i < Math.abs(num) - 1; i++) {
                    T t = this.remove();
                    this.addLast(t);
                }
            }

        }
    }

}
