package aoc.aoc2018;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.jgrapht.alg.util.Pair;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

public class Day7b {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s;

        SimpleDirectedGraph<Character, DefaultEdge> sdg = 
            new SimpleDirectedGraph<>(DefaultEdge.class);

        while( (s = br.readLine()) != null){
            parseStep(s, sdg);
        }
        
        Iterator<Character> iterator = new TopologicalOrderIterator<>(sdg, 
            Comparator.naturalOrder());

        List<Pair<Character, Integer>> nodes = new LinkedList<>();
        List<Character> filter;
        
        int t = 0;

        while(!sdg.vertexSet().isEmpty())
        {
            System.out.println("Workers: " + nodes);
            if(nodes.size() < 5)
            {
                filter = sdg.vertexSet().stream().filter(node -> sdg.incomingEdgesOf(node).size() == 0)
                                .sorted(Comparator.naturalOrder())
                                .collect(Collectors.toList());
                
                System.out.println("Filter: " + filter);
                while(nodes.size() < 5 && !filter.isEmpty())
                {
                    Character c = filter.remove(0);
                    if(!nodes.stream().filter(p -> p.getFirst() == c).findAny().isPresent())
                    {
                        nodes.add(new Pair<Character, Integer>(c, getTime(c)));
                        System.out.println("Added " + c + " = " + getTime(c));
                    }
                    else
                    {
                        System.out.println(c + " already present");
                    }
                    
                }

            }

            // 1 time tick
            List<Character> removeList = new LinkedList<>();
            for(Pair<Character,Integer> p : nodes)
            {
                p.setSecond(p.getSecond() - 1);
                if(p.getSecond() == 0)
                {
                    removeList.add(p.getFirst());
                }
            }
            
            if(!removeList.isEmpty())
            {
                System.out.println("Removing " + removeList);

                nodes.removeIf(node -> removeList.contains(node.getFirst()));
                sdg.removeAllVertices(removeList);
            }
            
            t++;
        }

        System.out.println("Total time " + t);
    }

    public static int getTime(Character c)
    {
        return (int)c - 64 + 60;
    }

    public static void parseStep(
        String inputLine,
        SimpleDirectedGraph<Character, DefaultEdge> graph)
    {
        Pattern pattern = 
            Pattern.compile("Step ([A-Z]) must be finished before step ([A-Z]) can begin\\.");

        Matcher matcher = pattern.matcher(inputLine);

        if(matcher.find())
        {
            Character source = matcher.group(1).charAt(0);
            Character target = matcher.group(2).charAt(0);
            graph.addVertex(source);
            graph.addVertex(target);
            graph.addEdge(source, target);
        }
    }
}
