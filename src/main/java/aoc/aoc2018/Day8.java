package aoc.aoc2018;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;

public class Day8 {
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String input = br.readLine();
        //String input = "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2";

        parseTree(input);
    }

    public static void parseTree(String input)
    {
        int[] numbers = Arrays.stream(input.split(" ")).mapToInt(Integer::parseInt).toArray();

        Stack<Node> node_stack = new Stack<>();
        Stack<Integer> processed_stack = new Stack<>();
        
        // Init all helper vars
        int i = 2;
        node_stack.push(new Node(numbers[0], numbers[1]));
        processed_stack.push(0);
        int sum = 0;
        
        while(i < numbers.length)
        {
            int counter = processed_stack.pop();
            if(counter == node_stack.peek().children)
            {
                // we processed all children

                Node node = node_stack.pop();
                node.metadata = new int[node.metadata_size];
                System.arraycopy(numbers, i, node.metadata, 0, node.metadata_size);
                
                i += node.metadata_size;
                sum += Arrays.stream(node.metadata).sum();
                continue;
            }

            int node_children = numbers[i++];
            int metadata_size = numbers[i++];
            Node node = new Node(node_children, metadata_size);

            processed_stack.push(counter + 1);

            if(node_children == 0)
            {
                node.metadata = new int[metadata_size];
                System.arraycopy(numbers, i, node.metadata, 0, metadata_size);

                // add this node to graph and connect with parent

                i += metadata_size;
                sum += Arrays.stream(node.metadata).sum();
            }
            else
            {
                node_stack.push(node);
                processed_stack.push(0);
            }

        }
        
        System.out.println(sum);
    }

    static class Node {
        public int children;
        public int metadata_size;
        public int[] metadata;

        Node(int children, int metadata_size)
        {
            this.children = children;
            this.metadata_size = metadata_size;
        }

        @Override
        public String toString() {
            return String.format("Node (%d,%d)", children, metadata_size);
        }
    }
}
