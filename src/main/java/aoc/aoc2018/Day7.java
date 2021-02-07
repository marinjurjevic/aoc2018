package aoc.aoc2018;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

public class Day7 {

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
        while (iterator.hasNext()) {
            Character node = iterator.next();
            System.out.print(node);
        }
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
