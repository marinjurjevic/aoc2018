package aoc.aoc2018;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Day13 {

    private static List<CartLocation> cart_locations;
    private static List<CartLocation> broken_cart_locations;

    public static final int MAP_WIDTH = 151;
    public static final int MAP_HEIGHT = 151;

    private static int counter = 0;
    private static char[][] map;

    public static void main(String[] args) {
        cart_locations = new LinkedList<>();
        broken_cart_locations = new LinkedList<>();

        map = new char [MAP_WIDTH][MAP_HEIGHT];
        
        try {
            parseInput();
        } catch (Exception e) {
        }
        
        while(cart_locations.size() > 1)
        {
            Collections.sort(cart_locations);

            for(CartLocation cart : cart_locations)
            {
                if(broken_cart_locations.contains(cart))
                {
                    continue;
                }
                cart.step();
            }

            cart_locations.removeAll(broken_cart_locations);
            broken_cart_locations.clear();
    
            counter++;
        }          

        printLocations();
    }

    public static void printMap(int from, int to)
    {
        for(int i = from; i <= to; i++)
        {
            System.out.println("" + new String(map[i]));
        }

    }

    public static void parseInput() throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        /* Parse map */
        String line;
        int row = 0;
        while((line = br.readLine()) != null)
        {
            char[] row_data = line.toCharArray();
            for(int i = 0; i < row_data.length; i++)
            {
                if(row_data[i] == '^' || row_data[i] == 'v'
                    || row_data[i] == '>' || row_data[i] == '<')
                {
                    cart_locations.add(new CartLocation(row, i, row_data[i]));
                }
            }

            // Copy to map array
            for(int i = 0; i < line.length(); i++)
            {
                map[row][i] = line.charAt(i);
            }
            map[row][line.length()]=0;
            row++;
        }
    }

    public static void printLocations()
    {
        if(cart_locations.isEmpty())
        {
            System.out.println("No carts survived!");
            return;
        }

        for(CartLocation cl : cart_locations)
        {
            System.out.println(cl);
        }
    }

    static class CartLocation implements Comparable<CartLocation>
    {
        static char[] positions = {'^', '>', 'v', '<'};
        static int instance_num = 0;

        int x_pos;
        int y_pos;
        char dir;
        char turn_dir;
        char map_char;
        int instance;

        CartLocation(CartLocation cl)
        {
            this.x_pos = cl.x_pos;
            this.y_pos = cl.y_pos;
            this.dir = cl.dir;
            this.turn_dir = cl.turn_dir;
            this.map_char = cl.map_char;
            this.instance = cl.instance;
        }

        CartLocation(int x_pos, int y_pos, char dir)
        {
            instance = CartLocation.instance_num;
            CartLocation.instance_num++;
            
            this.x_pos = x_pos;
            this.y_pos = y_pos;
            this.dir = dir;
            this.turn_dir = 'L';

            if(this.dir == '^' || this.dir == 'v')
            {
                this.map_char = '|';
            }
            else
            {
                this.map_char = '-';
            }
        }

        public char next_turn(int pos)
        {
            int next_pos = pos;
            if(this.turn_dir == 'L')
            {
                next_pos--;
            }
            else if(this.turn_dir == 'S')
            {
                // dir stays the same
            }
            else
            {
                next_pos++;
            }

            if(next_pos == -1) next_pos = 3;
            if(next_pos == 4) next_pos = 0;

            return positions[next_pos];
        }

        public void restoreMap()
        {

            System.out.println(String.format("Restoring map sign on [%d,%d] -> %c",
                 this.x_pos, this.y_pos, this.map_char));
            map[this.x_pos][this.y_pos] = this.map_char;
        }

        public void step()
        {
            int new_x = this.x_pos;
            int new_y = this.y_pos;
            char new_dir = this.dir;
    
            switch(this.dir)
            {
                case '^':
                    new_x--;
                    new_dir = next_turn(0);
                    break;
                
                case 'v':
                    new_x++;
                    new_dir = next_turn(2);
                    break;
                
                case '>':
                    new_y++;
                    new_dir = next_turn(1);
                    break;
                
                case '<':
                    new_y--;
                    new_dir = next_turn(3);
                    break;
                
                default:
                    System.out.println("ILLEGAL DIRECTION");
                    break;
            }

            switch(map[new_x][new_y])
            {
                case '+':   // intersection
                    // use calculated new_dir
                    this.dir = new_dir;
                    if(this.turn_dir == 'L')
                    {
                        this.turn_dir = 'S';
                    }
                    else if(this.turn_dir == 'S')
                    {
                        this.turn_dir = 'R';
                    }
                    else
                    {
                        this.turn_dir = 'L';
                    }
                break;

                case '^':
                case 'v':
                case '>':
                case '<':
                    System.out.println(String.format(
                        "%d CRASH ON [%d,%d] %c by cart %s", counter,
                        new_x, new_y, map[new_x][new_y], this));

                    // Cart crashed -> remove them instantly and restore map
                    this.restoreMap();
                    broken_cart_locations.add(this);
    
                    CartLocation crashed = new CartLocation(new_x,new_y,map[new_x][new_y]);       
                    
                    for(CartLocation cart : cart_locations)
                    {
                        if(cart.equals(crashed))
                        {
                            cart.restoreMap();
                            broken_cart_locations.add(cart);
                        }
                    }

                    return;
                
                case '/':
                    switch(this.dir)
                    {
                        case '^':
                            this.dir = '>';
                            break;

                        case 'v':
                            this.dir = '<';
                            break;
                        
                        case '>':
                            this.dir = '^';
                            break;
                        
                        case '<':
                            this.dir = 'v';
                            break;
                        
                        default:
                            break;
                    }
                    break;
    
                case '\\':
                    switch(this.dir)
                    {
                        case '^':
                            this.dir = '<';
                            break;

                        case 'v':
                            this.dir = '>';
                            break;
                        
                        case '>':
                            this.dir = 'v';
                            break;
                        
                        case '<':
                            this.dir = '^';
                            break;
                        
                        default:
                            break;
                    }
                    break;
                
                case '|':
                case '-':
                    break;

                default:
                    break;
            }
            
            // restore map char
            map[this.x_pos][this.y_pos] = this.map_char;

            // store new map char
            this.map_char = map[new_x][new_y];

            // update new coordinates
            this.x_pos = new_x;
            this.y_pos = new_y;

            // update cart location on map
            map[new_x][new_y] = this.dir;
        }

        @Override
        public String toString() {
            return String.format("#%d [%d,%d,%c] %c", 
                this.instance,
                this.x_pos,
                this.y_pos,
                this.turn_dir,
                this.dir
            );
        }

        @Override
        public boolean equals(Object obj) {
            if(obj == null)
            {
                return false;
            }

            if(this == obj)
            {
                return true;
            }
            
            if (getClass() != obj.getClass())
            {
                return false;
            }
                
            CartLocation cart_loc = (CartLocation)obj;

            return this.x_pos == cart_loc.x_pos && this.y_pos == cart_loc.y_pos;
        }

        @Override
        public int hashCode() {
            return Integer.valueOf(this.x_pos + this.y_pos).hashCode();
        }

        @Override
        public int compareTo(CartLocation arg0) {
            if(this.x_pos == arg0.x_pos)
            {
                return this.y_pos - arg0.y_pos;
            }

            return this.x_pos - arg0.x_pos;
        }
    }

}

