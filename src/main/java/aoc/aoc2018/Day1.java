package aoc.aoc2018;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Day1 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s;

        List<Integer> list = new ArrayList<>();
        while( (s = br.readLine()) != null){
            list.add(Integer.valueOf(s));
        }


        Set<Integer> set = new HashSet<Integer>();
        int sum = 0;
        int i = 0;
        while( set.add(sum)){
            int number = list.get(i);
            sum += number;

            i++;
            if(i == list.size()) i = 0;

        }


        System.out.println(sum);

    }
}
