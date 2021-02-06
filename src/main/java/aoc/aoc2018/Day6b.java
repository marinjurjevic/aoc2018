package aoc.aoc2018;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class Day6b {
    public static final int THRESHOLD_DISTANCE = 10000;

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
        Point[] sites = new Point[n];
        points.toArray(sites);
        
        int safeArea = 0;

        for(int i = 0; i < x_max; i++)
        {
            for(int j = 0; j < y_max; j++)
            {
                Point test = new Point(i,j);
                int test_distance_sum = 0;
                for(int k = 0; k < n; k++)
                {
                    test_distance_sum += Point.distance(test, sites[k]);
                }

                if(test_distance_sum < THRESHOLD_DISTANCE)
                {
                    safeArea++;
                }

            }

        }

        System.out.println("Safe area: " + safeArea);
        

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
