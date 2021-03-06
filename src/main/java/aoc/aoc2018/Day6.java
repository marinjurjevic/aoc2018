package aoc.aoc2018;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class Day6 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s;
        
        // find maximum values for bounding box 
        int x_max = 0;
        int y_max = 0;

        // read input data
        List<Point> points = new LinkedList<>();
        while( (s = br.readLine()) != null){
            String[] chunks = s.split(", ");
            int x = Integer.valueOf(chunks[0]);
            int y = Integer.valueOf(chunks[1]);
            
            x_max = Math.max(x, x_max);
            y_max = Math.max(y, y_max);

            points.add(new Point(x,y));
        }


        int n = points.size();
        Point box = new Point(x_max, y_max);
        System.out.println("Bounding box: " + box + " n = " + n);

        Point[] sites = new Point[n];
        int[] sitesCounter = new int[n];
        points.toArray(sites);

        for(int i = 0; i < x_max; i++)
        {
            for(int j = 0; j < y_max; j++)
            {
                Point test = new Point(i,j);
                int min_dist = Integer.MAX_VALUE;
                int min_index = 0;
                for(int k = 0; k < n; k++)
                {
                    int test_dist = Point.distance(test, sites[k]);
                    if(test_dist < min_dist)
                    {
                        min_dist = test_dist;
                        min_index = k;
                    }else if (test_dist == min_dist)
                    {
                        min_index = -1;
                    }
                }

                if(min_index != -1)
                {
                    if(i == 0 || i == x_max - 1 || j == 0 || j == y_max -1)
                    {
                        sitesCounter[min_index] = -1;
                    }
                    
                    if(sitesCounter[min_index] != -1)
                    {
                        sitesCounter[min_index]++;
                    }

                }
            }

        }
        
        int maxArea = 0;
        for(int i = 0; i < n; i++)
        {
            if(sitesCounter[i] > maxArea)
            {
                maxArea = sitesCounter[i];
            }
            System.out.println(i + " = " + sitesCounter[i]);
        }
        System.out.println("Max area: " + maxArea);

    }

    static class Point {
        int x;
        int y;

        Point(int x, int y){
            this.x = x;
            this.y = y;
        }

        public String toString(){
            return "[" + this.x + "," + this.y + "]";
        }

        public static int distance(Point p1, Point p2)
        {
            // use Manhattan distance
            return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
        }
    }
}
