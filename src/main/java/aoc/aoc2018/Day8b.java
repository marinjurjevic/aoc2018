package aoc.aoc2018;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Day8b {
    
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
        Stack<List<Node>> node_lists = new Stack<>();
        Stack<Integer> processed_stack = new Stack<>();
        
        // Init all helper vars
        int i = 2;
        Node root = new Node(numbers[0], numbers[1]);
        node_stack.push(root);
        node_lists.push(new LinkedList<Node>());
        processed_stack.push(0);

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
                
                List<Node> children = node_lists.pop();
                for(int m : node.metadata)
                {
                    try {
                        node.value += children.get(m - 1).value;
                    } catch (IndexOutOfBoundsException e) {
                        // skip invalid index
                        continue;
                    }
                }

                continue;
            }

            int node_children = numbers[i++];
            int metadata_size = numbers[i++];
            Node node = new Node(node_children, metadata_size);
            
            // Incrase parent children counter
            processed_stack.push(counter + 1);
            node_lists.peek().add(node);

            if(node_children == 0)
            {
                node.metadata = new int[metadata_size];
                System.arraycopy(numbers, i, node.metadata, 0, metadata_size);
                
                node.value = Arrays.stream(node.metadata).sum();
                // add this node to graph and connect with parent
                i += metadata_size;
            }
            else
            {
                node_stack.push(node);
                node_lists.push(new LinkedList<Node>());
                processed_stack.push(0);
            }

        }

        System.out.println("Root Value: " + root.value);
    }

    static class Node {
        public int children;
        public int metadata_size;
        public int[] metadata;
        public int value;

        Node(int children, int metadata_size)
        {
            this.children = children;
            this.metadata_size = metadata_size;
        }

        @Override
        public String toString() {
            String text = String.format("Node (%d,%d) [", children, metadata_size);
            for(int m : this.metadata)
            {
                text += " " + m;
            }
            text += " ] | Value: " + this.value;

            return text;
        }
    }
}
