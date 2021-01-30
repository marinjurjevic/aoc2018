package aoc.aoc2018;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Day2b {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s;

        Set<String> set = new HashSet<>();

        outer:
        while( (s = br.readLine()) != null){
            for(int i = 0; i < s.length(); i++){
                String newString = s.substring(0,i) + '*' + s.substring(i+1);
                if( !set.add(newString) ){
                    System.out.println(newString.replace("*",""));
                    break outer;
                }
            }
        }


    }

}
