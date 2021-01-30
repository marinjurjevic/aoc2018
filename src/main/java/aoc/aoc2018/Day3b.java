package aoc.aoc2018;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Day3b {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s;

        int matrix[][] = new int[1000][1000];

        while( (s = br.readLine()) != null){
            String[] chunks = s.split(" ");

            String[] coord = chunks[2].split(",");
            int y = Integer.valueOf(coord[0]);
            int x = Integer.valueOf(coord[1].substring(0,coord[1].length() - 1));

            String[] size = chunks[3].split("x");
            int dy = Integer.valueOf(size[0]);
            int dx = Integer.valueOf(size[1]);

            for(int i = x, n = x + dx; i < n; i++){
                for(int j = y, m = y + dy; j < m; j++){
                    matrix[i][j]++;
                }
            }
        }

        br = new BufferedReader(new FileReader("input/day3.in"));
        while( (s = br.readLine()) != null){
            String[] chunks = s.split(" ");

            String[] coord = chunks[2].split(",");
            int y = Integer.valueOf(coord[0]);
            int x = Integer.valueOf(coord[1].substring(0,coord[1].length() - 1));

            String[] size = chunks[3].split("x");
            int dy = Integer.valueOf(size[0]);
            int dx = Integer.valueOf(size[1]);

            boolean flag = true;
            outer:
            for(int i = x, n = x + dx; i < n; i++){
                for(int j = y, m = y + dy; j < m; j++){
                    if(matrix[i][j] > 1){
                        flag = false;
                        break outer;
                    }
                }
            }

            if(flag){
                System.out.println(s);
            }
        }
        br.close();
    }

}
