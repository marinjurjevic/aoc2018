package aoc.aoc2018;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Day2 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s;

        int sum2 = 0;
        int sum3 = 0;

        int counters[] = new int[26];
        while( (s = br.readLine()) != null){
            char line[] = s.toCharArray();
            for(int i = 0; i < line.length; i++){
                char c = line[i];
                counters[c - 'a']++;
            }


            for(int i = 0; i < 26; i++){
                if(counters[i] == 2){
                    sum2++;
                    break;
                }
            }

            for(int i = 0; i < 26; i++){
                if(counters[i] == 3){
                    sum3++;
                    break;
                }
            }

            resetCounter(counters);
        }


        System.out.println(sum2 * sum3);
    }

    public static void resetCounter(int array[]){
        for(int i = 0; i < array.length; i++){
            array[i] = 0;
        }
    }

    public static void printCounter(int array[]){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < array.length; i++){
            sb.append(array[i]);
            sb.append(' ');
        }
        System.out.println(sb.toString());
    }
}
