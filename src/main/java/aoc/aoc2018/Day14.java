package aoc.aoc2018;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day14 {

    private static final int RECIPE_NUM = 293801;

    private static List<Integer> recipes; 

    private static int pos1;

    private static int pos2;
    
    // state for mini fsm
    static int state = 0;

    static final int[] events = {2, 9, 3, 8, 0 ,1};
    //static final int[] events = {5,9,4,1,4};

    public static void main(String[] args) throws IOException {
        recipes  = new ArrayList<>();
        ((ArrayList<Integer>)recipes).ensureCapacity(600000);;

        pos1 = 0;
        pos2 = 1;
        
        recipes.add(3);
        recipes.add(7);

        /* Iterate RECIPE_NUM + 8 because two recipe are known beforehand */
        //part1();
        while(true)
        {
            iterate();
        }
    }

    private static void part1() {
        for(int i = 0; i < RECIPE_NUM + 8; i++)
        {
            iterate();
        }

        for(int r = RECIPE_NUM; r < RECIPE_NUM + 10; r++)
        {
            System.out.print(recipes.get(r));
        }
        System.out.println();
    }

    public static void printRecipes()
    {
        for(int i = 0; i < recipes.size(); i++)
        {
            if(i == pos1)
            {
                System.out.print(String.format("(%d) ", recipes.get(pos1)));
            }
            else if(i == pos2)
            {
                System.out.print(String.format("[%d] ", recipes.get(pos2)));
            }
            else
            {
                System.out.print(String.format("%d ", recipes.get(i)));
            }
        }
        System.out.println();
    }

    public static void iterate()
    {
        // add new recipe(s)
        int sum = recipes.get(pos1) + recipes.get(pos2);

        if(sum >= 10)
        {
            recipes.add(sum/10);
            checkRecipe();
            recipes.add(sum%10);
            checkRecipe();
        }
        else
        {
            recipes.add(sum);
            checkRecipe();
        }

        pos1 = movePosition(pos1);
        pos2 = movePosition(pos2);
    }

    public static int movePosition(int pos)
    {
        int new_pos;
        int delta = recipes.get(pos) + 1;

        if(pos + delta >= recipes.size())
        {
            new_pos = (pos + delta) % recipes.size();
        }
        else
        {
            new_pos = pos + delta;
        }

        return new_pos;
    }

    public static void checkRecipe()
    {
        int size = recipes.size();
        if(events[state] == recipes.get(size-1))
        {
            state++;
        }
        else
        {
            state = 0;
        }

        if(state == 6)
        {
            System.out.println("We're done!");
            System.out.println(size - 6);
            System.exit(-1);
        }
    }
}
